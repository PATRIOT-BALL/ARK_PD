package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.NervousImpairment;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.NervousPotion;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Bug_ASprite;
import com.watabou.utils.Random;

public class SeaBoss1 extends Mob{
    {
        spriteClass = Bug_ASprite.class;

        HP = HT = 1000;
        EXP = 40;

        defenseSkill = 25;

        properties.add(Property.SEA);
    }

    boolean SkillActive = false; // true라면 받는 피해 감소

    //패시브 : 매 턴마다 플레이어가 어디에 있던 신경손상 1% 부여
    //패턴 : 5+15턴마다 플레이어 위치 중심으로 3x3경고 -> 1턴 뒤 해당 위치에 4턴에 걸쳐 총합 600%의 피해 (매 턴마다 해당 범위의 공격력 x1.5배의 방어력으로 감소하는 피해)
    //패턴 발동 중에는 받는 피해가 75%감소하지만, 다른 행동을 하지 않는다.
    // HP가 500이하가 되면 공격력 증가

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
            } else enemy.buff(NervousImpairment.class).Sum(15);
        }

        return super.attackProc(enemy, damage);
    }

    @Override
    public void damage(int dmg, Object src) {
        if (SkillActive) dmg /= 4;
        super.damage(dmg, src);
    }
}
