package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class LensSprite extends MobSprite {

    public LensSprite() {
        super();

        texture( Assets.Sprites.LENS );

        TextureFilm frames = new TextureFilm( texture, 24, 24 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 1, 0, 1 );

        run = new Animation( 8, true );
        run.frames( frames, 0, 1, 0, 1 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0, 2, 3, 4, 5, 6 );

        die = new Animation( 10, false );
        die.frames( frames, 0, 7, 8, 9, 10, 11, 12 );

        play( idle );
    }
}
