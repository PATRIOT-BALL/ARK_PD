/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2020 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.ui.changelist;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Spinner;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfTransfusion;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GolemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SpinnerSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class v0_1_X_Changes {

	public static void addAllChanges(ArrayList<ChangeInfo> changeInfos) {
		add_v0_0_1_Changes(changeInfos);
	}

	public static void add_v0_0_1_Changes(ArrayList<ChangeInfo> changeInfos) {
		ChangeInfo changes = new ChangeInfo("v0.0.1", true, "");
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(Icons.get(Icons.SHPX), "쉬시면 앙대요!",
				"_-_ Released 05-08, 2021\n" +
						"_-_ v0.0.1\n" +
						"\n" +
						"빨리 박사를 구해서 로도스로 돌아와주세요!"));


		changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
		changes.hardlight(CharSprite.WARNING);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.BOMBTAIL, 6, 12, 16, 16), "폭탄새!",
				"- 날아다닙니다!\n\n" +
						"- 스펙이 매우 낮고 이동속도가 느립니다. 대신 자폭 피해가 매우 강합니다!"));

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SCOUT, 0, 0, 16, 16), "기술자!",
				"- 자신이 소환한 폭탄새를 회복,강화시키는 대신 불태워버립니다!"));

	/*
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
		changes.hardlight(CharSprite.POSITIVE);
		changeInfos.add(changes); */

	}
}