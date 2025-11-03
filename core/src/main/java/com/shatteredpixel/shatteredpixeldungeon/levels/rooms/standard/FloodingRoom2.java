package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Piranha;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ExplosiveTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.GrimTrap;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class FloodingRoom2 extends StandardRoom {
    @Override
    public float[] sizeCatProbs() {
        return new float[]{4, 1, 0};
    }

    @Override
    public boolean canMerge(Level l, Point p, int mergeTerrain) {
        int cell = l.pointToCell(pointInside(p, 1));
        return l.map[cell] == Terrain.WATER;
    }

    @Override
    public void paint(Level level) {
        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.WATER );
        for (Door door : connected.values()) {
            door.set( Door.Type.REGULAR );
        }

        int mines = (int)Math.round(Math.sqrt(square()));

        switch (sizeCat){
            case NORMAL:
                break;
            case LARGE:
                mines += 8;
                break;
            case GIANT:
                mines += 16;
                break;
        }

        for (int i = 0; i < mines; i++ ){
            int pos;
            do {
                pos = level.pointToCell(random(1));
            } while (level.traps.get(pos) != null);

            Painter.set(level, pos, Terrain.EMPTY_SP);

        }

    }
}
