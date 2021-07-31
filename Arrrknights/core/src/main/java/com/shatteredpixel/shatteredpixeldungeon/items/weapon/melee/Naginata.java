package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Naginata extends MeleeWeapon {
    {
        image = ItemSpriteSheet.NAGINATA;
        hitSound = Assets.Sounds.HIT_SPEAR;
        hitSoundPitch = 1.18f;

        tier = 4;
        DLY = 1.25f;
        RCH = 2;

        bones = false;
    }

    @Override
    public int max(int lvl) {
        return  5*(tier) +   //20 + 5
                lvl*(tier+1);
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        return super.proc(attacker, defender, damage);
    }
}
