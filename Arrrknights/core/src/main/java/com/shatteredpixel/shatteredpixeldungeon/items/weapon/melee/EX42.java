package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class EX42 extends MeleeWeapon {
    {
        image = ItemSpriteSheet.TRIDENT;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 0.65f;

        tier = 1;
        DLY = 0.8f;
        ACC = 0.8f;
        RCH = 2;

        bones = false;
    }
}
