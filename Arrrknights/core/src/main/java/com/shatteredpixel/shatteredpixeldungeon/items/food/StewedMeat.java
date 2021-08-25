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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MeatPower_Chargrilled;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MeatPower_Stewed;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WellFed;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.Recipe;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class StewedMeat extends Food {
	
	{
		image = ItemSpriteSheet.STEWED;
		energy = Hunger.HUNGRY/2f;
	}

	@Override
	protected void satisfy(Hero hero) {
		super.satisfy(hero);
		effect(hero);
	}

	public static void effect(Hero hero){
		if (Dungeon.hero.subClass == HeroSubClass.DESTROYER) { Buff.affect(hero, MeatPower_Stewed.class, MeatPower_Stewed.DURATION); }
		if (hero.hasTalent(Talent.LOVEMEAT))
		{
			Buff.affect(hero, WellFed.class).set(hero.pointsInTalent(Talent.LOVEMEAT) * 20);
			hero.HP = Math.min(Dungeon.hero.HP+hero.pointsInTalent(Talent.LOVEMEAT) * 3, hero.HT);
		}
	}
	
	@Override
	public int value() {
		return 8 * quantity;
	}
	
	public static class oneMeat extends Recipe.SimpleRecipe{
		{
			inputs =  new Class[]{MysteryMeat.class};
			inQuantity = new int[]{1};
			
			cost = 2;
			
			output = StewedMeat.class;
			outQuantity = 1;
		}
	}
	
	public static class twoMeat extends Recipe.SimpleRecipe{
		{
			inputs =  new Class[]{MysteryMeat.class};
			inQuantity = new int[]{2};
			
			cost = 3;
			
			output = StewedMeat.class;
			outQuantity = 2;
		}
	}
	
	//red meat
	//blue meat
	
	public static class threeMeat extends Recipe.SimpleRecipe{
		{
			inputs =  new Class[]{MysteryMeat.class};
			inQuantity = new int[]{3};
			
			cost = 4;
			
			output = StewedMeat.class;
			outQuantity = 3;
		}
	}

}
