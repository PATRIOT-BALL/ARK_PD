package com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Penetrate extends Weapon.Enchantment {
    private static ItemSprite.Glowing TEAL = new ItemSprite.Glowing( 0xDEB887 );

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage ) {
        int level = Math.max( 0, weapon.buffedLvl() );

        // lvl 0 - 25%
        // lvl 1 - 40%
        // lvl 2 - 50%
        float procChance = (level+1f)/(level+4f) * procChanceMultiplier(attacker);
        if (Random.Float() < procChance) {
            int bouns = Math.max(defender.drRoll(), defender.drRoll());

            if (bouns > weapon.buffedLvl() * 2) bouns = weapon.buffedLvl() * 2;

            damage += bouns;
        }

        return damage; }

    @Override
    public ItemSprite.Glowing glowing() {
        return TEAL;
    }
}
