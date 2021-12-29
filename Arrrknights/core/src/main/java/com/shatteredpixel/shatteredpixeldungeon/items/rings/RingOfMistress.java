package com.shatteredpixel.shatteredpixeldungeon.items.rings;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.text.DecimalFormat;

public class RingOfMistress extends Ring{
    {
        icon = ItemSpriteSheet.Icons.RING_MISTRESS;
    }

    public String statsInfo() {
        if (isIdentified()){
            return Messages.get(this, "stats", new DecimalFormat("#.##").format(100f * (Math.pow(1.15f, soloBuffedBonus()) - 1f)));
        } else {
            return Messages.get(this, "typical_stats", new DecimalFormat("#.##").format(15f));
        }
    }

    @Override
    protected RingBuff buff( ) {
        return new WeaponChargeUp();
    }

    public static float SPMultiplier( Char t ){
        return (float) Math.pow( 1.15, getBuffedBonus(t, WeaponChargeUp.class));
    }

    public class WeaponChargeUp extends RingBuff {
    }
}
