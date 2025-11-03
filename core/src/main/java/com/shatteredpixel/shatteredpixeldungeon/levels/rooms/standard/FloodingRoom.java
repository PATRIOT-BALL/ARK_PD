package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard;

import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;

public class FloodingRoom extends StandardRoom {
    @Override
    public void paint(Level level) {
        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1 , Terrain.WATER );

        for (Door door : connected.values()) {
            door.set( Door.Type.REGULAR );
        }
    }
}
