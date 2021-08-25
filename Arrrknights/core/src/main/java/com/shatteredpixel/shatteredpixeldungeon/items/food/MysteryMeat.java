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

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MeatPower_Mystery;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WellFed;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.AnnihilationGear;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class MysteryMeat extends Food {

	{
		image = ItemSpriteSheet.MEAT;
		energy = Hunger.HUNGRY/2f;
	}
	
	@Override
	protected void satisfy(Hero hero) {
		super.satisfy(hero);
		effect(hero);
	}

	public int value() {
		return 5 * quantity;
	}

	public static void effect(Hero hero){
		if (Dungeon.hero.subClass == HeroSubClass.DESTROYER) { Buff.affect(hero, MeatPower_Mystery.class, MeatPower_Mystery.DURATION); }

		if (Dungeon.hero.hasTalent(Talent.GOODMEAT)) {
			if (Dungeon.hero.pointsInTalent(Talent.GOODMEAT) != 2) {
				switch (Random.Int(8)) {
					case 0:
						GLog.w(Messages.get(MysteryMeat.class, "hot"));
						Buff.affect(hero, Burning.class).reignite(hero);
						break;
					case 1:
						GLog.w(Messages.get(MysteryMeat.class, "legs"));
						Buff.prolong(hero, Roots.class, Roots.DURATION * 2f);
						break;
					case 2:
						GLog.w(Messages.get(MysteryMeat.class, "not_well"));
						Buff.affect(hero, Poison.class).set(hero.HT / 5);
						break;
					case 3:
						GLog.w(Messages.get(MysteryMeat.class, "stuffed"));
						Buff.prolong(hero, Slow.class, Slow.DURATION);
						break;
				}
			}

		}
		else {
			switch (Random.Int(5)) {
				case 0:
					GLog.w(Messages.get(MysteryMeat.class, "hot"));
					Buff.affect(hero, Burning.class).reignite(hero);
					break;
				case 1:
					GLog.w(Messages.get(MysteryMeat.class, "legs"));
					Buff.prolong(hero, Roots.class, Roots.DURATION * 2f);
					break;
				case 2:
					GLog.w(Messages.get(MysteryMeat.class, "not_well"));
					Buff.affect(hero, Poison.class).set(hero.HT / 5);
					break;
				case 3:
					GLog.w(Messages.get(MysteryMeat.class, "stuffed"));
					Buff.prolong(hero, Slow.class, Slow.DURATION);
					break;
			}
		}

		if (hero.hasTalent(Talent.GOODMEAT))
		{
			AnnihilationGear Gear = hero.belongings.getItem(AnnihilationGear.class);
			if (hero.belongings.getItem(AnnihilationGear.class) != null && Gear.charge < Gear.chargeCap)
			{
				Gear.SPCharge(1);
			}
		}

		if (hero.hasTalent(Talent.LOVEMEAT))
		{
			Buff.affect(hero, WellFed.class).set(hero.pointsInTalent(Talent.LOVEMEAT) * 20);
			hero.HP = Math.min(Dungeon.hero.HP+hero.pointsInTalent(Talent.LOVEMEAT) * 3, hero.HT);
		}
	}
	
	public static class PlaceHolder extends MysteryMeat {
		
		{
			image = ItemSpriteSheet.FOOD_HOLDER;
		}
		
		@Override
		public boolean isSimilar(Item item) {
			return item instanceof MysteryMeat || item instanceof StewedMeat
					|| item instanceof ChargrilledMeat || item instanceof FrozenCarpaccio;
		}
		
		@Override
		public String info() {
			return "";
		}
	}
}
