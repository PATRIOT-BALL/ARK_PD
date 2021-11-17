package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ClosureSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_dummySprite;

public class Dummy extends Mob {
    {
        spriteClass = NPC_dummySprite.class;
        HP=HT=1000;
        properties.add(Property.IMMOVABLE);

        state = PASSIVE;
    }

    @Override
    public void beckon(int cell) {
        //do nothing
    }

    @Override
    protected boolean act() {
        HP = Math.min(HP+50, HT);
        return super.act();
    }

    public static void spawn(Level level, int poss) {
        Dummy WhatYourName = new Dummy();
        do {
            WhatYourName.pos = poss;
        } while (WhatYourName.pos == -1);
        level.mobs.add(WhatYourName);
    }
}
