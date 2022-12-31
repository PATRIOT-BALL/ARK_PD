package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.NervousImpairment;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Bug_ASprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Sea_ReaperSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class SeaReaper extends Mob{
    {
        spriteClass = Sea_ReaperSprite.class;

        HP = HT = 140;
        defenseSkill = 27;

        EXP = 15;
        maxLvl = 31;

        loot = new PotionOfHealing();
        lootChance = 0.17f;

        properties.add(Property.SEA);
    }

    boolean awaked = false;

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(26, 40);
    }

    @Override
    public int attackSkill( Char target ) {
        return 35;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 16);
    }

    @Override
    public float speed() {
        if (awaked) return super.speed() * 2f;
        return super.speed();
    }

    @Override
    public int defenseProc(Char enemy, int damage) {
        if (!awaked) awaked = true;

        return super.defenseProc(enemy, damage);
    }

    @Override
    protected boolean act() {
        if (awaked) {
            for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                Char ch = findChar( pos + PathFinder.NEIGHBOURS8[i] );
                if (ch != null && ch.isAlive() && ch instanceof Hero) {
                    if (enemy.buff(NervousImpairment.class) == null) {
                        Buff.affect(enemy, NervousImpairment.class);
                    }
                    else enemy.buff(NervousImpairment.class).Sum(10);
                }
            }
        }

        return super.act();
    }

    private static final String AWKAE   = "awaked";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( AWKAE, awaked );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        awaked = bundle.getBoolean(AWKAE);
    }
}
