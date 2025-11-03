package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.TiacauhFanatic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.TiacauhLancer;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.TiacauhShaman;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.TiacauhWarrior;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.LANCET2;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Alchemize;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.AquaBlast;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Arrays;

public class LACNET2Room extends SpecialRoom {
    @Override
    public int minWidth() { return 7; }

    @Override
    public int minHeight() { return 7; }

    // common등급 보상은 4개가 지급됩니다.
    private static final Class<?>[] Enemys = new Class<?>[]{
            TiacauhFanatic.class, TiacauhLancer.class, TiacauhWarrior.class, TiacauhShaman.class};

    public void paint( Level level ) {

        Door entrance = entrance();
        entrance.set(Door.Type.LOCKED);
        level.addItemToSpawn(new IronKey(Dungeon.depth));

        Painter.fill(level, this, Terrain.WALL);
        Painter.fill(level, this, 1, Terrain.GRASS);


        int heartX = Random.IntRange(left+1, right-1);
        int heartY = Random.IntRange(top+1, bottom-1);

        if (entrance.x == left) {
            heartX = right - 1;
        } else if (entrance.x == right) {
            heartX = left + 1;
        } else if (entrance.y == top) {
            heartY = bottom - 1;
        } else if (entrance.y == bottom) {
            heartY = top + 1;
        }

        placeChar(level, heartX + heartY * level.width(), new LANCET2());

        int lashers = ((width()-2)*(height()-2))/8;

        for (int i = 1; i <= lashers; i++){
            int pos;
            do {
                pos = level.pointToCell(random());
            } while (!validLANCETPos(level, pos));
            ArrayList<Class<?>> RandEnemy = new ArrayList<>(Arrays.asList(Enemys));
            placeChar(level, pos, (Mob)Reflection.newInstance(Random.element(RandEnemy)));
        }
    }

    private static boolean validLANCETPos(Level level, int pos){
        if (level.map[pos] != Terrain.GRASS){
            return false;
        }

        for (int i : PathFinder.NEIGHBOURS9){
            if (level.findMob(pos+i) != null){
                return false;
            }
        }

        return true;
    }

    private static void placeChar(Level level, int pos, Mob lancet){
        lancet.pos = pos;
        level.mobs.add( lancet );

        for(int i : PathFinder.NEIGHBOURS8) {
            if (level.map[pos + i] == Terrain.GRASS){
                Painter.set(level, pos + i, Terrain.HIGH_GRASS);
            }
        }
    }
}
