package com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

// 0, 6
public class SpriteConvert3 extends Item {
    {
        image = ItemSpriteSheet.CRYSTAL_CHEST;
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
        return 125;
    }
}
