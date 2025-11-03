package com.shatteredpixel.shatteredpixeldungeon.ui.changelist;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class v0_2_X_Changes {

    public static void addAllChanges(ArrayList<ChangeInfo> changeInfos) {
        add_v0_2_4_Changes(changeInfos);
        add_v0_2_3_Changes(changeInfos);
        add_v0_2_2_Changes(changeInfos);
        add_v0_2_1_Changes(changeInfos);
        add_v0_2_0_Changes(changeInfos);
    }

    public static void add_v0_2_4_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.2.4", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP_ARROW), "사격 무기",
                "_사격 무기_는 투척 무기를 탄환으로 사용하는 무기 계열로, 근접 공격력은 심각할 정도로 없습니다. 원거리 물리 공격을 가할 수 있습니다." + "\n\n" +
                        "_사격 무기_는 연계 블레이즈의 콤보와 사격 그레이스롯의 특수 사격을 발생시킬 수 있으며, 장전한 투척 무기의 티어에 따라 원거리 공격의 피해량이 결정됩니다." + "\n\n" +
                        "_사격 무기_는 3티어의 _DP27_, _9mm C1 기관단총_, 5티어인 _R4-C 돌격소총_이 추가되었습니다." + "\n\n\n" +
                "이 무기가 추가되면서, 기존의 _투척 무기_는 더 이상 강화할 수 없습니다. 단, _풍차 수리검_은 예외입니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.BACKPACK), "새로운 아이템들",
                "다음과 같은 아이템들이 추가되었습니다." + "\n\n" +
                        "_표준 탄약통_ : 가공소에서 제작할 수 있는 투척무기입니다. 3티어 투척 무기로, 사격 무기의 탄창으로 사용하시길 바랍니다. (내구도 매우 낮음)" + "\n\n" +
                        "_특수 탄약통_ : 가공소에서 제작할 수 있는 투척무기입니다. 4티어 투척 무기로, 사격 무기의 탄창으로 사용하시길 바랍니다. 또한, 이 탄창으로 사격 무기를 장전하면 추가 효과를 얻습니다." +"\n\n" +
                "_이베리아 연금술 세트_ : 새로운 유물로, 사용하지않는 아이템을 분해해 충전을 얻습니다. 충전 100%를 소모해 무작위 아이템을 창조할 수 있습니다." + "\n\n" +
                "_군주 인형_ : 기존의 _짜증나는 미보_를 대체합니다. 폭발에 휘말린 대상들의 정신을 지배하는 강력한 개조 폭탄입니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.NAMSEK), "아이템 시너지",
                "특정 무기 + 아이템을 장착하면 시너지 효과가 발생해 추가 능력을 얻을 수 있는 기능이 추가되었습니다." + "\n\n" +
                "현 버전에서 추가된 시너지 효과는 6종류입니다. 시너지 발동시 무기에 텍스트가 추가되어 확인할 수 있습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP_ARROW), "사격 무기 관련 특성",
                "_연계 전문화_의 _콤보 어택_ 특성에 사격 무기의 사격으로 콤보 획득시 확률적으로 추가 콤보를 얻는 기능이 추가되었습니다." + "\n\n" +
                        "_신속 전문화_의 _투사체 가속_ 특성이 사격 무기에도 적용되어, 최대 +45%의 피해 보너스를 얻을 수 있습니다." + "\n\n" +
                        "_사격 전문화_의 _강화 공유_ 특성의 피해 보너스가 5-15%에서 _6-18%_로 상승했으며, 사격 무기에도 적용됩니다. (단, 사격 무기는 효율 50%)"));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 변경",
                "_암살 전문화_의 _추가 수당_의 발동 방식이 약간 변경되어, 특성 레벨이 높으면 더 좋은 보상을 얻을 확률이 증가하는 특성으로 변경되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 변경",
                "_영소_ : 기존의 사거리가 길고 공격속도가 느린 무기에서, 1회 공격시 2번 타격하는 무기로 개편되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.MIZQ), "가드 퀘스트 보상 변경",
                "_가드 오퍼레이터_ 퀘스트의 보상이 _무조건 +1_ 이상이 보장됩니다. 대신 +2, +3 아이템이 나올 확률이 어느정도 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "머드락의 거대 석상 변경",
                "최대 체력이 80에서 _88_로 상승했습니다." + "\n\n" +
                "_낙석 패턴_의 쿨타임이 _66%_ 감소했습니다." + "\n\n" +
                "공격력이 24-28에서 _15-25_로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), "불사의 검은 뱀 변경",
                "공격력이 35-45에서 _40-50_으로 상승했습니다." + "\n\n" +
                "회피율이 35에서 _32_로 감소했으며, 무적 패턴의 쿨타임이 10%상승했습니다." + "\n\n" +
                "일부 패턴이 물 타일을 증발시킵니다." + "\n\n" +
                "기본 공격이 물 위에 있는 적에게 _70%_ 의 피해를 입힙니다." + "\n\n" +
                        "불사의 검은 뱀이 물 위에 있다면 매 턴마다 체력을 소량 잃고 회피율이 _50%_ 감소합니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.MIZQ), "전문화 능력 추가",
                "_연계 전문화_ 블레이즈는 사격 무기로 근접 공격시 확률적으로 총알을 얻습니다." + "\n\n" +
                "_신속 전문화_ 레드는 사격 무기의 재장전을 다른 캐릭터보다 빠르게 할 수 있습니다." + "\n\n" +
                "_사격 전문화_ 그레이스롯은 사격 무기의 사격 능력이 방어력을 50%무시합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 버프",
                "_블레이즈_의 _정신력 강화_ 특성에 해당 특성 발동시 자신에게 붙은 불을 즉시 끄는 기능이 추가되었습니다." + "\n\n" +
                        "_분노 블레이즈_의 _광분의 전기톱_ 특성의 발동 조건이 체력 50%이하에서 _분노 50%이상_ 으로 변경되었습니다." + "\n\n" +
                        "_연계 전문화_의 _기술 향상_의 +1 효과의 발동 조건이 콤보 7에서 _콤보 6_으로 감소했습니다." + "\n\n" +
                        "_아미야_의 _정신 분석_ 특성에 흑요석 반지 충전 기능이 추가되었습니다." + "\n\n" +
                        "_암살 전문화_의 _SWEEP_ 특성의 효율이 2-6%에서 _3-7%_로 상승했습니다." + "\n\n" +
                        "_로즈몬티스_의 _좋은 고기_ 특성의 충전 효과가 얼린 생고기에도 적용됩니다." + "\n\n" +
                "_로즈몬티스_의 _엄청 맛있는 식사_ 특성의 섬멸전 장비 충전량이 1-2에서 _2_로 고정되었으며, 벌꿀 쿠키 섭취시 허기량이 추가로 감소하는 능력이 추가되었습니다." + "\n\n" +
                        "_로즈몬티스_의 _고기가 좋아_ 특성에 체력 회복 효과가 추가되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 버프",
        "_동야의 마왕_ : 마탄 사격의 피해량이 1-6에서 _2-11_로 상승했습니다. 대신 마탄 사격이 정상적으로 턴을 소모합니다." + "\n\n" +
        "_해시계_ : 침묵 공격의 충전량이 최대 1에서 _2_ 로 상승했습니다." + "\n\n" +
                "_아트리아스의 검_ : 아츠 광선의 피해량이 2-12에서 _4-12_로, 강화 효율이 3에서 _4_로 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.BOMB), "폭탄류 버프",
                "모든 폭탄류 아이템의 피해량이 소폭 상승했습니다." + "\n\n" +
                "_이프리트 인형_, _서리별 인형_, _미보 -섬광폭발_의 효과 지속시간이 50%상승했습니다." + "\n\n" +
                "_THRM-EX_의 폭발에 휘말린 대상들은 취약에 걸립니다." + "\n\n" +
                "_파편 미보_의 피해량이 대폭 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ARTI), "유물 버프",
                "_커스텀 탐험 세트_ : 충전 속도가 상승했으며, Mon3tr 강화 효과가 더욱 강력해졌습니다." + "\n" +
                "커스텀 탐험 세트의 레벨에 비례해 Mon3tr의 체력 소모량이 감소합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ROSEMARI), "로즈몬티스 버프",
                "_섬멸전 장비_의 기본 공격력이 5-8에서 _6-11_으로 상승했습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 너프 1",
                "_블레이즈_의 _전술 방어막_ 특성의 방어 상승치가 1로 고정되었으며, 최대 상승치가 6에서 _4-8_로 변경되었습니다." + "\n\n" +
                        "_분노 블레이즈_의 _무한의 분노_ 특성의 피해 저항률이 30%에서 _20%_로 감소했습니다." + "\n\n" +
                        "_레드_의 _암살자의 신조_, _망토 강화_ 특성의 성능이 6-18%에서 _5-15%_로 감소했습니다." + "\n\n" +
                        "_자연 그레이스롯_의 _구제자의 기도_ 특성의 지속 턴이 2-4턴에서 _3-5_턴으로 상승했지만, 더 이상 아츠 기록장치 SP를 충전하지 않습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 너프 2",
                "_로즈몬티스_의 _몸이 가벼운 식사_의 부유 지속 턴이 10-15턴에서 _5-10턴_으로 감소했습니다." + "\n\n" +
                "_로즈몬티스_의 _초감각_ 특성이 최대 충젼량 +4까지만 과충전할 수 있도록 변경되었습니다." + "\n\n" +
                        "_로즈몬티스_의 _정신 회복의 강화_ 특성의 충전량이 2-3에서 _2-4_로 증가했지만, 더 이상 최대 충전량을 초과하여 충전할 수 없게됩니다." + "\n\n" +
                "_로즈몬티스_의 _섬멸 작전_ 특성의 피해 보너스가 3-9에서 _2-6_으로 감소했습니다."));
    }

    public static void add_v0_2_3_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.2.3", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BACKPACK), "새로운 아이템들",
                "다음과 같은 아이템들이 추가되었습니다." + "\n\n" +
                        "_식탁보_ : 3티어 무기로, 적을 밀쳐내고 특수 능력으로 체력과 아츠 기록장치의 SP를 회복할 수 있습니다." + "\n\n" +
                        "_맹목적인 순종_ : 4티어 무기로, 공격력이 낮지만 광역 공격을 가하며, 8회 타격마다 체력을 회복합니다.." + "\n\n" +
                        "_선현의 화신_ : 5티어 무기로, 공격시 확률적으로 실명을 걸고 SP를 충전합니다. SP를 소모해 시야 내 적들을 잠재우고 체력을 회복할 수 있습니다." + "\n\n" +
                        "_생명의 아츠 스태프_ : 새로운 아츠 스태프로, 3번 적중시킬 때마다 체력을 회복합니다. 회복 횟수에는 제한이 있습니다." + "\n\n" +
                "_스태프 오브 수수로_ : 생명의 아츠 스태프의 개조 스태프로, 회복 조건이 3회에서 2회로 감소하지만 피해량도 감소했습니다." + "\n\n" +
                "_W-0502_ : 특이한 도구로, 액체 화염 물약을 연료로 사용하여 물을 증발시키고 함정을 파괴할 수 있는 화염방사기입니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), "새로운 위기협약",
                "_창렬 : 클로저식 운영_ 제약이 추가되었습니다. 이 제약은 기존의 오리지늄 침식 제약을 대체합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "특별한 최종보스전",
                "특별한 조건을 만족하면 25층의 최종 보스가 변경되는 기믹이 추가되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.SKILL), "새로운 스킬",
                "_거울 속의 환영(1스킬)_ : 점멸의 돌과 같은 효과를 발동시킵니다." + "\n\n" +
                "_'다같이 가자!'(3스킬)_ : 자신의 주위에 바위게를 최대 4마리 소환합니다. 바위게의 레벨은 무기 강화에 어느정도 영향을 받습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 변경",
                "_성좌의 수호자_ : 명중률이 35%에서 _15%_로 감소했습니다. 4턴을 소모해 별의 힘을 모을 수 있으며, 최대 5회까지 모을 수 있게됩니다. 공격시 별의 힘을 소모해 시야 내 적들에게 광역 피해를 입힙니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.RING), " 캔낫 퀘스트 보상 변경",
                "16-19층의 _캔낫 퀘스트_의 보상이 변경되었습니다." + "\n\n" +
                "캔낫은 이제 _반지 강화 키트_를 보상으로 줍니다. 이 키트를 반지에 사용하여 반지의 강화 수치를 1단계 올릴 수 있습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "리유니온 조정",
                "_살카즈 백부장_ : 체력이 70에서 _110_으로, 명중률이 12에서 _15_로 상승했습니다." + "\n\n" +
                        "용병 소환의 쿨타임이 상승했으며, 대신 물리 공격을 받을 때마다 소환 쿨타임이 감소합니다." + "\n\n" +
                        "소환하는 용병의 공격력이 4-14에서 _2-13_으로, 방어력이 0-3에서 _0_으로 감소했습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 버프",
                "_동야의 마왕_ : 공격력이 2-9에서 _2-10_으로 상승했습니다." + "\n\n" +
                "_끓어오르는 투지_ : 명중률 보정치가 -30%에서 _-25%_로 상승했습니다." + "\n\n" +
                "_나의 소원_ : Mon3tr의 체력 소모율이 감소한 대신 공격할 때마다 체력을 일정량 소모하도록 변경되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ARTI), "유물 버프",
                "_로즈마리의 일기장, 붉은 사슬_ : 충전 속도가 20%상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 버프",
                "_검술 아미야_의 _과충전 공격_ 특성의 학률이 11-33%에서 _12.5-37.5%_로 상승했습니다." + "\n\n" +
                        "_지배 아미야_의 _지배력 강화_ 특성의 효율이 10-30%에서 _15-45%_로 상승했습니다." + "\n\n" +
                "_레드_의 _신비한 강화_ 특성의 효과가 3-5턴에서 _4-6_으로 상승했습니다." + "\n\n" +
                        "_자연 그레이스롯_의 _구제자의 기도_ 특성에, 효과 발동시 아츠 기록장치의 SP 5% 충전 효과가 추가되었습니다." +" \n\n" +
                "_수호 로즈몬티스_의 _수호 방벽 전개_ 특성에, 방벽 방어시 공격자에게 자신의 공격력 30-90%만큼 피해를 주는 기능이 추가되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.SKILL), "스킬 버프",
                "_네가 원하는 대로(3스킬)_ : 소환되는 방벽의 방어력이 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.STONE), "룬석 버프",
                "_애정의 돌_ : 적중당한 적이 10턴동안 침묵 상태가 됩니다." + "\n\n" +
                "_수면의 돌_ : 적중당한 적이 수면 상태가 아니라면, 3턴동안 감속 상태를 겁니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.RED), "신속 레드 버프",
                "신속 전문화 전직시, 방어구의 요구 힘이 1만큼 감소하는 능력이 추가되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ROSEMARI), "로즈몬티스 버프",
                "수호 전문화 전직시, 방벽 방어가 발동할 때마다 일정량의 보호막을 얻는 능력이 추가되었습니다." + "\n\n" +
                        "수호 전문화의 방벽 방어 효과가 보호막이 있을 땐 발동하지 않도록 변경되었습니다." + "\n\n" +
                "파괴 전문화 전직시 섬멸전 장비의 강화 효율이 상승하는 능력이 추가되었습니다. 대신 고기의 힘 추가 피해 배율은 약간 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "적군 버프",
                "_파우스트_ : 1회 공격으로 일정량 이상의 체력을 잃을 때마다 플레이어를 실명시킵니다." + "\n\n" +
                        "_켈시_ : Mon3tr 소환시, 플레이어를 입구 부분으로 강제로 텔레포트시키고 Mon3tr를 그 근처에 소환하도록 변경되었습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 너프",
                "_로도스 제식 장검_ : 기습시 최소 피해 보정이 100%에서 _70%_로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ARTI), "유물 너프",
                "_문명의 존속_ : 체력 재생 상승량이 10%감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 너프",
                "_자연 그레이스롯_의 _튼튼한 화살촉_ 특성의 명중률 보정치가 -25%-+25%에서 _-35%--5%_로 감소했습니다." + "\n\n" +
                "_로즈몬티스_의 _돈까스 메이커_ 특성의 발동 방식이 변경되어, 아이템을 드롭할 수 없는 적은 돈까스를 주지 않도록 변경되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP_WOND), "스태프 너프",
                "- _스태프 오브 위디_의 강화 효율이 2-4에서 _1-4_로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.MONNY), "전문화 너프",
                "_분노 전문화_ : 분노 획득량과 격노시 보호막량이 감소했습니다." + "\n\n" +
                        "_검술 전문화_ : 흑요석 반지로 근접 공격시 스태프 충전량이 감소했습니다." + "\n\n" +
                "_암살 전문화_ : 뼈찌르기 공격의 추가 피해가 15~60%에서 _13~52%_로 감소했습니다." + "\n\n" +
                "_자연 전문화_ : 쇠뇌 강화의 2단계 능력의 발동 조건이 3회 타격에서 _5회 타격_으로 변경되고, 보호막 획득량이 상승했습니다. 하지만 이제 보호막을 중첩시킬 수 없습니다." + "\n" +
                "또한, 3단계 특수 능력의 발동 확률이 20%에서 _14%_로 감소했습니다,"));
    }


    public static void add_v0_2_2_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.2.2", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BACKPACK), "새로운 아이템들",
                "다음과 같은 아이템들이 추가되었습니다." + "\n\n" +
                        "_시시오_ : 3티어 무기로, 체력이 낮을수록 공격력이 상승하지만 기습 공격이 불가능합니다." + "\n\n" +
                        "-_로도스 제식 장검_ : 1티어 무기로, 정제 금속과 1티어 무기 융합시 확률적으로 얻습니다. 기습시 피해 보정이 높고 적 처치시 즉시 투명화를 얻습니다." + "\n\n" +
                        "_스태프 오브 레나_ : 재성장의 아츠 스태프의 개조 스태프로, 직선 방향으로 식물의 힘을 발사해 피해를 입히고 현기증을 부여합니다. 또한, 발사 경로에 헤쳐진 풀을 생성합니다." + "\n\n" +
                        "_커스텀 탐험 세트_ : 새로운 유물로, 사용시 거울상을 소환합니다. 이 유물의 레벨에 따라 소환수들이 강력해집니다." ));

        changes.addButton(new ChangeButton(Icons.get(Icons.BADGES), "새로운 배지",
                "_하극상_\n29층의 켈시를 쓰러트려 하극상을 할 수 있게되었습니다. 하극상 성공시 즉시 게임이 끝납니다." + "\n\n" +
                "_널 기다릴게_\n이 배지는 해금 조건이 공개되어있지 않습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENTER), "새로운 NPC",
                "10층 보스 클리어시 추가 요소 혹은 정제 금속 조합법과 관련된 힌트를 주는 NPC가 추가되었습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 변경",
                "_그레이스롯_ :" + "\n\n" +
                        "_예리해진 감각_ 특성이 _폭발 화살_로 변경되었습니다. 폭발 화살 특성은 착탄 지점 주위의 적들에게 피해를 입힙니다." + "\n\n" +
                        "_아츠 집속_ 특성이 _침묵 화살_로 변경되었으며, 3티어 특성이 되었습니다. 침묵 화살 특성은 착탄 지점 주위의 적들을 침묵시킵니다." + "\n\n" +
                "_음파 화살_ 특성이 _속박 화살_로 변경되었습니다. 속박 화살 특성은 착탄 지점 주위의 적들을 속박합니다." + "\n\n" +
                "_접근 금지!_ 특성이 4티어 특성이 되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.GREY), "자연 그레이스롯 리메이크",
                "쇠뇌에 씨앗을 사용하여 강화하는 기능이 추가되었습니다. 최대 45개의 씨앗을 소모할 수 있습니다." + "\n\n" +
                "_튼튼한 화살촉_ 특성이 투척 무기의 근거리 명중률을 높히는 특성으로 변경되었습니다." + "\n\n" +
                "_블러드라인_ 특성이 투척 무기 적중시 대상 주위에 풀을 생성하며, 그레이스롯은 주위에 풀에 따라 피해 저항을 얻는 특성으로 변경되었습니다." + "\n\n" +
                "_보호의 이슬_ 특성은 _구제자의 기도_ 특성으로 변경되었으며, 시야 안에서 식물이 발동할 때 시야 내의 적들의 명중,회피를 감소시킬 수 있게됩니다." + "\n\n" +
                "_구제자의 신념_ 특성은 속박, 마비된 적에게 추가 피해를 입히는 특성으로 변경되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 변경",
                "_하야부사_ : 5티어로 상승했으며, 추가된 무기 시시오와 동일하게 변경되어 그에 따라 공격력과 명중률 등이 변경되었습니다." + "\n\n" +
                "_오리지늄 검_ : 공격할 때마다 SP를 충전하며, SP를 전부 소모하면 주위 적들에게 피해와 마비를 주고 유물 충전을 얻는 기능이 추가되었습니다." + "\n\n" +
                "_나기나타_ : 공격력이 4-25에서 _4-20_으로 감소했습니다. 이 무기로 공격받은 평범한 적이 체력이 15%이하면 즉사시키는 기능이 추가되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ARTI), "유물 삭제",
                "_공구 상자_ 유물이 삭제되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.BUG_KILL), "퀵슬롯 버튼 버그 수정",
                "이제 퀵슬롯 버튼 변경 시스템이 제대로 작동합니다! 변경전 / 변경후 퀵슬롯이 전부 저장됨으로, 안심하고 사용해주세요!"));

        changes.addButton(new ChangeButton(Icons.get(Icons.BACKPACK), "축복받은 앙크 변경",
                "축복받은 앙크 발동시 체력 회복량이 25%에서 _10%_로 감소했습니다." + "\n\n" +
                "대신 발동시 최대 체력 50%의 보호막을 얻으며, 5턴동안 정화의 보호막 상태를 얻습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP_WOND), "스태프 개조 키트 조합법 변경",
                "스태프 개조 키트의 조합법이 다음과 같이 변경되었습니다." + "\n\n" +
                        "1 : _재활용 금속_ + _아케인 촉매제_" + "\n" +
                        "2 : _무작위 스태프_ + _아케친 촉매제_"));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 버프",
                "_테르밋 블레이드_ : 공격력이 3-12에서 _5-12_ 로 상승했습니다." + "\n\n" +
                "_해시계_ : 공격력이 4-16에서 _4-18_로 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP_WOND), "스태프 버프",
                "_스태프 오브 압생트_의 피해 보너스가 75%에서 _100%_로 상승했습니다." + "\n\n" +
                        "_스태프 오브 메이어_가 개조 이전처럼 시야를 제공해줍니다." + "\n\n" +
                "_스태프 오브 위디_의 공격력이 2-7에서 _4-8_로 상승했으며, 강화 효율이 1-2에서 _2-4_로 상승했습니다. 또한 피해 공식이 변경되어 이전보다 큰 피해를 입힙니다." + "\n" +
                "또한, 이제 스태프 오브 위디의 충격파로 플레이어는 넉백되지 않습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.RING), "반지 버프",
                "_힘의 반지_ 의 최대 체력 상승량이 기본 3.5%에서 _4.5%_로 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.GREY), "사격 그레이스롯 버프",
                "사격 전문화시 다음의 보너스를 얻습니다." +"\n\n" +
                        "투척 무기 원거리 명중 보너스가 +50%에서 _+75%_로 상승합니다." + "\n\n" +
                        "일반 쇠뇌 공격이 1.1배의 피해를 입히며, 공격 증강을 제외한 특수 쇠뇌 사격의 피해량이 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 버프 1",
                "블레이즈의 _장인의 직감_ 1레벨의 감정 속도 보너스가 2배에서 _3배_ 로 상승했습니다." + "\n\n" +
                "블레이즈의 _엘리트 오퍼레이터_ 특성의 회복량이 2-3에서 _4-6_으로 상승했습니다." + "\n\n" +
                "블레이즈의 _급조한 투척물_ 특성의 쿨타임이 30턴에서 _25턴_ 으로 감소했습니다." + "\n\n" +
                "연계 블레이즈의 _콤보 어택_ 특성의 지속시간이 10-30턴에서 _15-45턴_으로 상승했습니다." + "\n\n" +
                "아미야의 _해답 찾기_ 특성의 충전 획득량이 2-3턴에서 _3-4턴_으로 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 버프 2",
                "아먀이의 _힘이 나는 식사_의 추가 피해량이 2-3에서 _3-4_로 상승했습니다." + "\n\n" +
                        "그레이스롯의 _회복된 자연_ 특성의 이름이 _자연의 은총_으로 변경되었으며, 주위 2-3칸 내의 적들에게 심안 효과를 얻는 효과가 추가되었습니다." + "\n\n" +
                "사격 그레이스롯의 _천리안_ 특성에 투척 무기의 원거리 명중 상승이 추가되었습니다." + "\n\n" +
                "사격 그레이스롯의 _저격_ 특성의 피해 보너스가 20-60%에서 _25-75%_로 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.SKILL), "스킬 버프",
                "_파노라마 오버로드 촬영_으로 소환되는 렌즈의 전체적인 성능이 대폭 개선되었습니다." + "\n\n" +
                "다음 스킬들의 공격력이 상승했습니다.\n\n스콜피온 테일, 바위를 깨는 망치, 창세기"));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS),  "리유니온 버프",
                "_기동 방패병_의 체력이 20에서 _22_로, 공격력이 2-5에서 _2-6_으로 상승했습니다." + "\n\n" +
                "_살카즈 백부장_의 소환 쿨타임이 대폭 감소했으며, 플레이어 주위에 소환을 시도합니다." + "\n" +
                        "소환되는 용병들의 체력이 12에서 _22_ 로 상승했습니다." +"\n" +
                        "대신 체력 50%이하일 때 소환 쿨타임 대폭 감소 능력이 삭제되었습니다." + "\n\n" +
                "_체르노보그 섬멸 강화_" + "\n" +
                "_머드락_ : 낙석 패턴이 돌이 떨어진 곳의 타일을 물 타일로 강제로 변환시키며, 축음기의 체력이 상승했습니다." + "\n\n" +
                "_메피스토_ : 소환되는 숙주병들이 _정화의 보호막_을 얻습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 너프",
                "블레이즈의 _전기톱 축제_ 특성의 발동 확률이 67-100%에서 _55-80%_로 감소했습니다." + "\n\n" +
                        "아미야의 _아츠 시야_ 특성의 지속시간이 10-15턴에서 _5-10턴_으로 감소했습니다." + "\n\n" +
                        "레드의 _망토 강화_ 특성의 충전 가속이 10-30%에서 _6-18%_로 감소했습니다." + "\n\n" +
                "그레이스롯의 _활력의 발걸음_ 특성의 쿨타임이 16-8턴에서 _18-12턴_ 으로 상승했습니다." + "\n\n" +
                "그레이스롯의 _개량 쇠뇌_ 특성의 공격력 상승량이 3-9에서 _2-6_으로 감소했습니다." + "\n\n" +
                "로즈몬티스의 _똑똑한 식사_ 특성의 이성 요구량이 5-3에서 _5-4_로 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.MONNY), "무기 너프",
                "_바위게 사육사_ : 공격력이 3-8에서 _3-13_으로 상승했지만, SP 충전량과 바위게의 능력치가 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_NERFS), "리유니온 너프",
                "_흉폭한 사냥개_의 공격력이 1-7에서 _1-6_으로 감소했습니다." + "\n\n" +
                "_숙주병사(폭주)_ 의 공격력이 18-28에서 _16-28_로 감소했으며, 집중 스킬의 쿨타임이 1턴 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ROSEMARI), "로즈몬티스 너프",
                "다음 보스들이 _섬멸전 장비_로 가하는 공격에 피해 저항을 얻습니다." + "\n\n" +
                "_머드락_ : 12% 감소" + "\n" + "_탈룰라?_ : 35%감소" + "\n" + "_Mon3tr_ : 15%감소"));
    }

    public static void add_v0_2_1_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.2.1", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BACKPACK), "새로운 아이템들",
                "다음과 같은 아이템들이 추가되었습니다." + "\n\n" +
                "_침묵의 아츠 스태프_ : 적에게 적중시키면 침묵을 부여하는 스태프입니다." + "\n\n" +
                "_스태프 오브 스노우상트_ : 침묵의 아츠 스태프의 개조 스태프로, 침묵을 부여함과 동시에 적중당한 적을 자신의 앞으로 끌어옵니다." + "\n\n" +
                "_지배의 반지_ : 근접 공격시 확률적으로 대상의 정신을 지배합니다. 저주받은 반지는 플레이어가 피격당했을 때 플레이어에게 정신 지배를 시도합니다." + "\n\n" +
                "_증폭의 반지_ : 보유하고있는 피해를 입힐 수 있는 스태프들의 공격력을 상승시킵니다. 저주받은 반지는 역으로 감소시킵니다." + "\n\n" +
                "_샌드비치_ : 가공소 조합 음식으로, 허기 회복량은 낮지만 섭취시마다 체력을 회복합니다. 이 음식은 4번에 걸쳐 섭취할 수 있습니다." + "\n\n" +
                "_봉크! 원자맛 음료_ : 상점 전용 아이템으로, 사용시 10턴동안 무적이 됩니다. 단, 공격이나 스태프 사용시 즉시 버프가 해제됩니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.RED), "새로운 스킬",
                "특별한 스킬 1개가 추가되었습니다. 일반적인 적 드롭 등으로는 얻을 수 없습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.ROSEMARI), "로즈몬티스 관련",
                "시작 아이템으로 돈까스 2장이 추가되었습니다." +"\n" +
                        "_섬멸전 장비_의 강화 효율이 상승했습니다." + "\n" +
                        "_충전의 일기장_과 _신비한 에너지의 일기장_ 사용시 섬멸전 장비를 2만큼 충전할 수 있도록 변경되었습니다." + "\n\n" +
                        "파괴 전문화 전직시, 고기를 먹었을 때 피해 보너스가 30%에서 15%로 감소했습니다." + "\n\n" +
                        "_섬멸전 장비_에 아츠 부여의 돌을 사용할 수 있도록 변경되었습니다. 단, 로즈몬티스가 전문화를 얻기 전에는 부여된 아츠의 힘을 쓸 수 없습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "무기 변경",
                "- _EX-42_ : 공격력이 1-6에서 _1-9_로 상승했지만, 사거리가 1로 감소했습니다." + "\n" +
                        "변형 기능이 추가되었습니다. 변형시 공격력이 1-3으로 감소하지만 사거리가 무제한이 되며, 명중률이 대폭 상승합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 변경",
                "- 로즈몬티스 대원의 _엄청 맛있는 식사_ 특성의 발동 조건이 애플 파이, 고기 파이 섭취에서 _벌꿀 쿠키_ 섭취로 변경되었습니다." + "\n" +
                        "대신 획득할 수 있는 장치 충전량이 2-4에서 _1-2_로 감소했습니다." + "\n\n" +
                        "- 로즈몬티스 대원의 _똑똑한 식사_ 특성이, 음식을 먹을 때마다 이성을 1 회복하며, 이성이 5-3이상이면 이성을 전부 소모해 땅에 떨어진 _무기/갑옷/스태프/반지를 주울 때 감정_하는 것으로 변경되었습니다." + "\n\n" +
                        "- 로즈몬티스 대원의 _돈까스 메이커_ 특성의 확률이 4-12%에서 _3-9%_로 감소했지만, 돈까스 섭취시 한 번에 장치를 2만큼 충전할 수 있도록 변경되었습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "무기 버프",
                "- _상처투성이 단검_ : 구매 및 판매 가격이 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.AMIYA), "스태프 버프",
                "- _스태프 오브 메이어_ : 자폭 피해가 상승했으며, 방어력을 무시하도록 변경되었습니다." + "\n\n" +
                "- _스태프 오브 압생트_ : 추가 피해 배율이 50%에서 _75%_ 로 상승했습니다." + "\n\n" +
                "- _스태프 오브 위디_ : 공격력이 1-1에서 _2-7_로 상승했습니다. 충전량 비례 추가 피해 배율이 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 버프",
                "- 로즈몬티스의 _몸이 가벼운 식사_ 특성을 찍을 경우, 부유 상태에서 섬멸전 장비 사용시 충전을 소모하지 않는 기능이 추가되었습니다." + "\n\n" +
                        "- 로즈몬티스의 _좋은 고기_ 특성을 찍을 경우, 조리되지않은 고기 섭취시 섬멸전 장비를 충전하는 기능이 추가되었습니다." +"\n\n" +
                "- 수호 전문화 로즈몬티스의 _수호 방벽 전개_ 특성의 피해 감소률이 60-80%에서 _65-95%_로 상승했습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "무기 너프",
                "- _리-엔필드_ : 공격력이 6-6에서 _9-9_로 상승했지만, 더 이상 6층 상점에서 확정으로 등장하지 않습니다. 또한, 가격이 대폭 상승했습니다." + "\n\n" +
                "- _HM-200_ : 힘 제한이 14에서 _18_로 상승했으며, 명중률 보너스가 삭제되었습니다." + "\n\n" +
                "- _바위게 사육사_ : 공격력이 3-10에서 _3-8_로, 공격속도가 133%에서 _100%_로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.MIZQ), "유물 너프",
                "- _문명의 존속_ : 레바테인의 _황혼_ 능력으로 강화 시도시, 피해 무효화 후 버프가 사라지도록 변경되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 너프",
                "- 분노 전문화 블레이즈의 _광분의 전기톱_ 특성의 발동 조건이 _체력 50%이하일 때 10회 피격_ 으로 변경되었습니다." + "\n\n" +
                        "- 파괴 전문화 로즈몬티스의 _본질 의식_ 특성의 보스 추가 피해량이 10-30%에서 _5-15%_로 감소했습니다." + "\n\n" +
                        "- 수호 전문화 로즈몬티스의 _로도스의 필라인_ 특성의 피해 상승량이 20-60%에서 _15-45%_로 감소했습니다." + "\n\n" +
                "- 수호 전문화 로즈몬티스의 _고속전투_ 특성의 이동속도 상승량이 12.5-37.5%에서 _10-30%_로 감소했습니다."));


    }

    public static void add_v0_2_0_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.2.0", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), "새로운 위기협약",
                "_결전 : 체르노보그 섬멸_" + "\n" +
                        "- 모든 보스가 강력해집니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "새로운 무기",
                "- _[5티어] 아트리아스의 검_ : 공격력이 낮지만, 매 공격마다 적을 관통하는 빔을 발사합니다." + "\n\n" +
                        "- _[5티어] 겨울의 상처_ : 기습 공격시 적을 한기 상태로 만듭니다. 한기 상태의 적에게 추가 피해를 입힙니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ARKPD), "새로운 무기 인챈트",
                "- _관통_ : 적의 방어력에 비례해 추가 피해를 입힙니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.RED), "새로운 스킬",
                "- _[1티어] 포자 확신_ : 목표 지점의 적을 속박, 침묵으로 만들고 부식 가스를 생성합니다." + "\n\n" +
                        "- _[1티어] 엄청 뜨거운 칼_ : 시야 내의 적에게 단검 2개를 던집니다. 저격의 반지로 피해량이 상승합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ROSEMARI), "신규 캐릭터",
                "- 로도스의 엘리트 오퍼레이터 _로즈몬티스_가 정식으로 참전합니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.SCROLL_HOLDER), "아츠 부여 관련",
                "- 아츠 부여의 돌이 일반적인 룬석 드롭 테이블에서 생성될 수 있습니다. 확률은 당신이 단챠로 픽뚫 6성을 뽑을 확률보다도 낮습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "무기 변경",
                "- _기병창 카빈_ : 공격력이 2-20에서 _2-12_로, 사거리가 3에서 _1_로 감소했습니다. 대신 공격속도가 66%에서 _100%_로 상승했습니다." +
                        "\n특수 기능 _가변_이 추가되었습니다. 활성화시 공격력과 사거리가 상승하지만 공격속도가 감소합니다." + "\n\n" +
                        "- _테르밋 블레이드_ : 공격력이 4-16에서 _3-12_로 감소했으며, 강화 효율이 4에서 _3_으로 감소했습니다.\n대신 공격이 적의 방어력을 무시합니다." + "\n\n" +
                        "- _마운틴 브레이커_ : 특수 기능 _스탠스 스위칭_이 추가되었습니다. 자유롭게 ON/OFF할 수 있으며, 활성화시 공격력이 2배로 상승하지만 공격속도가 감소합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 리메이크",
                "_분노 블레이즈_의 _광분의 전기톱_ 특성이 다음과 같이 리메이크되었습니다." + "\n\n" +
                        "- 10회 피격당한 후, 다음 공격이 시야 내 모든 적들에게 피해를 입히고 마비시킵니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "무기 버프",
                "- _바위게 사육사_ : SP 충전 속도가 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 버프",
                "- 다음 특성들이 상향 조정되었습니다." + "\n\n" +
                        "- _아미야_ : _신비한 힘의 일기장_ 의 지속시간이 30 턴에서 _45 턴_ 으로 상승했습니다." + "\n\n" +
                        "- _레드_ : _신비한 강화_의 망토 충전량이 2 ~ 3에서 _3 ~ 5_로 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.RED), "스킬 버프",
                "- _[1티어] 신영류_ : 아드레날린 획득량이 _2배_로 상승했습니다." + "\n\n" +
                        "- _[1티어] 크림슨 커터_ : 방어력을 무시합니다." + "\n\n"+
                        "- _[1티어] 늑대의 혼_ : 사거리가 3에서 _5_로 상승했습니다." + "\n\n" +
                        "- _[2티어] 낙지참_ : 저주당 축복 획득량이 150 턴에서 _250 턴_으로 상승했습니다." + "\n\n" +
                        "- _[3티어] 끓어오르는 투지_ : 방어력 상승량이 2배에서 _3배_ 로 상승했습니다." + "\n\n" +
                        "- _[3티어] 날개깃_ : 아드레날린 지속 시간이 50 턴에서 _65 턴_ 으로 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.AMIYA), "스태프 버프",
                "- _스태프 오브 위디_ 로 적을 벽에 부딪히게 만들면 더욱 큰 피해를 입힙니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ROSEMON), "반지 버프",
                "- _햇빛의 반지_의 기본치가 8%에서 _14%_ 로 상승했습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.RED), "스킬 너프",
                "- _진은참 (3스킬)_ 이 심안 버프와 연계할 수 없게 변경되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.SCROLL_HOLDER), "일기장 너프",
                "- _지배의 일기장_ 이 심안 버프와 연계할 수 없게 변경되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 너프",
                "- 다음 특성들이 하향 조정되었습니다." + "\n\n" +
                        "- _레드_ : _암살자의 신조_의 피해 보너스가 10 ~ 30%에서 _6 ~ 18%_ 로 감소했습니다." + "\n\n" +
                        "- _그레이스롯_ : _접근 금지!_의 발동 확률이 8 ~ 25%에서 _6 ~ 20%_ 로 감소했습니다."));


        changes = new ChangeInfo("0.2.0a", false, null);
        changes.hardlight(CharSprite.NEUTRAL);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.ARKPD), "난 버그가 싫어요",
                "- 기병창 카빈과 마운틴 브레이커에 관련된 버그가 수정되었습니다." + "\n\n" +
                "- 그리고 어째선지 무기 1개가 드롭테이블에서 빠져있었습니다. "));

        changes = new ChangeInfo("", false, null);
        changes.hardlight(CharSprite.NEUTRAL);
        changeInfos.add(changes);
    }
}
