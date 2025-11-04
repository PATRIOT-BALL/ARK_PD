package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.BurningTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.CorrosionTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ExplosiveTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.WeakeningTrap;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class Vocan_1Room extends StandardRoom {
    @Override
    public float[] sizeCatProbs() {
        return new float[]{4, 1, 0};
    }

    @Override
    public boolean canMerge(Level l, Point p, int mergeTerrain) {
        int cell = l.pointToCell(pointInside(p, 1));
        return l.map[cell] == Terrain.EMPTY;
    }

    @Override
    public void paint(Level level) {
        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.EMPTY );
        for (Door door : connected.values()) {
            door.set( Door.Type.REGULAR );
        }

        int mines = (int)Math.round(Math.sqrt(square()));

        switch (sizeCat){
            case NORMAL:
                mines -= 3;
                break;
            case LARGE:
                mines += 3;
                break;
            case GIANT:
                mines += 9;
                break;
        }

        for (int i = 0; i < mines; i++ ){
            int pos;
            do {
                pos = level.pointToCell(random(1));
            } while (level.traps.get(pos) != null);

            //randomly places some embers around the mines
            for (int j = 0; j < 8; j ++){
                int c = PathFinder.NEIGHBOURS8[Random.Int(8)];
                if (level.traps.get(pos+c) == null && level.map[pos+c] == Terrain.EMPTY){
                    Painter.set(level, pos+c, Terrain.EMBERS);
                }
            }

            Painter.set(level, pos, Terrain.SECRET_TRAP);
            if (Random.Int(2) == 0) level.setTrap(new BurningTrap().hide(), pos);
            else level.setTrap(new CorrosionTrap().hide(), pos);

        }

    }
}
