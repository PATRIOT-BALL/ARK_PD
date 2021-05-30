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
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class v0_1_X_Changes {

    public static void addAllChanges(ArrayList<ChangeInfo> changeInfos) {
        add_v0_1_1_Changes(changeInfos);
        add_v0_1_2_Changes(changeInfos);
    }

    public static void add_v0_1_1_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("Test v0.1.1", true, "");
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

    public static void add_v0_1_2_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("Test v0.1.2", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.CHARLIE), "몬가... 몬가 많이 추가댐!",
                "- 중요한거만 적을테니 다른건 외부 공지를 참조해주세요. 어차피 베타라서 다음 버전엔 또 엄청 바뀌니까요!"));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.AMIYA, 2, 34, 16, 16), "새로운 아츠 스태프들!",
                "- [스태프 개조 키트]를 사용해 특별한 아츠 스태프를 만들 수 있게되었습니다.\n\n" +
                        "- 기존 스태프에 [스태프 개조 키트]를 사용하면 특별한 아츠 스태프를 얻습니다. 키트는 연금술로 만들 수 있습니다."));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.FANATIC, 18, 3, 16, 16), "미니 이벤트",
                "- 던전에서 조건을 만족하면 확률적으로 특수 오브젝트가 등장합니다.\n\n" +
                        "- 해당 오브젝트와 상호작용시 몬가 일어납니다. 그것은 위험일 수도 있고, 기회일 수도 있습니다."));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.W, 6, 4, 16, 16), "보스 패턴 추가",
                "- W는 텔레포트 후 특수 지뢰를 던져 대원들을 압박합니다.\n\n" +
                        "- 머드락은 매 공격마다 자신의 공격력을 끌어올립니다. 하지만 이동할 때마다 얻은 공격력은 감소할 것입니다.\n" +
                        "또한, 축음기 2개가 파괴되면 스킬을 사용해 받는 피해를 감소시킨 후 자신을 강화합니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.ALEKS), "그래픽 변경!",
                "- 그래픽이 32px 기반으로 변경되었습니다!\n\n" +
                        "- 또한, 아이템 등의 가시성이 훨씬 좋아졌습니다.\n"));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.BLAZE, 742, 38, 16, 16), "스킬북 편의성 개선",
                "- 아츠 기록장치를 퀵슬롯에서 사용해도 스킬 정보를 확인할 수 있게되었습니다!\n\n" +
                        "- 다만, 상세한 정보 등은 인벤토리에서 확인하셔야합니다."));


        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.BLAZE, 22, 38, 16, 16), "분노 전문화 상향",
                "- 분노 상태에서 이동속도가 상승하며, 피해량이 더 높아졌습니다!!\n\n" +
                        "- 대신 격노 상태의 패널티가 커졌습니다. 자세한건 패치노트를 참조해주세요."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.AMIYA, 2, 34, 16, 16), "아미야 전체적인 너프",
                "- 아미야의 전체적인 특성이 하향당했으며, 지배 전문화 (아미야β)의 낙인 부여 확률이 감소했습니다.\n\n" +
                        "- 일부 특성은 상향되었습니다!"));
    }
}