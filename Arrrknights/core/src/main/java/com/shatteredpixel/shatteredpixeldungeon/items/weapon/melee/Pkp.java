package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class Pkp extends GunWeapon {
    {
        image = ItemSpriteSheet.PKP;
        hitSound = Assets.Sounds.HIT_AR;
        hitSoundPitch = 0.9f;

        FIREACC = 1f;
        FIRETICK = 0.2f;
        FIREDMG = 1f;
        bulletCap = 101;



        usesTargeting = true;

        defaultAction = AC_ZAP;
        tier = 5;
    }


    @Override
    protected void SPShot(Char ch) {
        Buff.affect(ch, Slow.class, 3f);
    }
}
