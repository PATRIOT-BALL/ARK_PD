package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mimic;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ingredients.BlackPepper;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Random;

public class MeatRoom extends SpecialRoom {

    public void paint( Level level ) {

        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.EMPTY );

        Painter.set( level, center(), Terrain.STATUE );

        Heap.Type heapType = Random.Int( 2 ) == 0 ? Heap.Type.SKELETON : Heap.Type.HEAP;

        int n = Random.IntRange( 1, 2 );
        for (int i=0; i < n; i++) {
            int pos;
            do {
                pos = level.pointToCell(random());
            } while (level.map[pos] != Terrain.EMPTY || level.heaps.get( pos ) != null || level.findMob(pos) != null);{

                level.drop( new MysteryMeat(), pos ).type = heapType;
            }
        }

        if (heapType == Heap.Type.HEAP) {
            for (int i=0; i < 1; i++) {
                int pos;
                do {
                    pos = level.pointToCell(random());
                } while (level.map[pos] != Terrain.EMPTY);
                level.drop( new MysteryMeat() , pos );
            }
        }

        int pos;
        do {
            pos = level.pointToCell(random());
        } while (level.map[pos] != Terrain.EMPTY);
        level.drop( new BlackPepper() , pos );

        entrance().set( Door.Type.LOCKED );
        level.addItemToSpawn( new IronKey( Dungeon.depth ) );
    }
}
