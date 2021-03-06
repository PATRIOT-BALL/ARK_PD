package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class MeatPower_Frozen extends FlavourBuff {
    public static final float DURATION = 300f;
    {
        type = Buff.buffType.POSITIVE;
    }

    @Override
    public int icon() { return BuffIndicator.UPGRADE; }

    @Override
    public String toString() { return Messages.get(this, "name"); }

    @Override
    public String desc() {
        return Messages.get(this, "desc", dispTurns());
    }
}
