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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAccuracy;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Castlebreaker extends MeleeWeapon {
	public static final String AC_ZAP = "ZAP";

	{
		image = ItemSpriteSheet.SCIMITAR;
		hitSound = Assets.Sounds.HIT_SPEAR;
		hitSoundPitch = 1.2f;

		tier = 3;
		DLY = 0.8f; //1.25x speed

		defaultAction = AC_ZAP;
		usesTargeting = true;
	}

	private int charge = 100;
	private int chargeCap = 100;

	@Override
	public int max(int lvl) {
		return  4*(tier+1) +    //15 base, down from 20
				lvl*(tier+1) - 1;   //scaling unchanged
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		int charged = 4;
		if (Dungeon.hero.belongings.getItem(RingOfAccuracy.class) != null) {
			if (Dungeon.hero.belongings.getItem(RingOfAccuracy.class).isEquipped(Dungeon.hero)) {
				charged*=2;
			}}
		SPCharge(charged);
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

		if (Dungeon.hero.belongings.weapon == this) {
			if (action.equals(AC_ZAP) && charge >= 40) {
				if (this.cursed != true) {
					cursedKnown = true;
					GameScene.selectCell(zapper);
				} else {
					Buff.affect(Dungeon.hero, Paralysis.class, 2f);
					cursedKnown = true;
					charge -= 40;
				}
			}
		}
	}

	@Override
	public String statsInfo() {
		return Messages.get(this, "stats_desc", 4+buffedLvl() * 3);
	}

	@Override
	public String desc() {
		String info = Messages.get(this, "desc");
		if (Dungeon.hero.belongings.getItem(RingOfAccuracy.class) != null) {
			if (Dungeon.hero.belongings.getItem(RingOfAccuracy.class).isEquipped(Dungeon.hero))
				info += "\n\n" + Messages.get( Castlebreaker.class, "setbouns");}

		return info;
	}

	public void SPCharge(int n) {
			charge += n;
			if (chargeCap < charge) charge = chargeCap;
			updateQuickslot();
	}

	@Override
	public String status() {
		if (chargeCap == 100)
			return Messages.format("%d%%", charge);


		//otherwise, if there's no charge, return null.
		return null;
	}

	private static final String CHARGE = "charge";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		if (chargeCap > 0) charge = Math.min(chargeCap, bundle.getInt(CHARGE));
		else charge = bundle.getInt(CHARGE);
	}

	protected static CellSelector.Listener zapper = new CellSelector.Listener() {

		@Override
		public void onSelect(Integer target) {

			if (target != null) {

				final Castlebreaker ss;
				if (curItem instanceof Castlebreaker) {
					Sample.INSTANCE.play( Assets.Sounds.HIT_GUNLANCE, 1f);
					ss = (Castlebreaker) Castlebreaker.curItem;

					Ballistica shot = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
					int cell = shot.collisionPos;

					if (target == curUser.pos || cell == curUser.pos) {
						GLog.i(Messages.get(Castlebreaker.class, "self_target"));
						return;
					}

					curUser.sprite.zap(cell);

					//attempts to target the cell aimed at if something is there, otherwise targets the collision pos.
					if (Actor.findChar(target) != null)
						QuickSlotButton.target(Actor.findChar(target));
					else
						QuickSlotButton.target(Actor.findChar(cell));

					if (ss.tryToZap(curUser, target)) {
						ss.fx(shot, new Callback() {
							public void call() {
								ss.onZap(shot);
							}
						});
					}


				}
			}
		}

		@Override
		public String prompt() {
			return Messages.get(Castlebreaker.class, "prompt");
		}
	};

	protected void fx( Ballistica bolt, Callback callback ) {
		MagicMissile.boltFromChar( curUser.sprite.parent,
				MagicMissile.FORCE,
				curUser.sprite,
				bolt.collisionPos,
				callback);
	}

	public boolean tryToZap(Hero owner, int target) {
		return true;
	}


	protected void onZap( Ballistica bolt ) {
		Char ch = Actor.findChar( bolt.collisionPos );
		if (ch != null) {
			int dmg = Random.Int(3, 4+buffedLvl() * 3);
			ch.damage(dmg, this);
			ch.damage(dmg, this);
			ch.damage(dmg, this);

			ch.sprite.burst(0xFFFFFFFF, buffedLvl() / 2 + 2);

		} else {
			Dungeon.level.pressCell(bolt.collisionPos);
		}
		charge -=40;
		updateQuickslot();
		curUser.spendAndNext(0.8f);
	}

}
