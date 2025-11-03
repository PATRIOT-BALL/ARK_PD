package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class SG_CQB extends GunWeapon {
    {
        image = ItemSpriteSheet.SG_CQB;
        hitSound = Assets.Sounds.HIT_GUN;
        hitSoundPitch = 0.9f;

        FIRE_ACC_MULT = 3f;
        FIRE_DELAY_MULT = 1.5f;
        FIRE_DAMAGE_MULT = 1.25f;
        bulletMax = 7;

        usesTargeting = true;

        defaultAction = AC_ZAP;

        tier = 4;
    }

    @Override
    protected void specialFire(Char ch) {
        Buff.affect(ch, Slow.class, 3f);
    }
}
