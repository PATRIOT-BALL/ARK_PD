package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.NervousImpairment;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.NervousPotion;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Bug_ASprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.First_talkSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class SeaBoss1 extends Mob{
    {
        spriteClass = First_talkSprite.class;

        HP = HT = 1000;
        EXP = 40;

        defenseSkill = 25;

        properties.add(Property.SEA);
        properties.add(Property.BOSS);
    }

    boolean SkillActive = false; // true라면 받는 피해 감소
    int phase = 0;

    /*
    1. 패턴은 평타 / 돌떨구기

    2. 돌떨구기는 머드락 패턴을 연속해서 쓰는 느낌. 맞으면 침식+낮은 딜 걸리고 침식터지면 감속때문에 연속해서 맞게됨

    3. 잡몹뽑음

    4. 보스맵에 침식회복시켜주는 종 있음

    5. 이 종 소추남 패턴에 맞으면 폭발해서 무빙 실수하면 하드모드됨
    */

    @Override
    public int damageRoll() {
        if (HP <= 500) return Random.NormalIntRange(45, 60);
        return Random.NormalIntRange(30, 50);
    }

    @Override
    public int attackSkill( Char target ) {
        return 40;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 24);
    }

    @Override
    public int attackProc(Char enemy, int damage) {

        if (enemy instanceof Hero || enemy instanceof DriedRose.GhostHero) {
            if (enemy.buff(NervousImpairment.class) == null) {
                Buff.affect(enemy, NervousImpairment.class);
            } else enemy.buff(NervousImpairment.class).Sum(20);
        }

        return super.attackProc(enemy, damage);
    }

    @Override
    public void damage(int dmg, Object src) {
        if (SkillActive) dmg /= 4;
        super.damage(dmg, src);

        if (phase==1 && HP <= 600) {
            HP = 600;
            phase = 2;
            GameScene.flash(0x80FF0000);
            Buff.affect(Dungeon.hero, Silence.class, 15f);
        }
    }

    @Override
    public void notice() {
        if (!BossHealthBar.isAssigned()) {
            BossHealthBar.assignBoss(this);
            yell(Messages.get(this, "notice"));

            if (phase == 0) {
                phase = 1;
            }
        }
    }

    private static final String PHASE   = "phase";


    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( PHASE, phase );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        phase = bundle.getInt(PHASE);
        BossHealthBar.assignBoss(this);

    }
}
