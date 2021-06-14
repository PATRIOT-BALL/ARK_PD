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

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Bundle;

public enum HeroSubClass {

	NONE( null ),
	
	GLADIATOR( "gladiator" ),
	BERSERKER( "berserker" ),
	
	WARLOCK( "warlock" ),
	BATTLEMAGE( "battlemage" ),
	
	ASSASSIN( "assassin" ),
	FREERUNNER( "freerunner" ),
	
	SNIPER( "sniper" ),
	WARDEN( "warden" ),

	DESTROYER("destroyer"),
	GUARDIAN("guardian");
	
	private String title;

	public String ChangeName() {
		if (this.title == "gladiator") {
			return Messages.get(this, "gladiator_changename");
		}
		else if (this.title == "berserker") {
			return Messages.get(this, "berserker_changename");
		}
		else if (this.title == "warlock") {
			return Messages.get(this, "warlock_changename");}
		else if (this.title == "battlemage") {
			return Messages.get(this, "battlemage_changename");}
		else if (this.title == "assassin") {
			return Messages.get(this, "assassin_changename");}
		else if (this.title == "freerunner") {
			return Messages.get(this, "freerunner_changename");}
		else if (this.title == "sniper") {
			return Messages.get(this, "sniper_changename");}
		else if (this.title == "warden") {
			return Messages.get(this, "warden_changename");}
		else if (this.title == "destroyer") {
			return Messages.get(this, "destroyer_changename");}
		else if (this.title == "guardian") {
			return Messages.get(this, "guardian_changename");}
		else
			return title;


	}
	
	HeroSubClass( String title ) {
		this.title = title;
	}
	
	public String title() {
		return Messages.get(this, title);
	}
	
	public String desc() {
		return Messages.get(this, title+"_desc");
	}
	
	private static final String SUBCLASS	= "subClass";
	
	public void storeInBundle( Bundle bundle ) {
		bundle.put( SUBCLASS, toString() );
	}
	
	public static HeroSubClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( SUBCLASS );
		return valueOf( value );
	}
	
}
