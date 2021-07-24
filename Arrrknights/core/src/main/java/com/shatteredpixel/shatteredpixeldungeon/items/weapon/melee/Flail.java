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
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Flail extends MeleeWeapon {

	{
		image = ItemSpriteSheet.FLAIL;
		hitSound = Assets.Sounds.HIT_CRUSH;
		hitSoundPitch = 0.8f;

		tier = 5;
		ACC = 1.2f; //0.9x accuracy
		//also cannot surprise attack, see Hero.canSurpriseAttack
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		int thisHP = attacker.HP;
		int thisHT = attacker.HT;
		if (thisHP <= thisHT/4) {
			damage *= 1.5f;

			if (Random.Int(2) < 1) {
				int healAmt = Math.round(damage * 0.1f);
				healAmt = Math.min(healAmt, attacker.HT - attacker.HP);

				if (healAmt > 0 && attacker.isAlive()) {

					attacker.HP += healAmt;
					attacker.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f, 1);
					attacker.sprite.showStatus(CharSprite.POSITIVE, Integer.toString(healAmt));

				}
			}
		}
		else if (thisHP <= thisHT / 2){
			damage *= 1.3f;
			if (Random.Int(4) < 1) {
				int healAmt = Math.round(damage * 0.1f);
				healAmt = Math.min(healAmt, attacker.HT - attacker.HP);

				if (healAmt > 0 && attacker.isAlive()) {

					attacker.HP += healAmt;
					attacker.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f, 1);
					attacker.sprite.showStatus(CharSprite.POSITIVE, Integer.toString(healAmt));

				}
			}
		}
		else if (thisHP <= thisHT - thisHT/4) {
			damage *= 1.1f;
		}
		return super.proc(attacker, defender, damage);
	}
}
