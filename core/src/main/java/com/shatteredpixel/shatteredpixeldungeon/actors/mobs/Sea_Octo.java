package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.NervousImpairment;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Sea_SpewerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.StrikerSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Sea_Octo extends Mob {
    {
        spriteClass = Sea_SpewerSprite.class;

        HP = HT = 125;
        defenseSkill = 20;

        EXP = 17;
        maxLvl = 36;

        loot = Generator.Category.SEED;
        lootChance = 0.3f;

        properties.add(Property.SEA);
    }

    private boolean firstTEEROR = false;

    @Override
    protected boolean act() {

        //스폰시 첫 행동하면서 명흔을 깝니다.
        if (!firstTEEROR) {
            Level.set(this.pos, Terrain.SEA_TERROR);
            GameScene.updateMap(this.pos);

            firstTEEROR = true;
        }
        return super.act();
    }

    @Override
    protected boolean canAttack(Char enemy) {
        if (Dungeon.level.map[this.pos] == Terrain.SEA_TERROR)
            return this.fieldOfView[enemy.pos] && Dungeon.level.distance(this.pos, enemy.pos) <= 8;
        return this.fieldOfView[enemy.pos] && Dungeon.level.distance(this.pos, enemy.pos) <= 1;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(20, 42);
    }

    @Override
    public int attackSkill( Char target ) {
        return 36;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 18); }

    @Override
    public int attackProc(Char enemy, int damage) {
        int ndamage = 8;
        if (Dungeon.isChallenged(Challenges.TACTICAL_UPGRADE)) ndamage = 16;

        if (Dungeon.depth == 39) ndamage /= 2;

        if (enemy instanceof Hero || enemy instanceof DriedRose.GhostHero) {
            if (enemy.buff(NervousImpairment.class) == null) {
                Buff.affect(enemy, NervousImpairment.class);
            } else enemy.buff(NervousImpairment.class).Sum(ndamage);
        }

        return super.attackProc(enemy, damage);
    }

    private static final String VAL   = "firstTEEROR";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( VAL, firstTEEROR );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        firstTEEROR = bundle.getBoolean(VAL);
    }
}
