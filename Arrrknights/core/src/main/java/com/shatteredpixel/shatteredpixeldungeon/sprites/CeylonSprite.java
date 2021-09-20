package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class CeylonSprite extends MobSprite {

    public CeylonSprite() {
        super();

        texture( Assets.Sprites.CEYLON );

        TextureFilm frames = new TextureFilm( texture, 38, 38 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 0, 0 );

        run = new Animation( 10, true );
        run.frames( frames, 0 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}