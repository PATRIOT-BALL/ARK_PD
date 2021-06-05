package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.levels.RhodesLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ClosureSprite;

public class Closure extends NPC {
    {
        spriteClass = ClosureSprite.class;
        HP=HT=10;
    }

    @Override
    public boolean interact(Char c) {
        sprite.attack(0);
        sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "NO"));
        return true;
    }

    @Override
    public void die(Object cause) {
        sprite.showStatus( CharSprite.NEUTRAL, Messages.get(Closure.class, "die"));
        super.die(cause);
    }

    public static void spawn(RhodesLevel level, int poss) {
        Closure WhatYourName = new Closure();
        do {
            WhatYourName.pos = poss;
        } while (WhatYourName.pos == -1);
        level.mobs.add(WhatYourName);
    }
}
