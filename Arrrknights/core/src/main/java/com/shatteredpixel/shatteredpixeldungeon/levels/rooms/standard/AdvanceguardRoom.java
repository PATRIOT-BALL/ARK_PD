package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard;

import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WaterOfAdvanceguard;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WaterOfAwareness;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WaterOfHealth;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WellWater;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class AdvanceguardRoom extends StandardRoom {

    private static final Class<?>[] WATERS =
            {WaterOfAdvanceguard.class};

    public Class<?extends WellWater> overrideWater = null;

    @Override
    public void paint(Level level) {
        Painter.fill(level, this, Terrain.WALL);
        Painter.fill(level, this, 1, Terrain.EMPTY);

        Point c = center();
        Painter.set(level, c.x, c.y, Terrain.WELL);

        @SuppressWarnings("unchecked")
        Class<? extends WellWater> waterClass =
                overrideWater != null ?
                        overrideWater :
                        (Class<? extends WellWater>)Random.element( WATERS );

        WellWater.seed(c.x + level.width() * c.y, 1, waterClass, level);

        for (Room.Door door : connected.values()) {
            door.set(Room.Door.Type.REGULAR);

        }
    }
}
