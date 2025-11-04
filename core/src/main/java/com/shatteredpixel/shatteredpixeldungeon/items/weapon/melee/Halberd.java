package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Halberd extends MeleeWeapon {
    {
        image = ItemSpriteSheet.VANILLA_AXE;
        hitSound = Assets.Sounds.HIT_STAB;
        hitSoundPitch = 1f;

        tier = 2;
    }

    @Override
    public int max(int lvl) {
        return  4*(tier+1) +    //12 base, down from 15
                lvl*(tier+1);   //scaling unchanged
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (defender.properties().contains(Char.Property.INFECTED)) {
            damage += 4;
        }

        return super.proc(attacker, defender, damage);
    }
}
