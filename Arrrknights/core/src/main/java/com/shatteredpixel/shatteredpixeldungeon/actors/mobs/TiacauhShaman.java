package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.sprites.TiacauhShamanSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.WaveCasterSprite;
import com.watabou.utils.Random;

public class TiacauhShaman extends TiacauhRitualist {
    {
        spriteClass = TiacauhShamanSprite.class;

        HP = HT = 130;

        loot = Generator.Category.SKL_RND;
        lootChance = 1f;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 48, 64 );
    }
}
