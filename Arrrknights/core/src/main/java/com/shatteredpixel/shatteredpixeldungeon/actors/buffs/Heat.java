package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Bundle;

public class Heat extends Buff {
    private enum State{
        NORMAL, OVERHEAT
    }
    private Heat.State state = Heat.State.NORMAL;

    private float power = 0;
    private final float powercap = 100;

    private static final String STATE = "state";
    private static final String POWER = "power";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(STATE, state);
        bundle.put(POWER, power);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        state = bundle.getEnum(STATE, Heat.State.class);
        power = bundle.getFloat(POWER);
    }


    @Override
    public String toString() {
        switch (state){
            case NORMAL: default:
                return Messages.get(this, "normal");
            case OVERHEAT:
                return Messages.get(this, "overheat");
        }
    }

    @Override
    public String desc() {
        switch (state) {
            case NORMAL:
            default:
                return Messages.get(this, "normal_desc", power);
            case OVERHEAT:
                return Messages.get(this, "overheat_desc", power);
        }
    }
}
