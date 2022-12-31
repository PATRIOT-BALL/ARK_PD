package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

/*
            if (hero.buff(NervousImpairment.class) == null) {
                Buff.affect(hero, NervousImpairment.class);
            }
              hero.buff(NervousImpairment.class).Sum(25);
 */

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class NervousImpairment extends Buff {

    int damages = 0;
    int limit = 100;

    public void Sum(int NervousDamage) {
        damages = Math.min(0, damages + NervousDamage);
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
        return BuffIndicator.CRIPPLE;
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", damages);
    }
}
