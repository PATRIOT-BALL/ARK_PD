package com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Overeating extends Weapon.Enchantment {

    private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x00000F );

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage ) {

        int level = Math.min( 5, weapon.buffedLvl() * 2 );
        if (attacker instanceof Hero) {

                Hunger hunger = Buff.affect(attacker, Hunger.class);

                if (!hunger.isStarving()) {
                    hunger.affectHunger(  -4 );
                }
                else {
                    attacker.damage(level, this);
                }
            damage *= 1.25f;
        }

        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return BLACK;
    }

}
