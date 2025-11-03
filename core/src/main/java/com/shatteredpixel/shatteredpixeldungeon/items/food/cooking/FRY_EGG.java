package com.shatteredpixel.shatteredpixeldungeon.items.food.cooking;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.Egg;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.Salt;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class FRY_EGG extends Food {
    {
        image = ItemSpriteSheet.EGG_FRY;
        energy = Hunger.HUNGRY/5f;

        stackable = true;
    }

    @Override
    protected void satisfy(Hero hero) {
        Buff.detach(hero, Burning.class);
        super.satisfy(hero);
    }

    @Override
    public int value() {
        return 20 * quantity;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{Egg.class, Salt.class};
            inQuantity = new int[]{1, 1};
            cost = 1;

            output = FRY_EGG.class;
            outQuantity = 1;
        }

    }
}
