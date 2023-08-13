package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Yog;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.YogFist;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss.EmperorPursuer;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.Camera;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class PursuerSprite extends MobSprite {
    public PursuerSprite() {
        super();

        int c = 0;

        texture( Assets.Sprites.EMPEROR_BLADE );

        TextureFilm frames = new TextureFilm( texture, 72, 46 );

        idle = new MovieClip.Animation( 5, true );
        idle.frames( frames, c+0, c+1, c+2, c+1, c+0 );

        run = new MovieClip.Animation( 8, true );
        run.frames( frames, c+7, c+8, c+9, c+10, c+11, c+12, c+13, c+14 );

        attack = new MovieClip.Animation( 15, false );
        attack.frames( frames, c+3, c+4, c+5, c+6);

        zap = new MovieClip.Animation( 8, false );
        zap.frames( frames, c+3, c+4, c+5, c+6 );

        die = new MovieClip.Animation( 10, false );
        die.frames( frames, c+15 );

        play( idle );//이 구문이 없을경우 스프라이트 오류가 남(찔러용 그거)
    }

    @Override
    public void attack( int cell ) {
        super.attack( cell );
    }

    public void zap( int cell ) {

        turnTo( ch.pos , cell );
        play( zap );

        MagicMissile.boltFromChar( parent,
                MagicMissile.SHADOW,
                this,
                cell,
                new Callback() {
                    @Override
                    public void call() {
                        //pre-0.8.0 saves
                        ((EmperorPursuer)ch).onZapComplete();

                    }
                } );
        Sample.INSTANCE.play( Assets.Sounds.ZAP );
    }

    @Override
    public void onComplete( MovieClip.Animation anim ) {
        super.onComplete( anim );
        if (anim == attack) {
            Camera.main.shake( 4, 0.2f );
        } else if (anim == zap) {
            idle();
        }
    }
}
