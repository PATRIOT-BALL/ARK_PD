package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Bug_ASprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Sea_RunnerSprite;
import com.watabou.utils.Random;

public class SeaRunner extends Mob {
    {
        spriteClass = Sea_RunnerSprite.class;

        HP = HT = 90;
        EXP = 13;
        maxLvl = 29;

        defenseSkill = 26;

        loot = new MysteryMeat();
        lootChance = 0.12f;

        properties.add(Property.SEA);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(20, 36);
    }

    @Override
    public int attackSkill( Char target ) {
        return 33;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 10);
    }

    @Override
    public int attackProc(Char enemy, int damage) {

        if (Random.Int(3) == 0) {
            Buff.affect(enemy, Hex.class, 3f);
        }
        else if (Random.Int(2) == 0) {
            Buff.prolong(enemy, Chill.class, 3f);
        }

        return super.attackProc(enemy, damage);
    }
}
