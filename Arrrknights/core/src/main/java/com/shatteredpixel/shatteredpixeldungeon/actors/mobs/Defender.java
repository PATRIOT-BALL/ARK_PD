package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DefenderSprite;
import com.watabou.utils.Random;

public class Defender extends Mob {
    {
        spriteClass = DefenderSprite.class;

        HP = HT = 130;
        defenseSkill = 0; //see damage()
        baseSpeed = 1f;

        maxLvl = 30;
        EXP = 14;


        properties.add(Property.LARGE);
    }

    public int damageRoll() {
        return Random.NormalIntRange(30, 42);
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(8, 26);
    }

    @Override
    public int attackSkill(Char target) {
        return 34;
    }
}
