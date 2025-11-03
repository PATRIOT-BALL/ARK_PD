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
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class v0_1_X_Changes {

    public static void addAllChanges(ArrayList<ChangeInfo> changeInfos) {
        add_v0_1_6_Changes(changeInfos);
        add_v0_1_5_Changes(changeInfos);
    }

    public static void add_v0_1_5_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.1.5", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BADGES), "스킨 시스템 추가",
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
                        "- _[4티어] M1887_ : 공격력이 매우 낮지만, 공격시 _대상 주위의 적들에게도 피해_를 입힙니다. 또한, 대상이 혼자라면 _2배의 피해_를 입힙니다!" + "\n\n"+
                "- _[4티어] 나기나타_ : 사거리가 길지만 공격속도와 명중률이 조금 낮은 평범한 무기입니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.RED), "새로운 스킬 추가",
                "- _[1티어] 늑대의 혼_ : 사거리 3의 범위 상태이상 공격을 가합니다. 범위 내 적들은 _침묵하여 특수 능력이 봉인_됩니다." + "\n\n" +
                        "- _[2티어] 꿈나라의 요람_ : 사거리 5의 범위 상태이상 공격을 가합니다. 범위 내 적들은 수면 상태에 빠지며, _수면이 끝날 때 피해_를 받습니다." + "\n\n" +
                "- _[2티어] 심화 치료_ : 즉시 체력을 전부 회복하며, 매우 강력한 지속 치료 상태가 됩니다. 사용시 아츠 기록장치에서 스킬이 _제거_ 됩니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.GOLD), "가챠 박스",
                "- 이 _수상한 상자_는 합성옥, 무기, 스킬개론 중 하나를 _무작위_로 줍니다. _플레이어 능력치 기반 확률 변동 시스템_이라는 획기적인 시스템이 탑재되어있습니다!"));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.AMIYA), "스태프 변경",
                "- _스태프 오브 브리즈_ 가 사용시 모든 SP를 소모하는 대신 회복의 안개가 소모 SP에 비례하며, 안개의 영향을 받는 _모든_ 캐릭터는 _아츠 면역_ 상태가 됩니다!" + "\n\n" +
                        "- 이제 흑요석 반지를 스태프 개조 키트로 개조할 수 있게되었지만, 스태프 개조 키트의 제작에 필요한 가공소 에너지가 6에서 _15_ 로 상승했습니다."));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.BLOOD_SHAMAN, 6, 7, 16, 16), "몬스터 변경",
                "- _블러드 위치_ : 자해 피해로 사망하지 않게됩니다." + "\n\n" +
                "- _W_ : 특수 지뢰 패턴의 카운트가 2배로 상승했지만, 대신 매 턴마다 카운트가 진행됩니다. (플레이어가 감지되면 매 턴 카운트 2 진행, 감지되지 않았다면 카운트 1 진행)" +
                "\n통합 운동 적용시 특수 지뢰 패턴의 시전 방식이 약간 변경되었으며, 보스 클리어 후 맵의 낭떠러지가 사라졌습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.FAUST, 6, 7, 16, 16), "리유니온 버프",
                "- _살카즈 도병_\n도약 공격이 더욱 많은 출혈을 부여합니다." + "\n\n" +
                        "- _살카즈 랜서_\n체력 80 -> 95" + "\n\n" +
                        "- _아츠마스터 A2_\n공격력 20-30 -> _20-35_\n빔 공격력 30-50 -> _35-55_\n방어력 0-10 -> _0-12_\n회피율 20 -> _12_"+ "\n\n" +
                        "- _살카즈 스나이퍼_\n공격력 30-40 -> _32-44_\n명중률 36 -> _42_"));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.TALRU_FIGHT, 6, 32, 16, 16), "탈룰라 강화",
                "- _우르수스 감시관_ 의 체력 20 -> _25_" + "\n\n" +
                        "- _탈룰라의 환영_들의 체력 300 -> _330_,\n공격력 18-36 -> _22-36_" + "\n\n" +
                        "- _불멸의 검은 뱀 환영_이 물을 증발시킬 확률이 _상승_했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "무기 버프",
                "- _해시계_ : 강화 효율이 4 에서 _5_ 로 상승했습니다." + "\n\n" +
                "- _상처투성이 단검_ : 전이 성공률이 상승했습니다." + "\n\n" +
                "- _리-엔필드_ : 공격시 SP를 20%충전하며, 100%일 때 공격시 1.3배의 피해를 입히는 기능이 추가되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 버프",
                "- 다음 특성들이 상향 조정되었습니다." + "\n\n" +
                        "- _지배 아미야_ : _환영 생성_ 특성의 발동 확률이 _14-42%_로 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.RED), "스킬 버프",
                "- _[1티어] 바람의 언어_ : 사거리가 2에서 _4_로 상승했습니다." + "\n\n" +
                        "- _[1티어] 격노의 눈_ : 마비 조건이 체력 33% 이하에서 _체력 50% 이하_ 로 상승했습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "무기 너프",
                "- _동야의 마왕_ : 마왕 에너지 발사의 기본 피해량이 2-8에서 _1-6_ 으로 감소했습니다." + "\n\n" +
                        "- _하야부사_ : 명중률 보정치가 +10%에서 _-15%_ 로 감소했습니다." + "\n\n" +
                "- _영소_ : 공격속도 보정치가 -33%에서 -_50%_ 로 감소했습니다." + "\n\n" +
                "- _끓어오르는 투지_ : 공격력이 5-18에서 _5-16_ 으로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.AMIYA), "스태프 너프",
                "- _스태프 오브 비그나_의 기본 사거리가 상승했지만, 강화에 비례한 사거리 증가량이 감소했습니다." + "\n\n" +
                "- _스태프 오브 커럽팅_의 타락 효율이 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 너프",
                "- 다음과 같은 특성들이 하향 조정되었습니다." + "\n\n" +
                        "- _블레이즈_ : _강철의 위장_ 특성의 피해 저항률이 _50-75%_로 감소했습니다." + "\n\n" +
                "- _검술 아미야_ : _영소 분야_ 특성의 피해 보너스가 _20-60%_로 감소했습니다." + "\n\n" +
                "- _지배 아미야_ : 낙인 부여 확률이 비정상적으로 높은 버그가 수정되었습니다.\n_정신 포식자_ 특성의 음식 섭취 효과 발동 확률이 _6-20%_로 감소했습니다." + "\n\n" +
                "- _레드_ : _향상된 반지_ 특성의 지속시간이 _2.5-7.5_턴으로 감소했습니다." + "\n\n" +
                "- _사격 그레이스롯_ : _강화 공유_ 특성의 특수 쇠뇌 공격 피해 상승량이 _5-15%_로 감소했습니다." + "\n\n" +
                "- _자연 그레이스롯_ : _블러드라인_ 특성의 나무 껍질 획득량이 _현재 레벨 -3_으로 감소했습니다."));


        changes = new ChangeInfo("0.1.5a", false, null);
        changes.hardlight(CharSprite.NEUTRAL);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.AMIYA), "스태프 너프",
                "- _스태프 오브 커럽팅_으로 소환되는 혈족의 체력이 강화 단계에 비례하게 변경되었으며, 소환될 때 약화 상태를 얻습니다."));

        changes = new ChangeInfo("", false, null);
        changes.hardlight(CharSprite.NEUTRAL);
        changeInfos.add(changes);
    }


    public static void add_v0_1_6_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.1.6", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.CHANGES), "새로운 기능",
                "- 배낭 버튼을 길게 누르면 퀵슬롯을 변경할 수 있습니다. 이로써 총 8개의 퀵슬롯을 사용할 수 있게 되었습니다.\n\n현재 기술적 문제로 세이브-로드시 활성화하지 않은 퀵슬롯은 초기화됩니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.RED), "새로운 스킬",
                "- _[2티어] 호신용 가시_ : 받는 피해가 감소하며, 피격시 주위 적에게 방어력 비례 피해를 줍니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.BADGES), "새로운 배지",
                "- _원암 25개 채취_ 배지와 _우리들의 빛(원암 40개 채취)_ 배지가 추가되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.SCROLL_HOLDER), "새로운 일기장",
                "- _워프의 일기장_ 과 _지배의 일기장_ 이 추가되었습니다. 해당 일기장들은 매우 강력하지만 획득이 어렵습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WARRIOR), "분노 전문화 새로운 능력",
                "- 적에게 투척 무기가 아닌 물건을 던지면 _광란_ 을 부여하고 자신은 즉시 _분노와 아츠 면역_ 을 얻는 능력이 새로 생겼습니다. 이 효과는 240턴마다 발동할 수 있습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "무기 변경",
                "- _M1887_ : 명중률 패널티가 사라졌지만, 범위 피해가 주 대상이 아닌 적들에겐 이전보다 적은 피해를 입힙니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.RED), "스킬 변경",
                "- _[2티어] 먼 옛날의 혈족_ : 소환되는 혈족의 체력과 방어력이 상승했습니다. 대신 회피율이 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.GREY), "그레이스롯 특수 능력 변경",
                "- _사격 전문화_를 익힌 그레이스롯의 _속도 증강_ 특수 사격이 변경되었습니다." + "\n\n" +
                "- 이제 3연발 사격 대신, 100%의 피해를 입히는 화살을 발사합니다. 또한, 해당 화살은 _대상이 회피해도_ 무조건 _불구_ 상태를 부여합니다."));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.FAUST, 6, 7, 16, 16), "리유니온 변경",
                "- _살카즈 백부장_\n폭주 조건이 변경되었으며, 소환 쿨타임이 상승했습니다. 폭주 돌입시 이전보다 매우 빠른 속도로 소환할 것입니다.\n또한, 축지법을 익혀 하늘을 날아다닙니다." +
                        "\n\n" + "또한, 소환되는 살카즈 용병들의 공격력이 4-12 에서 _4-14_로 상승했습니다."));
        
        

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "무기 버프",
                "- _레바테인_ : 단 한 번, _황혼_을 사용해 체력이 1이 되는 대신 강력한 버프를 받을 수 있습니다.\n공격력이 6-24 에서 _6-30_ 으로 상승했으며, " +
                        "명중률 보정치가 +40%에서 _+30%_ 로 하향 조정 되었습니다." + "\n\n" +
                "- _캐슬 브레이커_ : 공격력이 3-16 에서 _3-15_ 로 감소했습니다. 대신 공격할 때마다 SP를 회복하며 SP를 소모해 _클로즈드 볼트 연발_ 을 사용할 수 있습니다." + "\n\n" +
                "- _슈팅 글러브_ :  피해량 보정이 추가되어, 이전보다 높은 피해를 줄 수 있습니다." + "\n\n" +
                "- _바위게 사육사_ : SP 충전률이 상승했습니다." + "\n\n" +
                "- _상처투성이 단검_ : 전이 실패 확률이 대폭 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.AMIYA), "스태프 버프",
                "- _스태프 오브 압생트_ 의 피해량이 2-8(+2) 에서 _2-10(+3)_ 으로 상승했습니다." + "\n\n" +
                       "- _스태프 오브 스카이파이어_ 의 피해량이 3-6에서 _3-8_로 상승했습니다."));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.FANATIC, 6, 7, 16, 16), "리유니온 버프",
                "- _무뢰한_\n체력이 기존에 비해 약 _20~30%_, 공격력이 _33%_ 상승했습니다." + "\n\n" +
                "- _파우스트_\n체력이 180에서 _225_ 로 상승했습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "무기 너프",
                "- _동의 인장_ : 방어력 상승량이 0-2(+2) 에서 _0-2(+1)_ 로 하향 조정되었습니다. 또한, 적들이 반사 피해를 더 효율적으로 방어합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 너프",
                "- _블레이즈_\n_전술 방어막_ 특성의 방어력 상승 최대치가 6-18에서 _6_ 으로 고정되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.AMIYA), "스태프 너프",
                "- _스태프 오브 커럽팅_ 으로 소환하는 혈족의 체력이 적의 강함에 비례하도록 변경되였으며, 대신 강화 단계에 비례해 보호막을 얻고 소환됩니다." + "\n\n"
        + "또한, 베이스가 되는 [먼 옛날의 혈족]의 성능 조정에 따라, 혈족의 방어력이 상승하고 회피율이 감소했습니다."));

        changes.addButton(new ChangeButton(new Image(Assets.Sprites.FAUST, 6, 7, 16, 16), "리유니온 너프",
                "- _블러드 위치_\n공격력이 25-40 에서 _16-24_ 로 감소했습니다.\n대신 공격할 때마다 공격력이 _3-6_ 만큼 상승하며, 이 공격력 상승은 영구적입니다.\n\n또한, 체력이 최저값이 되면 이동속도가 감소합니다." + "\n\n" +
                "- _머드락_\n드롭하는 쇳조각의 갯수가 _2개_ 로 고정되었습니다."));

        changes = new ChangeInfo("0.1.6a", false, null);
        changes.hardlight(CharSprite.NEUTRAL);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WARRIOR), "기타 수정사항",
                "G52 전술 섬광 방패와 관련된 버그 일부가 수정되었으며, 고대 주물의 드롭 확률이 높아졌습니다." + "\n\n"
        + "대신 동의 인장의 방어력이 0-2(+1) 에서 0-2(+0.5)로 감소했습니다."));

        changes = new ChangeInfo("", false, null);
        changes.hardlight(CharSprite.NEUTRAL);
        changeInfos.add(changes);

    }

}