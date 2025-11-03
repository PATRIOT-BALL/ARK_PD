package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class EX42_GroundSprite extends MobSprite {
    public EX42_GroundSprite(){
        super();

        texture( Assets.Sprites.EX42 );

        TextureFilm frames = new TextureFilm( texture, 38, 36 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 0, 0 );

        run = new Animation( 10, true );
        run.frames( frames, 0 );

        attack = new Animation( 20, false );
        attack.frames( frames, 0);

        die = new Animation( 18, false );
        die.frames( frames, 1, 2, 3, 4, 5, 6, 7, 8, 9 );

        play( idle );}
    }
