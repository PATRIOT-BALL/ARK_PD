package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShaftParticle;
import com.watabou.noosa.TextureFilm;

public class Guard_operHeroSprite extends MobSprite {
    public Guard_operHeroSprite() {
        super();

        texture( Assets.Sprites.GUARD );

        TextureFilm frames = new TextureFilm( texture, 32, 32 );

        idle = new Animation( 5, true );
        idle.frames( frames, 18, 19 );

        run = new Animation( 13, true );
        run.frames( frames, 18, 19 );

        attack = new Animation( 18, false );
        attack.frames( frames, 20, 21, 8, 9, 10, 11, 12, 13, 14, 15, 21, 20 );

        die = new Animation( 8, false );
        die.frames( frames, 16 );


        play( idle );
    }

    //@Override
    //public void draw() {
    //Blending.setLightMode();
    //super.draw();
    //Blending.setNormalMode();
    //}

    @Override
    public void die() {
        super.die();
        emitter().start( ShaftParticle.FACTORY, 0.3f, 4 );
        //emitter().start( Speck.factory( Speck.LIGHT ), 0.2f, 3 );
    }

    @Override
    public int blood() {
        return 0xFFFFFF;
    }
}
