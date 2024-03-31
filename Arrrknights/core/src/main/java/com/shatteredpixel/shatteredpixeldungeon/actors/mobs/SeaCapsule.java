package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.NervousImpairment;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.NervousPotion;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Nevous;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Bug_ASprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Sea_CrawlerSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class SeaCapsule extends Mob{
    {
        spriteClass = Sea_CrawlerSprite.class;

        HP = HT = 150;
        EXP = 17;
        maxLvl = 32;

        defenseSkill = 10;

        loot = new Nevous();
        lootChance = 0.55f;

        properties.add(Property.SEA);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(12, 24);
    }

    @Override
    public int attackSkill( Char target ) {
        return 30;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(10, 20);
    }

    @Override
    public int defenseProc(Char enemy, int damage) {
        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
            Char ch = findChar( pos + PathFinder.NEIGHBOURS8[i] );
            if (ch != null && ch.isAlive() && ch instanceof Hero) {
                if (enemy.buff(NervousImpairment.class) == null) {
                    Buff.affect(enemy, NervousImpairment.class);
                }
                else enemy.buff(NervousImpairment.class).Sum(20);
            }
        }

        return super.defenseProc(enemy, damage);
    }
}
