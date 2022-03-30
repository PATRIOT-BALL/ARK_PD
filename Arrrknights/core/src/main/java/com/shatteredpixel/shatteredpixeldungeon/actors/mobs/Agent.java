package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ceylon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Imp;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BreakerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.InfantrySprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Siesta_AgentSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Agent extends Mob {
    {
        spriteClass = Siesta_AgentSprite.class;

        HP = HT = 125;
        defenseSkill = 26;

        EXP = 16;
        maxLvl = 31;

        loot = Generator.Category.SKL_RND;
        lootChance = 0.1f;

        immunities.add(Charm.class);
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if (buff(Silence.class) == null) {
            if (Random.Int(3) < 1) {
                float time = 5f;
                if (Dungeon.isChallenged(Challenges.TACTICAL_UPGRADE)) time = 10f;
                Buff.affect(enemy, Hex.class, time);
                Buff.affect(enemy, Vulnerable.class, time);
            }
        }

        return super.attackProc(enemy, damage);
    }

    @Override
    public boolean attack(Char enemy) {
        if (enemy == null) return false;

        boolean visibleFight = Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[enemy.pos];

        if (enemy.isInvulnerable(getClass())) {

            if (visibleFight) {
                enemy.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "invulnerable"));

                Sample.INSTANCE.play(Assets.Sounds.HIT_PARRY, 1f, Random.Float(0.96f, 1.05f));
            }

            return false;
        }
        else if (hit( this, enemy, true )) {

            int dmg = damageRoll();
            enemy.damage( dmg, this );
            enemy.sprite.bloodBurstA( sprite.center(), dmg );
            enemy.sprite.flash();

            if (Dungeon.level.heroFOV[pos]) Sample.INSTANCE.play(Assets.Sounds.HIT_SLASH, 1f, Random.Float(0.96f, 1.05f));

            if (enemy == Dungeon.hero && !enemy.isAlive()) {
                Dungeon.fail( getClass() );
            }
        } else {
            enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
        }

        if (!enemy.isAlive() && visibleFight) {
            if (enemy == Dungeon.hero) {

                Dungeon.fail( getClass() );
                GLog.n( Messages.capitalize(Messages.get(Char.class, "kill", name())) );
            }
        }

        return true;

    }

    @Override
    public int damageRoll() {
        if (buff(Silence.class) != null) return Random.NormalIntRange( 14, 26 );
        else return Random.NormalIntRange( 22, 31 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 42;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 20);
    }


    @Override
    public void rollToDropLoot() {
        Ceylon.Quest.process( this );

        super.rollToDropLoot();
    }
}
