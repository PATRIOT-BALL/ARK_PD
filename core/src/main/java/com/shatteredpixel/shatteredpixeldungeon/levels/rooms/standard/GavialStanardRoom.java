package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DemonSpawner;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.GiantMushroom;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class GavialStanardRoom extends StandardRoom {
    @Override
    public int minWidth() {
        return Math.max(super.minWidth(), 5);
    }

    @Override
    public int minHeight() {
        return Math.max(super.minHeight(), 5);
    }

    public void paint(Level level) {

        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.FURROWED_GRASS );

        for (Room.Door door : connected.values()) {
            door.set( Room.Door.Type.REGULAR );
        }

        Point c = center();
        int cx = c.x;
        int cy = c.y;

        GiantMushroom Mushroom = new GiantMushroom();
        Mushroom.pos = cx + cy * level.width();
        level.mobs.add(Mushroom);

    }

    @Override
    public boolean canPlaceCharacter(Point p, Level l) {
        return super.canPlaceCharacter(p, l) && l.pointToCell(p) != l.exit;
    }
}
