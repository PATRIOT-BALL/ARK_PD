package com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Potato extends Ingredients {
    {
        image = ItemSpriteSheet.POTATO;
    }

    @Override
    public int value() {
        return 8 * quantity;
    }
}
