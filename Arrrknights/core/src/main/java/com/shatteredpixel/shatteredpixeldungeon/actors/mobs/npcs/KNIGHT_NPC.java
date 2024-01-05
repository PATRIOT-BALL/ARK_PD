package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.NervousImpairment;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.AceSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class KNIGHT_NPC extends Mob{
    {
            HP=HT=200;
            defenseSkill=20;
            spriteClass = AceSprite.class;

            state = HUNTING;

            alignment = Alignment.ALLY;
            properties.add(Property.NPC);
            immunities.add(Burning.class);
            immunities.add(ToxicGas.class);
            immunities.add(Corrosion.class);
            immunities.add(NervousImpairment.class);

        WANDERING = new KNIGHT_NPC.Wandering();
        }

        private int KNIGHT_LEVEL = 0; // 강화

        public void LevelUP(){
        KNIGHT_LEVEL++;
         }

        @Override
        public int damageRoll() {

            return Random.NormalIntRange(20 * KNIGHT_LEVEL, 25 * KNIGHT_LEVEL);
        }

        @Override
        public int attackSkill( Char target ) {
            return 20 + (KNIGHT_LEVEL * 8);
        }

    @Override
    public float speed() {
        if (KNIGHT_LEVEL == 4) return super.speed() * 2f;
        return super.speed();
    }

        @Override
        public int drRoll() {
            return Random.NormalIntRange(KNIGHT_LEVEL * 5, 35);
        }

        @Override
        protected boolean act() {

            return super.act();
        }

    @Override
    public void damage(int dmg, Object src) {

            dmg /= (1 + KNIGHT_LEVEL);

        super.damage(dmg, src);
    }

    @Override
        public void die(Object cause) {
            yell(Messages.get(this, "fail"));
            super.die(cause);
        }

        @Override
        public int attackProc(Char enemy, int damage) {

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
    }