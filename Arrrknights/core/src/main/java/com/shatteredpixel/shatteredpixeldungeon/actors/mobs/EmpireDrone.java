package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Stylus;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.A_master1Sprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DroneSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HandclapSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Imperial_artillerySprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ThiefSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

public class EmpireDrone extends Mob {
    {
        spriteClass = Imperial_artillerySprite.class;

        HP = HT = 130;
        defenseSkill = 18;

        EXP = 20;
        maxLvl = 28;

        loot = new Stylus();
        lootChance = 1f;

        flying = true;

        baseSpeed = 0.5f;
        immunities.add(Silence.class);
    }

    private int CoolDown = 0;
    private int LastPos = -1;

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 45, 60 );
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange( 0, 20 );
    }

    @Override
    protected boolean canAttack(Char enemy) {
        return false;
    }

    @Override
    protected boolean act() {
        if (CoolDown == 0) {
            if (LastPos == -1) {
                if (state != HUNTING) return super.act();
                else if (!this.fieldOfView[Dungeon.hero.pos]) return super.act();

                LastPos = Dungeon.hero.pos;
                sprite.parent.addToBack(new TargetedCell(LastPos, 0xFF0000));

                // 패턴 딜레이 추가
                spend(GameMath.gate(TICK, Dungeon.hero.cooldown(), 3*TICK));
                Dungeon.hero.interrupt();
                return true;
            }
            else  {
                if (LastPos == Dungeon.hero.pos) {
                    int dmg = damageRoll() - Dungeon.hero.drRoll();
                    Dungeon.hero.damage(dmg, this);
                    Dungeon.hero.sprite.burst(CharSprite.NEGATIVE, 10);
                    CellEmitter.center(LastPos).burst(BlastParticle.FACTORY, 10);
                    Camera.main.shake(5, 0.5f);
                    CoolDown = 1;
                    LastPos = -1;
                    spend( TICK );

                    if (!Dungeon.hero.isAlive()) {
                        Dungeon.fail( getClass() );
                        GLog.n( Messages.get(this, "bomb_kill") );
                    }
                    return true;
                }
                else {
                    CellEmitter.center(LastPos).burst(BlastParticle.FACTORY, 10);
                    Camera.main.shake(5, 0.5f);
                    CoolDown = 1;
                    LastPos = -1;
                }
            }

        }
        else CoolDown--;

        return super.act();
    }

    private static final String CD   = "CoolDown";
    private static final String SKILLPOS   = "LastPos";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( CD, CoolDown );
        bundle.put( SKILLPOS, LastPos );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        CoolDown = bundle.getInt(CD);
        LastPos = bundle.getInt(SKILLPOS);

    }
}
