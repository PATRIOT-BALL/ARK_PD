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

public class Sea_ReaperSprite extends MobSprite {

    public Sea_ReaperSprite() {
        super();

        texture( Assets.Sprites.SEA_REAPER );

        updateChargeState(false);

    }

    public void updateChargeState(boolean charge) {

        int c = charge ? 9 : 0;

        TextureFilm frames = new TextureFilm( texture, 60, 46 );

        idle = new Animation( 5, true );
        idle.frames( frames, c+2, c+3, c+4, c+5, c+6, c+7, c+8 );

        run = new Animation( 20, true );
        run.frames( frames, c+2, c+3, c+4, c+5, c+6, c+7, c+8 );

        attack = new Animation( 15, false );
        attack.frames( frames, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 0 );

        play( idle );

    }
}
