package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Pompeii;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.SiestaBoss;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.Pombbay;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.SurfaceScene;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.Arrays;

public class SiestaBossLevel_part2 extends Level {
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
    public int Moneygirl = 229;

    @Override
    protected boolean build() {

        setSize(21, 32);
        Arrays.fill( map, Terrain.EMPTY );

        final int MID = width/2;

        Painter.fill( this, 0, 0, 21, 1, Terrain.WALL);
        Painter.fill( this, 0, 20, 21, 12, Terrain.WALL);
        Painter.fill( this, 0, 0, 1, 31, Terrain.WALL);
        Painter.fill( this, 20, 0, 1, 31, Terrain.WALL);

        map[22] = Terrain.WALL;
        map[23] = Terrain.WALL;
        map[43] = Terrain.WALL;

        map[39] = Terrain.WALL;
        map[40] = Terrain.WALL;
        map[61] = Terrain.WALL;


        // 보스 위치 주변
        map[29] = Terrain.WALL_DECO;
        map[31] = Terrain.WALL_DECO;
        map[30] = Terrain.WALL_DECO;
        map[32] = Terrain.WALL_DECO;
        map[33] = Terrain.WALL_DECO;
        map[49] = Terrain.WALL_DECO;
        map[50] = Terrain.WALL_DECO;
        map[70] = Terrain.WALL_DECO;
        map[54] = Terrain.WALL_DECO;
        map[55] = Terrain.WALL_DECO;
        map[76] = Terrain.WALL_DECO;
        map[69] = Terrain.WALL_DECO;
        map[91] = Terrain.WALL_DECO;
        map[90] = Terrain.WALL_DECO;
        map[77] = Terrain.WALL_DECO;
        map[97] = Terrain.WALL_DECO;
        map[98] = Terrain.WALL_DECO;

        map[51] = Terrain.STATUE;
        map[71] = Terrain.STATUE;
        map[53] = Terrain.STATUE;
        map[75] = Terrain.STATUE;

        ////// 중앙 기둥, 왼, 오
        map[176] = Terrain.WALL;
        map[218] = Terrain.WALL;
        map[196] = Terrain.WALL;
        map[197] = Terrain.WALL;
        map[198] = Terrain.WALL;
        map[219] = Terrain.WALL;
        map[175] = Terrain.WALL;

        map[180] = Terrain.WALL;
        map[181] = Terrain.WALL;
        map[222] = Terrain.WALL;
        map[200] = Terrain.WALL;
        map[201] = Terrain.WALL;
        map[202] = Terrain.WALL;
        map[221] = Terrain.WALL;

        //// 중앙아래
        map[232] = Terrain.WALL;
        map[253] = Terrain.WALL;
        map[274] = Terrain.WALL;
        map[254] = Terrain.WALL;
        map[275] = Terrain.WALL;
        map[276] = Terrain.WALL;

        map[250] = Terrain.WALL;
        map[271] = Terrain.WALL;
        map[292] = Terrain.WALL;
        map[270] = Terrain.WALL;
        map[291] = Terrain.WALL;
        map[290] = Terrain.WALL;

        // 기타 장식
        map[341] = Terrain.STATUE;
        map[342] = Terrain.STATUE;
        map[361] = Terrain.STATUE;
        map[362] = Terrain.STATUE;

        map[266] = Terrain.STATUE;
        map[267] = Terrain.STATUE;
        map[286] = Terrain.STATUE;
        map[287] = Terrain.STATUE;
        map[288] = Terrain.STATUE;
        map[309] = Terrain.STATUE;

        map[352] = Terrain.STATUE;
        map[374] = Terrain.STATUE;

        map[380] = Terrain.STATUE;
        map[400] = Terrain.STATUE;

        map[215] = Terrain.STATUE;
        map[236] = Terrain.STATUE;
        map[258] = Terrain.STATUE;

        map[67] = Terrain.STATUE;
        map[87] = Terrain.STATUE;
        map[88] = Terrain.STATUE;
        map[128] = Terrain.STATUE;
        map[129] = Terrain.STATUE;

        map[58] = Terrain.STATUE;
        map[80] = Terrain.STATUE;

        map[141] = Terrain.STATUE;
        map[163] = Terrain.STATUE;
        map[164] = Terrain.STATUE;

        map[206] = Terrain.STATUE;
        map[207] = Terrain.STATUE;
        map[228] = Terrain.STATUE;

        map[192] = Terrain.STATUE;
        map[282] = Terrain.STATUE;
        map[317] = Terrain.STATUE;
        map[376] = Terrain.STATUE;
        map[391] = Terrain.STATUE;
        map[411] = Terrain.STATUE;
        map[347] = Terrain.STATUE;
        map[407] = Terrain.STATUE;

        // 보스 생성 위치
        map[73] = Terrain.EMPTY_DECO;


        entrance = 388;

        exit = 52;

        map[entrance] = Terrain.ENTRANCE;
        map[exit] = Terrain.EXIT;

        feeling = Feeling.NONE;

        return true;
    }

    @Override
    public void occupyCell( Char ch ) {

        super.occupyCell( ch );

        // 36층 이후 개방시 WALL_DECO를 EXIT로 변경
        if (ch.pos != map[entrance] && map[exit] == Terrain.EXIT) {
            Pompeii boss = new Pompeii();
            boss.pos = 73;
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
        GameScene.updateMap( exit ); // 36층 이후 처리
        Dungeon.observe();
    }

    @Override
    public void unseal() {
        super.unseal();
        set( entrance, Terrain.ENTRANCE );
        GameScene.updateMap( entrance );

        map[exit] = Terrain.WALL_DECO; // 36층 이후 개방시 아래거를 활성화하고 이건 삭제
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
