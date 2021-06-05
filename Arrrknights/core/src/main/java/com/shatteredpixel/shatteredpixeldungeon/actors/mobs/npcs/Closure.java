package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.levels.GroundLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ClosureSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Guard_operSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LensSprite;
import com.watabou.utils.Random;

public class Closure extends NPC {
    {
        spriteClass = ClosureSprite.class;
        HP=HT=10;
    }

    @Override
    public boolean interact(Char c) {
        sprite.attack(0);
        sprite.showStatus( CharSprite.NEUTRAL, Messages.get(this, "NO"));
        return true;
    }

    public static void spawn(GroundLevel level, int poss) {
        Closure WhatYourName = new Closure();
        do {
            WhatYourName.pos = poss;
        } while (WhatYourName.pos == -1);
        level.mobs.add(WhatYourName);
    }
}
