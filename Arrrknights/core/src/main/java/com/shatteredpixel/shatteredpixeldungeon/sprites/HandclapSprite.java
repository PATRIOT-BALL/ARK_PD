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
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.Emitter.Factory;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class HandclapSprite extends MobSprite {
	
	private Animation pump;
	private Animation pumpAttack;

	private Emitter spray;
	private ArrayList<Emitter> pumpUpEmitters = new ArrayList<>();

	public HandclapSprite() {
		super();
		
		texture( Assets.Sprites.HANDCLAP );
		
		TextureFilm frames = new TextureFilm( texture, 56, 46 );
		
		idle = new Animation( 10, true );
		idle.frames( frames, 0, 1, 2, 3, 4, 5, 6, 0, 0, 0, 0, 0 );
		
		run = new Animation( 12, true );
		run.frames( frames, 7, 8, 9, 10, 11, 12, 13, 14 );
		
		pump = new Animation( 20, true );
		pump.frames( frames, 15, 16, 17, 18, 19 );

		pumpAttack = new Animation ( 16, false );
		pumpAttack.frames( frames, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27);

		attack = new Animation( 14, false );
		attack.frames( frames, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27 );
		
		die = new Animation( 8, false );
		die.frames( frames, 28, 29, 30, 31, 32, 33, 34, 35, 36 );
		
		play(idle);

		spray = centerEmitter();
		spray.autoKill = false;
		spray.pour( GooParticle.FACTORY, 0.04f );
		spray.on = false;
	}

	@Override
	public void link(Char ch) {
		super.link(ch);
		if (ch.HP*2 <= ch.HT)
			spray(true);
	}

	public void pumpUp( int warnDist ) {
		if (warnDist == 0){
			clearEmitters();
		} else {
			play(pump);
			Sample.INSTANCE.play( Assets.Sounds.CHARGEUP, 1f, warnDist == 1 ? 0.8f : 1f );
			PathFinder.buildDistanceMap(ch.pos, BArray.not(Dungeon.level.solid, null), 2);
			for (int i = 0; i < PathFinder.distance.length; i++) {
				if (PathFinder.distance[i] <= warnDist) {
					Emitter e = CellEmitter.get(i);
					e.pour(GooParticle.FACTORY, 0.04f);
					pumpUpEmitters.add(e);
				}
			}
		}
	}

	public void clearEmitters(){
		for (Emitter e : pumpUpEmitters){
			e.on = false;
		}
		pumpUpEmitters.clear();
	}

	public void triggerEmitters(){
		for (Emitter e : pumpUpEmitters){
			e.burst(ElmoParticle.FACTORY, 10);
		}
		Sample.INSTANCE.play( Assets.Sounds.BURNING );
		pumpUpEmitters.clear();
	}

	public void pumpAttack() { play(pumpAttack); }

	@Override
	public void play(Animation anim) {
		if (anim != pump && anim != pumpAttack){
			clearEmitters();
		}
		super.play(anim);
	}

	@Override
	public int blood() {
		return 0xFF000000;
	}

	public void spray(boolean on){
		spray.on = on;
	}

	@Override
	public void update() {
		super.update();
		spray.pos(center());
		spray.visible = visible;
	}

	public static class GooParticle extends PixelParticle.Shrinking {

		public static final Emitter.Factory FACTORY = new Factory() {
			@Override
			public void emit( Emitter emitter, int index, float x, float y ) {
				((GooParticle)emitter.recycle( GooParticle.class )).reset( x, y );
			}
		};

		public GooParticle() {
			super();

			color( 0x000000 );
			lifespan = 0.3f;

			acc.set( 0, +50 );
		}

		public void reset( float x, float y ) {
			revive();

			this.x = x;
			this.y = y;

			left = lifespan;

			size = 4;
			speed.polar( -Random.Float( PointF.PI ), Random.Float( 32, 48 ) );
		}

		@Override
		public void update() {
			super.update();
			float p = left / lifespan;
			am = p > 0.5f ? (1 - p) * 2f : 1;
		}
	}

	@Override
	public void onComplete( Animation anim ) {
		super.onComplete(anim);

		if (anim == pumpAttack) {

			triggerEmitters();

			idle();
			ch.onAttackComplete();
		} else if (anim == die) {
			spray.killAndErase();
		}
	}
}
