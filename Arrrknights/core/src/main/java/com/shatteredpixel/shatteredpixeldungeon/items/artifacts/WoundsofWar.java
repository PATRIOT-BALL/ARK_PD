package com.shatteredpixel.shatteredpixeldungeon.items.artifacts;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Oblivion;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class WoundsofWar extends Artifact {
    {
        image = ItemSpriteSheet.ARTIFACT_CAMERA;

        levelCap = 10;

        charge = 0;
        partialCharge = 0;
        chargeCap = 1 + level() / 5;

        defaultAction = AC_SNAP;
    }

    public static final String AC_SNAP = "SNAP";

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (isEquipped(hero) && !cursed)
            actions.add(AC_SNAP);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_SNAP)) {
            if (activeBuff == null) {
                if (!isEquipped(hero) && !hero.hasTalent(Talent.LIGHT_CLOAK))
                    GLog.i(Messages.get(Artifact.class, "need_to_equip"));
                else if (cursed) GLog.i(Messages.get(this, "cursed"));
                else if (charge < 1) GLog.i(Messages.get(this, "no_charge"));
                else {
                    PathFinder.buildDistanceMap( hero.pos, BArray.not( Dungeon.level.solid, null ), 4 );
                    for (int i = 0; i < PathFinder.distance.length; i++) {
                        if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                            Char ch = Actor.findChar(i);
                            if (ch != null && ch != hero) {
                                Buff.affect(ch, Oblivion.class, Oblivion.DURATION);
                            }
                        }
                    }
                    charge -= 1;
                    GameScene.flash( 0x80FFFFFF );
                    Sample.INSTANCE.play( Assets.Sounds.LIGHTNING, 1f, 1.32f );
                    updateQuickslot();

                    exp+=50;
                    if (exp >= 50 + level() * 50 && level() < levelCap) upgrade();

                    curUser.spendAndNext(1f);
                }
            }
        }
    }

    @Override
    public void level(int value) {
        super.level(value);
        chargeCap = 1 + level()/5;
    }

    @Override
    protected ArtifactBuff passiveBuff() { return new WoundsofWar.cameraRecharge();
    }

    public class cameraRecharge extends ArtifactBuff {
        public boolean act() {
            LockedFloor lock = target.buff(LockedFloor.class);
            if (activeBuff == null && (lock == null || lock.regenOn()) && !(Dungeon.depth >= 26 && Dungeon.depth <= 30)) {
                if (charge < chargeCap && !cursed) {
                    // 200 턴마다 100%충전 (기본)
                    float chargeGain = 0.005f + level() * 0.0001f;
                    chargeGain *= RingOfEnergy.artifactChargeMultiplier(target);
                    partialCharge += chargeGain;

                    if (partialCharge > 1 && charge < chargeCap) {
                        partialCharge--;
                        charge++;
                        updateQuickslot();
                    }
                }
            } else partialCharge = 0;

            spend(TICK);
            return true;
        }

        @Override
        public void charge(Hero target, float amount) {
            charge += Math.round(1 * amount);
            charge = Math.min(charge, chargeCap);
            updateQuickslot();
        }
    }
}
