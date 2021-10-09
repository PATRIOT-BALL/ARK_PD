package com.shatteredpixel.shatteredpixeldungeon.ui.changelist;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;

import java.util.ArrayList;

public class v0_3_X_Changes {
    public static void addAllChanges(ArrayList<ChangeInfo> changeInfos) {
        add_v0_3_1_Changes(changeInfos);
        add_v0_3_0_Changes(changeInfos);
    }

    public static void add_v0_3_1_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.3.1", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BACKPACK), "새로운 아이템",
                "_히라의 사냥꾼_ : 4티어 모드 무기로, 매우 변칙적인 활용성을 지닌 무기입니다. 모드에 따라 적의 등 뒤로 이동하거나 대상을 짧게 끌어당깁니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WAND_HOLSTER), "새로운 주문",
                "_힘의 정수_ : 무기를 분해하여 만드는 촉매제입니다." + "\n\n" +
                        "_불꽃의 서약_ : 사용시 100턴 동안 연소 면역을 부여하는 주문입니다."+ "\n\n" +
                "_무기 변환_ : 변환의 주문서 효과를 발동시키지만, 무기에만 사용할 수 있는 주문입니다." + "\n\n" +
                "_아방가르드한 변환_ : 해당 아이템에 아방가르드 우물 효과를 적용시키며, 강화를 유지합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "새로운 시너지 1종 추가",
                "_정밀 장전_"));

        changes.addButton(new ChangeButton(Icons.get(Icons.SKILL), "새로운 스킬",
                "_심해의 포식자(2스킬)_ : 대상에게 큰 피해를 입히고 등 뒤로 이동합니다. 만약 적을 처치하면 아드레날린을 얻습니다."));


        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "몬스터 변경",
                "_새싹 원석충_의 체력이 4에서 _1_로, 공격력이 1-4에서 _1-1_로 감소했습니다. 대신 회피율이 25에서 _35_로 상승했으며, 더 이상 _거대해진 엘리트_ 몬스터가 될 수 없습니다." + "\n\n" +
                "_화산 심장부_가 이제 무작위 물약 대신 _불꽃의 서약_을 드롭합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WAND_HOLSTER), "주문 변경",
                "_재활용_ : 1회 제작에 15개가 생성됩니다. 또한, 이제 일기장/포션, 씨앗/룬석 두 분류로 나뉘어 변환 시도시 다른 계열의 아이템으로 변환됩니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 상향",
                "_단검_ : 공격력이 1-8에서 _1-9_로 상승했습니다." + "\n\n" +
                "_캐슬 브레이커_ : _클로즈드 볼트 연발_ 능력의 강화 효율이 0-2에서 _0-3_으로 상승했습니다." +" \n\n" +
                "_쿠다리키리_ : 흡혈률이 20%증가했으며, 명중률 보정치가 +20%에서 _+30%_로 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 상향",
                "_블레이즈_의 _급조한 투척물_ 특성의 실명 부여량이 상승했습니다." + "\n\n" +
                "_레드_의 _불시의 타격_ 특성의 추가 피해가 상승했습니다." + "\n\n" +
                "_레드_의 _경량화 망토_ 특성에 망토 발동에 소모하는 턴 감소 효과가 추가되었습니다." + "\n\n" +
                        "_그레이스롯_의 _접근 금지!_ 특성의 발동 확률이 상승했습니다."
                        ));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 하향",
                "_타향의 노래_ : 강화 효율이 5에서 _4_로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_NERFS), "몬스터 하향",
                "_살카즈 저주술사_의 회피율이 8에서 _6_으로 감소했습니다." + "\n\n" +
                "_특공대원_\n체력이 140에서 _125_로, 공격력이 25-35에서 _21-31_로 감소했습니다. 대신 침묵 상태일 때 공격력이 12-24에서 _14-26_으로 상승했습니다."));
    }

    public static void add_v0_3_0_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.3.0", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), "추가 요소",
                "새로운 적 타입 : _감염 생물_이 추가되었습니다." + "\n\n" +
                "_시에스타_가 추가되었습니다. _로도스 아일랜드(27~30층)_ 에서 진입할 수 있습니다. 최대 레벨이 40으로 증가했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "챕터 5 새로운 적",
                "_정예 선봉_\n사거리 2의 물리 공격을 가하며, 방어력이 높은 편입니다. 체력이 50%이하가 되면 공격력이 상승합니다." + "\n\n" +
                "_백전 정예 선봉_\n정예 선봉의 변종으로, 능력치가 더 높습니다." + "\n\n" +
                "_제국 드론_\n아츠마스터 A2의 변종으로, 플레이어를 인식하면 플레이어 위치에 포격 좌표를 찍습니다. 포격 지점에서 벗어나지 않으면 큰일 날 것입니다." + "\n" +
                "제국 드론을 처치하면 100%확률로 로고스의 골필을 얻을 수 있습니다." + "\n\n" +
                "_돌격자_\n살카즈 랜서의 변종으로, 가속 중첩으로 공격력을 상승시키는 대신 대상을 밀쳐내는 공격을 가합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "새로운 필살기",
                "_연계 전문화 블레이즈_는 _사격 무기_를 장착한 상태에선 새로운 필살기를 사용합니다." + "\n\n" +
                        "_연속 타격 4 : 속사_는 대상에게 연속 타격*20%의 _사격 피해_를 입힙니다. 콤보를 1 얻습니다." + "\n\n" +
                "_연속 타격 8 : 근접 섬광탄_은 대상에게 125%의 피해를 입히고 1턴의 마비와 연속 타격*0.3턴의 실명을 부여하며, 콤보를 1 얻습니다." + "\n\n" +
                "_연속 타격 10 : 영거리 사격_은 대상에게 연속 타격*40%의 _사격 피해_를 입힙니다."+"\n\n" +
                "추가로, 사격 무기로 근접 공격시 탄환 장전 확률이 10%에서 25%로 증가했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "새로운 시너지 7종 추가",
                "_최후의 웬디고\n용문총경\n광기의 노래\n용암거영\n라인랩 프렌즈!\n친우의 검\n이세계 전술_"));

        changes.addButton(new ChangeButton(Icons.get(Icons.BACKPACK), "새로운 아이템",
                "_핼버드_ : 2티어 무기로, 감염 생물에게 추가 피해를 입힙니다." + "\n\n" +
                "_타향의 노래_ : 4티어 무기로, 자신이나 적이 물 타일 위에 있다면 피해량이 상승합니다. 이 효과는 중첩됩니다." + "\n\n" +
                "_이세계 전술장비_ : 100%충전 후 발동시 미보를 1개 생성하는 유물입니다. 미보 및 파생 인형들의 피해량이 상승하며, 투척시 즉시 폭발하고 마비를 부여합니다." + "\n\n" +
                "_특별한 스태프_ 2종이 추가되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.SKILL), "새로운 스킬",
                "_전투의 노래 (1스킬)_ : 모든 적들을 도발하여 자신의 위치로 오게합니다. 도발당한 적을 처치하면 추가 합성옥을 얻습니다."+"\n\n"+
                "_예리함의 극치 (3스킬)_ : 일정 턴동안 명중률이 대폭 상승하며, 적의 방어력을 무시합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ARKPD), "식재료",
                "_식재료_ 아이템이 추가되었습니다. 이 아이템들은 직접 섭취할 순 없지만, 가공소에서 조합 요리를 만드는데 활용할 수 있습니다!"));


        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "리유니온 변경",
                "_고에너지 원석충_\n" +
                        "체력이 50에서 _45_로, 회피율이 17에서 _14_로 감소했으며, 공격력이 10-20에서 _8-24_로 상승했습니다." + "\n\n" +
                        "독 주입 능력이 제거되었습니다. 대신 사망시 독 가스와 마비 가스를 분출합니다." + "\n\n" +
                "_숙주 병사(폭주)_\n" +
                "체력이 90에서 _105_로 증가했으며, 공격력이 16-28에서 _10-32_로 조정되었습니다. 대신 회피율이 30에서 _18_로 감소했습니다." + "\n\n" +
                "_집중_ 스킬의 쿨타임이 7-8턴에서 _9-11 턴_으로 증가했습니다. 대신 집중 상태일 때 최소 공격력과 이동속도가 2배가 됩니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 개편1",
                "_분노 전문화_의 _타오르는 투쟁_ 특성의 격노시 보호막 증가 효과가 제거되고, 대신 분노 투척의 분노 획득량 증가가 추가되었습니다." + "\n\n" +
                "_아미야_의 _에너지 넘치는 강화_ 특성이 _아츠 흡수_로 대체되었습니다. 이 특성은 아츠 기록장치로 스킬 발동시 흑요석 반지를 충전시킵니다." + "\n\n" +
                        "_아미야_의 _신비한 힘의 일기장_ 특성이 _해방_ 으로 대체되었으며, 4티어 특성이 되었습니다. 이 특성은 흑요석 반지의 최대 충전량을 상승시킵니다." + "\n\n" +
                        "_아미야_의 _정신분석_ 특성이 3티어 특성으로 변경되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 개편2",
                        "_레드_의 _보호의 그림자_ 특성이 _아츠의 그림자_로 대체되었습니다. 망토로 은신 지속 중 일정 턴마다 아츠 기록장치를 충전합니다." + "\n\n" +
                        "_레드_의 _신비한 강화_ 특성이 _추적자_로 대체되었습니다. 아츠 기록장치로 스킬 사용시 신속을 얻고 속박,불구,실명을 해제합니다." + "\n\n" +
                        "_그레이스롯_의 _무기 손질_ 특성이 _연속처치_로 대체되었습니다. 적 처치시 짧은 턴동안 이동속도가 상승합니다." + "\n\n" +
                "_로즈몬티스_의 _영점 조절_ 특성이 _내가 원하는 세상_으로 대체되었습니다. 섬멸전 장비의 충전이 없을 때 시전시 시야 내의 모든 일반 적을 즉사시키고 장비를 충전하지만, 쿨타임이 매우 길고 체력을 소모합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.AMIYA), "지배 아미야 능력 변경",
                "흑요석 반지의 물리 공격력이 감소하지만, 낙인이 부여된 적이 사망하면 흑요석 반지가 충전됩니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ARTI), "유뮬 개편",
                "_천구_ 의 충전 속도가 감소했지만, 레벨이 오르는 속도가 빨라졌습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP_WOND), "스태프 개편",
                "_스태프 오브 압생트_의 피해량이 2-10에서 _6-25_로 상승했으며, 강화 효율이 1-3에서 _3-7_로 상승했습니다. 대신 체력 50%이하인 적에게만 피해를 입힐 수 있습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "더욱 많은 변종",
                "변종 몬스터의 등장 확률이 2%에서 _3%_ 로 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 삭제",
        "_카시미어 전쟁도끼_가 게임에서 제외되었습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 상향",
                "_맹목적인 순종_의 강화 효율이 3에서 _4_로 증가했습니다. 대신 주 대상 피해 보너스가 최대 90%에서 _40%_로 감소했습니다." + "\n\n"+
        "_끓어오르는 투지_ 공격력이 5-16에서 _5-18_로 상승했으며, 강화 효율이 3에서 _4_로 증가했습니다. 또한, 명중률 보정치가 -25%에서 _-15%_로 상승했습니다." + "\n\n" +
                        "_HM-200_의 공격력이 3-32에서 _8-32_로 상승했습니다." +"\n\n" +
                        "_나의 소원_으로 소환한 Mon3tr의 이동속도가 상승했으며, Mon3tr가 공격할 때마다 무기의 SP를 충전시킵니다." + "\n\n" +
                "_사격 무기_ 들의 최대 탄창수가 소폭 상승했으며, 사격 공격의 기본 공격력이 상승하고 명중률이 50%상승했습니다." + "\n\n" +
                "_R4C_의 사격 공격의 최소 피해량이 소폭 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP_WOND), "스태프 및 주문 상향",
                "_스태프 오브 브리즈_의 안개 지속시간이 상승했습니다." + "\n\n" +
                "_스태프 오브 레나_의 기본 피해량이 2-6에서 _4-6_으로 상승했습니다." + "\n\n" +
                "_즉석 가공_ 의 제작에 필요한 에너지가 16으로 증가했지만, 이제 즉석 가공이 공급하는 에너지가 0이 아닌 4입니다!"));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 상향",
        "_블레이즈_의 _전기톱 연장_ 특성의 명중률 패널티가 제거되었으며, 대신 무기의 힘 요구치를 증가시킵니다.." + "\n\n" +
        "_연계 전문화_의 _치명적인 방어_ 특성의 이름이 _최후의 일격_으로 변경되었으며, 효과 발동시 아츠 기록장치를 충전하는 효과가 추가되었습니다." + "\n\n" +
                "_암살 전문화_의 _SWEEP_ 특성에 +2 효과로, 효과 발동시 아츠 시야 획득이 추가되었습니다." + "\n\n" +
        "_그레이스롯_의 _폭발 화살_ 특성의 피해 배율이 80~120%에서 _100~150%_로 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.SKILL), "스킬 상향",
                "_격노의 눈_과 _스콜피온 테일_, _창세기_의 피해량이 상승했습니다." + "\n\n" +
                        "_바람의 언어_의 현기증 부여량이 증가했습니다." + "\n\n" +
                        "_연막 전개_의 지속시간이 증가했습니다." + "\n\n" +
                "_낙지참_ 사용시 저주 장비 수와 관계없이 기본적으로 150턴의 축복을 얻습니다." + "\n\n" +
                "_'다 함께 가자!'_로 소환되는 바위게의 레벨이 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ARTI), "유뮬 상향",
                "_가드 오퍼레이터 근로 계약서_의 강화에 +4 / +7 / +10에 도달했을 때, 장착 아이템 최소 강화 보정이 추가되었습니다. 최대 +3까지 보정됩니다,"));

        changes.addButton(new ChangeButton(Icons.get(Icons.GOLD), "아방가르드 우물 상향",
                "_적소_ 변환 실패시 더 이상 아이템이 합성옥으로 변하지 않습니다." + "\n\n" +
                "_나의 소원_ 변환 성공 확률이 증가했으며, 실패해도 합성옥으로 변하지 않습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 하향",
                "_적소_의 공격력이 2-9에서 _2-8_로 감소했습니다." + "\n\n" +
                        "_바위게 사육사_의 무기 티어가 4티어로 증가했습니다." + "\n\n" +
                "_M1887_의 공격력이 4-12에서 _4-10_으로 감소했습니다." + "\n\n" +
                "_성좌의 수호자_의 집중 공격의 피해 배율이 20%감소했습니다." + "\n\n" +
                        "_영소_의 공격력이 5-18에서 _5-17_로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 하향",
                "_블레이즈_의 _전술 방어막_ 특성의 방어 상승 최대치가 4-8에서 _4-10_으로 상승했으며, 갑옷 강화당 방어 상승량이 1에서 _2_로 상승했습니다. 공식 변경으로 실적용 수치는 감소했습니다." +"\n\n"+
                "_아미야_의 _보호막 충전_ 특성의 성능이 5~7.5%에서 _4~6%_로 감소했습니다." + "\n\n" +
                        "_지배 전문화_의 _환영 생성_ 특성의 발동 확률이 14-42%에서 _10-30%_로 감소했습니다." + "\n\n" +
                "_레드_의 _은밀한 이동_ +1 효과의 범위가 3칸에서 _2칸_으로 감소했습니다." + "\n\n" +
                        "_신속 전문화_의 _특수작전_의 프리러닝 쿨타임 감소 효과가 2-6에서 _2-4_로 감소했습니다." + "\n\n" +
                "_수호 전문화_의 _수호 방벽 전개_의 피해 감소률이 65-95%에서 _60-80%_로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.SKILL), "스킬 하향",
                "_끓어오르는 투지_의 피해 저장 배율이 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ROSEMARI), "수호 전문화 하향",
                "방어 발동시 보호막 획득량이 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_NERFS), "리유니온 하향",
                "_아츠마스터 A2_\n체력이 100에서 _90_로, 아츠 광선 피해량이 35-55에서 _30-55_로 감소했습니다." + "\n\n" +
                "_살카즈 스나이퍼_\n체력이 110에서 _105_, 공격력이 32-44에서 _30-40_, 명중률이 42에서 _36_, 회피율이 24에서 _22_로 감소했습니다."));

    }
}
