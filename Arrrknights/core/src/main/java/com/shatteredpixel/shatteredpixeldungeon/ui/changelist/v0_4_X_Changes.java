package com.shatteredpixel.shatteredpixeldungeon.ui.changelist;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;

import java.util.ArrayList;

public class v0_4_X_Changes {
    public static void addAllChanges(ArrayList<ChangeInfo> changeInfos) {
        add_v0_4_1_Changes(changeInfos);
        add_v0_4_0_Changes(changeInfos);

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
