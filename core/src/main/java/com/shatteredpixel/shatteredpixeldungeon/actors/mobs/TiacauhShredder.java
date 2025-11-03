package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HaundSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Siesta_AgentSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Tiacauh_ShredderSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class TiacauhShredder extends Mob {
    {
        spriteClass = Tiacauh_ShredderSprite.class;

        HP = HT = 180;
        defenseSkill = 24;

        EXP = 15;
        maxLvl = 35;

        loot = Generator.Category.SKL_T2;
        lootChance = 0.24f;

        immunities.add(Silence.class);
    }

    @Override
    protected boolean act() {
        if (buff(Burning.class) != null) {
            damage(Random.IntRange(36, 48), Burning.class);
            if (!isAlive()) return true;
        }
        return super.act();
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 38, 48 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 40;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 20);
    }

    @Override
    public void damage(int dmg, Object src) {
        if (src == Burning.class) dmg *= 2;
        super.damage(dmg, src);
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        int grassCells = 0;
        for (int i : PathFinder.NEIGHBOURS9) {
            if (Dungeon.level.map[pos+i] == Terrain.FURROWED_GRASS
                    || Dungeon.level.map[pos+i] == Terrain.HIGH_GRASS){
                grassCells++;
            }
        }
        if (grassCells > 0) damage = Math.round(damage * (1f + (grassCells * 0.04f)));
        return super.attackProc(enemy, damage);
    }
}
