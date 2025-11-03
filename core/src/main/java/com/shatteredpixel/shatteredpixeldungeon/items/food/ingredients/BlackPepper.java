package com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class BlackPepper extends Ingredients {
    {
        image = ItemSpriteSheet.PEPPER;
    }

    @Override
    public int value() {
        return 18 * quantity;
    }
}
