package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class FlametailSword extends MeleeWeapon {
    {
        image = ItemSpriteSheet.BATTLE_AXE;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 0.9f;

        tier = 4;
        DLY = 0.9f;
    }

    @Override
    public int max(int lvl) {
        return  4*(tier) +    //20 base, down from 25
                lvl*(tier+1);   //scaling unchanged
    }
}
