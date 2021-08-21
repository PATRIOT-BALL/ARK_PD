package com.shatteredpixel.shatteredpixeldungeon.items.artifacts;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class CustomeSet extends Artifact {
    {
        image = ItemSpriteSheet.ARTIFACT_TOOLKIT;
        defaultAction = AC_SHADOW;

        levelCap = 10;

        charge = 100;
        partialCharge = 0;
        chargeCap = 100;

        defaultAction = AC_SHADOW;
    }

    public static final String AC_SHADOW = "SHADOW";

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (isEquipped(hero) && !cursed)
            actions.add(AC_SHADOW);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_SHADOW)) {
            if (activeBuff == null) {
                if (!isEquipped(hero) && !hero.hasTalent(Talent.LIGHT_CLOAK))
                    GLog.i(Messages.get(Artifact.class, "need_to_equip"));
                else if (cursed) GLog.i(Messages.get(this, "cursed"));
                else if (charge < 100) GLog.i(Messages.get(this, "no_charge"));
                else {
                    int mirror = 1 + level() / 5;
                    new ScrollOfMirrorImage().spawnImages(curUser, mirror);
                    charge = 0;
                    updateQuickslot();

                    if (level() < levelCap) upgrade();

                    curUser.spendAndNext(1f);
                }
            }
        }
    }

    @Override
    public void charge(Hero target, float amount) {
        if (charge < chargeCap) {
            charge += Math.round(1 * amount);
            if (charge >= chargeCap) {
                charge = chargeCap;
                updateQuickslot();
            }
        }
    }

    @Override
    public String desc() {
        String desc = super.desc();

        if (isEquipped(Dungeon.hero)) {
            if (cursed)
                desc += "\n\n";
            desc += Messages.get(this, "desc_cursed");
        }
        return desc;
    }

    @Override
    protected ArtifactBuff passiveBuff() {
        return new CustomeSet.CustomSetBuff();
    }


    public class CustomSetBuff extends ArtifactBuff {
        @Override
        public boolean act() {
            LockedFloor lock = target.buff(LockedFloor.class);
            if (activeBuff == null && (lock == null || lock.regenOn())) {
                if (charge < chargeCap && !cursed) {
                    // 667 턴마다 100%충전 (기본)
                    float chargeGain = 0.15f;
                    chargeGain *= RingOfEnergy.artifactChargeMultiplier(target);
                    partialCharge += chargeGain;

                    if (partialCharge > 1 && charge < chargeCap) {
                        partialCharge--;
                        charge++;
                        updateQuickslot();
                    }
                }
            }
            else partialCharge = 0;

            spend(TICK);
            return true;
        }
    }
}
