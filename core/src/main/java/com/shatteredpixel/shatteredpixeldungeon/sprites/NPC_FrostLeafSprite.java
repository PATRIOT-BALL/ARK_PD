package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class NPC_FrostLeafSprite extends MobSprite {
    public NPC_FrostLeafSprite() {
        super();

        texture( Assets.Sprites.NPC_FROST );

        TextureFilm frames = new TextureFilm( texture, 36, 34 );

        idle = new MovieClip.Animation( 2, true );
        idle.frames( frames, 0, 1  );

        run = new MovieClip.Animation( 10, true );
        run.frames( frames, 0 );

        attack = new MovieClip.Animation( 15, false );
        attack.frames( frames, 0 );

        die = new MovieClip.Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}
