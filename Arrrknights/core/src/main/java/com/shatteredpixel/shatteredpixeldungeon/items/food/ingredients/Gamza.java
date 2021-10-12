package com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Gamza extends Ingredients {
    {
        image = ItemSpriteSheet.GAMZA;
    }

    @Override
    public int value() {
        return 8;
    }
}
