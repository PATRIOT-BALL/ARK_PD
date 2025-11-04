package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard;

import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.BurningTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.OriginiumTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.PoisonDartTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.RockfallTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ShockingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.StormTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.WornDartTrap;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sungrass;
import com.watabou.utils.Random;

public class OrigiRoom extends StandardRoom {
    @Override
    public int minWidth() {
        return Math.max(super.minWidth(), 5);
    }

    @Override
    public int minHeight() {
        return Math.max(super.minHeight(), 5);
    }

    @Override
    public float[] sizeCatProbs() {
        return new float[]{3, 1, 0};
    }

    @Override
    public void paint(Level level) {
        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.EMPTY );



        int traps = 5;

        for (int i = 0; i < traps; i++ ){
            int pos;
            do {
                pos = level.pointToCell(random(1));
            } while (level.traps.get(pos) != null && level.map[pos] != Terrain.EMPTY);

                Painter.set(level, pos, Terrain.TRAP);
                if(i > 2) level.setTrap(new OriginiumTrap().reveal(), pos);
                else level.setTrap(new RockfallTrap().reveal(), pos);
        }

        for (Door door : connected.values()) {
            door.set( Door.Type.WATER );
        }
    }
}
