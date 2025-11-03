package com.shatteredpixel.shatteredpixeldungeon.items.food.cooking;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
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
import com.shatteredpixel.shatteredpixeldungeon.effects.CheckedCell;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.AnnihilationGear;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ChargrilledMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.food.FrozenCarpaccio;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.StewedMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.BlackPepper;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.Egg;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.Salt;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfClairvoyance;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ShadowCaster;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Point;

import java.util.ArrayList;

public class Yukjeon extends Food {
    {
        image = ItemSpriteSheet.YUKJEON;
        energy = Hunger.HUNGRY/2f;

        stackable = true;
    }

    private static final int DIST = 7;

    @Override
    protected void satisfy(Hero hero) {
        activate(hero.pos);
        super.satisfy(hero);
        effect(hero);
    }

    public int value() {
        return 20 * quantity;
    }

    public static void effect(Hero hero){
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

    private void activate(final int cell) {
        Point c = Dungeon.level.cellToPoint(cell);

        int[] rounding = ShadowCaster.rounding[DIST];

        int left, right;
        int curr;
        boolean noticed = false;
        for (int y = Math.max(0, c.y - DIST); y <= Math.min(Dungeon.level.height()-1, c.y + DIST); y++) {
            if (rounding[Math.abs(c.y - y)] < Math.abs(c.y - y)) {
                left = c.x - rounding[Math.abs(c.y - y)];
            } else {
                left = DIST;
                while (rounding[left] < rounding[Math.abs(c.y - y)]){
                    left--;
                }
                left = c.x - left;
            }
            right = Math.min(Dungeon.level.width()-1, c.x + c.x - left);
            left = Math.max(0, left);
            for (curr = left + y * Dungeon.level.width(); curr <= right + y * Dungeon.level.width(); curr++){

                GameScene.effectOverFog( new CheckedCell( curr, cell ) );
                Dungeon.level.mapped[curr] = true;

                if (Dungeon.level.secret[curr]) {
                    Dungeon.level.discover(curr);

                    if (Dungeon.level.heroFOV[curr]) {
                        GameScene.discoverTile(curr, Dungeon.level.map[curr]);
                        ScrollOfMagicMapping.discover(curr);
                        noticed = true;
                    }
                }

            }
        }

        if (noticed) {
            Sample.INSTANCE.play( Assets.Sounds.SECRET );
        }

        Sample.INSTANCE.play( Assets.Sounds.TELEPORT );
        GameScene.updateFog();


    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe {

        @Override
        public boolean testIngredients(ArrayList<Item> ingredients) {
            boolean meat = false;
            boolean peeper = false;
            boolean egg = false;

            for (Item ingredient : ingredients){
                if (ingredient.quantity() > 0) {
                    if (ingredient instanceof MysteryMeat
                            || ingredient instanceof StewedMeat
                            || ingredient instanceof ChargrilledMeat
                            || ingredient instanceof FrozenCarpaccio) {
                        meat = true;
                    } else if (ingredient instanceof Egg) {
                        egg = true;
                    } else if (ingredient instanceof BlackPepper) {
                        peeper = true;
                    }
                }
            }

            return egg && peeper && meat;
        }

        @Override
        public int cost(ArrayList<Item> ingredients) {
            return 4;
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
        public Item sampleOutput(ArrayList<Item> ingredients) { return new Yukjeon();
        }


    }
}
