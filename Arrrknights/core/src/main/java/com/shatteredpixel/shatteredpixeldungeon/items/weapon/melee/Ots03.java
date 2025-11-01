package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class Ots03 extends GunWeapon {
    {
        image = ItemSpriteSheet.OTS03;
        hitSound = Assets.Sounds.HIT_PISTOL;
        hitSoundPitch = 0.9f;

        FIREACC = 100f;
        FIRETICK = 5f;
        FIREDMG = 10f;
        bulletCap = 16;



        usesTargeting = true;

        defaultAction = AC_ZAP;
        tier = 4;
    }


    @Override
    protected void SPShot(Char ch) {
        Buff.affect(ch, Burning.class).reignite(ch);
    }

    @Override
    protected void fx(Ballistica bolt, Callback callback ) {
        int a = 0;
        if (spshot) a = 1;
        MagicMissile.boltFromChar( curUser.sprite.parent,
                MagicMissile.GUN_SHOT+a,
                curUser.sprite,
                bolt.collisionPos,
                callback);
        Sample.INSTANCE.play( Assets.Sounds.ZAP_GUN );
    }
}
