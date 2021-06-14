package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.secret;

import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WellWater;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.StandardRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.BlazingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.CursingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ExplosiveTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.PoisonDartTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ToxicTrap;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class SecretCursedWeaponRoom extends SecretRoom {

    @Override
    public boolean canMerge(Level l, Point p, int mergeTerrain) {
        int cell = l.pointToCell(pointInside(p, 1));
        return l.map[cell] == Terrain.EMPTY;
    }

    @Override
    public void paint(Level level) {
        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.EMPTY );
        for (Door door : connected.values()) {
            door.set( Door.Type.REGULAR );
        }

        int mines = (int)Math.round(Math.sqrt(square()));

        Weapon weapon = Generator.randomWeapon();
        weapon.level(1);
        weapon.cursed = true;
        weapon.enchant(Weapon.Enchantment.randomCurse());
        level.drop(weapon , level.pointToCell(random(1)) ).type = Heap.Type.CHEST;

        for (int i = 0; i < mines; i++ ){
            int pos;
            do {
                pos = level.pointToCell(random(1));
            } while (level.traps.get(pos) != null);

            for (int j = 0; j < 8; j ++){
                int c = PathFinder.NEIGHBOURS8[Random.Int(8)];
                if (level.traps.get(pos+c) == null && level.map[pos+c] == Terrain.EMPTY){
                    Painter.set(level, pos+c, Terrain.WATER);
                }
            }

            Painter.set(level, pos, Terrain.TRAP);
            level.setTrap(new CursingTrap(), pos);

        }

    }
}
