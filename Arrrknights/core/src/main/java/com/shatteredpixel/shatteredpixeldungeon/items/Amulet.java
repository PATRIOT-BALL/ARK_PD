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

package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.TomorrowRogueNight;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.AmuletScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

import java.io.IOException;
import java.util.ArrayList;

public class Amulet extends Item {

    private static final String AC_END = "END";

    {
        image = ItemSpriteSheet.AMULET;

        unique = true;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_END);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        GameScene.cancel();
        curUser = hero;
        curItem = this;
        //GLog.w(Messages.get(Hero.class, "name", curUser.pos)); // 플레이어의 현재 위치를 가짐. 오브젝트 배치할 때 쓰려고.
       // new ScrollOfMagicMapping().collect();

        if (action.equals(AC_END)) {
            showAmuletScene(false);
        } else if (action.equals(AC_DROP)) {
            Game.runOnRenderThread(new Callback() {
                @Override
                public void call() {
                    GameScene.show(new WndMessage(Messages.get(Amulet.class, "fail_drop") ));
                }
            });

        } else if (action.equals(AC_THROW)) {
            Game.runOnRenderThread(new Callback() {
                @Override
                public void call() {
                    GameScene.show(new WndMessage(Messages.get(Amulet.class, "fail_th") ));
                }
            });
        }
    }

    @Override
    public boolean doPickUp(Hero hero) {
        if (super.doPickUp(hero)) {

            if (!Statistics.amuletObtained) {
                Statistics.amuletObtained = true;
                hero.spend(-TIME_TO_PICK_UP);

                //add a delayed actor here so pickup behaviour can fully process.
                Actor.addDelayed(new Actor() {
                    @Override
                    protected boolean act() {
                        Actor.remove(this);
                        showAmuletScene(true);
                        return false;
                    }
                }, -5);
            }

            return true;
        } else {
            return false;
        }
    }

    private void showAmuletScene(boolean showText) {
        try {
            Dungeon.saveAll();
            AmuletScene.noText = !showText;
            Game.switchScene(AmuletScene.class, new Game.SceneChangeCallback() {
                @Override
                public void beforeCreate() {

                }

                @Override
                public void afterCreate() {
                    Badges.validateVictory();
                    Badges.validateChampion(Challenges.activeChallenges());
                    Badges.validateChampion_char(Challenges.activeChallenges());
                    Badges.saveGlobal();
                }
            });
        } catch (IOException e) {
            TomorrowRogueNight.reportException(e);
        }
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

}
