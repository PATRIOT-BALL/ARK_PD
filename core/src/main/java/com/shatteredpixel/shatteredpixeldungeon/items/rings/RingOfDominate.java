package com.shatteredpixel.shatteredpixeldungeon.items.rings;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class RingOfDominate extends Ring {
    {
        icon = ItemSpriteSheet.Icons.RING_DOMINATE;
    }

    public String statsInfo() {
        if (isIdentified()) {
            if (cursed) return Messages.get(this, "stats", 0);
            return Messages.get(this, "stats", 4+buffedLvl()*4);
        } else {
            return Messages.get(this, "typical_stats", 4);
        }
    }

    @Override
    protected RingBuff buff() {
        return new Dominatepower();
    }

    public static float Dominate( Char target ){
        return Math.max(0,getBonus(target, RingOfDominate.Dominatepower.class) * 4);
    }

    public static boolean Dominate_curse( Char target ){
        return getCursed(target,RingOfDominate.Dominatepower.class);
    }


    public class Dominatepower extends RingBuff {
    }
}
