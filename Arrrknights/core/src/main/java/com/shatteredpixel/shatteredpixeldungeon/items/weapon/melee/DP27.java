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

public class DP27 extends GunWeapon {
    {
        image = ItemSpriteSheet.DP27;
        hitSound = Assets.Sounds.HIT_GUN;
        hitSoundPitch = 0.9f;

        FIREACC = 3f;
        FIRETICK = 0.2f;
        bulletCap = 47;

        usesTargeting = true;

        defaultAction = AC_ZAP;
        tier = 3;
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
