package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.Emitter;

public class SandPillarSprite extends MobSprite {
    private Emitter cloud;

    public SandPillarSprite(){
        super();

        shadowWidth     = 1.35f;
        shadowHeight    = 0.175f;
        shadowOffset    = 0.25f;

        texture( Assets.Sprites.OBELISK );

        TextureFilm frames = new TextureFilm( texture, 28, 42 );

        idle = new MovieClip.Animation( 1, true );
        idle.frames( frames, 0);

        run = new MovieClip.Animation( 1, true );
        run.frames( frames, 0 );

        attack = new MovieClip.Animation( 1, false );
        attack.frames( frames, 0 );

        die = new MovieClip.Animation( 11, false );
        die.frames( frames, 0,1,2,3,4,5,5,5,5,5 );

        play( idle );
    }

    @Override
    public void link( Char ch ) {
        super.link( ch );

        if (cloud == null) {
            cloud = emitter();
            cloud.pour( Speck.factory(Speck.CORROSION), 0.7f );
        }
    }

    @Override
    public void turnTo(int from, int to) {
        //do nothing
    }

    @Override
    public void update() {

        super.update();

        if (cloud != null) {
            cloud.visible = visible;
        }
    }

    @Override
    public void die() {
        super.die();

        if (cloud != null) {
            cloud.on = false;
        }
    }
}
