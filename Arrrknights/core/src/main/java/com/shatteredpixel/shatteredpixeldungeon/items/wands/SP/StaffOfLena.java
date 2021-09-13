package com.shatteredpixel.shatteredpixeldungeon.items.wands.SP;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Web;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PurpleParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAmplified;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Iterator;

public class StaffOfLena extends DamageWand {
    private static ItemSprite.Glowing COL = new ItemSprite.Glowing( 0x00FF00 );
    {
        image = ItemSpriteSheet.WAND_REGROWTH;

        collisionProperties = Ballistica.STOP_SOLID;
    }


    public int min(int lvl){
        return 4+lvl;
    }

    public int max(int lvl){
        return 6+4*lvl+ RingOfAmplified.DamageBonus(Dungeon.hero) * 4;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return COL;
    }

    @Override
    public int targetingPos(Hero user, int dst) {
        return dst;
    }

    @Override
    protected void onZap( Ballistica beam ) {
        boolean terrainAffected = false;

        int level = buffedLvl();

        int maxDistance = Math.min(distance(), beam.dist);

        ArrayList<Char> chars = new ArrayList<>();


        for (int c : beam.subPath(1, maxDistance)) {
            Char ch;
            CellEmitter.get( c ).burst( LeafParticle.GENERAL, 10 );
            if ((ch = Actor.findChar( c )) != null) {

                chars.add( ch );
            }

            int terr = Dungeon.level.map[c];
            if (terr == Terrain.EMPTY || terr == Terrain.EMBERS || terr == Terrain.EMPTY_DECO ||
                    terr == Terrain.GRASS || terr == Terrain.HIGH_GRASS) {
            Level.set(c, Terrain.FURROWED_GRASS);
                GameScene.updateMap( c );}
        }

        if (terrainAffected) {
            Dungeon.observe();
        }

        int lvl = level;
        for (Char ch : chars) {
            processSoulMark(ch, chargesPerCast());
            ch.damage( damageRoll(lvl), this );
            Buff.affect(ch, Vertigo.class, 1f);
            ch.sprite.flash();
        }
    }

    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        //no direct effect, see magesStaff.reachfactor
    }

    private int distance() {
        return 6;
    }

    @Override
    protected void fx( Ballistica beam, Callback callback ) {

        int cell = beam.path.get(Math.min(beam.dist, distance()));
        callback.call();
    }

}
