package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.ExplodeSlug_A;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfStormClouds;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPetrification;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class BlastSpell extends TargetedSpell {
    private static ItemSprite.Glowing COL = new ItemSprite.Glowing( 0x000000 );
    {
        image = ItemSpriteSheet.RECYCLE;
    }

    public ItemSprite.Glowing glowing() {
        return COL;
    }

    @Override
    protected void affectTarget(Ballistica bolt, Hero hero) {
        int cell = bolt.collisionPos;

        Splash.at(cell, 0x000000, 10);

        Char target = Actor.findChar(cell);

        if (target != null && target != hero){
            Ballistica trajectory = new Ballistica(curUser.pos, target.pos, Ballistica.STOP_TARGET);
            trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
            WandOfBlastWave.throwChar(target, trajectory, 1); // 넉백 효과
        }
    }

    @Override
    public int value() {
        //prices of ingredients, divided by output quantity
        return Math.round(quantity * ((60 + 40) / 12f));
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{ScrollOfPetrification.class, PotionOfLevitation.class};
            inQuantity = new int[]{1, 1};

            cost = 8;

            output = BlastSpell.class;
            outQuantity = 12;
        }

    }
}
