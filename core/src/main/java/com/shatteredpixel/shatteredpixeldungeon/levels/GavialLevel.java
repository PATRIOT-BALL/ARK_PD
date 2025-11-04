package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ceylon;
import com.shatteredpixel.shatteredpixeldungeon.items.PortableCover;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Obsidian;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.GavialPainter;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.SiestaPainter;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.BurningTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.CursingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FlashingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FrostTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.GrimTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.HallucinationTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.OblivionTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.OozeTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.OriginiumTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.PoisonDartTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.StormTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ToxicTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.WarpingTrap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.noosa.Group;
import com.watabou.utils.Random;

public class GavialLevel extends RegularLevel {
    {
        color1 = 0x4b6636;
        color2 = 0xf2f2f2;
    }

    @Override
    protected int standardRooms(boolean forceMax) {
        if (forceMax) return 10;
        //8 to 10, average 8.67
        return 7+ Random.chances(new float[]{3, 2, 1});
    }

    @Override
    protected int specialRooms(boolean forceMax) {
        if (forceMax) return 3;
        return 1 + Random.chances(new float[]{1, 1});
    }

    @Override
    protected void createItems() {

        if (Dungeon.depth == 32 || Dungeon.depth == 34 || Dungeon.depth == 37 || Dungeon.depth == 39) {
            addItemToSpawn(new PotionOfStrength());}
        else  if (Dungeon.depth == 31 || Dungeon.depth == 33 || Dungeon.depth == 36 || Dungeon.depth == 38) addItemToSpawn(new ScrollOfUpgrade());

        super.createItems();
    }

    @Override
    public int nMobs() {
        // 다른 계층보다 몬스터가 4마리 많이 등장합니다. 컨셉 : 맵 넓고 몹이 존나 많음.
        return super.nMobs()+4;
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILSE_SARGON;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_SEWERS;
    }

    @Override
    protected Painter painter() {
        return new GavialPainter()
                .setWater(feeling == Feeling.WATER ? 0.38f : 0.18f, 4)
                .setGrass(feeling == Feeling.GRASS ? 0.99f : 0.30f, 3)
                .setTraps(nTraps(), trapClasses(), trapChances());
    }

    @Override
    protected Class<?>[] trapClasses() {
        return new Class[]{
                FrostTrap.class, ToxicTrap.class, OozeTrap.class, PoisonDartTrap.class, BurningTrap.class,
                OriginiumTrap.class, FlashingTrap.class, StormTrap.class, HallucinationTrap.class, OblivionTrap.class,
                GrimTrap.class, WarpingTrap.class
        };
    }

    @Override
    protected float[] trapChances() {
        return new float[]{
                4,4,4,4,4,
                2,2,2,2,2,
                1,1
        };
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
