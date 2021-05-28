package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WaterOfAdvanceguard;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WaterOfAwareness;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WaterOfHealth;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WellWater;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.StaffKit;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class AdvanceguardRoom extends StandardRoom {

    private static final Class<?>[] WATERS =
            {WaterOfAdvanceguard.class};

    public Class<? extends WellWater> overrideWater = null;

    @Override
    public void paint(Level level) {
        Painter.fill(level, this, Terrain.WALL);
        Painter.fill(level, this, 1, Terrain.EMPTY);

        Point c = center();
        Painter.set(level, c.x, c.y, Terrain.WELL);

        // 조건 : 플레이어의 힘이 12초과, 7~9층일 경우, 플레이어가 1스킬을 배웠을 경우. 확률적으로
        if (Dungeon.hero.STR > 12 && Dungeon.depth > 6 && Dungeon.depth < 10 && Dungeon.hero.SK1 != null) {
            if (Random.Int(0, 10) < 2) {
                int w = width() - 2;
                int h = height() - 2;
                int shift = Random.Int(2);
                int pos = w > h ?
                        left + 1 + shift * 2 + (top + 2 + Random.Int(h - 2)) * level.width() :
                        (left + 2 + Random.Int(w - 2)) + (top + 1 + shift * 2) * level.width();

                level.drop(new StaffKit(), pos).type = Heap.Type.DOOLTA;

                for (Room.Door door : connected.values()) {
                    door.set(Room.Door.Type.REGULAR);}

                } else{
                    @SuppressWarnings("unchecked")
                    Class<? extends WellWater> waterClass =
                            overrideWater != null ?
                                    overrideWater :
                                    (Class<? extends WellWater>) Random.element(WATERS);

                    WellWater.seed(c.x + level.width() * c.y, 1, waterClass, level);

                    for (Room.Door door : connected.values()) {
                        door.set(Room.Door.Type.REGULAR);

                    }
                }
            } else {
                @SuppressWarnings("unchecked")
                Class<? extends WellWater> waterClass =
                        overrideWater != null ?
                                overrideWater :
                                (Class<? extends WellWater>) Random.element(WATERS);

                WellWater.seed(c.x + level.width() * c.y, 1, waterClass, level);

                for (Room.Door door : connected.values()) {
                    door.set(Room.Door.Type.REGULAR);

                }
            }
        }
    }
