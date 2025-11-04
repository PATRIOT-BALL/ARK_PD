package com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Salt extends Ingredients {
    {
        image = ItemSpriteSheet.SALT;
    }

    @Override
    public int value() {
        return 8 * quantity;
    }
}
