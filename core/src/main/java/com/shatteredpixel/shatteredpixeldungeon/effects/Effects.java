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

package com.shatteredpixel.shatteredpixeldungeon.effects;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.Image;

public class Effects {

	public enum  Type {
		RIPPLE,
		LIGHTNING,
		WOUND,
		EXCLAMATION,
		CHAIN,
		DEATH_RAY,
		LIGHT_RAY,
		HEALTH_RAY
	}
	
	public static Image get( Type type ) {
		Image icon = new Image( Assets.Effects.EFFECTS );
		switch (type) {
			case RIPPLE:
				icon.frame(icon.texture.uvRect(0, 0, 16, 16));
				break;
			case LIGHTNING:
				icon.frame(icon.texture.uvRect(0, 48, 16, 64));
				break;
			case WOUND:
				icon.frame(icon.texture.uvRect(16, 6, 32, 17));
				break;
			case EXCLAMATION:
				icon.frame(icon.texture.uvRect(0, 32, 16, 48));
				break;
			case CHAIN:
				icon.frame(icon.texture.uvRect(6, 16, 12, 23));
				break;
			case DEATH_RAY:
				icon.frame(icon.texture.uvRect(16, 17, 32, 23));
				break;
			case LIGHT_RAY:
				icon.frame(icon.texture.uvRect(16, 24, 32, 30));
				break;
			case HEALTH_RAY:
				icon.frame(icon.texture.uvRect(16, 31, 32, 37));
				break;
		}
		return icon;
	}
}
