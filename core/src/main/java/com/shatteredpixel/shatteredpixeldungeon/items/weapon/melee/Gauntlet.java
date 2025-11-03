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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Twilight;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class Gauntlet extends MeleeWeapon {
	public static final String AC_ZAP = "ZAP";

	{
		image = ItemSpriteSheet.GAUNTLETS;
		hitSound = Assets.Sounds.HIT_PUNCH;
		hitSoundPitch = 1.2f;

		defaultAction = AC_ZAP;

		tier = 5;
		DLY = 0.2f; //2x speed
	}

	private boolean swiching = false;

	@Override
	public int max(int lvl) {
		if (swiching == false) {
			return Math.round(1f * (tier + 1)) +
					lvl * Math.round(0.5f * (tier + 1));
		} else {
			return Math.round(2f * (tier + 1)) +
					lvl * Math.round(1f * (tier + 1)); }
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_ZAP);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_ZAP)) {
		if (swiching == true) {swiching = false; DLY = 0.2f;}
		else {swiching = true; DLY = 0.33f;}

		updateQuickslot();
		curUser.spendAndNext(0.5f);}
		}

	@Override
	public String desc() {
		if (swiching) return Messages.get(this, "desc_mode");
		else return Messages.get(this, "desc");
	}

	@Override
	public String status() {
		if (this.isIdentified()) {
			if (swiching) return "EX";
			else return "NM";
		}
		else return null;}

	private static final String SWICH = "swiching";
	private static final String RCHSAVE = "RCH";
	private static final String DLYSAVE = "DLY";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(SWICH, swiching);
		bundle.put(RCHSAVE, RCH);
		bundle.put(DLYSAVE, DLY);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		swiching = bundle.getBoolean(SWICH);
		RCH = bundle.getInt(RCHSAVE);
		DLY = bundle.getFloat(DLYSAVE);
	}
}
