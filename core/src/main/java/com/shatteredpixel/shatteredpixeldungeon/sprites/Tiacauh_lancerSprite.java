package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class Tiacauh_lancerSprite extends MobSprite {

    public Tiacauh_lancerSprite() {
        super();

        texture( Assets.Sprites.TIACAUH_LANCER);

        TextureFilm frames = new TextureFilm( texture, 56, 46 );

        idle = new Animation( 5, true );
        idle.frames( frames, 0, 1, 2, 1, 0 );

        run = new Animation( 8, true );
        run.frames( frames, 3, 4, 5, 6, 7, 8, 9, 10 );

        attack = new Animation( 20, false );
        attack.frames( frames, 3, 4, 5, 6, 7, 8, 9, 10  );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}
