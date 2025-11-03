package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class PRTS_Sprite extends MobSprite {

    public PRTS_Sprite() {
        super();

        texture( Assets.Sprites.PRTS1 );

        TextureFilm frames = new TextureFilm( texture, 32, 32 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 0, 0 );

        run = new Animation( 10, true );
        run.frames( frames, 0 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );

        alpha(0.66f);
    }
}