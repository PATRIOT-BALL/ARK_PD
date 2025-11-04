package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Pompeii;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Tomimi;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.Group;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.Arrays;

public class GavialBossLevel1 extends Level {
    {
        color1 = 0x801500;
        color2 = 0xa68521;
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILSE_SARGON;
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
    }

    private static final int ROOM_TOP = 12;

    @Override
    protected boolean build() {

        setSize(21, 21);
        Arrays.fill( map, Terrain.EMPTY );

        final int MID = width/2;
        // 52 : 토미미 2페이즈 위치
        // 241 : 토미미 초기 스폰 및 3페이즈 위치
        // 47,57 : 2페이즈 구조물 생성 위치
        //401, 417 : 몹 스폰 포인트 1

        Painter.fill( this, 0, 0, 21, 1, Terrain.WALL);
        Painter.fill( this, 0, 20, 21, 1, Terrain.WALL);

        Painter.fill( this, 0, 0, 1, 21, Terrain.WALL);
        Painter.fill( this, 20, 0, 1, 21, Terrain.WALL);

        Painter.fill( this, 7, 0, 2, 8, Terrain.WALL);
        Painter.fill( this, 12, 0, 2, 8, Terrain.WALL);

        Painter.fill( this, 4, 4, 13, 4, Terrain.WALL);

        Painter.fill( this, 3, 18, 15, 1, Terrain.WALL);

        Painter.fill( this, 2, 10, 4, 1, Terrain.WALL);
        Painter.fill( this, 15, 10, 4, 1, Terrain.WALL);


        map[360] = Terrain.WALL;
        map[374] = Terrain.WALL;

        map[287] = Terrain.STATUE;
        map[307] = Terrain.STATUE;
        map[308] = Terrain.STATUE;

        map[218] = Terrain.STATUE;
        map[238] = Terrain.STATUE;
        map[239] = Terrain.STATUE;
        map[240] = Terrain.STATUE;
        map[260] = Terrain.STATUE;
        map[261] = Terrain.STATUE;

        map[319] = Terrain.STATUE;

        entrance = 388;

        exit = 178;

        map[entrance] = Terrain.ENTRANCE;
        map[exit] = Terrain.WALL_DECO;

        feeling = Feeling.NONE;

        return true;
    }

    @Override
    public void occupyCell( Char ch ) {

        super.occupyCell( ch );

        if (ch.pos != map[entrance] && map[exit] == Terrain.WALL_DECO) {
            Tomimi boss = new Tomimi();
            boss.pos = 241;
            GameScene.add( boss );
            seal();
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
    }
}
