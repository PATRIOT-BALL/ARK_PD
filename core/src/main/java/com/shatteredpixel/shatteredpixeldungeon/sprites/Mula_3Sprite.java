package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class Mula_3Sprite extends MobSprite {

    public Mula_3Sprite() {
        super();

        texture( Assets.Sprites.MULA_TAIL );

        TextureFilm frames = new TextureFilm( texture, 72, 50 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0 );

        run = new Animation( 10, true );
        run.frames( frames, 0 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}