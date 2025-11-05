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

package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.watabou.noosa.Image;

public enum Icons {
	
	//button icons
	CHECKED,
	UNCHECKED,
	INFO,
	CHALLENGE_OFF,
	CHALLENGE_ON,
	PREFS,
	LANGS,
	EXIT,
	CLOSE,
	ARROW,
	DISPLAY,
	DATA,
	AUDIO,
	TALENT,
	SKIN,

	//ingame UI icons
	SKULL,
	BUSY,
	COMPASS,
	SLEEP,
	ALERT,
	LOST,
	TARGET,
	BACKPACK,
	SEED_POUCH,
	SCROLL_HOLDER,
	POTION_BANDOLIER,
	WAND_HOLSTER,
	ICE_BOX,
	HERO_CHANGES,
	INFO_CERT,
	
	//hero & rankings icons
	DEPTH,
	WARRIOR,
	MAGE,
	RED,
	HUNTRESS,
	ROSEMON,
	NEARLS_S,
	COMBO,
	RISKARM_S,
	CHEN_S,

	//main menu icons
	ENTER,
	GOLD,
	RANKINGS,
	BADGES,
	NEWS,
	CHANGES,
	SHPX,
	
	//misc icons
	LIBGDX,
	WATA,
	WARNING,
	PATRIOT,

	//32x32 icons for credits
	ALEKS,
	CHARLIE,
	CUBE_CODE,
	PURIGRO,
	ARCNOR,
	ARKPD,
	NAMSEK,
	MIZQ,
    DANWO,

	BLAZE,
	AMIYA,
	P_RED,
	GREY,
	NEARL,
	ROSEMARI,
	RISKARM,
	CHEN,
	HYPER,
	MONNY,

	//변경화면용 아이콘
	BUG_KILL,
	ENEMY_BUFFS,
	ENEMY_NERFS,
	SKILL,
	WEP_ARROW,
	WEP,
	WEP_WOND,
	RING,
	ARTI,
	POTION,
	STONE,
	SCROLL,
	BOMB;



	public Image get() {
		return get( this );
	}
	
	public static Image get( Icons type ) {
		Image icon = new Image( Assets.Interfaces.ICONS );
		switch (type) {
		case CHECKED:
			icon.frame( icon.texture.uvRect( 0, 0, 16, 12 ) );
			break;
		case UNCHECKED:
			icon.frame( icon.texture.uvRect( 16, 0, 32, 12 ) );
			break;
		case INFO:
			icon.frame( icon.texture.uvRect( 32, 0, 46, 14 ) );
			break;
		case CHALLENGE_ON:
			icon.frame( icon.texture.uvRect( 47, 0, 65, 16 ) );
			break;
		case CHALLENGE_OFF:
			icon.frame( icon.texture.uvRect( 65, 0, 83, 16 ) );
			break;
		case PREFS:
			icon.frame( icon.texture.uvRect( 83, 0, 95, 12 ) );
			break;
		case LANGS:
			icon.frame( icon.texture.uvRect( 97, 0, 109, 12 ) );
			break;
		case EXIT:
			icon.frame( icon.texture.uvRect( 113, 0, 127, 14 ) );
			break;
			case SKIN:
				icon.frame( icon.texture.uvRect( 128, 0, 143, 13 ) );
				break;
		case CLOSE:
			icon.frame( icon.texture.uvRect( 0, 16, 11, 27 ) );
			break;
		case ARROW:
			icon.frame( icon.texture.uvRect( 16, 16, 27, 27 ) );
			break;
		case DISPLAY:
			icon.frame( icon.texture.uvRect( 32, 16, 45, 32 ) );
			break;
		case DATA:
			icon.frame( icon.texture.uvRect( 48, 16, 64, 31 ) );
			break;
		case AUDIO:
			icon.frame( icon.texture.uvRect( 64, 16, 78, 30 ) );
			break;
		case TALENT:
			icon.frame( icon.texture.uvRect( 80, 16, 93, 29 ) );
			break;
		case SKULL:
			icon.frame( icon.texture.uvRect( 0, 32, 8, 40 ) );
			break;
		case BUSY:
			icon.frame( icon.texture.uvRect( 8, 32, 16, 40 ) );
			break;
		case COMPASS:
			icon.frame( icon.texture.uvRect( 0, 40, 7, 45 ) );
			break;
		case SLEEP:
			icon.frame( icon.texture.uvRect( 0, 224, 10, 234 ) );
			break;
		case ALERT:
			icon.frame( icon.texture.uvRect( 10, 224, 20, 235 ) );
			break;
		case LOST:
			icon.frame( icon.texture.uvRect( 20, 224, 30, 234 ) );
			break;
		case TARGET:
			icon.frame( icon.texture.uvRect( 32, 32, 48, 48 ) );
			break;
		case BACKPACK:
			icon.frame( icon.texture.uvRect( 48, 32, 58, 42 ) );
			break;
		case SCROLL_HOLDER:
			icon.frame( icon.texture.uvRect( 58, 32, 68, 42 ) );
			break;
		case SEED_POUCH:
			icon.frame( icon.texture.uvRect( 68, 32, 78, 42 ) );
			break;
		case WAND_HOLSTER:
			icon.frame( icon.texture.uvRect( 78, 32, 88, 42 ) );
			break;
		case POTION_BANDOLIER:
			icon.frame( icon.texture.uvRect( 88, 32, 98, 42 ) );
			break;
			case ICE_BOX:
				icon.frame( icon.texture.uvRect( 115, 68, 127, 78 ) );
				break;
			case INFO_CERT:
				icon.frame( icon.texture.uvRect( 127, 64, 143, 82 ) );
				break;
			case HERO_CHANGES:
				icon.frame( icon.texture.uvRect( 143, 64, 166, 82 ) );
				break;
			
		case DEPTH:
			icon.frame( icon.texture.uvRect( 0, 48, 14, 64 ) );
			break;
		case WARRIOR:
			icon.frame( icon.texture.uvRect( 16, 48, 32, 61 ) );
			break;
		case MAGE:
			icon.frame( icon.texture.uvRect( 32, 48, 48, 61 ) );
			break;
		case RED:
			icon.frame( icon.texture.uvRect( 48, 48, 64, 61 ) );
			break;
		case HUNTRESS:
			icon.frame( icon.texture.uvRect( 64, 48, 80, 61 ) );
			break;
			case ROSEMON:
				icon.frame( icon.texture.uvRect( 96, 48, 112, 61 ) );
				break;
			case NEARLS_S:
				icon.frame( icon.texture.uvRect( 80, 48, 96, 61 ) );
				break;
			case COMBO:
				icon.frame( icon.texture.uvRect( 112, 48, 128, 64 ) );
				break;
			case RISKARM_S:
				icon.frame( icon.texture.uvRect( 128, 48, 144, 64 ) );
				break;
			case CHEN_S:
				icon.frame( icon.texture.uvRect( 144, 48, 160, 64 ) );
				break;
			case ENTER:
			icon.frame( icon.texture.uvRect( 0, 64, 16, 80 ) );
			break;
		case RANKINGS:
			icon.frame( icon.texture.uvRect( 17, 64, 34, 80 ) );
			break;
		case BADGES:
			icon.frame( icon.texture.uvRect( 34, 64, 50, 80 ) );
			break;
		case NEWS:
			icon.frame( icon.texture.uvRect( 51, 64, 67, 79 ) );
			break;
		case CHANGES:
			icon.frame( icon.texture.uvRect( 68, 64, 83, 79 ) );
			break;
		case SHPX:
			icon.frame( icon.texture.uvRect( 83, 64, 99, 80 ) );
			break;
		case GOLD:
			icon.frame( icon.texture.uvRect( 99, 64, 115, 81 ) );
			break;
		
		case LIBGDX:
			icon.frame( icon.texture.uvRect( 0, 81, 16, 94 ) );
			break;
		case WATA:
			icon.frame( icon.texture.uvRect( 17, 81, 34, 93 ) );
			break;
		case WARNING:
			icon.frame(icon.texture.uvRect(34, 81, 48, 95));
			break;

			case CUBE_CODE:
				icon.frame( icon.texture.uvRect( 101, 16, 128, 46 ) );
				icon.scale.set(PixelScene.align(0.49f));
				break;



			case ALEKS:
				icon.frame( icon.texture.uvRect( 0, 96, 32, 128 ) );
				icon.scale.set(PixelScene.align(0.49f));
				break;
			case CHARLIE:
				icon.frame( icon.texture.uvRect( 32, 96, 64, 128 ) );
				icon.scale.set(PixelScene.align(0.49f));
				break;
			case ARCNOR:
				icon.frame( icon.texture.uvRect( 64, 96, 96, 128 ) );
				icon.scale.set(PixelScene.align(0.49f));
				break;
			case PURIGRO:
				icon.frame( icon.texture.uvRect( 96, 96, 128, 128 ) );
				icon.scale.set(PixelScene.align(0.49f));
				break;
			//타이틀 이미지들 ↓
			case ARKPD:
				icon.frame( icon.texture.uvRect( 0, 192, 32, 224 ) );
				icon.scale.set(PixelScene.align(0.49f));
				break;
			case NAMSEK:
				icon.frame( icon.texture.uvRect( 32, 192, 64, 224 ) );
				icon.scale.set(PixelScene.align(0.49f));
				break;
			case MIZQ:
				icon.frame( icon.texture.uvRect( 64, 192, 96, 224 ) );
				icon.scale.set(PixelScene.align(0.49f));
				break;
			case HYPER:
				icon.frame(icon.texture.uvRect(96, 192, 128, 224));
				icon.scale.set(PixelScene.align(0.49f));
				break;
            case DANWO:
                icon.frame(icon.texture.uvRect(128, 192, 160, 224));
                icon.scale.set(PixelScene.align(0.49f));
                break;

				//캐릭터 선택창 이미지
			case BLAZE:
				icon.frame(icon.texture.uvRect(0, 128, 20, 148));
				break;
			case AMIYA:
				icon.frame(icon.texture.uvRect(20, 128, 40, 148));
				break;
			case P_RED:
				icon.frame(icon.texture.uvRect(40, 128, 60, 148));
				break;
			case GREY:
				icon.frame(icon.texture.uvRect(60, 128, 80, 148));
				break;
			case NEARL:
				icon.frame(icon.texture.uvRect(80, 128, 100, 148));
				break;
			case ROSEMARI:
				icon.frame(icon.texture.uvRect(100, 128, 120, 148));
				break;
			case MONNY:
				icon.frame(icon.texture.uvRect(0, 148, 20, 168));
				break;
			case RISKARM:
				icon.frame(icon.texture.uvRect(120, 128, 140, 148));
				break;
			case CHEN:
				icon.frame(icon.texture.uvRect(140, 128, 160, 148));
				break;
			//변경화면 아이콘들
			case BUG_KILL:
				icon.frame(icon.texture.uvRect(40, 148, 60, 168));
				break;
			case ENEMY_BUFFS:
				icon.frame(icon.texture.uvRect(48, 80, 64, 95));
				break;
			case ENEMY_NERFS:
				icon.frame(icon.texture.uvRect(64, 81, 80, 95));
				break;
			case SKILL:
				icon.frame(icon.texture.uvRect(80, 81, 96, 95));
				break;
			case WEP_ARROW:
				icon.frame(icon.texture.uvRect(60, 148, 80, 168));
				break;
			case WEP:
				icon.frame(icon.texture.uvRect(80, 148, 100, 168));
				break;
			case WEP_WOND:
				icon.frame(icon.texture.uvRect(100, 148, 120, 168));
				break;
			case RING:
				icon.frame(icon.texture.uvRect(0, 168, 12, 188));
				break;
			case ARTI:
				icon.frame(icon.texture.uvRect(12, 168, 32, 188));
				break;
			case POTION:
				icon.frame(icon.texture.uvRect(32, 168, 51, 188));
				break;
			case STONE:
				icon.frame(icon.texture.uvRect(51, 168, 68, 188));
				break;
			case SCROLL:
				icon.frame(icon.texture.uvRect(68, 168, 86, 188));
				break;
			case BOMB:
				icon.frame(icon.texture.uvRect(86, 168, 105, 189));
				break;
		}
		return icon;
	}
	
	public static Image get( HeroClass cl ) {
		switch (cl) {
		case WARRIOR:
			return get( WARRIOR );
		case MAGE:
			return get( MAGE );
		case ROGUE:
			return get( RED );
		case HUNTRESS:
			return get( HUNTRESS );
		case ROSECAT:
			return get( ROSEMON );
		case NEARL:
			return get(NEARLS_S);
		case CHEN: //첸포인트
			return get(CHEN_S);
		default:
			return get(WARRIOR);
		}
	}
}
