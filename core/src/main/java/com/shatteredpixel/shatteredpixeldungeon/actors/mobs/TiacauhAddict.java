package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hallucination;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BreakerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HaundSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Tiacauh_DrugsSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class TiacauhAddict extends Mob {
    {
        spriteClass = Tiacauh_DrugsSprite.class;
        baseSpeed = 2f;

        HP = HT = 120;
        defenseSkill = 24;

        EXP = 14;
        maxLvl = 32;

        loot = Generator.Category.SKL_RND;
        lootChance = 0.1f;

        immunities.add(Silence.class);
        immunities.add(ToxicGas.class);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 36, 46 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 42;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 14);
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if (Random.Int(2) == 0) {
            Buff.affect(enemy, Weakness.class, 4f);
            Buff.affect(enemy, Vulnerable.class, 4f);
        }

        if (Dungeon.isChallenged(Challenges.TACTICAL_UPGRADE) && Random.Int(5) == 0) {
            Buff.affect(enemy, Hallucination.class).set(3f);
        }
        return super.attackProc(enemy, damage);
    }

    @Override
    public void damage(int dmg, Object src) {
        if (src == Burning.class) dmg *= 2;
        super.damage(dmg, src);
    }
}
