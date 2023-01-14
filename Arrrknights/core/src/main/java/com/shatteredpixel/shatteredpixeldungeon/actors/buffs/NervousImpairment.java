package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

/*
            if (hero.buff(NervousImpairment.class) == null) {
                Buff.affect(hero, NervousImpairment.class);
            }
              hero.buff(NervousImpairment.class).Sum(25);
 */

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class NervousImpairment extends Buff {

    float damages = 0;
    float limit = 100;

    public void Sum(float NervousDamage) {
        damages = Math.min(100, damages + NervousDamage);
        if (damages < 0) detach();
        if (damages >= limit) Burst();
    }

    void Burst() {
        target.damage(target.HT / 4, this);
        Buff.affect(target, Slow.class, 2f);
        Buff.affect(target, Weakness.class, 2f);

        this.detach();
    }

    @Override
    public int icon() {
        return BuffIndicator.IMPAIRMENT;
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", damages);
    }

    private static final String POW = "Power";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(POW, damages);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        damages = bundle.getFloat(POW);
    }
}
