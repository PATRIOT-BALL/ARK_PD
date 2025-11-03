package com.shatteredpixel.shatteredpixeldungeon.ui.changelist;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;

import java.util.ArrayList;

public class v0_5_X_Changes {
    public static void addAllChanges(ArrayList<ChangeInfo> changeInfos) {
        add_v0_5_0_Changes(changeInfos);
    }

    public static void add_v0_5_0_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("v0.5.0", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.DEPTH), "이베리아",
                "아직 개발중입니다!"));
        changes.addButton(new ChangeButton(Icons.get(Icons.WEP), "사격무기",
                "새로운 무기 종류인 _사격 무기_가 추가되었습니다" + "\n\n" +
                "_화이트팽 465_ 3티어 무기입니다" + "\n\n" +
                "_스털링 기관단총_ 3티어 무기로, 공격속도가 빠릅니다" + "\n\n" +
                "_DP 27_ 3티어 무기로, 공격속도가 매우 빠릅니다" + "\n\n" +
                "_OTs-03_ 4티어 무기로, 강력한 데미지를 가졌지만 사격속도가 매우 느립니다" + "\n\n" +
                "_6P41_ 5티어 무기로, 공격속도가 빠르지만 명중률이 낮습니다" + "\n\n" +
                "_R4-C_ 5티어 무기로, 공격속도가 매우 빠릅니다" + "\n\n"));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(CharSprite.WARNING);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "buffs"), false, null);
        changes.hardlight(CharSprite.POSITIVE);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "nerfs"), false, null);
        changes.hardlight(CharSprite.NEGATIVE);
        changeInfos.add(changes);
    }
}
