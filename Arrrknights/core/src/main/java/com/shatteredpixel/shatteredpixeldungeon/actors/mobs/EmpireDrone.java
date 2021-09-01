package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HandclapSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ThiefSprite;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

public class EmpireDrone extends Mob {
    {
        spriteClass = ThiefSprite.class;

        HP = HT = 75;
        defenseSkill = 12;

        EXP = 0; // 미정
        maxLvl = 5; // 미정

        loot = new MysteryMeat(); // 미정
        lootChance = 0.2f;

    }

    private int CoolDown = 8;
    private int LastPos = -1;

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 30, 60 );
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange( 0, 8 );
    }

    @Override
    protected Char chooseEnemy() {
        return null;
    }

    @Override
    protected boolean act() {
        if (CoolDown == 0) {
            if (LastPos == -1) {
                if (state != HUNTING) return true;
                LastPos = Dungeon.hero.pos;
                sprite.parent.addToBack(new TargetedCell(LastPos, 0xFF0000));

                // 패턴 딜레이 추가
                spend(GameMath.gate(TICK, Dungeon.hero.cooldown(), 3*TICK));
                Dungeon.hero.interrupt();
                return true;
            }
            else  {
                if (LastPos == Dungeon.hero.pos) {
                    Dungeon.hero.damage(damageRoll(), this);
                    Dungeon.hero.sprite.burst(CharSprite.NEGATIVE, 10);
                    CellEmitter.center(Dungeon.hero.pos).burst(HandclapSprite.GooParticle.FACTORY, 60);
                    Camera.main.shake(5, 0.5f);
                    CoolDown = 5;
                    LastPos = -1;
                    spend( TICK );
                    return true;
                }
                else {
                    CellEmitter.center(LastPos).burst(HandclapSprite.GooParticle.FACTORY, 60);
                    Camera.main.shake(5, 0.5f);
                    CoolDown = 5;
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
