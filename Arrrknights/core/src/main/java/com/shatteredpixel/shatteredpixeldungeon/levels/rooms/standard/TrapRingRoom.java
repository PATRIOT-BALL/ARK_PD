package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WellWater;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Piranha;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.BurningTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ExplosiveTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ShockingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.StormTrap;
import com.watabou.utils.Random;

public class TrapRingRoom extends StandardRoom {
    @Override
    public int minWidth() {
        return Math.max(super.minWidth(), 7);
    }

    @Override
    public int minHeight() {
        return Math.max(super.minHeight(), 7);
    }

    @Override
    public float[] sizeCatProbs() {
        return new float[]{3, 1, 0};
    }

    @Override
    public void paint(Level level) {
        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.EMPTY );
        Painter.fill( level, this, 2, Terrain.GRASS );
        Painter.fill( level, this, 3, Terrain.EMPTY );

        int traps = 3;
        boolean hidden = false;
        if(Random.Int(8) == 0) {
            hidden = true;
            traps+=2;
        }
        for (int i = 0; i < traps; i++ ){
            int pos;
            do {
                pos = level.pointToCell(random(1));
            } while (level.traps.get(pos) != null && level.map[pos] != Terrain.EMPTY);

            if(hidden) {
                Painter.set(level, pos, Terrain.SECRET_TRAP);
                level.setTrap(new StormTrap().hide(), pos);
            }
            else {
                Painter.set(level, pos, Terrain.SECRET_TRAP);
                level.setTrap(new ShockingTrap().hide(), pos);}
        }
        if(hidden) {
        int pos = level.pointToCell(center());
        if (level.traps.get(pos) == null) {
            Painter.set(level, pos, Terrain.SECRET_TRAP);
            level.setTrap(new StormTrap().hide(), pos);
        }
        level.drop(Generator.random(Generator.Category.SKL_RND), pos ).type = Heap.Type.CHEST;
        }


        for (Door door : connected.values()) {
            door.set( Door.Type.REGULAR );
    }
}
}
