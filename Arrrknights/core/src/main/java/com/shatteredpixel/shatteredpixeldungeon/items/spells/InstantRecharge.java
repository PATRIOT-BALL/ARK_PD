package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.StaffKit;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.MetalShard;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfAntiMagic;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class InstantRecharge extends Spell {
    private static ItemSprite.Glowing COL = new ItemSprite.Glowing( 0x0FF0FF );
    {
        image = ItemSpriteSheet.MAGIC_ARMOR;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return COL;
    }

    @Override
    protected void onCast(Hero hero) {
        Sample.INSTANCE.play(Assets.Sounds.READ, 1.33f, 1.65f);
        for (Wand.Charger c : hero.buffs(Wand.Charger.class)){
                c.gainCharge(99f);
        }
        hero.sprite.operate(hero.pos);
        hero.spendAndNext(1f);

        this.detach(hero.belongings.backpack);
    }



    @Override
    public int value() {
        //prices of ingredients, divided by output quantity
        return Math.round(quantity * ((5 + 40) / 8f));
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe {

        @Override
        public boolean testIngredients(ArrayList<Item> ingredients) {
            boolean wand = false;
            boolean al = false;
            for (Item i : ingredients){
                if (i instanceof Wand){
                    wand = true;
                    //if it is a regular or exotic potion
                } else if (i instanceof AlchemicalCatalyst) {
                    al = true;
                }
            }

            return wand && al;
        }

        @Override
        public int cost(ArrayList<Item> ingredients) {
            return 5;
        }

        @Override
        public Item brew(ArrayList<Item> ingredients) {

            for (Item i : ingredients){
                i.quantity(i.quantity()-1);
            }

            return sampleOutput(null);
        }

        @Override
        public Item sampleOutput(ArrayList<Item> ingredients) {
            return new InstantRecharge().quantity(2);
        }
    }
}
