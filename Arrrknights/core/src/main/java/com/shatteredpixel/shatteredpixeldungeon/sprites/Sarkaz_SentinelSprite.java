package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class Sarkaz_SentinelSprite extends MobSprite {

    public Sarkaz_SentinelSprite() {
        super();

        texture( Assets.Sprites.SARKAZ_SENTINEL );

        TextureFilm frames = new TextureFilm( texture, 32, 38 );

        idle = new Animation( 2, true );
        idle.frames( frames, 1, 1, 1 );

        run = new Animation( 10, true );
        run.frames( frames, 0 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}