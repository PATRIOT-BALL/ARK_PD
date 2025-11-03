package com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

// 3-5
public class SpriteConvert2 extends Item {
    {
        image = ItemSpriteSheet.LOCKED_CHEST;
    }

    @Override
    public boolean doPickUp(Hero hero) {
        // 변수 처리
        return true;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public int value() {
        return 50;
    }
}
