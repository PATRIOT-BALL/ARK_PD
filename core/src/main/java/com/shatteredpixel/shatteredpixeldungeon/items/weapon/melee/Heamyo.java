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
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Pompeii;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.SiestaBoss;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Heamyo extends MeleeWeapon {

    {
        image = ItemSpriteSheet.HEAMYO;
        hitSound = Assets.Sounds.GOLD;

        tier = 1;
        ACC = 12000f;
        DLY = 0.1f; //0.67x speed
        RCH = 300;
    }

    @Override
    public int max(int lvl) {
        return  100000000;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
      //  defender.sprite.killAndErase();
     //   defender.destroy();

        return super.proc(attacker, defender, damage);
    }

    @Override
    public int defenseFactor( Char owner ) {
        return 600+300*buffedLvl();    //6 extra defence, plus 3 per level;
    }

    public String statsInfo(){
        if (isIdentified()){
            return Messages.get(this, "stats_desc", 6+3*buffedLvl());
        } else {
            return Messages.get(this, "typical_stats_desc", 6);
        }
    }
}