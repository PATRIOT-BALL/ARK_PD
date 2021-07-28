package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;

public class BeachRoom extends StandardRoom {
    @Override
    public int minWidth() {
        return Math.max(7, super.minWidth());
    }

    @Override
    public int minHeight() {
        return Math.max(7, super.minHeight());
    }

    @Override
    public float[] sizeCatProbs() {
        return new float[]{0, 3, 1};
    }

    @Override
    public void paint(Level level) {

        Painter.fill( level, this, Terrain.WALL );

        Painter.fillEllipse(level, this, 2, Terrain.WATER);

        for (Door door : connected.values()) {
            door.set( Door.Type.REGULAR );
            if (door.x == left || door.x == right){
                Painter.drawInside(level, this, door, width()/2, Terrain.WATER);
            } else {
                Painter.drawInside(level, this, door, height()/2, Terrain.WATER);
            }
        }

        Painter.fillEllipse(level, this, 4, Terrain.EMPTY);
        Painter.fillEllipse(level, this, 6, Terrain.WALL);

    }
}
