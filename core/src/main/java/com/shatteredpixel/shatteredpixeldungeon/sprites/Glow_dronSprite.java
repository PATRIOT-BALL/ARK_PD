package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class Glow_dronSprite extends MobSprite {
    public Glow_dronSprite() {
        super();

        texture( Assets.Sprites.GLOW_DRON );

        TextureFilm frames = new TextureFilm( texture, 32, 32 );

        idle = new Animation( 8, true );
        idle.frames( frames, 0, 1, 2, 3, 4, 5 );

        run = new Animation( 10, true );
        run.frames( frames, 0, 1, 2, 3, 4, 5 );

        attack = new Animation( 18, false );
        attack.frames( frames, 6, 7, 8, 9, 10, 11, 12, 13, 14, 8 );

        die = new Animation( 10, false );
        die.frames( frames, 12, 13, 14, 8 );

        play( idle );
    }
}
