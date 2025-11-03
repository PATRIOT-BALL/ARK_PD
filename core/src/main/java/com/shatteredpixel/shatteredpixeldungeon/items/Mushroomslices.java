package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Mushroomslices extends Item {
    {
        image = ItemSpriteSheet.MUSH;

        stackable = true;
        bones = false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }
}
