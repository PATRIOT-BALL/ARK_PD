package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class NPC_AstesiaSprite extends MobSprite {
    public NPC_AstesiaSprite() {
        super();

        texture( Assets.Sprites.NPC_ASTESIA );

        TextureFilm frames = new TextureFilm( texture, 36, 34 );

        idle = new MovieClip.Animation( 8, true );
        idle.frames( frames, 0, 1, 2, 3, 4, 5 );

        run = new MovieClip.Animation( 15, false );
        run.frames( frames, 50 );

        attack = new MovieClip.Animation( 15, false );
        attack.frames( frames, 50 );

        zap = attack.clone();

        die = new MovieClip.Animation( 8, false );
        die.frames( frames, 50 );

        play( idle.clone() );
    }

    @Override
    public void idle() {
        isMoving = false;
        super.idle();
    }

}