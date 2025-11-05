package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Pkp extends GunWeapon {
    {
        image = ItemSpriteSheet.PKP;
        hitSound = Assets.Sounds.HIT_AR;
        hitSoundPitch = 0.9f;

        FIRE_ACC_MULT = 1f;
        FIRE_DELAY_MULT = 0.25f;
        FIRE_DAMAGE_MULT = 0.25f;
        bulletMax = 101;

        usesTargeting = true;

        defaultAction = AC_ZAP;
        tier = 5;
    }


    @Override
    protected void specialFire(Char ch) {
        Buff.affect(ch, Slow.class, 3f);
    }
}
