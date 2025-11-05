package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Sig553 extends GunWeapon {
    {
        image = ItemSpriteSheet.SIG553;
        hitSound = Assets.Sounds.HIT_GUN;
        hitSoundPitch = 0.9f;

        FIRE_ACC_MULT = 10f;
        FIRE_DELAY_MULT = 0.4f;
        FIRE_DAMAGE_MULT = 0.4f;
        bulletMax = 31;

        usesTargeting = true;

        defaultAction = AC_ZAP;

        tier = 3;
    }

    @Override
    protected void specialFire(Char ch) {
        Buff.affect(ch, Silence.class, 3f);
    }
}
