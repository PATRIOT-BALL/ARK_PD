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

        tier = 4;
        RCH = 4;
        ACC = 1.33f; //33% boost to accuracy
    }


    @Override
    public int max(int lvl) {
        return  8*(tier+1) +    // 40+8
                lvl*(tier+4);   //scaling unchanged
    }

    // 공격시 대상과의 거리가 4 이하면 피해량이 25%로 감소합니다.
    // 조합법 : 백파 무기 + 감자 2개 (연금술)

    @Override
    public int proc(Char attacker, Char defender, int damage) {

        int sppos = Dungeon.level.distance(attacker.pos, defender.pos);
        if (sppos < 4) {
            return super.proc(attacker, defender, damage / 4);
        }

        return super.proc(attacker, defender, damage);
    }
}
