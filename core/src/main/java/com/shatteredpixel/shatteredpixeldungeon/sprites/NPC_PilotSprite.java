package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class NPC_PilotSprite extends MobSprite {

    public NPC_PilotSprite() {
        super();

        texture( Assets.Sprites.NPC_PILOT );

        TextureFilm frames = new TextureFilm( texture, 32, 32 );

        idle = new Animation( 8, true );
        idle.frames( frames, 0, 1, 2, 3, 4, 5 );

        run = new Animation( 10, true );
        run.frames( frames, 0 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}