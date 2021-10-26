package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Eunectes;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Tomimi;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.Group;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.Arrays;

public class GavialBossLevel2 extends Level {
    {
        color1 = 0x801500;
        color2 = 0xa68521;
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILSE_SIESTA2;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_SIESTA2;
    }

    @Override
    public void create() {
        super.create();
        for (int i=0; i < length(); i++) {
            int flags = Terrain.flags[map[i]];
            if ((flags & Terrain.PIT) != 0){
                passable[i] = avoid[i] = false;
                solid[i] = true;
            }
        }
        for (int i = (height-ROOM_TOP+2)*width; i < length; i++){
            passable[i] = avoid[i] = false;
            solid[i] = true;
        }
        for (int i = (height-ROOM_TOP+1)*width; i < length; i++){
            if (i % width < 4 || i % width > 12 || i >= (length-width)){
                discoverable[i] = false;
            } else {
                visited[i] = true;
            }
        }
    }

    private static final int ROOM_TOP = 12;

    @Override
    protected boolean build() {

        setSize(15, 21);
        Arrays.fill( map, Terrain.EMPTY );

        final int MID = width/2;

        Painter.fill( this, 0, 0, 15, 1, Terrain.WALL);
        Painter.fill( this, 0, 9, 15, 12, Terrain.WALL);
        Painter.fill( this, 0, 0, 1, 21, Terrain.WALL);
        Painter.fill( this, 14, 0, 1, 21, Terrain.WALL);

        Painter.fill( this, 1, 2, 1, 6, Terrain.STATUE);
        Painter.fill( this, 2, 3, 1, 4, Terrain.STATUE);

        Painter.fill( this, 13, 2, 1, 6, Terrain.STATUE);
        Painter.fill( this, 12, 3, 1, 4, Terrain.STATUE);

        entrance = 127;

        exit = 22;

        map[entrance] = Terrain.ENTRANCE;
        map[exit] = Terrain.EXIT;

        feeling = Feeling.NONE;

        return true;
    }

    @Override
    public void occupyCell( Char ch ) {

        super.occupyCell( ch );

        if (ch.pos != map[entrance] && map[exit] == Terrain.EXIT) {
            Eunectes boss = new Eunectes();
            boss.pos = 37;
            GameScene.add( boss );
            seal();

            boss.notice();
        }
    }

    @Override
    public void seal() {
        super.seal();
        set( entrance, Terrain.EMPTY );
        set( exit, Terrain.EMPTY );
        GameScene.updateMap( entrance );
        GameScene.updateMap( exit );
        Dungeon.observe();
    }

    @Override
    public void unseal() {
        super.unseal();
        set( entrance, Terrain.ENTRANCE );
        GameScene.updateMap( entrance );

        set( exit, Terrain.EXIT );
        GameScene.updateMap( exit );

        Dungeon.observe();
    }

    @Override
    protected void createMobs() {
    }

    public Actor addRespawner() {
        return null;
    }

    @Override
    protected void createItems() {
    }

    @Override
    public int randomRespawnCell( Char ch ) {
        int cell;
        do {
            cell = entrance + PathFinder.NEIGHBOURS8[Random.Int(8)];
        } while (!passable[cell]
                || (Char.hasProp(ch, Char.Property.LARGE) && !openSpace[cell])
                || Actor.findChar(cell) != null);
        return cell;
    }

    @Override
    public Group addVisuals () {
        super.addVisuals();
        HallsLevel.addHallsVisuals(this, visuals);
        return visuals;
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        for (int i=0; i < length(); i++) {
            int flags = Terrain.flags[map[i]];
            if ((flags & Terrain.PIT) != 0){
                passable[i] = avoid[i] = false;
                solid[i] = true;
            }
        }
        for (int i = (height-ROOM_TOP+2)*width; i < length; i++){
            passable[i] = avoid[i] = false;
            solid[i] = true;
        }
        for (int i = (height-ROOM_TOP+1)*width; i < length; i++){
            if (i % width < 4 || i % width > 12 || i >= (length-width)){
                discoverable[i] = false;
            } else {
                visited[i] = true;
            }
        }
    }
}
