package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.CorrosiveGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.EnergyParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.AceSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class ACE_BATTLE extends Mob {
    {
        HP=HT=135;
        defenseSkill=20;
        spriteClass = AceSprite.class;

        state = HUNTING;

        alignment = Alignment.ALLY;
        properties.add(Property.NPC);
        immunities.add(Burning.class);
        immunities.add(ToxicGas.class);
        immunities.add(Corrosion.class);

        WANDERING = new ACE_BATTLE.Wandering();
    }

    private int attackcount = 0;
    private int skillcount = 0;

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(25, 35);
    }

    @Override
    public int attackSkill( Char target ) {
        return 35;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(12, 20);
    }

    @Override
    protected boolean act() {
        if (isAlive()) {
            HP = Math.min(HP+2, HT);
        }

        if (!Dungeon.level.locked){
            die(this);
        }
        return super.act();
    }

    @Override
    public void die(Object cause) {
        yell(Messages.get(this, "fail"));
        super.die(cause);
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        // 특수 스킬 패턴. 12번 공격마다
        if (skillcount == 4) {
            Buff.affect(this, Adrenaline.class, 2f);
            Buff.affect(Dungeon.hero, Adrenaline.class, 2f);
            this.sprite.centerEmitter().start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
            yell(Messages.get(this, "battlecry"));
            skillcount = 0;
            return super.attackProc(enemy, damage);
        }

        // 기본 특수 패턴. 3회 공격시 피해량 상승
        if (attackcount > 2) {
            damage *= 1.5f;
            CellEmitter.center(enemy.pos).burst(BlastParticle.FACTORY, 3);
            skillcount++;
            attackcount = 0;
        }
        else attackcount++;
        return super.attackProc(enemy, damage);
    }

    public void setTarget(int cell) {
        target = cell;
    }

    private class Wandering extends Mob.Wandering {

        @Override
        public boolean act( boolean enemyInFOV, boolean justAlerted ) {
            if ( enemyInFOV ) {

                enemySeen = true;

                notice();
                alerted = true;
                state = HUNTING;
                target = enemy.pos;

            } else {

                enemySeen = false;

                int oldPos = pos;
                target = Dungeon.hero.pos;
                //always move towards the hero when wandering
                if (getCloser( target )) {
                    //moves 2 tiles at a time when returning to the hero
                        getCloser( target );

                    spend( 1 / speed() );
                    return moveSprite( oldPos, pos );
                } else {
                    spend( TICK );
                }

            }
            return true;
        }

    }


    private static final String ATT =   "attackcount";
    private static final String SKL =       "skillcount";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);

        bundle.put( ATT, attackcount );
        bundle.put( SKL, skillcount );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);

        attackcount = bundle.getInt( ATT );
        skillcount = bundle.getInt( SKL );
    }
}
