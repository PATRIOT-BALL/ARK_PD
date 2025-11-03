package com.shatteredpixel.shatteredpixeldungeon.items.wands.SP;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAmplified;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Shocking;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class StaffOfGreyy extends DamageWand {
    private static ItemSprite.Glowing COL = new ItemSprite.Glowing( 0xFFD700 );
    {
        image = ItemSpriteSheet.WAND_LIGHTNING;
    }

    private ArrayList<Char> affected = new ArrayList<>();

    private ArrayList<Lightning.Arc> arcs = new ArrayList<>();

    public int min(int lvl){
        return 3+lvl;
    }

    public int max(int lvl){
        return 8+4*lvl+ RingOfAmplified.DamageBonus(Dungeon.hero) * 4;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return COL;
    }

    @Override
    protected void onZap( Ballistica bolt ) {

        for (Char ch : affected){
            if (ch == Dungeon.hero) Camera.main.shake( 2, 0.3f );
            ch.sprite.centerEmitter().burst( SparkParticle.FACTORY, 3 );
            ch.sprite.flash();

            if (ch != curUser && ch.alignment == curUser.alignment && ch.pos != bolt.collisionPos){
                continue;
            }
            processSoulMark(ch, chargesPerCast());
            if (ch == curUser) {
                ch.damage(Math.round(damageRoll() * 0.25f), this);
            } else {
                ch.damage(Math.round(damageRoll()), this);
            }
        }

        if (!curUser.isAlive()) {
            Dungeon.fail( getClass() );
            GLog.n(Messages.get(this, "ondeath"));
        }
    }

    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        //acts like shocking enchantment
        new Shocking().proc(staff, attacker, defender, damage);
    }

    private void arc( Char ch ) {

        int dist = (Dungeon.level.water[ch.pos] && !ch.flying) ? 4 : 3;

        ArrayList<Char> hitThisArc = new ArrayList<>();
        PathFinder.buildDistanceMap( ch.pos, BArray.not( Dungeon.level.solid, null ), dist );
        for (int i = 0; i < PathFinder.distance.length; i++) {
            if (PathFinder.distance[i] < Integer.MAX_VALUE){
                Char n = Actor.findChar( i );
                if (n == Dungeon.hero && PathFinder.distance[i] > 1)
                    //the hero is only zapped if they are adjacent
                    continue;
                else if (n != null && !affected.contains( n )) {
                    hitThisArc.add(n);
                }
            }
        }

        affected.addAll(hitThisArc);
        for (Char hit : hitThisArc){
            arcs.add(new Lightning.Arc(ch.sprite.center(), hit.sprite.center()));
            arc(hit);
        }
    }

    @Override
    protected void fx( Ballistica bolt, Callback callback ) {

        affected.clear();
        arcs.clear();

        int cell = bolt.collisionPos;

        Char ch = Actor.findChar( cell );
        if (ch != null) {
            affected.add( ch );
            arcs.add( new Lightning.Arc(curUser.sprite.center(), ch.sprite.center()));
            arc(ch);
        } else {
            arcs.add( new Lightning.Arc(curUser.sprite.center(), DungeonTilemap.raisedTileCenterToWorld(bolt.collisionPos)));
            CellEmitter.center( cell ).burst( SparkParticle.FACTORY, 3 );
        }

        //don't want to wait for the effect before processing damage.
        curUser.sprite.parent.addToFront( new Lightning( arcs, null ) );
        Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
        callback.call();
    }
}
