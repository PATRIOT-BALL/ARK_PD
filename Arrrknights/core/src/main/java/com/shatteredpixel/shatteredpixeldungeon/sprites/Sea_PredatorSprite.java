/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, orQJ
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
import com.watabou.noosa.TextureFilm;

public class Sea_PredatorSprite extends MobSprite {

    public Sea_PredatorSprite() {
        super();

        texture( Assets.Sprites.SEA_PREDATOR );

        TextureFilm frames = new TextureFilm( texture, 34, 34 );

        idle = new Animation( 10, true );
        idle.frames( frames, 0 );

        run = new Animation( 12, true );
        run.frames( frames, 1, 2, 3, 4, 5, 6 );

        attack = new Animation( 15, false );
        attack.frames( frames, 7, 8, 9, 10, 11, 12, 13 );

        die = new Animation( 10, false );
        die.frames( frames, 14, 15, 16, 17, 18, 19, 20 );

        play( idle );
    }
}
