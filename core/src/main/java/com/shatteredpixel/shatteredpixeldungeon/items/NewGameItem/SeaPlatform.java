package com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.levels.SeaBossLevel2;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Platform;
import com.shatteredpixel.shatteredpixeldungeon.plants.Earthroot;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class SeaPlatform extends Platform {

    {
        image = 1;
        platformClass = Generator.class;
    }

    @Override
    public void activate(Char ch) {

    }

    public static class Generator extends Platform.Generator {
        {
            image = ItemSpriteSheet.SOMETHING;

            platformClass = SeaPlatform.class;

            bones = true;
        }

        @Override
        protected void onThrow( int cell ) {
            Dungeon.level.createPlatform(this, cell);
//            // Only activate in boss level 2 in Iberia and if thrown tile is a Nethersea Brand
//            if (Dungeon.level instanceof SeaBossLevel2 && Dungeon.level.map[cell] == Terrain.SEA_TERROR) {
//                Dungeon.level.createPlatform(this, cell);
//            } else {
//                super.onThrow(cell);
//            }
        }
    }
}
