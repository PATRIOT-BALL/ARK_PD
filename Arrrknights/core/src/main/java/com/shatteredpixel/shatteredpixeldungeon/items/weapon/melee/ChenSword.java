package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class ChenSword extends MeleeWeapon {
    {
        image = ItemSpriteSheet.SHORTSWORD;
        hitSound = Assets.Sounds.HIT_SWORD;
        hitSoundPitch = 1.1f;

        tier = 1;

        bones = false;
    }
}
