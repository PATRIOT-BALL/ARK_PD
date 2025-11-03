package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.ExplodeSlug_A;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.Salt;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfStormClouds;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class SaltBlast extends TargetedSpell {
    private static ItemSprite.Glowing COL = new ItemSprite.Glowing( 0x0000FF );
    {
        image = ItemSpriteSheet.AQUA_BLAST;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return COL;
    }

    @Override
    protected void affectTarget(Ballistica bolt, Hero hero) {
        int cell = bolt.collisionPos;

        Splash.at(cell, 0x00AAFF, 10);

        for (int i : PathFinder.NEIGHBOURS9){
            if (i == 0 || Random.Int(5) != 0){
                Dungeon.level.setCellToWater(false, cell+i);
            }
        }

        Char target = Actor.findChar(cell);

        if (target != null && target != hero){
            //just enough to skip their current turn
            if (target instanceof ExplodeSlug_A) Buff.affect(target, Silence.class, 15f);
            Buff.affect(target, Weakness.class, 12f);
        }
    }

    @Override
    public int value() {
        //prices of ingredients, divided by output quantity
        return Math.round(quantity * ((60 + 40) / 12f));
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{AquaBlast.class, Salt.class};
            inQuantity = new int[]{1, 1};

            output = SaltBlast.class;
            outQuantity = 1;
        }

    }
}
