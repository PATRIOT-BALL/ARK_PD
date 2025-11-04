package com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Talu_BlackSnake;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Talulah;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Dragonkill extends Weapon.Enchantment {

    private static ItemSprite.Glowing RED = new ItemSprite.Glowing(0x660022);

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if (defender instanceof Talulah) {
            damage = Random.IntRange(3232,8876);
        }
        else if (defender instanceof Talu_BlackSnake) {
            damage *= 2f;
        }

        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return RED;
    }
}

