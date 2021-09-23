package com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class SugarFlower extends Ingredients {
    {
        image = ItemSpriteSheet.DEWDROP;
    }

    @Override
    public int value() {
        return 8;
    }
}
