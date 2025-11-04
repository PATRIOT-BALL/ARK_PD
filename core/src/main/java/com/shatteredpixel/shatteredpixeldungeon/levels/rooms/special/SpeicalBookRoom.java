package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;

public class SpeicalBookRoom extends SpecialRoom {

    @Override
    public int minWidth() {
        return 8;
    }

    @Override
    public int minHeight() {
        return 8;
    }

    @Override
    public void paint(Level level) {
        Painter.fill( level, this, Terrain.WALL );
        Painter.fill(level, this, 1, Terrain.BOOKSHELF);

        Door entrance = entrance();

        level.addItemToSpawn(new StoneOfBlast());
        level.addItemToSpawn(new PotionOfLiquidFlame());

        int dropPos1;
        int dropPos2;


        dropPos1 = level.pointToCell(random());
        level.map[dropPos1] = Terrain.EMPTY;
        level.drop( Generator.random(Generator.Category.RING), dropPos1);

        do{
            dropPos2 = level.pointToCell(random());
        } while (dropPos2 == dropPos1);

        level.map[dropPos2] = Terrain.EMPTY;
        level.drop( Generator.random(Generator.Category.SKL_RND), dropPos2);

        entrance.set(Door.Type.BARRICADE);
    }

    @Override
    public boolean canPlaceWater(Point p) {
        return false;
    }

    @Override
    public boolean canPlaceGrass(Point p) {
        return false;
    }

    @Override
    public boolean canPlaceCharacter(Point p, Level l) {
        return super.canPlaceCharacter(p, l) && l.map[l.pointToCell(p)] != Terrain.EMPTY_SP;
    }
}
