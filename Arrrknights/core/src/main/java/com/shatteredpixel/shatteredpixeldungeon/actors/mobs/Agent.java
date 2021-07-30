package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BreakerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.InfantrySprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Agent extends Mob {
    {
        spriteClass = BreakerSprite.class;

        HP = HT = 140;
        defenseSkill = 30;

        EXP = 17;
        maxLvl = 32;

        loot = Generator.Category.SKL_RND;
        lootChance = 0.1f;

        immunities.add(Charm.class);
        immunities.add(Silence.class);
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if (Random.Int(3) < 1)
        {
            Buff.affect(enemy, Hex.class, 5f);
            Buff.affect(enemy, Vulnerable.class, 5f);
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
        return Random.NormalIntRange( 25, 35 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 42;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 20);
    }
}
