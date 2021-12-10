package com.shatteredpixel.shatteredpixeldungeon.items.food.cooking;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.Egg;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.Salt;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class SmokeEgg extends Food {
    {
        image = ItemSpriteSheet.SMOKEEGG;
        energy = Hunger.HUNGRY/4f;

        stackable = true;
    }

    @Override
    protected void satisfy(Hero hero) {
        super.satisfy(hero);
    }

    @Override
    public int value() {
        return 10 * quantity;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{Egg.class};
            inQuantity = new int[]{1};
            cost = 3;

            output = SmokeEgg.class;
            outQuantity = 1;
        }

    }
}
