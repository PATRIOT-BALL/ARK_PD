package com.shatteredpixel.shatteredpixeldungeon.effects.particles;

import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Random;

public class BlueBlastParticle extends PixelParticle.Shrinking {
    public static final Emitter.Factory FACTORY = new Emitter.Factory() {
        @Override
        public void emit( Emitter emitter, int index, float x, float y ) {
            ((BlueBlastParticle)emitter.recycle( BlueBlastParticle.class )).reset( x, y );
        }
        @Override
        public boolean lightMode() {
            return true;
        }
    };

    public BlueBlastParticle() {
        super();

        color( 0x0000FF );
        acc.set( 0, +50 );
    }

    public void reset( float x, float y ) {
        revive();

        this.x = x;
        this.y = y;

        left = lifespan = Random.Float();

        size = 8;
        speed.polar( -Random.Float( 3.1415926f ), Random.Float( 32, 64 ) );
    }

    @Override
    public void update() {
        super.update();
        am = left > 0.8f ? (1 - left) * 5 : 1;
    }
}
