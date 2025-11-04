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

public class Ghost2Sprite extends MobSprite {

    public Ghost2Sprite() {
        super();

        texture( Assets.Sprites.GHOST1 );
        TextureFilm film = new TextureFilm( texture, 32, 32 );

        int c = 30;

        idle = new Animation( 1, true );
        idle.frames( film, c+0 );

        run = new Animation( 17, true );
        run.frames( film, c+1, c+2, c+3, c+4, c+5, c+6, c+7, c+8 );

        die = new Animation( 10, false );
        die.frames( film, c+0 );

        attack = new Animation( 12, false );
        attack.frames( film, c+0 );

        idle();
    }
}