package com.shatteredpixel.shatteredpixeldungeon.items.artifacts;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RadiantKnight;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class SealOfLight extends Artifact {
    {
        image = ItemSpriteSheet.ARTIFACT_NEARL;
        defaultAction = AC_HIKARI;

        levelCap = 0;

        charge = 100;
        partialCharge = 0;
        chargeCap = 100;

        unique = true;
        bones = false;

        defaultAction = AC_HIKARI;
    }

    public static final String AC_HIKARI = "HIKARI";

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (isEquipped(hero) && !cursed)
            actions.add(AC_HIKARI);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_HIKARI)) {
            if (hero.buff(RadiantKnight.class) == null) {
                if (!isEquipped(hero) && !hero.hasTalent(Talent.LIGHT_CLOAK))
                    GLog.i(Messages.get(Artifact.class, "need_to_equip"));
                else if (cursed) GLog.i(Messages.get(this, "cursed"));
                else if (charge < 100) GLog.i(Messages.get(this, "no_charge"));
                else {
                    Buff.affect(hero, RadiantKnight.class, RadiantKnight.DURATION);

                    if (hero.subClass == HeroSubClass.KNIGHT) {
                        Buff.affect(hero, Haste.class, 5f +  hero.pointsInTalent(Talent.QUICK_TACTICS));
                    }

                    if (hero.hasTalent(Talent.BLESSED_CHAMPION)) {
                        Buff.affect(hero, Bless.class, 10f *  hero.pointsInTalent(Talent.BLESSED_CHAMPION));
                    }

                    if (hero.hasTalent(Talent.PEGASUS_AURA)) {
                        int Barrior = hero.HT/20;
                        Barrior *= hero.pointsInTalent(Talent.PEGASUS_AURA);
                        Buff.affect(hero, Barrier.class).setShield(Barrior);
                    }

                    if (hero.hasTalent(Talent.QUICK_TACTICS)) hero.spendAndNext(0f);
                    else hero.spendAndNext(1f);
                    GameScene.flash( 0x80FFFFFF );
                    Sample.INSTANCE.play(Assets.Sounds.SKILL_BABYNIGHT);
                    charge = 0;
                    updateQuickslot();
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
            if (cursed) {
                desc += "\n\n";
            desc += Messages.get(this, "desc_cursed");}
        }
        return desc;
    }

    @Override
    protected ArtifactBuff passiveBuff() {
        return new SealOfLight.HIKARIBuff();
    }


    public class HIKARIBuff extends ArtifactBuff {
        @Override
        public boolean act() {
            LockedFloor lock = target.buff(LockedFloor.class);
            if ((lock == null || lock.regenOn())) {
                if (charge < chargeCap && !cursed) {
                    // 약 300 턴마다 100%충전 (기본)
                    float chargeGain = 0.34f;
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
            updateQuickslot();
        }
    }
}
