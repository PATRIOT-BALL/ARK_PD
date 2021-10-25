package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class RadiantSpear extends MeleeWeapon {
    {
        image = ItemSpriteSheet.REQUIEM;
        hitSound = Assets.Sounds.HIT_SPEAR;
        hitSoundPitch = 1.18f;

        tier = 5;
        ACC = 1.25f;
    }

    @Override
    public int max(int lvl) {
        return  5*(tier) +   //25 + 5
                lvl*(tier); }


    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (defender.buff(Vulnerable.class) != null) {
            damage += attacker.damageRoll() / 5;
        }

        return super.proc(attacker, defender, damage);
    }
}
