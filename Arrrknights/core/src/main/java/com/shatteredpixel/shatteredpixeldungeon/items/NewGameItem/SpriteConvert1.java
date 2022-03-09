package com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem;


import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

// 1-2
public class SpriteConvert1 extends Item {
    {
        image = ItemSpriteSheet.CHEST;
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
        return 25;
    }
}
