package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class Tiacauh_BraveSprite extends MobSprite {

    public Tiacauh_BraveSprite() {
        super();

        texture( Assets.Sprites.TIACAUH_BRAVE);

        TextureFilm frames = new TextureFilm( texture, 56, 46 );

        idle = new Animation( 5, true );
        idle.frames( frames, 0, 1, 2, 3, 2, 1, 0 );

        run = new Animation( 6, true );
        run.frames( frames, 4, 5, 6, 7, 8, 9, 10, 11 );

        attack = new Animation( 20, false );
        attack.frames( frames, 4, 5, 6, 7, 8, 9, 10, 11 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}
