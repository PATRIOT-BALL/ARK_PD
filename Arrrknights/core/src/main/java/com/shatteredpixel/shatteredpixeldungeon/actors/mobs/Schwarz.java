package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BugSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ClosureSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HandclapSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SarkazSniperEliteSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SchwarzSprite;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Schwarz extends Mob {
    private static final String[] LINE_KEYS = {"snipe1", "snipe2", "snipe3"};
    {
        spriteClass = SchwarzSprite.class;

        HP = HT = 1000;
        defenseSkill = 100;

        state = HUNTING;

        maxLvl = 45;
        EXP = -1;
        properties.add(Property.BOSS);
        immunities.add(Drowsy.class);
        immunities.add(MagicalSleep.class);
        immunities.add(Terror.class);
        immunities.add(Silence.class);
    }

    public int Phase = 1;
    private int CoolDown = 8;
    private int LastPos = -1;

    @Override
    public int damageRoll()
    {
        if (Phase==2) return Random.NormalIntRange( 65, 80 );
        return Random.NormalIntRange( 50, 70 );
    }

    @Override
    public int attackSkill( Char target ) {return 40; }

    @Override
    public void damage(int dmg, Object src) {
        Sample.INSTANCE.play( Assets.Sounds.HIT_PARRY, 1, Random.Float(0.96f, 1.05f));
        sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "parried"));
    }


    @Override
    protected boolean act() {
        if (CoolDown == 0) {
            if (LastPos == -1) {
                LastPos = Dungeon.hero.pos;
                sprite.parent.addToBack(new TargetedCell(LastPos, 0xFF0000));
                yell(Messages.get(this, Random.element( LINE_KEYS )));

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
                    Sample.INSTANCE.play(Assets.Sounds.SKILL_CROSSBOW);
                    if (Phase == 1) CoolDown = 8;
                    else CoolDown = 5;
                    LastPos = -1;
                    spend( TICK );
                    return true;
                }
                else {
                    CellEmitter.center(LastPos).burst(HandclapSprite.GooParticle.FACTORY, 60);
                    Camera.main.shake(5, 0.5f);
                    Sample.INSTANCE.play(Assets.Sounds.SKILL_CROSSBOW);
                    if (Phase == 1) CoolDown = 8;
                    else CoolDown = 5;
                    LastPos = -1;
                }
            }

        }
        else CoolDown--;

        return super.act();
    }

    private static final String PHASE   = "Phase";
    private static final String CD   = "CoolDown";
    private static final String SKILLPOS   = "LastPos";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( PHASE, Phase );
        bundle.put( CD, CoolDown );
        bundle.put( SKILLPOS, LastPos );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        Phase = bundle.getInt(PHASE);
        CoolDown = bundle.getInt(CD);
        LastPos = bundle.getInt(SKILLPOS);

    }
}
