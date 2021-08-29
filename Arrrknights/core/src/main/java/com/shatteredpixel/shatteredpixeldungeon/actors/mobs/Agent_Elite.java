package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BreakerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Bug_ASprite;
import com.watabou.utils.Random;

public class Agent_Elite extends Agent {
    {
        spriteClass = BreakerSprite.class;

        HP = HT = 155;

        EXP = 18;

        loot = Generator.Category.SKL_RND;
        lootChance = 1f;

        immunities.add(Charm.class);
        immunities.add(Silence.class);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 28, 40 );
    }
}
