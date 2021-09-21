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
                "_시에스타_가 추가되었습니다. _로도스 아일랜드(27~30층)_ 에서 진입할 수 있습니다. 최대 레벨이 40으로 증가했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "챕터 5 새로운 적",
                "_정예 선봉_\n사거리 2의 물리 공격을 가하며, 방어력이 높은 편입니다. 체력이 50%이하가 되면 공격력이 상승합니다." + "\n\n" +
                "_백전 정예 선봉_\n정예 선봉의 변종으로, 능력치가 더 높습니다." + "\n\n" +
                "_제국 드론_\n아츠마스터 A2의 변종으로, 플레이어를 인식하면 플레이어 위치에 포격 좌표를 찍습니다. 포격 지점에서 벗어나지 않으면 큰일 날 것입니다." + "\n" +
                "제국 드론을 처치하면 100%확률로 로고스의 골필을 얻을 수 있습니다." + "\n\n" +
                "_돌격자_\n살카즈 랜서의 변종으로, 가속 중첩으로 공격력을 상승시키는 대신 대상을 밀쳐내는 공격을 가합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.BLAZE), "새로운 필살기",
                "_연계 전문화 블레이즈_는 _사격 무기_를 장착한 상태에선 새로운 필살기를 사용합니다." + "\n\n" +
                        "_연속 타격 4 : 속사_는 대상에게 연속 타격*15%의 _사격 피해_를 입힙니다. 콤보를 1 얻습니다." + "\n\n" +
                "_연속 타격 8 : 근접 섬광탄_은 대상에게 125%의 피해를 입히고 1턴의 마비와 연속 타격*0.3턴의 실명을 부여하며, 콤보를 1 얻습니다." + "\n\n" +
                "_연속 타격 10 : 영거리 사격_은 대상에게 연속 타격*40%의 _사격 피해_를 입힙니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "새로운 시너지 7종 추가",
                "_최후의 웬디고\n용문총경\n광기의 노래\n용암거영\n라인랩 프렌즈!\n친우의 검\n이세계 전술_"));

        changes.addButton(new ChangeButton(Icons.get(Icons.BACKPACK), "새로운 아이템",
                "_타향의 노래_ : 4티어 무기로, 자신이나 적이 물 타일 위에 있다면 피해량이 상승합니다. 이 효과는 중첩됩니다." + "\n\n" +
                "_이세계 전술장비_ : 100%충전 후 발동시 미보를 1개 생성하는 유물입니다. 미보 및 파생 인형들의 피해량이 상승하며, 투척시 즉시 폭발하고 마비를 부여합니다." + "\n\n" +
                "_특별한 스태프_ 2종이 추가되었습니다."));


        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.BUG_KILL), "버그 수정",
                "체르노보그 섬멸 강화 머드락의 물타일 관련 버그가 어느정도 수정되었습니다." + "\n\n" +
                        "스킨 모델 관련 버그가 수정되었습니다. (해당 버그 픽스로 27층 세이브 데이터가 작동하지 않을 수도 있습니다.)" + "\n\n" +
                        "랭킹 기록에서 아이템 1개가 보이지않는 버그가 수정되었습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 개편",
                "_아미야_의 _에너지 넘치는 강화_ 특성이 _아츠 흡수_로 대체되었습니다. 이 특성은 아츠 기록장치로 스킬 발동시 흑요석 반지를 충전시킵니다." + "\n\n" +
                "_레드_의 _보호의 그림자_ 특성이 _아츠의 그림자_로 대체되었습니다. 망토로 은신 지속 중 일정 턴마다 아츠 기록장치를 충전합니다." + "\n\n" +
                "_레드_의 _신비한 강화_ 특성이 _추적자_로 대체되었습니다. 아츠 기록장치로 스킬 사용시 신속을 얻고 속박,불구,실명을 해제합니다." + "\n\n" +
                "_그레이스롯_의 _무기 손질_ 특성이 _연속처치_로 대체되었습니다. 적 처치시 짧은 턴동안 이동속도가 상승합니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ARTI), "유뮬 개편",
                "_천구_ 의 충전 속도가 감소했지만, 레벨이 오르는 속도가 빨라졌습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP_WOND), "스태프 개편",
                "_스태프 오브 압생트_의 피해량이 2-10에서 _6-18_로 상승했으며, 강화 효율이 1-3에서 _3-7_로 상승했습니다. 대신 체력 50%이하인 적에게만 피해를 입힐 수 있습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_BUFFS), "더욱 많은 변종",
                "변종 몬스터의 등장 확률이 2%에서 _3%_ 로 상승했습니다."));

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

        changes.addButton(new ChangeButton(Icons.get(Icons.WEP_WOND), "스태프 상향",
                "_스태프 오브 브리즈_의 안개 지속시간이 상승했습니다." + "\n\n" +
                "_스태프 오브 레나_의 기본 피해량이 2-6에서 _4-6_으로 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), "특성 상향",
        "_연계 전문화_의 _치명적인 방어_ 특성의 이름이 _최후의 일격_으로 변경되었으며, 효과 발동시 아츠 기록장치를 충전하는 효과가 추가되었습니다." + "\n\n" +
                "_암살 전문화_의 _SWEEP_ 특성에 +2 효과로, 효과 발동시 아츠 시야 획득이 추가되었습니다." + "\n\n" +
        "_그레이스롯_의 _폭발 화살_ 특성의 피해 배율이 80~120%에서 _100~150%_로 상승했습니다."));

        changes.addButton(new ChangeButton(Icons.get(Icons.SKILL), "스킬 상향",
                "_격노의 눈_과 _스콜피온 테일_의 피해량이 상승했습니다." + "\n\n" +
                "_낙지참_ 사용시 저주 장비 수와 관계없이 기본적으로 150턴의 축복을 얻습니다."));

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

        changes.addButton(new ChangeButton(Icons.get(Icons.ENEMY_NERFS), "리유니온 하향",
                "_살카즈 스나이퍼_\n체력이 110에서 _105_, 공격력이 32-44에서 _30-40_, 명중률이 42에서 _36_, 회피율이 24에서 _22_로 감소했습니다."));

    }
}
