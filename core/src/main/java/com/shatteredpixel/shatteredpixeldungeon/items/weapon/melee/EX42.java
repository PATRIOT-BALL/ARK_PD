package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Piranha;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class EX42 extends MeleeWeapon {

    public static final String AC_ZAP = "ZAP";
    {
        image = ItemSpriteSheet.EX41;
        hitSound = Assets.Sounds.HIT_WALL1;
        hitSoundPitch = 0.65f;

        defaultAction = AC_ZAP;

        tier = 1;

        bones = false;
    }

    private boolean swiching = false;

    @Override
    public int getReach() {
        return swiching ? 50 : 1;
    }

    @Override
    public int max(int lvl) {
        if (swiching == false) {
            return  1 + 4*(tier+1) +     //9
                    lvl*(tier+1);  //+1 per level, down from +2
        } else {
            return  1 + tier+1 +     //3
                    lvl*(tier);}  //+1 per level, down from +2
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {

        if (defender instanceof Piranha)
        {
            damage *= 3;
        }
        return super.proc(attacker, defender, damage);
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
            swiching = !swiching;
            updateQuickslot();
            curUser.spendAndNext(0.5f);}
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
    private static final String RCHSAVE = "RCH";
    private static final String ACCSAVE = "ACC";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(SWICH, swiching);
        bundle.put(RCHSAVE, RCH);
        bundle.put(ACCSAVE, ACC);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        swiching = bundle.getBoolean(SWICH);
        RCH = bundle.getInt(RCHSAVE);
        ACC = bundle.getInt(ACCSAVE);
    }

}
