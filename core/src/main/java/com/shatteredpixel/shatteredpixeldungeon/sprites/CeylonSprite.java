package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class CeylonSprite extends MobSprite {

    public CeylonSprite() {
        super();

        texture( Assets.Sprites.CEYLON );

        TextureFilm frames = new TextureFilm( texture, 38, 38 );

        idle = new Animation( 10, true );
        idle.frames( frames, 0, 1, 2, 3, 4, 5, 6 );

        run = new Animation( 10, true );
        run.frames( frames, 0 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0 );

        die = new Animation( 16, false );
        die.frames( frames, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 38, 38, 38, 38, 39, 40, 41, 42, 43, 44, 0 );

        play( idle );
    }
}