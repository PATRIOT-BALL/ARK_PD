package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Piranha;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Skeleton;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Firesteel extends MissileWeapon {
    {
        image = ItemSpriteSheet.FLINT;
        hitSound = Assets.Sounds.HIT;
        hitSoundPitch = 1.1f;

        bones = false;

        tier = 1;
        baseUses = 6;
        sticky = false;
    }

    private Char enemy;

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (defender instanceof Skeleton){
            damage = 999;
        }
        return super.proc(attacker, defender, damage);
    }

    @Override
    public int value() {
        return super.value() / 2; //half normal value
    }
}
