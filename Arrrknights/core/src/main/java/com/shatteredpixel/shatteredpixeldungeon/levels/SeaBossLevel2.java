package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Eunectes;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.SeaBoss2_Phase2_Head;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.SeaBoss2_Phase2_Mid;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.SeaBoss2_Phase2_Tail;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.Group;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.Arrays;

public class SeaBossLevel2 extends Level {
    {
        color1 = 0x801500;
        color2 = 0xa68521;
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILSE_IBERIA2;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_IBERIA2;
    }

    @Override
    public void create() {
        super.create();
    }

    private static final int ROOM_TOP = 12;

    @Override
    protected boolean build() {

        setSize(15, 11);
        Arrays.fill( map, Terrain.EMPTY );

        final int MID = width/2;

        Painter.fill( this, 0, 0, 15, 1, Terrain.STATUE);
        Painter.fill( this, 0, 10, 15, 1, Terrain.STATUE);
        Painter.fill( this, 0, 0, 1, 11, Terrain.STATUE);
        Painter.fill( this, 14, 0, 1, 11, Terrain.STATUE);


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
            SeaBoss2_Phase2_Head boss1 = new SeaBoss2_Phase2_Head();
            boss1.pos = 39;
            GameScene.add( boss1 );

            SeaBoss2_Phase2_Mid boss2 = new SeaBoss2_Phase2_Mid();
            boss2.pos = 37;
            GameScene.add( boss2 );

            SeaBoss2_Phase2_Tail boss3 = new SeaBoss2_Phase2_Tail();
            boss3.pos = 35;
            GameScene.add( boss3 );


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
