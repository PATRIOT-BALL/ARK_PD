package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ShopkeeperSprite;

public class MiniShopkeeper extends Shopkeeper {
    {
        spriteClass = ShopkeeperSprite.class;
        properties.add(Property.IMMOVABLE);
    }
}
