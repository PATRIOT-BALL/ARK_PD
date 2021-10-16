package com.shatteredpixel.shatteredpixeldungeon.items.food.cooking;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WellFed;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.SugarFlower;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.PhaseShift;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class HoneyBread extends Food {
    {
        image = ItemSpriteSheet.PAN_CAKE;
        energy = Hunger.HUNGRY;

        stackable = true;
    }

    @Override
    protected void satisfy(Hero hero) {
        super.satisfy( hero );
        Buff.affect(hero, Bless.class, 25f);
    }

    @Override
    public int value() {
        return 20 * quantity;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{SugarFlower.class, Food.class};
            inQuantity = new int[]{1, 1};

            output = HoneyBread.class;
            outQuantity = 1;
        }

    }
}
