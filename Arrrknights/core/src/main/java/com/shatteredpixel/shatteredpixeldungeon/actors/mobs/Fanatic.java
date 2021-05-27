package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.A_master2Sprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.FanaticSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Fanatic extends Mob {
    private static final float SPAWN_DELAY	= 2f;

    {
        spriteClass = FanaticSprite.class;

        EXP = 1;
        maxLvl = -8;
        flying = false;
    }

    public Fanatic() {
        super();

        HP = HT = Dungeon.depth * 3 - 8;
        defenseSkill = Dungeon.depth;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 1 + Dungeon.depth / 2, (Dungeon.depth * 3) / 2);
    }

    @Override
    public int attackSkill( Char target ) {
        return 15 + Dungeon.depth;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 1);
    }

    @Override
    public float speed() {
        return super.speed() * 1.1f;
    }

    public static void spawnAround( int pos ) {
        for (int n : PathFinder.NEIGHBOURS4) {
            spawnAt( pos + n );
        }
    }
    public static Fanatic spawnAt( int pos ) {
        if (!Dungeon.level.solid[pos] && Actor.findChar( pos ) == null) {

            Fanatic w = new Fanatic();
            w.pos = pos;
            w.state = w.HUNTING;
            GameScene.add( w, SPAWN_DELAY );

            w.sprite.emitter().burst( ShadowParticle.CURSE, 5 );

            return w;
        } else {
            return null;
        }
    }
}
