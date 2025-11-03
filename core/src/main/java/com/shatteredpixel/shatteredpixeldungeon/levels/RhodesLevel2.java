package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Closure;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.GreenCat;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.SkinModel;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.noosa.Group;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.Arrays;

public class RhodesLevel2 extends Level {
    {
        color1 = 0x801500;
        color2 = 0xa68521;

        viewDistance = 18;
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILSE_RHODES;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_PRISON;
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

    private static final int ROOM_TOP = 6;
    public int Moneygirl = 229;

    @Override
    protected boolean build() {

        setSize(18, 28);
        Arrays.fill( map, Terrain.WALL );

        final int MID = width/2;

        Painter.fill( this, 0, 0, 15, 24, Terrain.EMPTY);

        Painter.fill( this, MID, 0, 1, 24, Terrain.WALL);
        Painter.fill( this, MID -4, 0, 1, 24, Terrain.WALL);

        Painter.fill( this, MID, 6, 1, 1, Terrain.DOOR);
        Painter.fill( this, MID - 4, 6, 1, 1, Terrain.DOOR);
        Painter.fill( this, MID, 17, 1, 1, Terrain.DOOR);
        Painter.fill( this, MID - 4, 17, 1, 1, Terrain.DOOR);

        Painter.fill( this, 0, 10, 5, 4, Terrain.WALL);
        Painter.fill( this, 10, 10, 5, 4, Terrain.WALL);

        Painter.fill( this, 0, 0, 15, 1, Terrain.WALL);
        Painter.fill( this, 0, 23, 15, 1, Terrain.WALL);

        entrance = 403;
        exit = 25;

        map[entrance] = Terrain.ENTRANCE;
        Painter.set( this, exit, Terrain.EXIT );

        feeling = Feeling.NONE;

        return true;
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
