package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Warlock;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class Acid_AlphaSprite extends MobSprite{
    public Acid_AlphaSprite() {
        super();

        texture( Assets.Sprites.SPITTER );

        TextureFilm frames = new TextureFilm( texture, 32, 32 );

        idle = new Animation( 12, true );
        idle.frames( frames, 14, 14, 14, 14, 14, 14, 14, 14, 15, 16, 15, 16, 15, 16 );

        run = new Animation( 4, true );
        run.frames( frames, 19, 20 );

        attack = new Animation( 15, false );
        attack.frames( frames, 14, 17, 18 );

        zap = attack.clone();

        die = new Animation( 12, false );
        die.frames( frames, 14, 21, 22, 23, 24 );

        play( idle );
    }

    public void zap( int cell ) {

        turnTo( ch.pos , cell );
        play( zap );

        MagicMissile.boltFromChar( parent,
                MagicMissile.FIRE,
                this,
                cell,
                new Callback() {
                    @Override
                    public void call() {
                        ((Warlock)ch).onZapComplete();
                    }
                } );
        Sample.INSTANCE.play( Assets.Sounds.ZAP );
    }

    @Override
    public void onComplete( Animation anim ) {
        if (anim == zap) {
            idle();
        }
        super.onComplete( anim );
    }
}
