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

package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.GameMath;

public class GuerrillaSprite extends MobSprite {
	
	public GuerrillaSprite() {
		super();
		
		texture( Assets.Sprites.GUERRILLA );
		
		TextureFilm frames = new TextureFilm( texture, 42, 42 );
		
		idle = new Animation( 2, true );
		idle.frames( frames, 0, 0, 0, 0 );
		
		run = new Animation( 10, true );
		run.frames( frames, 1, 2, 3, 4, 5, 6, 7, 8 );
		
		attack = new Animation( 12, false );
		attack.frames( frames, 1, 3 );
		
		die = new Animation( 5, false );
		die.frames( frames, 0 );
		
		play( idle );
	}

	private static int[] tierFrames = {0, 20, 40, 60, 80, 100};

	public void setArmor( int tier ){
		int c = tierFrames[(int)GameMath.gate(0, tier, 5)];

		TextureFilm frames = new TextureFilm( texture, 42, 42 );

		idle.frames( frames, 0+c, 0+c, 0+c, 0+c, 0+c);
		run.frames( frames, 1+c, 2+c, 3+c, 4+c, 5+c, 6+c, 7+c );
		attack.frames( frames, 1+c, 3+c );
		//death animation is always armorless

		play( idle, true );

	}

	@Override
	public int blood() {
		return 0xFFcdcdb7;
	}
}
