package com.shatteredpixel.shatteredpixeldungeon.items.rings;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.text.DecimalFormat;

public class RingOfAmplified extends Ring {
    {
        icon = ItemSpriteSheet.Icons.RING_AMPLIFIED;
    }

    public String statsInfo() {
            return Messages.get(this, "stats");
    }

    @Override
    protected RingBuff buff() {
        return new WandPowerup();
    }

    public static int DamageBonus( Char target ){
        return (int) getBonus(target, RingOfAmplified.WandPowerup.class);
    }


    public class WandPowerup extends RingBuff {
    }
}
