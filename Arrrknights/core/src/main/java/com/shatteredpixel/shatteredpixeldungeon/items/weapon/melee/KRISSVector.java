package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class KRISSVector extends MeleeWeapon {
    public static final String AC_ZAP = "ZAP";
    {
        image = ItemSpriteSheet.KRISS_V;
        hitSound = Assets.Sounds.HIT_GUN;
        hitSoundPitch = 0.9f;

        defaultAction = AC_ZAP;

        tier = 5;
        ACC = 1f;
        DLY = 1f; //0.67x speed
        RCH = 2;    //extra reach
    }

    private int mode = 0;
    // 0, 1, 2

    @Override
    public int max(int lvl) {
        return  4*(tier+1) +                	//12 + 3
                lvl*(tier+1);
    }


    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_ZAP);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_ZAP)) {

            updateQuickslot();
            curUser.spendAndNext(0.5f);
        }
    }

    @Override
    public String desc() {
        if (mode == 2) return Messages.get(this, "desc_mode2");
        else if (mode == 1) return Messages.get(this, "desc_mode1");
        else return Messages.get(this, "desc");
    }

    @Override
    public String status() {
        if (this.isIdentified()) {
            if (mode == 2) return "OV";
            else if (mode == 1) return "SH";
            else return "AR";
        }
        else return null;}

    private static final String SWICH = "mode";
    private static final String RCHSAVE = "RCH";
    private static final String DLYSAVE = "DLY";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(SWICH, mode);
        bundle.put(RCHSAVE, RCH);
        bundle.put(DLYSAVE, DLY);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        mode = bundle.getInt(SWICH);
        RCH = bundle.getInt(RCHSAVE);
        DLY = bundle.getFloat(DLYSAVE);
    }
}
