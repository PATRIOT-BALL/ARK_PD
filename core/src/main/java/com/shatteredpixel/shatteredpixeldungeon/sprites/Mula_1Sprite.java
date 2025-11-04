package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class Mula_1Sprite extends MobSprite {

    public Mula_1Sprite() {
        super();

        texture( Assets.Sprites.MULA_HEAD );

        TextureFilm frames = new TextureFilm( texture, 152, 130 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 );

        run = new Animation( 10, true );
        run.frames( frames, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 );

        die = new Animation( 10, false );
        die.frames( frames, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 );

        play( idle );
    }


    public void attack( int cell ) {
        play( attack );
    }
}