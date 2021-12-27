package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class NPC_AstesiaSprite extends MobSprite {
    public NPC_AstesiaSprite() {
        super();

        texture( Assets.Sprites.AST );

        TextureFilm frames = new TextureFilm( texture, 36, 36 );

        idle = new Animation( 7, true );
        idle.frames( frames, 41, 42, 43, 44, 45, 46, 41, 42, 43, 44, 45, 46, 41, 42, 43, 44, 45, 46, 41, 42, 43, 44, 45, 46, 41, 42, 43, 47, 48, 46  );

        run = new Animation( 10, true );
        run.frames( frames, 0 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );
    }
}
