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
import com.shatteredpixel.shatteredpixeldungeon.sprites.ImpSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class v0_1_X_Changes {

    public static void addAllChanges(ArrayList<ChangeInfo> changeInfos) {
        add_v0_1_5_Changes(changeInfos);
    }

    public static void add_v0_1_5_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.1.5", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.ALEKS), "스킨 시스템 추가",
                "- 로도스 아일랜드(27층)에서 _스킨 시스템_을 사용할 수 있습니다." + "\n\n" +
                        "- 각 스킨마다 _특정 배지를 요구_하며, 해당 배지가 있다면 캐릭터의 이미지를 스킨으로 덮어씌울 수 있습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "4티어 특성 추가",
                "- _4티어 특성_이 추가되었습니다." + "\n\n" +
                        "- 각 캐릭터는 _2개의 공통 특성_과 _1개의 전문화 특성_, 총 3개의 4티어 특성을 가집니다."));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.IMP, 6, 4, 16, 16), "분노한 상인 캔낫",
                "- 먼슬리카드 유물이 없어도 _20층 상점에서 도둑질_을 할 수 있게되었습니다." + "\n\n" +
                        "- 20층 상점에서 도둑질에 실패하면 _캔낫이 분노하여 공격_해올 것입니다!"));

        changes.addButton(new ChangeButton(Icons.get(Icons.HUNTRESS), "새로운 무기 추가",
                "- _[3티어] 바위게 사육사_ : 공격력이 낮지만, 공격을 통해 SP를 충전할 수 있습니다. SP가 100%일 때 공격하면 _같이 싸워주는 바위게를 소환_합니다!" + "\n\n" +
                        "- _[4티어] M1887_ : 공격력이 매우 낮지만, 공격시 _대상 주위의 적들에게도 피해_를 입힙니다. 또한, 대상이 혼자라면 _2배의 피해_를 입힙니다!"));

        changes.addButton(new ChangeButton(Icons.get(Icons.RED), "새로운 스킬 추가",
                "- _[1티어] 늑대의 혼_ : 사거리 3의 범위 상태이상 공격을 가합니다. 범위 내 적들은 _침묵하여 특수 능력이 봉인_됩니다." + "\n\n" +
                        "- _[2티어]꿈나라의 요람_ : 사거리 5의 범위 상태이상 공격을 가합니다. 범위 내 적들은 _수면 상태에 빠지며, 수면이 끝날 때 피해_를 받습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ROSEMARI), "가챠 박스",
                "- 이 _수상한 상자_는 합성옥, 무기, 스킬개론 중 하나를 _무작위_로 줍니다. _플레이어 능력치 기반 확률 변동 시스템_이라는 획기적인 시스템이 탑재되어있습니다!"));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.AMIYA), "스태프 개조 키트",
                "- 이제 흑요석 반지에도 사용할 수 있습니다."));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.BLOOD_SHAMAN, 6, 7, 16, 16), "블러드 위치 변경",
                "- 자해 피해로 사망하지 않게됩니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.FAUST, 6, 7, 16, 16), "리유니온 버프",
                "- _살카즈 도병_\n도약 공격이 더욱 많은 출혈을 부여합니다" + "\n\n" +
                        "- _아츠마스터 A2_\n공격력 20-30 -> _20-35_\n빔 공격력 30-50 -> _35-55_\n방어력 0-10 -> _0-12_\n회피율 20 -> _12_"+ "\n\n" +
                        "- _살카즈 스나이퍼_\n공격력 30-40 -> _32-44_\n명중률 36 -> _42_"));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.TALRU_FIGHT, 6, 32, 16, 16), "탈룰라 강화",
                "- _우르수스 감시관_ 의 체력 20 -> _25_" + "\n\n" +
                        "- _탈룰라의 환영_들의 체력 300 -> _330_,\n공격력 18-36 -> _22-36_" + "\n\n" +
                        "- _불멸의 검은 뱀 환영_이 물을 증발시킬 확률이 _상승_했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 버프",
                "- 다음 특성들이 상향 조정되었습니다." + "\n\n" +
                        "- _지배 아미야_ : _환영 생성_ 특성의 발동 확률이 _14-42%_로 상승했습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "무기 너프",
                "- _하야부사_ : 명중률 보정치가 +10%에서 _-15%_로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.AMIYA), "스태프 너프",
                "- _스태프 오브 비그나_의 기본 사거리가 상승했지만, 강화에 비례한 사거리 증가량이 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 너프",
                "- 다음과 같은 특성들이 하향 조정되었습니다." + "\n\n" +
                        "- _블레이즈_ : _강철의 위장_ 특성의 피해 저항률이 _50-75%_로 감소했습니다." + "\n\n" +
                "- _검술 아미야_ : _영소 분야_ 특성의 피해 보너스가 _20-60%_로 감소했습니다." + "\n\n" +
                "- _지배 아미야_ : 낙인 부여 확률이 비정상적으로 높은 버그가 수정되었습니다.\n_정신 포식자_ 특성의 음식 섭취 효과 발동 확률이 _6-20%_로 감소했습니다." + "\n\n" +
                "- _레드_ :  _향상된 반지_ 특성의 지속시간이 _2.5-7.5_턴으로 감소했습니다." + "\n\n" +
                "- _사격 그레이스롯_ : _강화 공유_ 특성의 특수 활 공격 피해 상승량이 _5-15%_로 감소했습니다." + "\n\n" +
                "- _자연 그레이스롯_ : _블러드라인_ 특성의 나무 껍질 획득량이 _현재 레벨 -3_으로 감소했습니다."));
    }
}