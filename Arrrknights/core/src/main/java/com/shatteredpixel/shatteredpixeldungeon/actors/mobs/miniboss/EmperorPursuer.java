package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.YogFist;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.FistSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GreenCatSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class EmperorPursuer extends Mob {
    {
        spriteClass = FistSprite.Dark.class;

        HP = HT = 500;
        defenseSkill = 16;

        EXP = 50;
        maxLvl = 45;

        state = PASSIVE;

        properties.add(Property.BOSS);
        properties.add(Property.LARGE);
        immunities.add(Charm.class);
        immunities.add(Silence.class);
        immunities.add(Amok.class);
        immunities.add(Terror.class);
    }

    private int BoltCoolDown = 0;
    private int GasCoolDown = 0;
    private int BurstCoolDown = 0;
    private int BusrtTime = 0;
    private int Burstpos = -1;

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 38, 50 );
    }

    @Override
    public int attackSkill(Char target) {
        return 48;
    }

    @Override
    public int drRoll() { return Random.NormalIntRange( 0, 16 ); }

    @Override
    public void damage(int dmg, Object src) {
        // 70을 초과하는 피해는 25%로 감소시킵니다.
        if (dmg > 70) { dmg = 70 + ((dmg-70) / 4); }
        if (state == PASSIVE) state = HUNTING;
        super.damage(dmg, src);
    }

    @Override
    protected boolean doAttack( Char enemy ) {

        if (Dungeon.level.adjacent( pos, enemy.pos ) && (BoltCoolDown > 0)) {

            return super.doAttack( enemy );

        } else {

            BoltCoolDown = 3;
            if (sprite != null && (sprite.visible || enemy.sprite.visible)) {
                sprite.zap( enemy.pos );
                return false;
            } else {
                zap();
                return true;
            }
        }
    }

    public static class DarkBolt{}

    protected void zap() {
        spend( 1f );

        if (hit( this, enemy, true )) {

            enemy.damage( Random.NormalIntRange(16, 28), new EmperorPursuer.DarkBolt() );
                Buff.affect(enemy, Blindness.class, 5f);

            if (!enemy.isAlive() && enemy == Dungeon.hero) {
                Dungeon.fail( getClass() );
                GLog.n( Messages.get(Char.class, "kill", name()) );
            }

        } else {
            enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
        }

    }


    @Override
    protected boolean act() {
        return super.act();
    }
}
