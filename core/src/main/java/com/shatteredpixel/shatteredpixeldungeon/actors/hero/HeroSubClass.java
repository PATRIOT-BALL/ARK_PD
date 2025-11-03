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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public enum HeroSubClass {

	NONE,

	GLADIATOR,
	BERSERKER,
	HEAT,

	WARLOCK,
	BATTLEMAGE,
	CHAOS,

	ASSASSIN,
	FREERUNNER,
	WILD,

	SNIPER,
	WARDEN,
	STOME,

	DESTROYER,
	GUARDIAN,
	WAR,

	KNIGHT,
	SAVIOR,
	FLASH,

	SWORDMASTER,
	SPSHOOTER;

	public String title() {
		return Messages.get(this, name());
	}

	public String shortDesc() {
		return Messages.get(this, name()+"_short_desc");
	}

	public String desc() {
		//Include the staff effect description in the battlemage's desc if possible
		if (this == BATTLEMAGE){
			String desc = Messages.get(this, name() + "_desc");
			if (Game.scene() instanceof GameScene){
				MagesStaff staff = Dungeon.hero.belongings.getItem(MagesStaff.class);
				if (staff != null && staff.wandClass() != null){
					desc += "\n\n" + Messages.get(staff.wandClass(), "bmage_desc");
					desc = desc.replaceAll("_", "");
				}
			}
			return desc;
		} else {
			return Messages.get(this, name() + "_desc");
		}
	}

	private static final String SUBCLASS	= "subClass";

	public void storeInBundle( Bundle bundle ) {
		bundle.put( SUBCLASS, toString() );
	}

	public static HeroSubClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( SUBCLASS );
		return valueOf( value );
	}

	public Image icon(){
		switch (this){
			case GLADIATOR: default:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 0, 80, 16, 16);
			case BERSERKER:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 16, 80, 16, 16);
			case HEAT:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 0, 96, 16, 16);

			case WARLOCK:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 32, 80, 16, 16);
			case BATTLEMAGE:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 48, 80, 16, 16);
			case CHAOS:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 208, 80, 16, 16);

			case ASSASSIN:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 64, 80, 16, 16);
			case FREERUNNER:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 80, 80, 16, 16);

			case SNIPER:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 96, 80, 16, 16);
			case WARDEN:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 112, 80, 16, 16);
			case STOME:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 240, 80, 16, 16);

			case DESTROYER:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 128, 80, 16, 16);
			case GUARDIAN:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 144, 80, 16, 16);

			case KNIGHT:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 160, 80, 16, 16);
			case SAVIOR:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 176, 80, 16, 16);
			case FLASH:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 224, 80, 16, 16);


			case WAR:
				return new Image(Assets.Interfaces.BUFFS_LARGE, 192, 80, 16, 16);
		}
	}
	
}
