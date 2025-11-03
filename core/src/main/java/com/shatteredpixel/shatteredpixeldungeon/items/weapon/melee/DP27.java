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
        hitSound = Assets.Sounds.HIT_GUN2;
        hitSoundPitch = 0.9f;

        FIRE_ACC_MULT = 1f;
        FIRE_DELAY_MULT = 0.25f;
        FIRE_DAMAGE_MULT = 0.2f;
        bulletMax = 47;

        usesTargeting = true;

        defaultAction = AC_ZAP;
        tier = 3;
    }

    @Override
    protected void specialFire(Char ch) {
        Buff.affect(ch, Burning.class).reignite(ch);
    }

    @Override
    protected void fx(Ballistica bolt, Callback callback ) {
        int a = 0;
        if (specialFire) a = 1;
        MagicMissile.boltFromChar( curUser.sprite.parent,
                MagicMissile.GUN_SHOT+a,
                curUser.sprite,
                bolt.collisionPos,
                callback);
        Sample.INSTANCE.play( Assets.Sounds.ZAP_GUN );
    }
}
