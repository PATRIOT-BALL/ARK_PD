package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class SchwarzSprite extends MobSprite {

    public SchwarzSprite() {
        super();

        texture( Assets.Sprites.SCHWARZ );

        TextureFilm frames = new TextureFilm( texture, 42, 32 );

        idle = new Animation( 2, true );
        idle.frames( frames, 9 );

        run = new Animation( 10, true );
        run.frames( frames, 10, 11, 12, 13, 14, 15, 16, 17 );

        attack = new Animation( 15, false );
        attack.frames( frames, 1, 2, 3, 4, 5, 6, 7, 8, 9 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}