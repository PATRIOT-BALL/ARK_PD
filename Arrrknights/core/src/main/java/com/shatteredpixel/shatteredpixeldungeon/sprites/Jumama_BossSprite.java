package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class Jumama_BossSprite extends MobSprite {

    public Jumama_BossSprite() {
        super();

        texture( Assets.Sprites.JUMAMA);

        TextureFilm frames = new TextureFilm( texture, 36, 50 );

        idle = new Animation( 7, true );
        idle.frames( frames, 0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 6, 7, 5 );

        run = new Animation( 13, true );
        run.frames( frames, 0 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0, 1, 2 );

        die = new Animation( 15, false );
        die.frames( frames, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 );

        play( idle );
    }
}
