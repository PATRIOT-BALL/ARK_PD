package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Shaman;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.WaveCaster;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class WaveCasterSprite extends MobSprite {
    public WaveCasterSprite()
    {
        super();


        texture( Assets.Sprites.WAVE_CASTER );

        TextureFilm frames = new TextureFilm( texture, 32, 32 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0 );

        run = new Animation( 12, true );
        run.frames( frames, 0 );

        attack = new Animation( 25, false );
        attack.frames( frames, 0 );

        zap = attack.clone();

        die = new Animation( 12, false );
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
                        ((WaveCaster)ch).onZapComplete();
                    }
                } );
        Sample.INSTANCE.play( Assets.Sounds.ZAP );
    }
}
