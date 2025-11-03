package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class FaustSprite extends MobSprite {

    public FaustSprite() {
        super();

        texture( Assets.Sprites.FAUST );

        TextureFilm frames = new TextureFilm( texture, 32, 32 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 0, 0 );

        run = new Animation( 10, true );
        run.frames( frames, 1 );

        attack = new Animation( 15, false );
        attack.frames( frames, 2, 3 );

        die = new Animation( 10, false );
        die.frames( frames, 4 );

        play( idle );
    }
}