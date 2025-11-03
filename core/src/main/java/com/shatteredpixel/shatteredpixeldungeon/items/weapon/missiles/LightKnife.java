package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class LightKnife extends MissileWeapon {
    {
        image = ItemSpriteSheet.HOLY_KNIFE;
        hitSound = Assets.Sounds.HIT_KNIFE;
        hitSoundPitch = 1.2f;

        bones = false;

        tier = 1;
        baseUses = 5;
    }

    @Override
    public int max(int lvl) {
        return  6 * tier +                      //6 base, up from 5
                (tier == 1 ? 2*lvl : tier*lvl); //scaling unchanged
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        Buff.affect(defender, Blindness.class, 3f);
        return super.proc(attacker, defender, damage);
    }
}
