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

package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MeatPower_Stewed;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WellFed;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.AnnihilationGear;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class SmallRation extends Food {

	{
		image = ItemSpriteSheet.OVERPRICED;
		energy = Hunger.HUNGRY/2f;
	}

	@Override
	protected void satisfy(Hero hero) {
		float EN = energy;
		if (hero.hasTalent(Talent.DELICIOUS_FOOD)) {
			EN *= 1 + hero.pointsInTalent(Talent.DELICIOUS_FOOD) / 2;
		}

		if (Dungeon.isChallenged(Challenges.NO_FOOD)){
			Buff.affect(hero, Hunger.class).satisfy(EN/3f);
		} else {
			Buff.affect(hero, Hunger.class).satisfy(EN);
		}

		effect(hero);
	}

	public static void effect(Hero hero){
		if (hero.hasTalent(Talent.DELICIOUS_FOOD))
		{
			AnnihilationGear Gear = hero.belongings.getItem(AnnihilationGear.class);
			if (hero.belongings.getItem(AnnihilationGear.class) != null && Gear.charge < Gear.chargeCap)
			{
				Gear.SPCharge(hero.pointsInTalent(Talent.DELICIOUS_FOOD));
			}
		}
	}


	@Override
	public int value() {
		return 10 * quantity;
	}
}
