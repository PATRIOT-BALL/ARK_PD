package com.shatteredpixel.shatteredpixeldungeon.ui.changelist;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;

import java.util.ArrayList;

public class v0_4_X_Changes {
    public static void addAllChanges(ArrayList<ChangeInfo> changeInfos) {
        add_v0_4_4_Changes(changeInfos);
        add_v0_4_3_Changes(changeInfos);
        add_v0_4_2_Changes(changeInfos);
        add_v0_4_1_Changes(changeInfos);
        add_v0_4_0_Changes(changeInfos);

    }

    public static void add_v0_4_4_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.4.4", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.MIZQ), "새로운 상태이상 : 위장",
                "위장은 플레이어와 적 모두가 가질 수 있는 상태입니다." + "\n\n" +
                "플레이어에게 적용시 _투명화_와 동일한 기능을 합니다. 단, 근처에 적이 있다면 해제됩니다." + "\n\n" +
                "적은 위장 중 받는 피해가 절반이 됩니다. 심안에 노출되거나 플레이어가 발광 혹은 인접한 상태라면 해제됩니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), "위기협약 난이도 상승",
                "_악의: 전술 강화_ 와 _환시: 팬텀 로도스_의 협약 난이도가 상승했습니다."));


        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "몬스터 리메이크",
                "_유령_ : 물건을 훔쳤을 때 이동속도가 감소했지만, 훔친 뒤 주기적으로 _위장_합니다." + "\n\n" +
                "_기술정찰병_ : 체력이 40에서 _22_로 감소했습니다. 대신 영구적으로 _위장_합니다. 또한, 더 이상 자신이 소환한 폭탄새로 피해를 받지 않습니다." + "\n\n" +
                "_살카즈 도병_ : 체력이 60에서 _38_로 감소했습니다. 대신 영구적으로 _위장_합니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "열기 블레이즈 상향",
                "_과열_ 발동 중 열기가 60% 이상이면 _불사_ 상태가 되어 죽지 않게됩니다. 불사 발동 중 피격시 열기를 잃습니다." + "\n\n" +
                "_인내의 불꽃_ 특성에 불사 상태 발동에 요구하는 열기가 감소하는 효과가 추가되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 상향",
                "_콜람의 패검_의 SP 충전률이 상승했습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 너프",
                "_오리지늄 검_의 SP 충전률이 감소했습니다." + "\n\n" +
                "_고난의 진술자_의 명중률 보정치가 0%에서 _-10%_로, 화염탄 피해 배율이 200%에서 _125%_로 감소했으며, 더 이상 기습공격을 할 수 없습니다."));


    }

    public static void add_v0_4_3_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.4.3", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "새로운 적",
                "_머드락 소대원_ : 3챕터의 적으로, 높은 능력치를 지녔으며, 스태프로 공격을 입히면 능력치가 감소합니다." + "\n\n" +
                "_팬텀 로도스_ : 팬텀 로도스 위기협약 적용시 추가되는 적입니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.BACKPACK), "새로운 아이템 및 시너지",
                "_새로운 시너지_ : 투쟁을 여기서 멈추노라 / 신이 선택한 감독관" + "\n\n" +
                        "_고난의 진술자_ : 5티어 무기로, 공격시 대상 주위에도 피해를 입히며, 대상 주위에 적이 없다면 2배의 피해를 입힙니다. 특수 능력으로 화염탄을 발사할 수 있습니다." + "\n\n" +
                "_영혼 보호의 영약_, _영혼 파괴의 영약_, _강철 피부의 영약_이 추가되었습니다. 이 아이템들은 재활용 금속을 활용하는 가공소 아이템입니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.RED), "레드 3번째 전문화",
                "야생 전문화가 추가되었습니다. 야생 전문화는 _검풍_을 사용해 원거리 공격을 가하며, 특성으로 검풍의 충전과 유틸성을 강화할 수 있습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), "새로운 위기협약",
                "_악의: 전술 강화_\n일부 적이 추가 능력을 얻거나 기존 능력이 강화됩니다." + "\n\n" +
                "_환시: 팬텀 로도스_\n보스 이전 층에서 로도스 대원의 환영이 나타납니다. 환영은 모습에 따라 다른 능력을 지니며, 내려가는 계단의 사용을 막아버립니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 변경",
                "_로도스 제식 장검_의 조합 성공확률이 증가했습니다. 대신 기습 피해 보너스가 70%에서 _55%_로 감소했습니다." + "\n\n" +
                "_겨울의 상처_의 한기 대상 추가 피해량이 증가했습니다. 대신 기습 피해 보너스가 33%에서 _25%_로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.AMIYA), "지배 아미야 변경",
                "_지배력 강화_ 특성이 대상에게 물리 피해를 입힐 때 확률적으로 정신 지배를 거는 특성으로 변경되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.NEARL), "니어 조정",
                "_기사의 맹세_ 특성의 충전량이 8-12%에서 _10-15%_로 증가했습니다." + "\n\n" +
                "_카시미어의 기사_ 특성의 발동 확률이 33%에서 _20%_로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "숙주 부랑자 변경",
                "숙주 부랑자의 체력이 120에서 _95_로 감소했지만, 적을 텔레포트 시킬 때 _속박_과 _침묵_을 1턴 부여합니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.ARTI), "유물 상향",
                "_가드 오퍼레이터 근로 계약서_의 가드 오퍼레이터 최대 체력이 증가했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "적 상향",
                "_시장 크로닌_이 텔레포트 발동시 모든 거울상과 아미야의 환영을 파괴합니다." + "\n\n" +
                "_토미미_의 3페이즈 잡몹 소환 속도가 증가했습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.ARTI), "유물 하향",
                "_커스텀 탐험세트, 이세계 전술장비, 전쟁의 상처_의 충전 속도가 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.GREY), "자연 전문화 하향",
                "_블러드라인_ 특성이 이제 굶주림 피해를 줄여주지 못합니다."));


    }

    public static void add_v0_4_2_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.4.2", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BACKPACK), "새로운 아이템 및 시너지",
                "_새로운 시너지_ : 금색의 나침반" + "\n\n" +
                "_에케베리아_ : 5티어 무기로, 드론을 소환해 대상에게 마법 피해를 입힐 수 있습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.HERO_CHANGES), "블레이즈, 그레이스롯, 니어 3번째 전문화",
                "블레이즈 : _열기 전문화_가 추가되었습니다. 열기 전문화는 자신의 체력을 소모해 전투력을 증가시킵니다." + "\n" +
                "그레이스롯 : _폭풍 전문화_가 추가되었습니다. 폭풍 전문화는 공격적인 기동전투에 특화되어있습니다." + "\n" +
                "니어 : _섬광 전문화_가 추가되었습니다. 섬광 전문화는 빠른 인장 사용과 실명에 특화되어있습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.RING), "암살의 반지 상향",
                "암살의 반지의 기습 피해 상승량이 기본 5%에서 _6%_로 상승했습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.AMIYA), "혼돈 아미야 하향",
                "갑옷으로 얻는 방어력이 감소합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.SKILL), "스킬 하향",
                "_어두운 밤의 장막_과 _날개깃_의 성능이 감소했습니다."));


    }

        public static void add_v0_4_1_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.4.1", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BACKPACK), "새로운 아이템 및 시너지",
                "_새로운 아이템 시너지_ : 프로급 개량 렌즈 / 엔젤 불릿" + "\n\n" +
                        "_전장촬영사_ : 사거리가 긴 4티어 무기로, 특수 능력을 사용해 조건을 만족하면 적 하나를 20턴 뒤 처형할 수 있습니다." + "\n\n" +
                        "_전쟁의 상처_ : 사용시 적들에게 _망각_을 부여하는 유물입니다." + "\n\n" +
                        "_렌즈 인형_ : 범위 내 적들에게 _망각_을 부여하는 폭발물입니다." + "\n\n" +
                        "_인간 사료_ : 먹는데 시간이 오래 걸리는 요리입니다."
                ));

        changes.addButton(new ChangeButton(Icons.get(Icons.NEWS), "새로운 상태이상 : 망각",
                "_망각_ 상태인 캐릭터는 시야와 행동속도가 감소합니다. 실질적으로 실명과 감속 상태가 동시에 부여되는 매우 치명적인 상태이상으로, 다른 행동속도 감소 디버프와 중첩됩니다." + "\n\n" +
                        "또한, 이 상태이상과 관련된 함정이 추가되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.GOLD), "정보증명서 시스템 추가",
                "게임 플레이로 획득할 수 있는 _영구 재화_인 _정보증명서_가 추가되었습니다. 28층의 특수 상점에서 유용한 아이템으로 바꿀 수 있습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.MIZQ), "로도스 아일랜드 편의성 패치",
                "28층 입장시 마법 지도와 심안 효과가 발동하여 로도스의 지형을 파악할 수 있게됩니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.AMIYA), "아미야 3번째 전문화",
                "아미야의 새로운 전문화 _혼돈_ 이 추가되었습니다. 혼돈 아미야는 리스크가 매우 크지만, 강력한 마법 피해를 가합니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.AMIYA), "아미야 조정",
                "_해답 찾기_ 특성의 충전 획득량이 3-4턴에서 _4-5턴_으로 증가했습니다." + "\n\n" +
                        "_예비 보호막_ 특성의 보호막 획득량이 3-5에서 _3-6_으로 증가했습니다." + "\n\n" +
                "_보호막 충전_ 특성의 보호막 획득량이 4-6%에서 _2.5-5%_로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 조정",
                "_성좌의 수호자_ : 공격력이 4-20(+5)에서 _4-15(+3)_으로 감소했습니다." + "\n" +
                "충전 요구 턴이 4턴에서 _2턴_으로 감소했으며, 추가 피해 배율이 20%에서 _70%_로 증가했습니다.\n(최대 충전 기준 100% -> _210%_)"));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "몬스터 상향",
                "_버섯중독자_ : 체력이 115에서 _120_으로, 명중률이 38에서 _42_로, 디버프 부여 확률이 33%에서 _50%_로 상승했습니다. 공격력이 36-50에서 _36-46_으로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 상향",
                "_살라스의 칼날_의 공격력이 4-18에서 _4-20_으로 상승했습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.ROSEMARI), "전쟁 쪽냥이 하향",
                "_공격력 증강_시 EX-44의 피해 증가량이 40%에서 _35%_로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_NERFS), "몬스터 하향",
                "_워리어_ : 체력이 150에서 _165_로, 경험치가 18에서 _20_으로 상승했습니다. 공격력이 38-54에서 _35-57_로 조정되었습니다.\n" +
                        "회피율이 21에서 _18_로 감소했으며, 방어력이 30-50(0-24) 에서 _20-50(0-18)_로 감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 하향",
                "_레퀴엠_의 공격력이 5-12에서 _5-10_으로, 방어력이 0-4에서 _0-3_으로 감소했습니다."));
    }

    public static void add_v0_4_0_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.4.0", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BACKPACK), "새로운 아이템 및 시너지",
                "_새로운 아이템 시너지_ : 폭군 / 바벨 / 천객만래" + "\n\n" +
                "_철의육합_ : 10회 공격마다 모드가 변경되는 무기입니다. 통상 상태에선 높은 공격력을, 변형 상태일 땐 매 공격이 넉백시키는 효과를 가집니다." + "\n\n" +
                        "_홀로가는 먼 길_ : SP를 충전한 후 3가지 특수 기능 중 1개를 사용 가능한 사거리 2의 무기입니다." + "\n\n" +
                "_여제의 반지_ : 장착한 무기의 SP 충전률을 올려주는 반지입니다. 단, _나의 소원_은 효과를 받을 수 없습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.NEWS), "새로운 챕터 및 NPC",
                "새로운 챕터 _대족장의 귀환_이 추가되었습니다. 해당 챕터는 31층 이동 전 가비알에게 말을 걸면 진입할 수 있습니다." + "\n\n" +
                "28층 NPC _아스테시아_ 가 추가되었습니다. 아스테시아는 현재 장착한 무기의 시너지 아이템을 안내해줍니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ROSEMARI), "로즈몬티스 3번째 전문화",
                "로즈몬티스의 새로운 전문화 _전쟁_ 이 추가되었습니다. 전쟁 로즈몬티스는 EX-44를 소환하여 적을 섬멸합니다." + "\n\n" +
                "다구리엔 장사없다고 전기톱 쓰는 필라인이 알려줬습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 개편",
                "_글래스고 깡패_ : 공격속도가 500%에서 _250%_ 로 감소했으며, 감소한 수치에 비례해 공격력과 강화 효율이 상승했습니다." + "\n\n" +
                "_적소_ : 플레이어 레벨이 21 이상일 때 공격력과 강화 효율이 상승하는 효과가 추가되었습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 상향",
                "_인귀_ : 대검 모드의 회복량이 강화에 비례해 상승하도록 변경되었습니다." + "\n\n" +
                "_R4-C 돌격소총_ : 사격 속도와 명중률이 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.P_RED), "레드 상향",
                "레드가 발리스틱 나이프를 자동으로 회수합니다." + "\n\n" +
                "_추적자_ 특성에 사냥 능력으로 적 처치시 체력 회복 효과가 추가되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "적 상향",
                "_제국 선봉_의 공격력이 22-30에서 _22-33_ 으로 상승했습니다. (체력 50%이하일 때 공격력은 기존과 같습니다.)" + "\n\n" +
                        "_정예 제국 선봉_의 명중률이 30에서 _33_ 으로 상승했습니다." + "\n\n" +
                        "_돌격자_의 공격력이 24-30에서 _24-40_ 으로 상승했습니다." + "\n\n" +
                        "_보병_의 체력이 135에서 _125_ 로 감소했지만, 공격력이 32-44에서 _33-45_ 로, 방어력이 0-18에서 _0-20_ 으로 상승했습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_NERFS), "적 하향",
                "_공수부대병_의 공격력이 3-9에서 _2-9_ 로 감소했습니다." + "\n\n" +
                        "_진형파괴병_의 공격력이 5-18에서 _5-17_ 로, 명중률이 16에서 _15_ 로 감소했습니다." + "\n\n" +
                "_숙주 병사(폭주)_의 공격력이 10-32에서 _10-27_ 로 감소했습니다. 집중 상태일 때 공격력은 기존과 같습니다." + "\n\n" +
                "_숙주 리더(폭주)_의 집중 버프 관련 사항이 적용되지않아 지나치게 강력했던 버그가 수정되었습니다." + "\n\n" +
                "_용암 원석충_ : 체력이 155에서 _145_ 로, 공격력이 33-45에서 _31-45_ 로 감소했습니다."));

        changes = new ChangeInfo("0.4.0a", false, null);
        changes.hardlight(CharSprite.NEUTRAL);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BUG_KILL), "버그 수정",
                "끄앙! 버그입니다!"));
    }
}
