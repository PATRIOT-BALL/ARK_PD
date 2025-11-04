package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.items.Recipe;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.MetalShard;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class UpMagazine extends MissileWeapon {
    {
        image = ItemSpriteSheet.AMMO2;
        hitSound = Assets.Sounds.HIT_MAGIC;
        hitSoundPitch = 1f;

        tier = 4;
        baseUses = 1;
    }

    @Override
    public int value() {
        return super.value() / 2; //half normal value
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe{
        {
            inputs =  new Class[]{NormalMagazine.class, MetalShard.class};
            inQuantity = new int[]{1, 1};

            cost = 4;

            output = UpMagazine.class;
            outQuantity = 1;
        }
    }
}
