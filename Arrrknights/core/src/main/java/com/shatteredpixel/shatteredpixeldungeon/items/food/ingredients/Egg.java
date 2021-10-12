package com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Egg extends Ingredients {
    {
        image = ItemSpriteSheet.POMBBAY;
    }

    @Override
    public int value() {
        return 8;
    }
}
