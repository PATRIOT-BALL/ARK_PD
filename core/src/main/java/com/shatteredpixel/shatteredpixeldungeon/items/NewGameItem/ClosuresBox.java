package com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public abstract class ClosuresBox extends Item {
    public static final String AC_OPEN = "OPEN";

    protected int OpenLevel;

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        if (Dungeon.hero.lvl >= OpenLevel) actions.add( AC_OPEN );
        return actions;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }
}
