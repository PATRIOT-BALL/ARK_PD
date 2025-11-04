package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.AcidSlug_A;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Warlock;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class Acid_AlphaSprite extends MobSprite{
    public Acid_AlphaSprite() {
        super();

        texture( Assets.Sprites.RED_SPITTER );

        TextureFilm frames = new TextureFilm( texture, 32, 32 );

        idle = new Animation( 8, true );
        idle.frames( frames, 0, 1, 2, 3, 4, 5 );

        run = new Animation( 10, true );
        run.frames( frames, 0, 1, 2, 3, 4, 5 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0, 6, 7, 8, 9, 10, 11, 7 );

        zap = attack.clone();

        die = new Animation( 10, false );
        die.frames( frames, 0 );
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
                        ((AcidSlug_A)ch).onZapComplete();
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
