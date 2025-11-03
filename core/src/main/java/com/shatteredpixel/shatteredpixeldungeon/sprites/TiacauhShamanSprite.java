package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.TiacauhRitualist;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.TiacauhShaman;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class TiacauhShamanSprite extends MobSprite {
    public TiacauhShamanSprite()
    {
        super();


        texture( Assets.Sprites.TIACAUH_SHAMAN );

        TextureFilm frames = new TextureFilm( texture, 56, 46 );

        idle = new MovieClip.Animation( 2, true );
        idle.frames( frames, 0 );

        run = new MovieClip.Animation( 12, true );
        run.frames( frames, 0 );

        attack = new MovieClip.Animation( 25, false );
        attack.frames( frames, 0 );

        zap = attack.clone();

        die = new MovieClip.Animation( 12, false );
        die.frames( frames, 0,0,0 );

        play( idle );
    }

    public void zap( int cell ) {

        turnTo( ch.pos , cell );
        play( zap );

        MagicMissile.boltFromChar( parent,
                MagicMissile.SHAMAN_BLUE,
                this,
                cell,
                new Callback() {
                    @Override
                    public void call() {
                        ((TiacauhShaman)ch).onZapComplete();
                    }
                } );
        Sample.INSTANCE.play( Assets.Sounds.ZAP );
    }
}
