package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Blackperro;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Closure;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Dobermann;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Dummy;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Jessica;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.SkinModel;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Weedy;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.tiles.CustomTilemap;
import com.watabou.noosa.Group;
import com.watabou.noosa.Tilemap;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.Arrays;

public class NewRhodesLevel2 extends Level {

    {
        color1 = 0x801500;
        color2 = 0xa68521;
    }

    @Override
    public String tilesTex () {
        return Assets.Environment.TILSE_RHODES;
    }

    @Override
    public String waterTex () {
        return Assets.Environment.WATER_PRISON;
    }

    @Override
    public void create () {
        super.create();
        for (int i = 0; i < length(); i++) {
            int flags = Terrain.flags[map[i]];
            if ((flags & Terrain.PIT) != 0) {
                passable[i] = avoid[i] = false;
                solid[i] = true;
            }
        }
    }

    private static final int ROOM_TOP = 6;

    @Override
    protected boolean build () {

        setSize(68, 85);
        Arrays.fill(map, Terrain.CHASM);

        // 0이 가장 왼쪽 ~ 위쪽임. 1 늘어날 때마다 오른쪽 / 아래쪽으로 1칸
        // X랑 Y는 위치. W랑 H는 각각 길이/너비 같은거임. W랑 H는 최소 1 이상의 값을 줘야함.
        // 0,0,10,1 을 하면 X,Y가 0,0인 위치에서 시작해서 가로로 10칸, 세로로 1칸 크기로 한다는 것.
        // 주의 사항 : 최대값은 위의 setSize 값의 -1임. 그러니까 x값 합이 18이 되도록 하면 버그남.

        // 베이스. 건드리지 말 것
        Painter.fill(this, 0, 0, 68, 85, Terrain.WALL);

        // 개발팀 구역 A1
        Painter.fill(this, 2, 54, 15, 4, Terrain.EMPTY);
        Painter.fill(this, 8, 53, 1, 4, Terrain.AVOID);
        Painter.fill(this, 0, 57, 31, 1, Terrain.WALL);

        map[3882] = Terrain.EMPTY;
        map[3889] = Terrain.EMPTY;
        map[3750] = Terrain.AVOID;

        // 훈련실 구역 A2
        Painter.fill(this, 2, 62, 28, 4, Terrain.EMPTY);
        Painter.fill(this, 0, 61, 31, 1, Terrain.WALL);

        Painter.fill(this, 12, 62, 1, 2, Terrain.AVOID);
        Painter.fill(this, 12, 65, 1, 1, Terrain.AVOID);
        Painter.fill(this, 15, 62, 1, 2, Terrain.AVOID);
        Painter.fill(this, 15, 65, 1, 1, Terrain.AVOID);
        Painter.fill(this, 17, 62, 1, 2, Terrain.AVOID);
        Painter.fill(this, 17, 65, 1, 1, Terrain.AVOID);
        Painter.fill(this, 23, 62, 1, 4, Terrain.AVOID);

        // 복도 A3
        Painter.fill(this, 2, 58, 33, 3, Terrain.EMPTY);
        Painter.fill(this, 2, 58, 1, 3, Terrain.ENTRANCE);
        Painter.fill(this, 25, 58, 1, 3, Terrain.AVOID);
        map[4164] = Terrain.EMPTY;

        entrance = 4015;
        exit = 3607;

        map[exit-1] = Terrain.EXIT;
        map[exit] = Terrain.EXIT;

        feeling = Level.Feeling.NONE;

        NewRhodesLevel2.CustomeMap vis = new NewRhodesLevel2.CustomeMap();
        vis.setRect(0, 0, width(), height());
       customTiles.add(vis);

        return true;
    }

    @Override
    protected void createMobs () {
    }

    public Actor addRespawner () {
        return null;
    }

    @Override
    protected void createItems () {
        // 3882 : 차단벽 위치.
        Closure.spawn(this, 3682);
        SkinModel.spawn(this, 3751);
        Weedy.spawn(this, 3968);
        Dummy.spawn(this, 4286);
        Dummy.spawn(this, 4354);
        Jessica.spawn(this, 4295);
        Dobermann.spawn(this, 4298);
    }

    @Override
    public int randomRespawnCell (Char ch ){
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
    public void restoreFromBundle (Bundle bundle){
        super.restoreFromBundle(bundle);
    }

    public static class CustomeMap extends CustomTilemap {

        {
            texture = Assets.Environment.RHODES_28F;
        }

        @Override
        public Tilemap create() {
            Tilemap v = super.create();
            int[] data = new int[tileW*tileH];
            for (int i = 0; i < data.length; i++){
                data[i] = i;
            }

            v.map( data, tileW );
            return v;
        }
    }
}
