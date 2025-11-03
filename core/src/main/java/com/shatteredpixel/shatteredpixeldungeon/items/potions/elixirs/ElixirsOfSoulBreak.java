package com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Freezing;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hallucination;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ToxicImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PoisonParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.MetalShard;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HandclapSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class ElixirsOfSoulBreak extends Elixir {
    {
        image = ItemSpriteSheet.ELIXIR_SOUL2;
    }

    @Override
    public void apply(Hero hero) {
        Buff.affect(hero, Hallucination.class).set(50f);
        hero.sprite.emitter().burst(PoisonParticle.SPLASH, 10);
    }

    @Override
    public void shatter( int cell ) {

        if (Dungeon.level.heroFOV[cell]) {
            identify();

            splash( cell );
            Sample.INSTANCE.play( Assets.Sounds.SHATTER );
        }
    }

    @Override
    protected void splash(int cell) {
        for (int offset : PathFinder.NEIGHBOURS9){
            Char c = Actor.findChar(cell+offset);
            if (c != null) Buff.affect(c, Hallucination.class).set(50f);
            CellEmitter.center(offset).burst(HandclapSprite.GooParticle.FACTORY, 10);

        }
        super.splash(cell);
    }

    @Override
    protected int splashColor() {
        return 0xFFF0BFFA;
    }

    @Override
    public int value() {
        //prices of ingredients
        return quantity * (50 + 40);
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{PotionOfToxicGas.class, MetalShard.class};
            inQuantity = new int[]{1, 1};

            cost = 6;

            output = ElixirsOfSoulBreak.class;
            outQuantity = 1;
        }

    }
}
