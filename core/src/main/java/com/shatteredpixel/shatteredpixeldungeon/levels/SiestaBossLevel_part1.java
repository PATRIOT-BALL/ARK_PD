package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.SiestaBoss;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ceylon;
import com.shatteredpixel.shatteredpixeldungeon.items.ArmorUpKit;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.Group;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.Arrays;

public class SiestaBossLevel_part1 extends Level {
    {
        color1 = 0x801500;
        color2 = 0xa68521;

        viewDistance = 4;
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_CITY;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_CITY;
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
    public int Moneygirl = 229;

    @Override
    protected boolean build() {

        setSize(17, 32);
        Arrays.fill( map, Terrain.EMPTY );

        final int MID = width/2;

        Painter.fill( this, 0, 0, 17, 1, Terrain.WALL);
        Painter.fill( this, 0, 20, 17, 12, Terrain.WALL);
        Painter.fill( this, 0, 0, 1, 31, Terrain.WALL);
        Painter.fill( this, 16, 0, 1, 31, Terrain.WALL);

        // 제일 외곽 벽
        Painter.fill( this, 2, 2, 13, 1, Terrain.WALL);
        Painter.fill( this, 2, 18, 13, 1, Terrain.WALL);
        Painter.fill( this, 2, 2, 1, 16, Terrain.WALL);
        Painter.fill( this, 14, 2, 1, 16, Terrain.WALL);
        map[42] = Terrain.DOOR;
        map[314] = Terrain.DOOR;
        map[184] = Terrain.DOOR;
        map[172] = Terrain.DOOR;

        map[54] = Terrain.WALL;
        map[64] = Terrain.WALL;
        map[292] = Terrain.WALL;
        map[302] = Terrain.WALL;
        map[290] = Terrain.WALL;
        map[66] = Terrain.WALL;
        map[52] = Terrain.WALL;
        map[304] = Terrain.WALL;

        map[36] = Terrain.EMPTY;
        map[48] = Terrain.EMPTY;
        map[308] = Terrain.EMPTY;
        map[320] = Terrain.EMPTY;

        // 안쪽 벽
        Painter.fill( this, 6, 2, 1, 16, Terrain.WALL);
        Painter.fill( this, 10, 2, 1, 16, Terrain.WALL);
        Painter.fill( this, 2, 8, 12, 1, Terrain.WALL);
        Painter.fill( this, 2, 12, 12, 1, Terrain.WALL);
        map[176] = Terrain.DOOR;
        map[180] = Terrain.DOOR;
        map[144] = Terrain.DOOR;
        map[212] = Terrain.DOOR;

        map[76] = Terrain.WALL;
        map[110] = Terrain.WALL;
        map[246] = Terrain.WALL;
        map[280] = Terrain.WALL;

        // 안쪽 방의 벽
        Painter.fill( this, 4, 2, 1, 6, Terrain.WALL);
        Painter.fill( this, 4, 12, 1, 6, Terrain.WALL);
        Painter.fill( this, 12, 2, 1, 6, Terrain.WALL);
        Painter.fill( this, 12, 12, 1, 6, Terrain.WALL);
        map[139] = Terrain.DOOR;
        map[207] = Terrain.DOOR;
        map[149] = Terrain.DOOR;
        map[217] = Terrain.DOOR;
        map[91] = Terrain.DOOR;
        map[95] = Terrain.DOOR;
        map[261] = Terrain.DOOR;
        map[265] = Terrain.DOOR;
        map[274] = Terrain.DOOR;
        map[286] = Terrain.DOOR;
        map[70] = Terrain.DOOR;
        map[82] = Terrain.DOOR;
        map[39] = Terrain.DOOR;
        map[45] = Terrain.DOOR;
        map[311] = Terrain.DOOR;
        map[317] = Terrain.DOOR;


        entrance = 331;

        exit = 25;

        map[entrance] = Terrain.ENTRANCE;

        feeling = Feeling.NONE;



        return true;
    }

    @Override
    public void occupyCell( Char ch ) {

        super.occupyCell( ch );

        if (ch.pos == 212 && ch == Dungeon.hero && map[entrance] == Terrain.ENTRANCE && map[exit] != Terrain.EXIT) {

            seal();

        }
    }

    @Override
    public void seal() {
        super.seal();
        set( entrance, Terrain.EMPTY );
        set( exit, Terrain.EMPTY ); // 36층 이후 처리
        GameScene.updateMap( entrance );
        GameScene.updateMap( exit ); // 36층 이후 처리
        Dungeon.observe();

        if (!Ceylon.Quest.isSpawnd()) {
            new ArmorUpKit().doPickUp(Dungeon.hero);
        }

        SiestaBoss boss = new SiestaBoss();
        boss.pos = 178;
        GameScene.add( boss );
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
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.WATER:
                return Messages.get(HallsLevel.class, "water_name");
            case Terrain.GRASS:
                return Messages.get(HallsLevel.class, "grass_name");
            case Terrain.HIGH_GRASS:
                return Messages.get(HallsLevel.class, "high_grass_name");
            case Terrain.STATUE:
            case Terrain.STATUE_SP:
                return Messages.get(HallsLevel.class, "statue_name");
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.WATER:
                return Messages.get(HallsLevel.class, "water_desc");
            case Terrain.STATUE:
            case Terrain.STATUE_SP:
                return Messages.get(HallsLevel.class, "statue_desc");
            case Terrain.BOOKSHELF:
                return Messages.get(HallsLevel.class, "bookshelf_desc");
            default:
                return super.tileDesc( tile );
        }
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
