package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MeatPower_Stewed;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WellFed;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sungrass;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class Sandvich extends Food {
    {
        image = ItemSpriteSheet.SANDBITCH;
        energy = Hunger.HUNGRY/4f;

        stackable = false;
    }

    private int eat = 0; // 3일때 사용시 아이템 제거

    @Override
    protected void satisfy(Hero hero) {
        super.satisfy(hero);
        eat++;
        effect(hero);

        if (eat < 4) {
            Item result = this;
            if (result == null){
                //This shouldn't ever trigger
                GLog.n( Messages.get(this, "nothing") );
                curItem.collect( curUser.belongings.backpack );
            } else {
                    if (!result.collect()){
                        Dungeon.level.drop(result, curUser.pos).sprite.drop();
                    }
                }
        }
    }

    public static void effect(Hero hero){
        hero.HP = Math.min( hero.HP + hero.HT / 5, hero.HT );
        hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", 4-eat);
    }

    @Override
    public int value() {
        return 20 * eat;
    }

    private static final String stack = "eat";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(stack, eat);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        eat = bundle.getInt(stack);
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe {

        @Override
        public boolean testIngredients(ArrayList<Item> ingredients) {
            boolean seed = false;
            boolean ration = false;
            boolean meat = false;

            for (Item ingredient : ingredients){
                if (ingredient.quantity() > 0) {
                    if (ingredient instanceof Sungrass.Seed) {
                        seed = true;
                    } else if (ingredient.getClass() == Food.class) {
                        ration = true;
                    } else if (ingredient instanceof MysteryMeat
                            || ingredient instanceof StewedMeat
                            || ingredient instanceof ChargrilledMeat
                            || ingredient instanceof FrozenCarpaccio) {
                        meat = true;
                    }
                }
            }

            return seed && ration && meat;
        }

        @Override
        public int cost(ArrayList<Item> ingredients) {
            return 6;
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
        public Item sampleOutput(ArrayList<Item> ingredients) { return new Sandvich();
        }


    }
}
