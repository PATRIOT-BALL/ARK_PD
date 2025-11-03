package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class MetallicUnion extends MeleeWeapon {
    public static final String AC_CHANGE = "CHANGE";
    {
        image = ItemSpriteSheet.PANDA;
        hitSound = Assets.Sounds.HIT_PUNCH;
        hitSoundPitch = 0.9f;

        defaultAction = AC_CHANGE;

        tier = 4;
        ACC = 0.95f;
    }

    private boolean swiching = true;
    private int AttackCount = 0;

    @Override
    public int max(int lvl) {
        if (!swiching)
            return  5*(tier+1) +                	// 25 + 6
                    lvl*(tier+2);
        else return  2*(tier) +                	// 8 + 2
                lvl*(tier-2);
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_CHANGE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_CHANGE)) {
            if (swiching == true) {
                swiching = false;
                AttackCount = 0;
                GLog.w(Messages.get(this, "modechange_nm"));
                updateQuickslot();
                curUser.spendAndNext(1f);
            }
        }
    }


    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (swiching) {
            Ballistica trajectory = new Ballistica(attacker.pos, defender.pos, Ballistica.STOP_TARGET);
            trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
            WandOfBlastWave.throwChar(defender, trajectory, 1); // 넉백 효과
        }

        AttackCount++;
        if (attacker instanceof Hero) {
            if (AttackCount >= 10) {
                if (swiching) {
                    swiching = false;
                    GLog.w(Messages.get(this, "modechange_nm"));
                    AttackCount = 0;
                    updateQuickslot();
                } else {
                    swiching = true;
                    GLog.w(Messages.get(this, "modechange_ex"));
                    AttackCount = 0;
                    updateQuickslot();
                }
            }
        }

        return super.proc(attacker, defender, damage);
    }

    @Override
    public String desc() {
        if (swiching) return Messages.get(this, "desc_mode2");
        else return Messages.get(this, "desc_mode1");
    }

    public String statsInfo(){
        return Messages.get(this, "stats_desc");
    }

    @Override
    public String status() {
        if (this.isIdentified()) {
            if (swiching) return "EX";
            else return "NM";
        }
        else return null;}

    private static final String SWICH = "swiching";
    private static final String COUNT = "AttackCount";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(SWICH, swiching);
        bundle.put(COUNT, AttackCount);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        swiching = bundle.getBoolean(SWICH);
        AttackCount = bundle.getInt(COUNT);
    }
}
