package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.sprites.InfantrySprite;
import com.watabou.utils.Random;

// 화람지심 보병
public class Infantry extends Mob {
    {
        spriteClass = InfantrySprite.class;

        HP = HT = 110;
        defenseSkill = 24;

        EXP = 15;
        maxLvl = 29;

        loot = Gold.class;
        lootChance = 0.5f;

        immunities.add(Silence.class);
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
        return Random.NormalIntRange(0, 16);
    }
}
