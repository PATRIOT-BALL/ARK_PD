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
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Twilight;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class RunicBlade extends MeleeWeapon {
	public static final String AC_ZAP = "ZAP";
	{
		image = ItemSpriteSheet.RUNIC_BLADE;
		hitSound = Assets.Sounds.HIT_STAB;
		hitSoundPitch = 1f;

		defaultAction=AC_ZAP;

		tier = 4;
	}

	//Essentially it's a tier 4 weapon, with tier 3 base max damage, and tier 5 scaling.
	//equal to tier 4 in damage at +5

	@Override
	public int max(int lvl) {
		return  5*(tier) +                	//20 base, down from 25
				Math.round(lvl*(tier+2));	//+6 per level, up from +5
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		int recharge = damage / 4;
		recharge = Math.min(7, recharge);
		recharge = Math.max(2, recharge);

		SPCharge(recharge);

		updateQuickslot();
		return super.proc(attacker, defender, damage);
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
			if (Dungeon.hero.belongings.weapon == this) {
				if (charge >= chargeCap) {
					curUser.sprite.zap(0);
					PathFinder.buildDistanceMap(curUser.pos, BArray.not(Dungeon.level.solid, null), 2);
					for (int cell = 0; cell < PathFinder.distance.length; cell++) {
						if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
							Char ch = Actor.findChar(cell);
							if (ch != null&& !(ch instanceof Hero)) {
								ch.damage(Dungeon.hero.damageRoll() / 2, Dungeon.hero);
								ch.sprite.burst(CharSprite.NEGATIVE, 10);
								if (ch.isAlive()) Buff.prolong(ch, Paralysis.class, 2);
								 }}}


					Buff.affect(Dungeon.hero, ArtifactRecharge.class).set(6).ignoreHornOfPlenty=false;
					Sample.INSTANCE.play(Assets.Sounds.SKILL_TEXAS);
					charge = 0;
				}
			}
			else GLog.w(Messages.get(this, "dont_equ"));
		}
	}

	@Override
	public String status() {

		//if the artifact isn't IDed, or is cursed, don't display anything
		if (!isIdentified() || cursed) {
			return null;
		}
		//display as percent
		if (chargeCap == 100)
			return Messages.format("%d%%", charge);


		//otherwise, if there's no charge, return null.
		return null;
	}
}
