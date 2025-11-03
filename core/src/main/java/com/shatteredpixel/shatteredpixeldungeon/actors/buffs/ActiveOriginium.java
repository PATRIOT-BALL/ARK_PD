package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.PointF;

import static com.watabou.utils.Random.NormalFloat;

public class ActiveOriginium extends Buff {
    {
        type = Buff.buffType.NEGATIVE;
        announced = true;
    }

    protected float level;

    public float level(){
        return level;
    }

    private static final String LEVEL	= "level";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( LEVEL, level );

    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        level = bundle.getFloat( LEVEL );
    }

    public void set( float level ) {
        this.level = Math.max(this.level, level);
    }

    @Override
    public int icon() {
        return BuffIndicator.ACORG;
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public boolean act() {
        if (target.isAlive()) {

            level = NormalFloat((level * 0.5f) - 1, level);
            int dmg = Math.round((level * 0.2f) + (target.HP * 0.05f));
            if (dmg < 1) dmg = 1;
            if (target == Dungeon.hero) dmg += 1 + (Dungeon.hero.lvl / 5);

            dmg = Math.min(30, dmg);

            if (level > 0) {

                target.damage( dmg, this );
                if (target.sprite.visible) {
                    Splash.at( target.sprite.center(), -PointF.PI / 2, PointF.PI / 6,
                            target.sprite.blood(), Math.min( 10 * dmg / target.HT, 10 ) );
                }

                if (target == Dungeon.hero && !target.isAlive()) {
                    Dungeon.fail( getClass() );
                    GLog.n( Messages.get(this, "ondeath") );
                }

                spend( TICK );
            } else {
                detach();
            }

        } else {

            detach();

        }

        return true;
    }

    @Override
    public String heroMessage() {
        return Messages.get(this, "heromsg");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", Math.round(level));
    }
}
