package com.shatteredpixel.shatteredpixeldungeon.ui.changelist;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class v0_2_X_Changes {

    public static void addAllChanges(ArrayList<ChangeInfo> changeInfos) {
        add_v0_2_0_Changes(changeInfos);
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
                        "\n특수 기능 _가변_이 추가되었습니다. 자유롭게 ON/OFF할 수 있으며, 활성화시 변경 전 기병창과 같은 능력치를 가지게됩니다." + "\n\n" +
                        "- _테르밋 블레이드_ : 공격력이 4-16에서 _3-12_로 감소했으며, 강화 효율이 4에서 _3_으로 감소했습니다.\n대신 공격이 적의 방어력을 무시합니다." + "\n\n" +
                        "- _마운틴 브레이커_ : 특수 기능 _스탠스 스위칭_이 추가되었습니다. 자유롭게 ON/OFF할 수 있으며, 활성화시 공격력이 2배로 상승하지만 공격속도가 감소합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 리메이크",
                "_분노 블레이즈_의 _광분의 전기톱_ 특성이 다음과 같이 리메이크되었습니다." + "\n\n" +
                        "- 10회 피격당한 후, 다음 공격이 시야 내 모든 적들에게 피해를 입히고 마비시킵니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

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
                        "- _[3티어] 날개깃 : 아드레날린 지속 시간이 50 턴에서 _65 턴_ 으로 상승했습니다."));

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


       // changes = new ChangeInfo("0.1.5a", false, null);
     //   changes.hardlight(CharSprite.NEUTRAL);
     //   changeInfos.add(changes);
    }
}
