package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Kinetic;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class SeethingBurst extends FlavourBuff {

    {
        type = buffType.POSITIVE;
        announced = true;
    }

    protected int Power = 0;

    public static final float DURATION = 40;

    @Override
    public boolean act() {
        if (target.isAlive()) {
            Buff.affect(target, Kinetic.ConservedDamage.class).setBonus(Power);
            detach();
        }
        else
            detach();

        return true;
    }

    @Override
    public int icon() {
        return BuffIndicator.AMOK;
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (DURATION - visualcooldown()) / DURATION);
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", dispTurns());
    }

    public void GetDamage(int damage)
    {
        Power += damage * 2.5f;
    }

    private static final String POW = "Power";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(POW, Power);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        Power = bundle.getInt(POW);
    }
}