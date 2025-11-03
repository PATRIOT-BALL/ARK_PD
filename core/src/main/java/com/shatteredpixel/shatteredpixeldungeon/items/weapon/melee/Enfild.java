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
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Enfild extends MeleeWeapon {

    {
        image = ItemSpriteSheet.ENFILD;
        hitSound = Assets.Sounds.SKILL_BEEP;
        hitSoundPitch = 0.9f;

        tier = 2;
        DLY = 1.5f; //0.67x speed
        RCH = 50;    //extra reach
    }

    @Override
    public int min(int lvl) { return  9 + buffedLvl() * 2; }

    @Override
    public int max(int lvl) {return  9 + buffedLvl() * 2; }

    @Override
    public int value() { return super.value() + 60; }

    @Override
    public boolean isUpgradable() { return true; }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (charge >= chargeCap) {
            damage *= 1.3f;
            charge = 0;
        }
        else SPCharge(20);
        return super.proc(attacker, defender, damage);
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
