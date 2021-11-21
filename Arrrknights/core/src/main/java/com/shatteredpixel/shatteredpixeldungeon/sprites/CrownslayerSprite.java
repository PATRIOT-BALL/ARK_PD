package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class CrownslayerSprite extends MobSprite {

    public CrownslayerSprite() {
        super();

        texture( Assets.Sprites.CS );

        TextureFilm frames = new TextureFilm( texture, 36, 36 );

        idle = new Animation( 7, true );
        idle.frames( frames, 1, 2, 3, 4, 5, 6);

        run = new Animation( 16, true );
        run.frames( frames, 7, 8, 9, 10, 11, 12, 13, 14 );

        attack = new Animation( 18, false );
        attack.frames( frames, 15, 16, 17, 18, 19, 20 );

        die = new Animation( 10, false );
        die.frames( frames, 21 );

        alpha(0.75f);

        play( idle );
    }
}