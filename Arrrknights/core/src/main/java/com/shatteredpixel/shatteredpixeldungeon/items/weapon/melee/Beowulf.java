/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Beowulf extends MeleeWeapon {

    {
        image = ItemSpriteSheet.BEOWULF;
        hitSound = Assets.Sounds.HIT_BONK;
        hitSoundPitch = 1f;

        tier = 5;
        RCH = 4;
        ACC = 1.2f; //32% boost to accuracy
    }


    @Override
    public int max(int lvl) {
        return  4*(tier+1) +    // 24 + 5
                lvl*(tier+1);   //scaling unchanged
    }

    // 공격시 대상과의 거리에 따라 공격력 25~100%의 3x3범위 범위 피해 (25% / 33% / 50% / 100)

    @Override
    public int proc(Char attacker, Char defender, int damage) {

        int sppos = Dungeon.level.distance(attacker.pos, defender.pos);

        if (attacker instanceof Hero) {
            for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
                if (Dungeon.level.adjacent(mob.pos, defender.pos) && mob.alignment != Char.Alignment.ALLY) {
                    int dmg = (Dungeon.hero.damageRoll() - defender.drRoll()) / Math.max(1, 5 - sppos);

                    mob.damage(dmg, this);
                }
            }
        }

        return super.proc(attacker, defender, damage);
    }
}
