package com.shatteredpixel.shatteredpixeldungeon.items.weapon.curses;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ActiveOriginium;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class contamination extends Weapon.Enchantment {

    private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage ) {

        float procChance = 1/10f * procChanceMultiplier(attacker);
        if (Random.Float() < procChance) {
            Buff.affect(attacker, ActiveOriginium.class).set(attacker.HT * 0.1f);
        }

        return damage;
    }

    @Override
    public boolean curse() {
        return true;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return BLACK;
    }
}
