package com.shatteredpixel.shatteredpixeldungeon.ui.changelist;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;

import java.util.ArrayList;

public class v0_4_X_Changes {
    public static void addAllChanges(ArrayList<ChangeInfo> changeInfos) {
        add_v0_4_0_Changes(changeInfos);

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
                "플레이어 이외의 존재가 철의 육합 사용시 넉백이 이상하게 적용되는 버그가 수정되었습니다." + "\n" +
                "유격대 홀로그램으로 인한 게임 크래시 버그가 수정되었습니다." + "\n" +
                "디스트로이어 관련 프리징 버그가 어느정도 수정되었습니다."));
    }
}
