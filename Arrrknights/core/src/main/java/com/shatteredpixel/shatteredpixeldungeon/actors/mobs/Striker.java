package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.sprites.StrikerSprite;
import com.watabou.utils.Random;

public class Striker extends Mob{
    {
        spriteClass = StrikerSprite.class;

        HP = HT = 100;
        defenseSkill = 20;

        EXP = 14;
        maxLvl = 26;

        loot = Gold.class;
        lootChance = 0.25f;
        immunities.add(Silence.class);
    }

    @Override
    protected boolean canAttack(Char enemy) {
        return this.fieldOfView[enemy.pos] && Dungeon.level.distance(this.pos, enemy.pos) <= 2;
    }

    @Override
    public int damageRoll() {
        if (HP <= HT/2) return Random.NormalIntRange(33,45);
        return Random.NormalIntRange(22,33);
    }

    @Override
    public int attackSkill( Char target ) {
        return 30;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(8, 24); }

}
