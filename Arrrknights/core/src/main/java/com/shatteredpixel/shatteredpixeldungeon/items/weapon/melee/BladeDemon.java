package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class BladeDemon extends MeleeWeapon {
    public static final String AC_ZAP = "ZAP";
    {
        image = ItemSpriteSheet.BLADE_DEMON;
        hitSound = Assets.Sounds.HIT_STAB;
        hitSoundPitch = 1.16f;

        tier = 4;
        DLY = 0.8f; // 125%
    }

    private boolean swiching = false;

    @Override
    public int max(int lvl) {
        return  4*(tier+1) + 3 +    //23 + 5
                lvl*(tier+1);   //scaling unchanged
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
            if (swiching == true) {
                swiching = false;DLY = 0.8f;
            } else {
                swiching = true;DLY = 1f;
            }

            updateQuickslot();
            curUser.spendAndNext(0.5f);
        }
    }

    public boolean isSwiching() {
        return swiching;
    }

    @Override
    public String desc() {
        if (swiching) return Messages.get(this, "desc_mode");
        else return Messages.get(this, "desc");
    }

    @Override
    public String status() {
        if (this.isIdentified()) {
            if (swiching) return "EX";
            else return "NM";
        }
        else return null;}

    private static final String SWICH = "swiching";
    private static final String DLYSAVE = "DLY";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(SWICH, swiching);
        bundle.put(DLYSAVE, DLY);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        swiching = bundle.getBoolean(SWICH);
        DLY = bundle.getFloat(DLYSAVE);
    }
}
