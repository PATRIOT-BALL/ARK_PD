package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class NPC_dummySprite extends MobSprite {

    public NPC_dummySprite() {
        super();

        texture( Assets.Sprites.NPC_DUMMY );

        TextureFilm frames = new TextureFilm( texture, 36, 36 );

        idle = new Animation( 20, true );
        idle.frames( frames, 0);

        run = new Animation( 10, true );
        run.frames( frames, 0 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}