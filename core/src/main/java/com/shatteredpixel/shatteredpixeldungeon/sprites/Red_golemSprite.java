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
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM200;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class Red_golemSprite extends MobSprite {

    public Red_golemSprite() {
        super();

        texture( Assets.Sprites.RED_GOLEM );

        TextureFilm frames = new TextureFilm( texture, 50, 50 );

        idle = new Animation( 10, true );
        idle.frames( frames, 0 );

        run = new Animation( 10, true );
        run.frames( frames, 0 );

        attack = new Animation( 25, false );
        attack.frames( frames, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 );

        die = new Animation( 8, false );
        die.frames( frames, 9, 10, 11 );

        play( idle );
    }

    @Override
    public int blood() {
        return 0xFFFFFF88;
    }

}