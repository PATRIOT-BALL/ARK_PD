package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Necromancer;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BreakerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Mon3terSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Mon3tr extends Mob {
    {
        spriteClass = Mon3terSprite.class;

        HP = HT = 600;
        defenseSkill = 30;

        EXP = 50;
        maxLvl = 30;
        flying = true;

        state = HUNTING;

        properties.add(Property.BOSS);
        immunities.add(Charm.class);
        immunities.add(Silence.class);
        immunities.add(Amok.class);
        immunities.add(Terror.class);
        immunities.add(WandOfBlastWave.class);
    }

    private int spell = 0; // 1 def, 2 knokback, 3 atk
    private int cooldown = 0; // 1 def, 2 knokback, 3 atk

    @Override
    public float spawningWeight() {
        return 0;
    }

    public void teleportSpend(){
        spend(TICK);
    }

    @Override
    protected boolean act() {
        if (cooldown <= 0) {
            spell = Random.Int(1,4);
            switch (spell) {
                case 1:
                    GLog.w(Messages.get(Kaltsit.class, "skill1"));
                    Sample.INSTANCE.play( Assets.Sounds.SKILL_MON1 );
                    CellEmitter.get( this.pos ).burst( ShadowParticle.UP, 10 );
                    Buff.affect(this, MagicImmune.class, 3f);
                    this.beckon( Dungeon.hero.pos );
                    break;
                case 2:
                    GLog.w(Messages.get(Kaltsit.class, "skill2"));
                    Sample.INSTANCE.play( Assets.Sounds.SKILL_MON2 );
                    CellEmitter.get( this.pos ).burst( ShadowParticle.UP, 10 );
                    Buff.affect(this, Stamina.class, 3f);
                    this.beckon( Dungeon.hero.pos );
                    break;
                case 3:
                    GLog.w(Messages.get(Kaltsit.class, "skill3"));
                    Sample.INSTANCE.play( Assets.Sounds.SKILL_MON2 );
                    CellEmitter.get( this.pos ).burst( ShadowParticle.UP, 10 );
                    Buff.affect(this, FireImbue.class).set(3f);
                    this.beckon( Dungeon.hero.pos );
                    break;
            }
            cooldown = Random.Int(4,8);
            return super.act();
        }
        else cooldown -= 1;

        return super.act();
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if (spell == 2) {
            Ballistica trajectory = new Ballistica(this.pos, enemy.pos, Ballistica.STOP_TARGET);
            trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
            WandOfBlastWave.throwChar(enemy, trajectory, 3); // 넉백 효과
        }

        Sample.INSTANCE.play( Assets.Sounds.HIT_BREAK );
        return super.attackProc(enemy, damage);
    }

    @Override
    public int damageRoll() {
        if (spell == 3) return Random.NormalIntRange( 50, 75 );
        return Random.NormalIntRange( 35, 45 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 55;
    }

    @Override
    public int drRoll() {
        if (spell == 1) return Random.NormalIntRange( 16, 32 );
        return Random.NormalIntRange(0, 20);
    }

    @Override
    public void damage(int dmg, Object src) {
        if (spell == 1) dmg /= 2;

        if (dmg > 100) dmg = 100 + ((dmg-100)/10);

        super.damage(dmg, src);
    }

    private static final String SPELL	    = "spell";
    private static final String CD	    = "cooldown";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( SPELL, spell );
        bundle.put( CD, cooldown );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        spell = bundle.getInt( SPELL );
        cooldown = bundle.getInt( CD );
    }

}
