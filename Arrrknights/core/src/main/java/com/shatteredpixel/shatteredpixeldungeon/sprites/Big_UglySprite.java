package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class Big_UglySprite extends MobSprite {

    public Big_UglySprite() {
        super();

        texture( Assets.Sprites.BIG_UGLY);

        TextureFilm frames = new TextureFilm( texture, 72, 60 );

        idle = new Animation( 7, true );
        idle.frames( frames, 0 );

        run = new Animation( 13, true );
        run.frames( frames, 0 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}