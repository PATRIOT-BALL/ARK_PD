package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class WintersScar extends MeleeWeapon {
    {
        image = ItemSpriteSheet.WAR_HAMMER;
        hitSound = Assets.Sounds.HIT_CRUSH;
        hitSoundPitch = 1.12f;

        tier = 5;
    }

    @Override
    public int max(int lvl) {
        return  4*(tier+1) +    //20 base
                lvl*(tier+1);
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (defender.buff(Chill.class) != null) damage = Math.round(damage * 1.15f);

        if (defender instanceof Mob && ((Mob) defender).surprisedBy(attacker)) {
            Buff.affect(defender, Chill.class, 4f);
        }
        return super.proc(attacker, defender, damage);
    }

    @Override
    public int damageRoll(Char owner) {
        if (owner instanceof Hero) {
            Hero hero = (Hero)owner;
            Char enemy = hero.enemy();
            if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
                //deals 33% toward max to max on surprise, instead of min to max.
                int diff = max() - min();
                int damage = augment.damageFactor(Random.NormalIntRange(
                        min() + Math.round(diff*0.33f),
                        max()));
                int exStr = hero.STR() - STRReq();
                if (exStr > 0) {
                    damage += Random.IntRange(0, exStr);
                }
                return damage;
            }
        }
        return super.damageRoll(owner);
    }
}
