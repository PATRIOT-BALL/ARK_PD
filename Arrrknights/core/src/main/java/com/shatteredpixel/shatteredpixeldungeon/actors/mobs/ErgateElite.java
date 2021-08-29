package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.items.food.FrozenCarpaccio;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ThiefSprite;
import com.watabou.utils.Random;

public class ErgateElite extends Ergate {
    {
        spriteClass = ThiefSprite.class;

        HP = HT = 90;

        state = WANDERING;

        loot = new FrozenCarpaccio();
        lootChance = 1f;
    }

    @Override
    protected float attackDelay() {
        return super.attackDelay()*0.4f;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 18);
    }
}
