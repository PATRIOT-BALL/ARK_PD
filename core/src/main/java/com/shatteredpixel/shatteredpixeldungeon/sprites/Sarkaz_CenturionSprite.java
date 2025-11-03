package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class Sarkaz_CenturionSprite extends MobSprite {

    public Sarkaz_CenturionSprite() {
        super();

        texture( Assets.Sprites.SARKAZ_CENTURION );

        TextureFilm frames = new TextureFilm( texture, 42, 38 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 0, 0 );

        run = new Animation( 14, true );
        run.frames( frames, 1, 2, 3, 4, 5, 6, 7, 8 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}