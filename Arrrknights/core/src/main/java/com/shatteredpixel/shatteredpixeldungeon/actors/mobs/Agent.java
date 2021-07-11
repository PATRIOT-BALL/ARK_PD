package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BreakerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.InfantrySprite;
import com.watabou.utils.Random;

public class Agent extends Mob {
    {
        spriteClass = BreakerSprite.class;

        HP = HT = 135;
        defenseSkill = 28;

        EXP = 17;
        maxLvl = 32;

        loot = Generator.Category.SKL_RND;
        lootChance = 0.1f;

        immunities.add(Silence.class);
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if (Random.Int(3) < 1)
        {
            Buff.affect(enemy, Hex.class, 5f);
            Buff.affect(enemy, Vulnerable.class, 5f);
        }

        return super.attackProc(enemy, damage);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 30, 42 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 40;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 20);
    }
}
