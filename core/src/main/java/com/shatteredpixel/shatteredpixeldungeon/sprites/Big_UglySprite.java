package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class Big_UglySprite extends MobSprite {

    public Big_UglySprite() {
        super();

        texture( Assets.Sprites.BIG_UGLY);

        TextureFilm frames = new TextureFilm( texture, 78, 60 );

        idle = new Animation( 10, true );
        idle.frames( frames, 1, 2 );

        run = new Animation( 13, true );
        run.frames( frames, 3, 4, 5, 6, 7, 8 );

        attack = new Animation( 13, false );
        attack.frames( frames, 9, 10, 11, 12, 13, 14, 15, 16 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}