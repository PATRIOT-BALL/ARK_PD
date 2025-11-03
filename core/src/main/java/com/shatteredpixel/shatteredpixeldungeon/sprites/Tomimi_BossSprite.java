package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class Tomimi_BossSprite extends MobSprite {

    public Tomimi_BossSprite() {
        super();

        texture( Assets.Sprites.TOMIMI);

        TextureFilm frames = new TextureFilm( texture, 36, 36 );

        idle = new Animation( 7, true );
        idle.frames( frames, 1, 2, 3, 4, 5, 6 );

        run = new Animation( 13, true );
        run.frames( frames, 7, 8, 9, 10, 11, 12, 13, 14 );

        attack = new Animation( 18, false );
        attack.frames( frames, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26 );

        die = new Animation( 10, false );
        die.frames( frames, 27, 28, 29 );

        play( idle );
    }
}
