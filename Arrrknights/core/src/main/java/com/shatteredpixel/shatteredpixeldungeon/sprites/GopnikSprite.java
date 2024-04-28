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
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.GameMath;

public class GopnikSprite extends MobSprite {

    public GopnikSprite() {
        super();

        texture( Assets.Sprites.GOPNIK );

        TextureFilm frames = new TextureFilm( texture, 58, 44 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 0, 0, 0 );

        run = new Animation( 18, true );
        run.frames( frames, 0, 1, 2 );

        attack = new Animation( 12, false );
        attack.frames( frames, 3, 4, 5, 6, 7 );

        die = new Animation( 10, false );
        die.frames( frames, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 );

        play( idle );
    }

    private static int[] tierFrames = {0, 30, 60, 90, 120, 150};

    public void setArmor( int tier ){
        int c = tierFrames[(int)GameMath.gate(0, tier, 5)];

        TextureFilm frames = new TextureFilm( texture, 58, 44 );

        idle.frames( frames, 0+c, 0+c, 0+c, 0+c, 0+c);
        run.frames( frames, 0+c, 1+c, 2+c );
        attack.frames( frames, 3+c, 4+c, 5+c, 6+c, 7+c );
        //death animation is always armorless

        play( idle, true );

    }

    @Override
    public int blood() {
        return 0xFFcdcdb7;
    }
}
