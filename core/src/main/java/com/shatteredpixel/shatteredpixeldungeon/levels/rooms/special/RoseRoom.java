package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special;

import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Foliage;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Rose;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;

public class RoseRoom extends SpecialRoom {

    //reduced max size to limit chest numbers.
    // normally would gen with 8-28, this limits it to 8-16
    @Override
    public int maxHeight() { return 5; }
    public int maxWidth() { return 5; }

    public void paint(Level level ) {

        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.HIGH_GRASS );
        Painter.fill( level, this, 2, Terrain.GRASS );

        Door entrance = entrance();
        entrance.set( Door.Type.HIDDEN );
        int door = entrance.x + entrance.y * level.width();

        Foliage light = (Foliage)level.blobs.get( Foliage.class );
        if (light == null) {
            light = new Foliage();
        }
        for (int i=top + 1; i < bottom; i++) {
            for (int j=left + 1; j < right; j++) {
                light.seed( level, j + level.width() * i, 1 );
            }
        }
        level.blobs.put( Foliage.class, light );


        int pos = level.pointToCell(random( 2 ));
        Rose rose = new Rose();

        level.drop(rose, pos) ;
    }
}
