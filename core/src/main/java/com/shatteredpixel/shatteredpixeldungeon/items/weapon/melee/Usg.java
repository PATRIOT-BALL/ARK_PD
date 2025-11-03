package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Usg extends GunWeapon {
    {
        image = ItemSpriteSheet.USG;
        hitSound = Assets.Sounds.HIT_PISTOL;
        hitSoundPitch = 0.9f;

        FIRE_ACC_MULT = 5f;
        FIRE_DELAY_MULT = 1f;
        FIRE_DAMAGE_MULT = 0.8f;
        bulletMax = 21;

        usesTargeting = true;

        defaultAction = AC_ZAP;

        tier = 2;
    }

    @Override
    protected void specialFire(Char ch) {
        Buff.affect(ch, Slow.class, 3f);
    }
}
