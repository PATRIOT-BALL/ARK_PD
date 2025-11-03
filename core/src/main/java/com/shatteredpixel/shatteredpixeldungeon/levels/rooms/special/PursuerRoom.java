package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.FireCore;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss.EmperorPursuer;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class PursuerRoom extends SpecialRoom {
    @Override
    public int maxHeight() { return 12; }
    public int maxWidth() { return 12; }
    public int minHeight() { return 9; }
    public int minWidth() { return 9; }

    public void paint(Level level ) {

        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.EMPTY_SP );

        Door entrance = entrance();

        entrance.set( Door.Type.BARRICADE );

        Point c = center();
        int cx = c.x;
        int cy = c.y;
        EmperorPursuer Pursuer = new EmperorPursuer();
        Pursuer.pos = cx + cy * level.width();
        level.mobs.add( Pursuer );

        level.addItemToSpawn( new PotionOfLiquidFlame() );
    }
}
