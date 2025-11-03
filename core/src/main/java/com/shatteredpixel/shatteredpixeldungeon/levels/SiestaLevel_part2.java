package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Oblivion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Obsidian;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.CityPainter;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.SewerPainter;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.SiestaPainter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special.CoreRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special.DemonSpawnerRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.BurningTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.CursingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ExplosiveTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FlashingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FrostTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.GrimTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.HallucinationTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.OblivionTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.OozeTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.OriginiumTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.PoisonDartTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.RockfallTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ShockingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.StormTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ToxicTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.WarpingTrap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.Group;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SiestaLevel_part2 extends RegularLevel {
    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
    }

    @Override
    protected int standardRooms(boolean forceMax) {
        if (forceMax) return 7;
        //8 to 10, average 8.67
        return 7 + Random.chances(new float[]{3, 2, 1});
    }

    @Override
    protected int specialRooms(boolean forceMax) {
        if (forceMax) return 3;
        return 1 + Random.chances(new float[]{1, 1});
    }

    @Override
    protected void createItems() {

        if (Dungeon.depth == 37 || Dungeon.depth == 39) {
            addItemToSpawn(new ScrollOfUpgrade());
        } else addItemToSpawn(new PotionOfStrength());
        super.createItems();
    }

    @Override
    public int nMobs() {
        // 다른 계층보다 몬스터가 4마리 많이 등장합니다.
        return super.nMobs() + 4;
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
    protected Painter painter() {
        return new SiestaPainter()
                .setWater(feeling == Feeling.WATER ? 0.98f : 0.38f, 4)
                .setGrass(feeling == Feeling.GRASS ? 0.80f : 0.20f, 3)
                .setTraps(nTraps(), trapClasses(), trapChances());
    }

    @Override
    protected Class<?>[] trapClasses() {
        return new Class[]{
                ShockingTrap.class, RockfallTrap.class, BurningTrap.class, ExplosiveTrap.class,
                OriginiumTrap.class, FlashingTrap.class, StormTrap.class, CursingTrap.class, OblivionTrap.class,
                GrimTrap.class, WarpingTrap.class, HallucinationTrap.class
        };
    }

    @Override
    protected float[] trapChances() {
        return new float[]{
                4, 4, 4, 4,
                2, 2, 2, 2, 2,
                1, 1,1
        };
    }

    @Override
    protected ArrayList<Room> initRooms() {
        ArrayList<Room> rooms = super.initRooms();

        rooms.add(new CoreRoom());
        rooms.add(new CoreRoom());

        return rooms;
    }

    @Override
    protected void createMobs() {

        super.createMobs();
    }


    @Override
    public Group addVisuals() {
        super.addVisuals();
        return visuals;
    }

}
