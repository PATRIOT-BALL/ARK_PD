package com.shatteredpixel.shatteredpixeldungeon.items.food.cooking;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MeatPower_Chargrilled;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MeatPower_Frozen;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WellFed;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.AnnihilationGear;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ChargrilledMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.BlackPepper;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.Salt;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class NotBarFood extends Food {
    {
        image = ItemSpriteSheet.MEAT_SRICK;
        energy = Hunger.HUNGRY/2f;

        stackable = true;
    }

    @Override
    protected void satisfy(Hero hero) {
        super.satisfy(hero);
        effect(hero);
    }

    public int value() {
        return 20 * quantity;
    }

    public static void effect(Hero hero){
        Buff.affect(Dungeon.hero, MagicImmune.class, 3f);

        if (hero.HP < hero.HT) {
            hero.HP = Math.min( hero.HP + hero.HT / 10, hero.HT );
            hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
        }

        if (Dungeon.hero.subClass == HeroSubClass.DESTROYER) { Buff.affect(hero, MeatPower_Chargrilled.class, MeatPower_Frozen.DURATION); }

        if (hero.hasTalent(Talent.LOVEMEAT))
        {
            Buff.affect(hero, WellFed.class).set(hero.pointsInTalent(Talent.LOVEMEAT) * 20);
            hero.HP = Math.min(Dungeon.hero.HP+hero.pointsInTalent(Talent.LOVEMEAT) * 3, hero.HT);
        }

        if (hero.hasTalent(Talent.GOODMEAT))
        {
            AnnihilationGear Gear = hero.belongings.getItem(AnnihilationGear.class);
            if (hero.belongings.getItem(AnnihilationGear.class) != null && Gear.charge < Gear.chargeCap)
            {
                Gear.SPCharge(1);
            }
        }
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe {

        @Override
        public boolean testIngredients(ArrayList<Item> ingredients) {
            boolean meat = false;
            boolean peeper = false;
            boolean solt = false;

            for (Item ingredient : ingredients){
                if (ingredient.quantity() > 0) {
                    if (ingredient instanceof ChargrilledMeat) {
                        meat = true;
                    } else if (ingredient instanceof Salt) {
                        solt = true;
                    } else if (ingredient instanceof BlackPepper) {
                        peeper = true;
                    }
                }
            }

            return solt && peeper && meat;
        }

        @Override
        public int cost(ArrayList<Item> ingredients) {
            return 2;
        }

        @Override
        public Item brew(ArrayList<Item> ingredients) {
            if (!testIngredients(ingredients)) return null;

            for (Item ingredient : ingredients){
                ingredient.quantity(ingredient.quantity() - 1);
            }

            return sampleOutput(null);
        }

        @Override
        public Item sampleOutput(ArrayList<Item> ingredients) { return new NotBarFood();
        }


    }
}
