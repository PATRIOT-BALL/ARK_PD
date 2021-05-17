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
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
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

		changes.addButton(new ChangeButton(Icons.get(Icons.ALEKS), "새로운 버프 : 활성 오리지늄",
				"- 새로 추가된 버프입니다.\n\n" +
						"- 체력이 지속적으로 감소합니다. 그 대신 공격 속도가 2배가 됩니다.\n" +
						"- 이 버프가 있을 때 공격하면 버프가 갱신될 것입니다."));


		changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
		changes.hardlight(CharSprite.WARNING);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.BOMBTAIL, 6, 12, 16, 16), "폭탄새",
				"- [해골]에서 변경되었습니다.\n\n" +
						"- 스펙이 매우 낮고 이동속도가 느립니다. 대신 자폭 피해가 매우 강합니다."));

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SCOUT, 6, 4, 16, 16), "정찰기술자",
				"- [네크로맨서]에서 변경되었습니다.\n\n" +
						"- 폭탄새를 소환합니다. 그런데 소환한 폭탄새를 회복,강화하는게 아닌 터트려버립니다!"));

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.THROWER, 12, 6, 16, 16), "폭주 숙주병들",
				"- [드워프 수도승], [드워프 흑마법사]에서 변경되었습니다.\n\n" +
						"- [폭주한 숙주병사]는 공격속도가 너프되었습니다.\n\n" +
						"- [폭주한 숙주투척병]은 원거리 공격이 물리 공격으로 변경되었씁니다.\n\n" +
						"- 공격시 자신에게 '활성 오리지늄'버프를 부여합니다."));

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.I_GOLEM, 14, 6, 16, 16), "거대석상",
				"- [DM200]에서 변경되었습니다.\n" +
						"- 멀리 있을 때 독가스를 날리는게 아니라 낙석 패턴(DM300의 보스 패턴)을 발동시킵니다."));

	/*
		changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
		changes.hardlight(CharSprite.POSITIVE);
		changeInfos.add(changes); */

	}
}