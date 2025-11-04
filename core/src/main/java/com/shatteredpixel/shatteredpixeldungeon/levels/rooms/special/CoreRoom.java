package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DemonSpawner;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.FireCore;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.EntranceRoom;
import com.shatteredpixel.shatteredpixeldungeon.tiles.CustomTilemap;
import com.watabou.noosa.Tilemap;
import com.watabou.utils.Point;

public class CoreRoom extends SpecialRoom {
    @Override
    public void paint(Level level) {

        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.EMPTY );

        Point c = center();
        int cx = c.x;
        int cy = c.y;

        Room.Door door = entrance();
        door.set(Room.Door.Type.UNLOCKED);

        FireCore core = new FireCore();
        core.pos = cx + cy * level.width();
        level.mobs.add( core );

    }

    @Override
    public boolean connect(Room room) {
        //cannot connect to entrance, otherwise works normally
        if (room instanceof EntranceRoom) return false;
        else                              return super.connect(room);
    }

    @Override
    public boolean canPlaceTrap(Point p) {
        return false;
    }

    @Override
    public boolean canPlaceWater(Point p) {
        return false;
    }

    @Override
    public boolean canPlaceGrass(Point p) {
        return false;
    }

    public static class CustomFloor extends CustomTilemap {

        {
            texture = Assets.Environment.HALLS_SP;
        }

        @Override
        public Tilemap create() {
            Tilemap v = super.create();
            int cell = tileX + tileY * Dungeon.level.width();
            int[] map = Dungeon.level.map;
            int[] data = new int[tileW*tileH];
            for (int i = 0; i < data.length; i++){
                if (i % tileW == 0){
                    cell = tileX + (tileY + i / tileW) * Dungeon.level.width();
                }

                if (Dungeon.level.findMob(cell) instanceof FireCore){
                    data[i-1] = 5 + 4*8;
                    data[i] = 6 + 4*8;
                    data[i+1] = 7 + 4*8;
                    i++;
                    cell++;
                } else if (map[cell] == Terrain.EMPTY_DECO) {
                    if (Statistics.amuletObtained){
                        data[i] = 31;
                    } else {
                        data[i] = 27;
                    }
                } else {
                    data[i] = 19;
                }

                cell++;
            }
            v.map( data, tileW );
            return v;
        }

    }
}
