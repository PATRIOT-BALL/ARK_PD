package com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs;

import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.IronSkin;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SnowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.MetalShard;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ForceCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.levels.HallsLevel;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ElixirsOfIronSkin extends Elixir {
    {
        image = ItemSpriteSheet.ELIXIR_IRON;
    }

    @Override
    public void apply(Hero hero) {
        if (hero.buff(IronSkin.class) == null) Buff.affect(hero, IronSkin.class);
        Buff.affect(hero, Barkskin.class).set(2 + (Dungeon.depth / 2), 25);
        hero.sprite.burst(0xFF18C3E6, 5);
    }

    @Override
    protected int splashColor() {
        return 0xFF18C3E6;
    }

    @Override
    public int value() {
        //prices of ingredients
        return quantity * (50 + 40);
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{PotionOfStrength.class, MetalShard.class, ForceCatalyst.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 6;

            output = ElixirsOfIronSkin.class;
            outQuantity = 1;
        }

    }
}
