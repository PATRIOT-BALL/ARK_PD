package com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;

public class Ingredients extends Item {
    {
        stackable = true;
    }
    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public int value() {
        return 5 * quantity;
    }
}
