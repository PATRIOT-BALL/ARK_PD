package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;

public class Guardoper_ItermUpgrade extends Buff {

    @Override
    public boolean act() {
        DriedRose rose = Dungeon.hero.belongings.getItem(DriedRose.class);
        if (!rose.isEquipped(Dungeon.hero)) detach();

        return super.act();
    }

    public static int bounslevel(int level ) {
        DriedRose rose = Dungeon.hero.belongings.getItem(DriedRose.class);
        if (rose != null) {
            int bounslevel = 0;

            if (rose.level() == 10) bounslevel = 3;
            else if (rose.level() >= 7) bounslevel = 2;
            else if (rose.level() >= 4) bounslevel = 1;

            if (level >= bounslevel) {
                //zero or negative levels are unaffected
                return level;
            } else {
                return bounslevel;
            }
        }
        else return level;
    }
}
