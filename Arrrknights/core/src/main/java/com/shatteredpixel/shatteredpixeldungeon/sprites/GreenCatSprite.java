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
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShaftParticle;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.Emitter;

public class GreenCatSprite extends MobSprite {
    private Emitter summoningBones;

    public GreenCatSprite() {
        super();

        texture( Assets.Sprites.KALTSIT );

        TextureFilm frames = new TextureFilm( texture, 32, 32 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 0, 0 );

        run = new Animation( 10, true );
        run.frames( frames, 0 );

        attack = new Animation( 17, false );
        attack.frames( frames, 0, 1, 2, 3, 4, 4, 4, 5, 6, 7, 2, 8 );

        die = new Animation( 10, false );
        die.frames( frames, 8 );

        play( idle );
    }


    // 나중에 프리스티스 시체 나오면 옮겨넣을 것.
    @Override
    public void die() {
        super.die();
        emitter().start( ShaftParticle.FACTORY, 0.3f, 4 );
        //emitter().start( Speck.factory( Speck.LIGHT ), 0.2f, 3 );
    }

    public static class NPC extends GreenCatSprite {
        public NPC() {
            super();

            texture(Assets.Sprites.KALTSIT_NPC);
            shadowHeight=-1.1f;

            TextureFilm frames = new TextureFilm(texture, 34, 34);

            idle = new Animation(6, true);
            idle.frames(frames, 0, 1, 2, 3, 4, 5);

            run = new Animation(10, true);
            run.frames(frames, 0);

            attack = new Animation(17, false);
            attack.frames(frames, 0);

            die = new Animation(10, false);
            die.frames(frames, 8);

            play(idle);

        }
    }
}