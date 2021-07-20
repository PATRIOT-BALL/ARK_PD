package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class PurgatoryKnife extends MissileWeapon {
    private static ItemSprite.Glowing COL = new ItemSprite.Glowing( 0x00008B);
    {
        image = ItemSpriteSheet.LAVA;
        hitSound = Assets.Sounds.HIT_KNIFE;
        hitSoundPitch = 1.2f;

        bones = false;

        tier = 1;
        baseUses = 1000;
    }
    @Override
    public ItemSprite.Glowing glowing() { return COL; }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (defender.properties().contains(Char.Property.MINIBOSS) != true && defender.properties().contains(Char.Property.BOSS) != true)
        if (Random.Int(76) < 74) {
            Ballistica route = new Ballistica(defender.pos, attacker.pos, Ballistica.PROJECTILE);
            int cell = route.collisionPos;

            //can't occupy the same cell as another char, so move back one.
            if (Actor.findChar(cell) != null && cell != defender.pos)
                cell = route.path.get(route.dist - 1);

            if (Dungeon.level.avoid[cell]) {
                ArrayList<Integer> candidates = new ArrayList<>();
                for (int n : PathFinder.NEIGHBOURS8) {
                    cell = route.collisionPos + n;
                    if (Dungeon.level.passable[cell] && Actor.findChar(cell) == null) {
                        candidates.add(cell);
                    }
                }
                if (candidates.size() > 0)
                    cell = Random.element(candidates);
            }

            ScrollOfTeleportation.appear(defender, cell);
        }

        return super.proc(attacker, defender, damage);
    }

    @Override
    public boolean isUpgradable() { return false; }

    @Override
    public int value() {
        return 70;
    }
}
