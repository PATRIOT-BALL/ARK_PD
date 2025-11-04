package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.EarthParticle;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.FanaticSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Jumama_BossSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.Camera;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Eunectes extends Mob {
    {
        spriteClass = Jumama_BossSprite.class;

        HT = HP = 800;
        defenseSkill = 25;

        state = HUNTING;

        properties.add(Property.BOSS);
        immunities.add(Silence.class);
    }

    private boolean isBarrier = false;

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 48, 60 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 45;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 24);
    }

    @Override
    public void damage(int dmg, Object src) {
        if (dmg > HT / 2) {
            dmg = HT / 2;
        }

        super.damage(dmg, src);

        if (HT / 2 >= HP && !isBarrier) {
            Buff.affect(this, Barrier.class).setShield(400);
            isBarrier = true;
        }
    }

    @Override
    protected boolean act() {
        if (buff(Barrier.class) != null) {
            HP = Math.min(HP + 8, HT);
        }
        return super.act();
    }

    @Override
    public void notice() {
        if (!BossHealthBar.isAssigned()) {
            BossHealthBar.assignBoss(this);
            yell(Messages.get(this, "notice"));
        }
    }

    @Override
    public void die(Object cause) {
        int thispos = pos;
        Buff.affect(Dungeon.hero, TBUTCount.class, 3f).setpoint(thispos);
        super.die(cause);

        yell(Messages.get(this, "die"));
    }

    private static final String BARRIER = "isBarrier";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( BARRIER, isBarrier );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        isBarrier = bundle.getBoolean(BARRIER);
    }

    public static class TBUTCount extends FlavourBuff {

        int pos;

        public void setpoint(int n) {
            pos = n; }
        @Override
        public void detach() {
            TheBigUglyThing newboss = new TheBigUglyThing();
            newboss.pos = pos;
            GameScene.flash( 0x80FFFFFF );
            Camera.main.shake(2, 2f);

            if (Actor.findChar(pos) != null) {
                int pushPos = pos;
                for (int c : PathFinder.NEIGHBOURS8) {
                    if (Actor.findChar(pos + c) == null
                            && Dungeon.level.passable[pos + c]
                            && (Dungeon.level.openSpace[pos + c] || !hasProp(Actor.findChar(pos), Property.LARGE))
                            && Dungeon.level.trueDistance(pos, pos + c) > Dungeon.level.trueDistance(pos, pushPos)) {
                        pushPos = pos + c;
                    }
                }

                //push enemy, or wait a turn if there is no valid pushing position
                if (pushPos != pos) {
                    Char ch = Actor.findChar(pos);
                    Actor.addDelayed( new Pushing( ch, ch.pos, pushPos ), -1 );
                    ch.pos = pushPos;
                    Dungeon.level.occupyCell(ch );
                }
            }

            GameScene.add(newboss);

            PathFinder.buildDistanceMap(pos, BArray.not(Dungeon.level.solid, null), 3);
            for (int i = 0; i < PathFinder.distance.length; i++) {
                if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                    int vol = Fire.volumeAt(i, Fire.class);
                    if (vol < 3){
                        CellEmitter.get( i ).burst(EarthParticle.FALLING, 5 );
                    }
                }}

            super.detach();
        }

        private static final String POS = "pos";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(POS, pos);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            pos = bundle.getInt(POS);
        }
    }
}
