package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Striker_EliteSprite;
import com.watabou.utils.Random;

public class TiacauhSniper extends TiacauhLancer {
    {
        spriteClass = Striker_EliteSprite.class;

        HP = HT = 125;
        defenseSkill = 16;

        EXP = 17;
        maxLvl = 30;

        immunities.add(Silence.class);


        loot = new PotionOfHealing();
        lootChance = 1f;
    }

    @Override
    protected boolean canAttack(Char enemy) {
        return this.fieldOfView[enemy.pos] && Dungeon.level.distance(this.pos, enemy.pos) <= 4;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(30,42);
    }
}
