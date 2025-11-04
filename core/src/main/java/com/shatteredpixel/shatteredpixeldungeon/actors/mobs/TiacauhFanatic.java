package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.sprites.EnragedSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HaundSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Tiacauh_fanaticSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class TiacauhFanatic extends Mob {
    {
        spriteClass = Tiacauh_fanaticSprite.class;

        HP = HT = 75;
        defenseSkill = 40;

        EXP = 14;
        maxLvl = 29;

        loot = Gold.class;
        lootChance = 0.35f;

        immunities.add(Silence.class);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 20, 32 );
    }

    @Override
    protected float attackDelay() {
        return super.attackDelay() * 0.4f;
    }

    @Override
    public int attackSkill( Char target ) {
        return 36;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 14);
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        int dmgbouns = enemy.drRoll() / 4;
        dmgbouns = Math.min(dmgbouns, 8);
        damage += dmgbouns;
        return super.attackProc(enemy, damage);
    }

    @Override
    public void damage(int dmg, Object src) {
        if (src == Burning.class) dmg *= 2;
        super.damage(dmg, src);
    }
}
