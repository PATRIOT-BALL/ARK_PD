package com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.IronSkin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.MetalShard;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ForceCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ElixirsOfSoulProtection extends Elixir {
    {
        image = ItemSpriteSheet.ELIXIR_SOUL1;
    }

    @Override
    public void apply(Hero hero) {
        if (Dungeon.isChallenged(Challenges.NO_HEALING)){
            Buff.affect( hero, Poison.class).set(4 + hero.lvl/2);
        }
        else {
            PotionOfHealing.cure( hero );
            Buff.affect(hero, Healing.class).setHeal((int) (0.5f * hero.HT + 10), 0.25f, 0);
            Buff.affect(hero, Barrier.class).setShield(hero.HT / 2);
            hero.sprite.burst(0xFF18C3E6, 5);
            Talent.onHealingPotionUsed( hero );
        }
    }

    @Override
    protected int splashColor() {
        return 0xFF58CEEE;
    }

    @Override
    public int value() {
        //prices of ingredients
        return quantity * (50 + 40);
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{PotionOfHealing.class, MetalShard.class, ForceCatalyst.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 6;

            output = ElixirsOfSoulProtection.class;
            outQuantity = 1;
        }

    }
}
