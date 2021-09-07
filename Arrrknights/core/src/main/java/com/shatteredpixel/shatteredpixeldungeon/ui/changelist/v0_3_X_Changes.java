package com.shatteredpixel.shatteredpixeldungeon.ui.changelist;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;

import java.util.ArrayList;

public class v0_3_X_Changes {
    public static void addAllChanges(ArrayList<ChangeInfo> changeInfos) {
        add_v0_3_0_Changes(changeInfos);
    }

    public static void add_v0_3_0_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.3.0", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), "새로운 챕터",
                "_시에스타 Part 1_이 추가되었습니다. _31~35층_ 지역이며, _로도스 아일랜드(27~30층)_ 에서 진입할 수 있습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "챕터 5 새로운 적",
                "_정예 선봉_\n사거리 2의 물리 공격을 가하며, 방어력이 높은 편입니다. 체력이 50%이하가 되면 공격력이 상승합니다." + "\n\n" +
                "_백전 정예 선봉_\n정예 선봉의 변종으로, 능력치가 더 높습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "챕터 5 변종 추가",
                "_제국 드론_\n아츠마스터 A2의 변종으로, 플레이어를 인식하면 플레이어 위치에 포격 좌표를 찍습니다. 포격 지점에서 벗어나지 않으면 큰일 날 것입니다." + "\n" +
                "제국 드론을 처치하면 100%확률로 로고스의 골필을 얻을 수 있습니다." + "\n\n" +
                "_돌격자_\n살카즈 랜서의 변종으로, 가속 중첩으로 공격력을 상승시키는 대신 대상을 밀쳐내는 공격을 가합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "새로운 필살기",
                "_연계 전문화 블레이즈_는 _사격 무기_를 장착한 상태에선 새로운 필살기를 사용합니다." + "\n\n" +
                "_연속 타격 8 : 근접 섬광탄_은 대상에게 125%의 피해를 입히고 1턴의 마비와 연속 타격*0.3턴의 실명을 부여하며, 콤보를 유지합니다." + "\n\n" +
                "_연속 타격 10 : 영거리 사격_은 대상에게 연속 타격*30%의 피해를 입힙니다. 이 공격의 피해량은 무기의 근접 공격력이 아닌 _사격 무기의 사격 피해량_을 따릅니다."));


        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 개편",
                "_아미야_의 _에너지 넘치는 강화_ 특성이 _아츠 흡수_로 대체되었습니다. 이 특성은 아츠 기록장치로 스킬 발동시 흑요석 반지를 충전시킵니다." + "\n\n" +
                "_레드_의 _보호의 그림자_ 특성이 _아츠의 그림자_로 대체되었습니다. 망토로 은신 지속 중 일정 턴마다 아츠 기록장치를 충전합니다." + "\n\n" +
                "_레드_의 _신비한 강화_ 특성이 _추적자_로 대체되었습니다. 아츠 기록장치로 스킬 사용시 신속을 얻고 속박을 해제합니다." + "\n\n" +
                "_그레이스롯_의 _무기 손질_ 특성이 _연속처치_로 대체되었습니다. 적 처치시 짧은 턴동안 이동속도가 상승합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ARTI), "유뮬 개편",
                "_천구_ 의 충전 속도가 감소했지만, 레벨이 오르는 속도가 빨라졌습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 상향",
                "_맹목적인 순종_ 공격력이 4-14에서 _4-15_로 상승했으며, 강화 효율이 3에서 _4_로 증가했습니다. 대신 주 대상 피해 보너스가 최대 90%에서 _60%_로 감소했습니다." + "\n\n"+
        "_끓어오르는 투지_ 공격력이 5-16에서 _5-18_로 상승했으며, 강화 효율이 3에서 _4_로 증가했습니다. 또한, 명중률 보정치가 -25%에서 _-15%_로 상승했습니다." + "\n\n" +
                        "_나의 소원_으로 소환한 Mon3tr가 공격할 때마다 무기의 SP를 충전시킵니다." + "\n\n" +
                "_사격 무기_ 들의 최대 탄창수가 소폭 상승했으며, 사격 기능의 기본 공격력이 상승하고 명중률이 50%상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 상향",
        "_그레이스롯_의 _폭발 화살_ 특성의 피해 배율이 80~120%에서 _100~150%_로 상승했습니다."));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "무기 하향",
                "_적소_의 공격력이 2-9에서 _2-8_로 감소했습니다." + "\n\n" +
                "_영소_의 공격력이 5-18에서 _5-17_로 감소했습니다." + "\n\n" +
                "_M1887_의 공격력이 4-12에서 _4-10_으로 감소했습니다." + "\n\n" +
                "_성좌의 수호자_의 집중 공격의 피해 배율이 20%감소했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 하향",
                "_레드_의 _은밀한 이동_ +1 효과의 범위가 3칸에서 _2칸_으로 감소했습니다."));

    }
}
