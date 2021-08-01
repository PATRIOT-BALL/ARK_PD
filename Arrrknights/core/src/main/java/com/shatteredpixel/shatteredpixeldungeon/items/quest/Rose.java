package com.shatteredpixel.shatteredpixeldungeon.items.quest;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Rose extends Item {
    {
        image = ItemSpriteSheet.MYOSOTIS;
        unique = true;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }
}
