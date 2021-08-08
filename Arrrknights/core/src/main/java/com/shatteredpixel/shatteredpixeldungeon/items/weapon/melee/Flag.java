package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Flag extends MeleeWeapon  {
    {
        image = ItemSpriteSheet.WEAPON_HOLDER;
        hitSound = Assets.Sounds.HIT;
        hitSoundPitch = 1f;

        tier = 3;
    }

    @Override
    public int max(int lvl) {
        return  4*(tier) +   //12 + 3
                lvl*(tier); }
}
