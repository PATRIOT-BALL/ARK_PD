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
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Greatsword extends MeleeWeapon {

	{
		image = ItemSpriteSheet.GREATSWORD;
		hitSound = Assets.Sounds.HIT_CHAINSAW2;
		hitSoundPitch = 1f;


		tier=5;
		DLY = 0.5f;
		ACC = 0.85f;
		RCH = 2;

	}
	@Override
	public int max(int lvl) {
		return  3*(tier+1) +    // 18 + 3
				lvl*(tier-1);   //scaling unchanged
	}

}
