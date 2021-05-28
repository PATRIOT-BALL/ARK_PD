package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class BreakerSprite extends MobSprite {

    public BreakerSprite() {
        super();

        texture( Assets.Sprites.BREAKER );

        TextureFilm frames = new TextureFilm( texture, 38, 32 );

        idle = new Animation( 8, true );
        idle.frames( frames, 0 );

        run = new Animation( 17, true );
        run.frames( frames, 1, 2, 3, 4, 5, 6, 7, 8 );

        attack = new Animation( 15, false );
        attack.frames( frames, 9, 10, 10, 11, 11, 12 );

        die = new Animation( 12, false );
        die.frames( frames, 0 );

        play( idle );
    }
}