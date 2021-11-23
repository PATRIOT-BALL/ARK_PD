package com.shatteredpixel.shatteredpixeldungeon.items.artifacts;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

import java.util.ArrayList;

public class IsekaiItem extends Artifact {
    {
        image = ItemSpriteSheet.ARTIFACT_ASH;
        levelCap = 10;

        charge = 0;
        chargeCap = 100;

        defaultAction = AC_BOMB;
    }

    public static final String AC_BOMB = "BOMB";


    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (!cursed && isEquipped(hero)) {
            actions.add(AC_BOMB);
        }
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_BOMB)){
            if (charge == chargeCap) {
                Dungeon.level.drop(new Bomb(), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                charge = 0;
                if (level() < levelCap) upgrade();
                updateQuickslot();
            }
        }
    }

    @Override
    public String status() {

        //if the artifact isn't IDed, or is cursed, don't display anything
        if (!isIdentified() || cursed) {
            return null;
        }
        //display as percent
        if (chargeCap == 100)
            return Messages.format("%d%%", charge);


        //otherwise, if there's no charge, return null.
        return null;
    }


    public void getCharge(int gaincharge) {
        charge += gaincharge;
        charge = Math.min(charge, chargeCap);
    }

    @Override
    public String desc() {
        String desc = super.desc();

        if (isEquipped(Dungeon.hero)) {
            if (cursed) {
                desc += "\n\n";
            desc += Messages.get(this, "desc_cursed");}
        }
        return desc;
    }


    @Override
    protected ArtifactBuff passiveBuff() { return new IsekaiItem.IsekaiBuff();
    }

    public class IsekaiBuff extends ArtifactBuff {
        public boolean act() {
            LockedFloor lock = target.buff(LockedFloor.class);
            if (activeBuff == null && (lock == null || lock.regenOn()) && !(Dungeon.depth >= 26 && Dungeon.depth <= 30)) {
                if (charge < chargeCap && !cursed) {
                    // 500 턴마다 100%충전 (기본)
                    float chargeGain = 0.2f;
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

        @Override
        public void charge(Hero target, float amount) {
            charge += Math.round(1*amount);
            charge = Math.min(charge, chargeCap);
            if (charge == chargeCap) GLog.p( Messages.get(Artifact.class, "full_charge") );
            updateQuickslot();
        }
    }
}
