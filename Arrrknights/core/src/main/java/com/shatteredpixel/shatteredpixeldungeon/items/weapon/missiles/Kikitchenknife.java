package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Kikitchenknife extends MissileWeapon {
    {
        image = ItemSpriteSheet.TENGU_SHOCKER;
        hitSound = Assets.Sounds.LIGHTNING;
        hitSoundPitch = 0.8f;

        tier = 4;
        baseUses = 6;
        sticky = false;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        Buff.affect(defender, Silence.class, 10f);

        return super.proc(attacker, defender, damage);
    }
}
