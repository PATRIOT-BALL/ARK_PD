package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SpinnerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ThrowerSprite;
import com.watabou.utils.Random;

public class StrikerElite extends Striker {
    {
        spriteClass = ThrowerSprite.class;

        HP = HT = 130;
        defenseSkill = 20;

        loot = new ScrollOfTeleportation();
        lootChance = 1f;
    }
    @Override
    public int damageRoll() {
        if (HP <= HT/2) return Random.NormalIntRange(45,60);
        return Random.NormalIntRange(30,40);
    }

    @Override
    public int attackSkill( Char target ) {
        return 30;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(8, 24);
    }
}
