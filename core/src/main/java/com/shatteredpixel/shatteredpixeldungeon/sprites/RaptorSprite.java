/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Elemental;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.RainbowParticle;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Callback;

public class RaptorSprite extends MobSprite {

    private Emitter particles;

    public RaptorSprite() {
        super();

        texture( Assets.Sprites.RAPTOR );

        TextureFilm frames = new TextureFilm( texture, 46, 46 );

        idle = new Animation( 8, true );
        idle.frames( frames, 0, 1, 2, 3, 2, 1, 0 );

        run = new Animation( 10, true );
        run.frames( frames, 1, 2, 3, 2, 1 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0, 4, 5, 6, 5, 4 );

        die = new Animation( 10, false );
        die.frames( frames, 0, 7, 8, 9, 10 );


        play( idle );
    }

    @Override
    public void link( Char ch ) {
        super.link( ch );

        if (particles == null) {
            particles = createEmitter();
        }
    }

    @Override
    public void update() {
        super.update();

        if (particles != null){
            particles.visible = visible;
        }
    }

    @Override
    public void die() {
        super.die();
        if (particles != null){
            particles.on = false;
        }
    }

    @Override
    public void kill() {
        super.kill();
        if (particles != null){
            particles.killAndErase();
        }
    }

    public void zap( int cell ) {

        turnTo( ch.pos , cell );
        play( zap );

        MagicMissile.boltFromChar( parent,
                MagicMissile.RAINBOW,
                this,
                cell,
                new Callback() {
                    @Override
                    public void call() {
                        ((Elemental)ch).onZapComplete();
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

    protected Emitter createEmitter() {
        Emitter emitter = emitter();
        emitter.pour( RainbowParticle.BURST, 0.025f );
        return emitter;
    }
}