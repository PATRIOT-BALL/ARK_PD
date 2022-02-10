package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ShopkeeperSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Texas_shopkeeperSprite;

public class MiniShopkeeper extends Shopkeeper {
    {
        spriteClass = Texas_shopkeeperSprite.class;
        properties.add(Property.IMMOVABLE);
    }
}
