package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class C1_9mm extends GunWeapon {
    {
        image = ItemSpriteSheet.C1;
        hitSound = Assets.Sounds.HIT_GUN;
        hitSoundPitch = 0.9f;

        FIRE_ACC_MULT = 3f;
        FIRE_DELAY_MULT = 0.66f;
        FIRE_DAMAGE_MULT = 0.6f;
        bulletMax = 34;

        usesTargeting = true;

        defaultAction = AC_ZAP;

        tier = 3;
    }

    @Override
    protected void specialFire(Char ch) {
        Buff.affect(ch, Slow.class, 3f);
    }
}
