package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.MasterThievesArmband;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.UnstableSpellbook;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAccuracy;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMistress;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class KRISSVector extends MeleeWeapon {
    public static final String AC_ZAP = "ZAP";
    {
        image = ItemSpriteSheet.KRISS_V;
        hitSound = Assets.Sounds.ZAP_GUN;
        hitSoundPitch = 1.76f;

        defaultAction = AC_ZAP;

        tier = 5;
        ACC = 1.4f;
        DLY = 1f;
        RCH = 2;    //extra reach
    }

    private int mode = 0;
    // 0, 1, 2

    @Override
    public int max(int lvl) {
        return  4*(tier) +                	//20 + 4
                lvl*(tier-1);
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

        if (action.equals(AC_ZAP) && isEquipped(hero)) {
            mode++;
            if (mode > 2) mode = 0;

            switch (mode) {
                default:
                case 0:
                    ACC = 1.4f;
                    DLY = 1f;
                    break;
                case 1:
                    ACC = 1f;
                    DLY = 0.75f;
                    break;
                case 2:
                    ACC = 0.4f;
                    DLY = 0.5f;
                    break;
            }

            updateQuickslot();
            curUser.spendAndNext(1f);
        }
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (mode == 2) {
            defender.damage(attacker.damageRoll(), attacker);
        }
        return super.proc(attacker, defender, damage);
    }

    @Override
    public String desc() {
        String info;
        if (mode == 2) info = Messages.get(this, "desc_mode2");
        else if (mode == 1) info = Messages.get(this, "desc_mode1");
        else info = Messages.get(this, "desc");

        if (VectorSetBouns()) {
            info += "\n\n" + Messages.get(KRISSVector.class, "setbouns"); }
        return info;
    }

    @Override
    public String status() {
        if (this.isIdentified()) {
            if (mode == 2) return "OV";
            else if (mode == 1) return "SH";
            else return "AR";
        }
        else return null;}

    public static boolean VectorSetBouns() {
        if (!(Dungeon.hero.belongings.weapon instanceof KRISSVector)) return false;

        if (Dungeon.hero.belongings.getItem(RingOfAccuracy.class) != null && Dungeon.hero.belongings.getItem(MasterThievesArmband.class) != null) {
            if (Dungeon.hero.belongings.getItem(RingOfAccuracy.class).isEquipped(Dungeon.hero) && Dungeon.hero.belongings.getItem(MasterThievesArmband.class).isEquipped(Dungeon.hero))
                return true;
        }
        return false;
    }

    private static final String SWICH = "mode";
    private static final String DLYSAVE = "DLY";
    private static final String ACCSAVE = "ACC";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(SWICH, mode);
        bundle.put(ACCSAVE, ACC);
        bundle.put(DLYSAVE, DLY);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        mode = bundle.getInt(SWICH);
        ACC = bundle.getFloat(ACCSAVE);
        DLY = bundle.getFloat(DLYSAVE);
    }
}
