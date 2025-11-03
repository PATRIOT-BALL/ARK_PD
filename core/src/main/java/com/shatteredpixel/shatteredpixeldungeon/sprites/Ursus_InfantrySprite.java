package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class Ursus_InfantrySprite extends MobSprite {

    public Ursus_InfantrySprite() {
        super();

        texture( Assets.Sprites.URSUS_INFANTRI );

        TextureFilm frames = new TextureFilm( texture, 32, 32 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0 );

        run = new Animation( 12, true );
        run.frames( frames, 0);

        attack = new Animation( 12, false );
        attack.frames( frames, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}
