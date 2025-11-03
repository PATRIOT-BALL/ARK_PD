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

package com.shatteredpixel.shatteredpixeldungeon.scenes;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.TomorrowRogueNight;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.Archs;
import com.shatteredpixel.shatteredpixeldungeon.ui.ExitButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.ScrollPane;
import com.shatteredpixel.shatteredpixeldungeon.ui.StyledButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.ui.changelist.ChangeInfo;
import com.shatteredpixel.shatteredpixeldungeon.ui.changelist.v0_1_X_Changes;
import com.shatteredpixel.shatteredpixeldungeon.ui.changelist.v0_2_X_Changes;
import com.shatteredpixel.shatteredpixeldungeon.ui.changelist.v0_3_X_Changes;
import com.shatteredpixel.shatteredpixeldungeon.ui.changelist.v0_4_X_Changes;
import com.shatteredpixel.shatteredpixeldungeon.ui.changelist.v0_5_X_Changes;
import com.watabou.noosa.Camera;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.RectF;

import java.util.ArrayList;

public class ChangesScene extends PixelScene {
	
	public static int changesSelected = 0;
	
	@Override
	public void create() {
		super.create();

		RectF insets = getCommonInsets();

		if (Dungeon.depth == 5)
		{
			Music.INSTANCE.play(Assets.Music.BOSS, true);
		}
		else if (Dungeon.depth == 10)
		{
			Music.INSTANCE.play(Assets.Music.BOSS2, true);
		}
		else if (Dungeon.depth == 15)
		{
			if (Dungeon.hero.CharSkin== Hero.MUDROCK) 	Music.INSTANCE.play(Assets.Music.BOSS3_MUDROCKSP, true);
			else Music.INSTANCE.play(Assets.Music.BOSS3, true);
		}
		else if (Dungeon.depth == 20)
		{
			Music.INSTANCE.play(Assets.Music.BOSS4, true);
		}
		else if (Dungeon.depth == 25 || Dungeon.depth == 26)
		{
			Music.INSTANCE.play(Assets.Music.BOSS5, true);
		}
		else if (Dungeon.depth == 35)
		{
			if (Dungeon.extrastage_Gavial) Music.INSTANCE.play(Assets.Music.BOSS6_SARGON, true);
			else Music.INSTANCE.play(Assets.Music.BOSS6_SIESTA, true);
		}
		else if (Dungeon.depth == 40)
		{
			if (Dungeon.extrastage_Gavial) Music.INSTANCE.play(Assets.Music.BOSS6_SARGON2, true);
			else Music.INSTANCE.play(Assets.Music.BOSS6_SIESTA2, true);
		}
		else if (Dungeon.depth >= 1 && Dungeon.depth < 5)
		{
			Music.INSTANCE.play(Assets.Music.GAME, true);
		}
		else if (Dungeon.depth >= 6 && Dungeon.depth < 10)
		{
			Music.INSTANCE.play(Assets.Music.GAME2, true);
		}
		else if (Dungeon.depth >= 11 && Dungeon.depth < 15)
		{
			Music.INSTANCE.play(Assets.Music.GAME3, true);
		}
		else if (Dungeon.depth >= 16 && Dungeon.depth < 20)
		{
			Music.INSTANCE.play(Assets.Music.GAME4, true);
		}
		else if (Dungeon.depth >= 21 && Dungeon.depth < 25)
		{
			Music.INSTANCE.play(Assets.Music.GAME5, true);
		}
		else if (Dungeon.depth >= 27 && Dungeon.depth < 31) {
			Music.INSTANCE.play(Assets.Music.RHODOS, true);
		}
		else if (Dungeon.depth >= 31 && Dungeon.depth < 35) {
			if (Dungeon.extrastage_Gavial) Music.INSTANCE.play(Assets.Music.GAME6_SARGON1, true);
			else Music.INSTANCE.play(Assets.Music.GAME6_SIESTA, true);
		}
		else if (Dungeon.depth >= 36 && Dungeon.depth < 40) {
			if (Dungeon.extrastage_Gavial) Music.INSTANCE.play(Assets.Music.GAME6_SARGON2, true);
			else Music.INSTANCE.play(Assets.Music.GAME6_SIESTA2, true);
		}


		int w = Camera.main.width;
		int h = Camera.main.height;

		w -= insets.left + insets.right;
		h -= insets.top + insets.bottom;

		RenderedTextBlock title = PixelScene.renderTextBlock( Messages.get(this, "title"), 9 );
		title.hardlight(Window.TITLE_COLOR);
		title.setPos(
				insets.right + (w - title.width()) / 2f,
				insets.top + (20 - title.height()) / 2f
		);
		align(title);
		add(title);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width() - insets.left, insets.top );
		add( btnExit );

		NinePatch panel = Chrome.get(Chrome.Type.TOAST);

		int pw = 135 + panel.marginLeft() + panel.marginRight() - 2;
		int ph = h - 52 - (int) insets.bottom;

		panel.size( pw, ph );
		panel.x = (w - pw) / 2f;
		panel.y = title.bottom() + 5;
		align( panel );
		add( panel );
		
		final ArrayList<ChangeInfo> changeInfos = new ArrayList<>();
		
		switch (changesSelected){
			case 0: default:
				v0_5_X_Changes.addAllChanges(changeInfos);
				break;
			case 1:
				v0_4_X_Changes.addAllChanges(changeInfos);
				break;
			case 2:
				v0_3_X_Changes.addAllChanges(changeInfos);
				break;
			case 3:
				v0_2_X_Changes.addAllChanges(changeInfos);
				break;
			case 4:
				v0_1_X_Changes.addAllChanges(changeInfos);
		     	break;
		}

		ScrollPane list = new ScrollPane( new Component() ){

			@Override
			public void onClick(float x, float y) {
				for (ChangeInfo info : changeInfos){
					if (info.onClick( x, y )){
						return;
					}
				}
			}

		};
		add( list );

		Component content = list.content();
		content.clear();

		float posY = 0;
		float nextPosY = 0;
		boolean second = false;
		for (ChangeInfo info : changeInfos){
			if (info.major) {
				posY = nextPosY;
				second = false;
				info.setRect(0, posY, panel.innerWidth(), 0);
				content.add(info);
				posY = nextPosY = info.bottom();
			} else {
				if (!second){
					second = true;
					info.setRect(0, posY, panel.innerWidth()/2f, 0);
					content.add(info);
					nextPosY = info.bottom();
				} else {
					second = false;
					info.setRect(panel.innerWidth()/2f, posY, panel.innerWidth()/2f, 0);
					content.add(info);
					nextPosY = Math.max(info.bottom(), nextPosY);
					posY = nextPosY;
				}
			}
		}

		content.setSize( panel.innerWidth(), (int)Math.ceil(posY) );

		list.setRect(
				panel.x + panel.marginLeft(),
				panel.y + panel.marginTop() - 1,
				panel.innerWidth() + 2,
				panel.innerHeight() + 2);
		list.scrollTo(0, 0);

		StyledButton btn0_5 = new StyledButton(Chrome.Type.GREY_BUTTON_TR, "v0.5"){
			@Override
			protected void onClick() {
				super.onClick();
				if (changesSelected != 0) {
					changesSelected = 0;
					TomorrowRogueNight.seamlessResetScene();
				}
			}
		};
		if (changesSelected != 0) btn0_5.textColor( 0xBBBBBB );
		btn0_5.setRect(list.left()-4f, list.bottom(), 26, changesSelected == 0 ? 19 : 15);
		addToBack(btn0_5);

		StyledButton btn0_4 = new StyledButton(Chrome.Type.GREY_BUTTON_TR, "v0.4"){
			@Override
			protected void onClick() {
				super.onClick();
				if (changesSelected != 1) {
					changesSelected = 1;
					TomorrowRogueNight.seamlessResetScene();
				}
			}
		};
		if (changesSelected != 1) btn0_4.textColor( 0xBBBBBB );
		btn0_4.setRect(btn0_5.right() + 1, list.bottom(), 26, changesSelected == 0 ? 19 : 15);
		addToBack(btn0_4);

		StyledButton btn0_3 = new StyledButton(Chrome.Type.GREY_BUTTON_TR, "v0.3"){
			@Override
			protected void onClick() {
				super.onClick();
				if (changesSelected != 2) {
					changesSelected = 2;
					TomorrowRogueNight.seamlessResetScene();
				}
			}
		};
		if (changesSelected != 2) btn0_3.textColor( 0xBBBBBB );
		btn0_3.setRect(btn0_4.right() + 1, list.bottom(), 26, changesSelected == 0 ? 19 : 15);
		addToBack(btn0_3);

		StyledButton btn0_2 = new StyledButton(Chrome.Type.GREY_BUTTON_TR, "v0.2"){
			@Override
			protected void onClick() {
				super.onClick();
				if (changesSelected != 3) {
					changesSelected = 3;
					TomorrowRogueNight.seamlessResetScene();
				}
			}
		};
		if (changesSelected != 3) btn0_2.textColor( 0xBBBBBB );
		btn0_2.setRect(btn0_3.right() + 1, list.bottom(), 26, changesSelected == 0 ? 19 : 15);
		addToBack(btn0_2);

		StyledButton btn0_1 = new StyledButton(Chrome.Type.GREY_BUTTON_TR, "v0.1"){
			@Override
			protected void onClick() {
				super.onClick();
				if (changesSelected != 4) {
					changesSelected = 4;
					TomorrowRogueNight.seamlessResetScene();
				}
			}
		};
		if (changesSelected != 4) btn0_1.textColor( 0xBBBBBB );
		btn0_1.setRect(btn0_2.right() + 1, list.bottom(), 26, changesSelected == 0 ? 19 : 15);
		addToBack(btn0_1);


		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		fadeIn();
	}
	
	@Override
	protected void onBackPressed() {
		TomorrowRogueNight.switchNoFade(TitleScene.class);
	}

}
