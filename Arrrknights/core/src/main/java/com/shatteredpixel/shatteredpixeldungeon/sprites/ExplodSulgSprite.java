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
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.ExplodSlug_N;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

//TODO improvements here
public class ExplodSulgSprite extends MobSprite {

	public ExplodSulgSprite() {
	}

	@Override
	public void link(Char ch) {
		super.link(ch);
		if (parent != null) {
			parent.sendToBack(this);
			if (aura != null){
				parent.sendToBack(aura);
			}
		}
	}

	@Override
	public void onComplete( Animation anim ) {
		super.onComplete( anim );

		if (anim == die) {
			Sample.INSTANCE.play(Assets.Sounds.HIT_BREAK);
			emitter().burst( BlastParticle.FACTORY, 10 );
		}
	}


	@Override
	public int blood() {
		return 0xFFBFE5B8; }


	public static class Normal extends ExplodSulgSprite {
		public Normal() {
			super();

			perspectiveRaise = 0f;

			texture( Assets.Sprites.SPINNER );

			int c = 0; // 21

			TextureFilm frames = new TextureFilm( texture, 32, 32 );

			idle = new Animation( 10, true );
			idle.frames( frames, c+20 );

			run = new Animation( 15, true );
			run.frames( frames, c+0, c+1, c+2, c+3, c+4, c+5 );

			attack = new Animation( 14, false );
			attack.frames( frames, c+6, c+7, c+8, c+9, c+10 );

			zap = attack.clone();

			die = new Animation( 18, false );
			die.frames( frames, c+10, c+11, c+12, c+13, c+14, c+15, c+16, c+17, c+18, c+19 );

			play( idle );
		}

		public void zap( int cell ) {

			turnTo( ch.pos , cell );
			play( zap );

			MagicMissile.boltFromChar( parent,
					MagicMissile.MAGIC_MISSILE,
					this,
					cell,
					new Callback() {
						@Override
						public void call() {
							((ExplodSlug_N)ch).shootWeb();
						}
					} );
			Sample.INSTANCE.play( Assets.Sounds.MISS );
		}

		@Override
		public void onComplete( Animation anim ) {
			if (anim == zap) {
				play( run );
			}
			super.onComplete( anim );
		}
	}


	public static class Elite extends ExplodSulgSprite {
		public Elite() {
			super();

			perspectiveRaise = 0f;

			texture( Assets.Sprites.SPINNER );

			int c = 21;

			TextureFilm frames = new TextureFilm( texture, 32, 32 );

			idle = new Animation( 10, true );
			idle.frames( frames, c+20 );

			run = new Animation( 15, true );
			run.frames( frames, c+0, c+1, c+2, c+3, c+4, c+5 );

			attack = new Animation( 12, false );
			attack.frames( frames, c+6, c+7, c+8, c+9, c+10 );

			zap = attack.clone();

			die = new Animation( 22, false );
			die.frames( frames, c+10, c+11, c+12, c+13, c+14, c+15, c+16, c+17, c+18, c+19 );

			play( idle );
		}
	}
}
