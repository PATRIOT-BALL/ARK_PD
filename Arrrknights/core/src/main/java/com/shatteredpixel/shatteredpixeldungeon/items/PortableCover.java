package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPetrification;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.BlastSpell;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class PortableCover extends Item {
    private static String AC_USE = "USE";
    {
        image = ItemSpriteSheet.BARRI;
        stackable = true;
        defaultAction = AC_USE;
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_USE );
        return actions;
    }

    @Override
    public void execute( final Hero hero, String action ) {

        super.execute( hero, action );
        if (action.equals( AC_USE )) {
            Buff.append(curUser, PortableCover.CoverBuff.class, 3f);
            detach(curUser.belongings.backpack);
            curUser.spendAndNext(1f);
        }
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
        return 30 * quantity;
    }

    public static class CoverBuff extends FlavourBuff {
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{Mushroomslices.class};
            inQuantity = new int[]{2};

            cost = 0;

            output = PortableCover.class;
            outQuantity = 1;
        }

    }
}
