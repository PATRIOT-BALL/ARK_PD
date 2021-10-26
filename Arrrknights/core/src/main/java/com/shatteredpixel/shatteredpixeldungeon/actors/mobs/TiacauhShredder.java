package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HaundSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Siesta_AgentSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class TiacauhShredder extends Mob {
    {
        spriteClass = Siesta_AgentSprite.class;

        HP = HT = 135;
        defenseSkill = 24;

        EXP = 15;
        maxLvl = 35;

        loot = Generator.Category.STONE;
        lootChance = 0.5f;

        immunities.add(Silence.class);
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
