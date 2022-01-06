package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Hallucination extends Buff {
    public static final float DURATION	= 10f;

    {
        type = buffType.NEGATIVE;
        announced = true;
    }

    protected float left;

    private static final String LEFT	= "left";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( LEFT, left );

    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        left = bundle.getFloat( LEFT );
    }

    public void set( float duration ) {
        this.left = duration;
    }

    @Override
    public boolean act() {
        spend(TICK);
        left -= TICK;
        if (left <= 0){
            detach();
        }

        return true;
    }

    public void proc(){
        if (Random.Int(4) == 0) target.damage(target.damageRoll() / 3, target);

        if (target instanceof Hero && !target.isAlive()) {
            Dungeon.fail( getClass() );
            GLog.n( Messages.get(this, "ondeath") );
        }
    }

    @Override
    public int icon() {
        return BuffIndicator.VERTIGO;
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (DURATION - left+1) / DURATION);
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() { return Messages.get(this, "desc"); }
}
