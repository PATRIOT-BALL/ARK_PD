package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class ZealotSprite extends MobSprite {
    public ZealotSprite(){
        super();

        texture( Assets.Sprites.ZEALOT );

        TextureFilm frames = new TextureFilm( texture, 42, 30 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 0, 0 );

        run = new Animation( 10, true );
        run.frames( frames, 0 );

        attack = new Animation( 20, false );
        attack.frames( frames, 0);

        die = new Animation( 18, false );
        die.frames( frames, 0 );

        play( idle );}
}
