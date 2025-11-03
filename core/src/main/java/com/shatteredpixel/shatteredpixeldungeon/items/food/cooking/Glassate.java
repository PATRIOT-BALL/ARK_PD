package com.shatteredpixel.shatteredpixeldungeon.items.food.cooking;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.Egg;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.Salt;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.SugarFlower;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Glassate extends Food {
    {
        image = ItemSpriteSheet.NUNEDDINE;
        energy = Hunger.HUNGRY;

        stackable = true;
    }
    @Override
    protected float eatingTime(){
        if (Dungeon.hero.hasTalent(Talent.IRON_STOMACH)
                || Dungeon.hero.hasTalent(Talent.ENERGIZING_MEAL)
                || Dungeon.hero.hasTalent(Talent.MYSTICAL_MEAL)
                || Dungeon.hero.hasTalent(Talent.INVIGORATING_MEAL)
                || Dungeon.hero.hasTalent(Talent.FASTMEAL)
                || Dungeon.hero.hasTalent(Talent.COMBAT_MEAL)
                || Dungeon.hero.hasTalent(Talent.LATENT_MEAL)){
            return TIME_TO_EAT - 1;
        } else {
            return TIME_TO_EAT + 1;
        }
    }

    @Override
    public int value() {
        return 20 * quantity;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{SugarFlower.class, Salt.class, Egg.class};
            inQuantity = new int[]{1, 1, 1};

            output = Glassate.class;
            cost = 5;
            outQuantity = 1;
        }

    }
}
