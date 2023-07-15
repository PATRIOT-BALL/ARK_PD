package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class KazemaruSprite extends MobSprite {

    public KazemaruSprite() {
        super();

        texture( Assets.Sprites.KAZE );

        TextureFilm frames = new TextureFilm( texture, 42, 38 );

        idle = new Animation( 8, false );
        idle.frames( frames, 24 );

        run = new Animation( 17, true );
        run.frames( frames, 22 );

        attack = new Animation( 17, false );
        attack.frames( frames, 18, 19, 20, 21, 22, 23, 24, 1, 2, 3, 4, 5, 6, 7, 8, 9 );

        die = new Animation( 15, false );
        die.frames( frames, 10, 11, 12, 13, 14, 15, 16, 17 );


        play( idle );//이 구문이 없을경우 스프라이트 오류가 남(찔러용 그거)
    }
}