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
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Yog;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.YogFist;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.CorrosionParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.Camera;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Callback;

public abstract class FistSprite extends MobSprite {

	private static final float SLAM_TIME	= 0.33f;

	protected int boltType;

	protected abstract int texOffset();

	private Emitter particles;
	protected abstract Emitter createEmitter();

	public FistSprite() {
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

	@Override
	public void attack( int cell ) {
		super.attack( cell );
	}

	public void zap( int cell ) {

		turnTo( ch.pos , cell );
		play( zap );

		MagicMissile.boltFromChar( parent,
				boltType,
				this,
				cell,
				new Callback() {
					@Override
					public void call() {
						//pre-0.8.0 saves
						if (ch instanceof Yog.BurningFist){
							((Yog.BurningFist)ch).onZapComplete();
						} else {
							((YogFist)ch).onZapComplete();
						}
					}
				} );
		Sample.INSTANCE.play( Assets.Sounds.ZAP );
	}

	@Override
	public void onComplete( Animation anim ) {
		super.onComplete( anim );
		if (anim == attack) {
			Camera.main.shake( 4, 0.2f );
		} else if (anim == zap) {
			idle();
		}
	}

	public static class Burning extends FistSprite {

		public Burning() {
			super();

			int c = 0;

			texture( Assets.Sprites.BLACK_SNAKE );

			TextureFilm frames = new TextureFilm( texture, 72, 44 );

			idle = new Animation( 2, true );
			idle.frames( frames, c+0 );

			run = new Animation( 8, true );
			run.frames( frames, c+0 );

			attack = new Animation( 18, false );
			attack.frames( frames, c+1, c+2, c+3, c+4, c+5, c+6, c+7, c+8, c+9, c+10, c+11 );

			zap = new Animation( 20, false );
			zap.frames( frames, c+5, c+6, c+7, c+8, c+9, c+10, c+11 );

			die = new Animation( 10, false );
			die.frames( frames, c+0 );

			play( idle );
		}

		{
			boltType = MagicMissile.FIRE;
		}

		@Override
		protected int texOffset() {
			return 0;
		}

		@Override
		protected Emitter createEmitter() {
			Emitter emitter = emitter();
			emitter.pour( FlameParticle.FACTORY, 0.06f );
			return emitter;
		}

		@Override
		public int blood() {
			return 0xFFFFDD34;
		}

	}

	public static class Soiled extends FistSprite {

		public Soiled() {
			super();

			int c = 0;

			texture( Assets.Sprites.BOSS_2 );

			TextureFilm frames = new TextureFilm( texture, 44, 40 );

			idle = new Animation( 2, true );
			idle.frames( frames, c+0 );

			run = new Animation( 8, true );
			run.frames( frames, c+0 );

			attack = new Animation( 18, false );
			attack.frames( frames, c+0 );

			zap = new Animation( 8, false );
			zap.frames( frames, c+0 );

			die = new Animation( 10, false );
			die.frames( frames, c+0 );

			play( idle );
		}

		{
			boltType = MagicMissile.FOLIAGE;
		}

		@Override
		protected int texOffset() {
			return 30;
		}

		@Override
		protected Emitter createEmitter() {
			Emitter emitter = emitter();
			emitter.pour( LeafParticle.GENERAL, 0.06f );
			return emitter;
		}

		@Override
		public int blood() {
			return 0xFF7F5424;
		}

	}

	public static class Rotting extends FistSprite {

		public Rotting() {
			super();

			int c = 0;

			texture( Assets.Sprites.BOSS_3 );

			TextureFilm frames = new TextureFilm( texture, 46, 44 );

			idle = new Animation( 2, true );
			idle.frames( frames, c+0 );

			run = new Animation( 8, true );
			run.frames( frames, c+0 );

			attack = new Animation( 18, false );
			attack.frames( frames, c+0 );

			zap = new Animation( 8, false );
			zap.frames( frames, c+0 );

			die = new Animation( 10, false );
			die.frames( frames, c+0 );

			play( idle );
		}

		{
			boltType = MagicMissile.TOXIC_VENT;
		}

		@Override
		protected int texOffset() {
			return 60;
		}

		@Override
		protected Emitter createEmitter() {
			Emitter emitter = emitter();
			emitter.pour(Speck.factory(Speck.TOXIC), 0.25f );
			return emitter;
		}

		@Override
		public int blood() {
			return 0xFFB8BBA1;
		}

	}

	public static class Rusted extends FistSprite {

		public Rusted() {
			super();

			int c = 0;

			texture( Assets.Sprites.PATRIOT );

			TextureFilm frames = new TextureFilm( texture, 72, 48 );

			idle = new Animation( 2, true );
			idle.frames( frames, c+0 );

			run = new Animation( 8, true );
			run.frames( frames, c+c+1, c+2, c+3, c+4, c+5, c+6, c+7, c+8, c+9, c+10 );

			attack = new Animation( 18, false );
			attack.frames( frames, c+0 );

			zap = new Animation( 8, false );
			zap.frames( frames, c+0 );

			die = new Animation( 10, false );
			die.frames( frames, c+0 );

			play( idle );
		}

		{
			boltType = MagicMissile.CORROSION;
		}

		@Override
		protected int texOffset() {
			return 90;
		}

		@Override
		protected Emitter createEmitter() {
			Emitter emitter = emitter();
			emitter.pour(CorrosionParticle.MISSILE, 0.06f );
			return emitter;
		}

		@Override
		public int blood() {
			return 0xFF7F7F7F;
		}

	}

	public static class Bright extends FistSprite {

		public Bright() {
			super();

			int c = 0;

			texture( Assets.Sprites.MEPHI_SINGER );

			TextureFilm frames = new TextureFilm( texture, 58, 42 );

			idle = new Animation( 2, true );
			idle.frames( frames, c+0 );

			run = new Animation( 8, true );
			run.frames( frames, c+0 );

			attack = new Animation( 18, false );
			attack.frames( frames, c+0 );

			zap = new Animation( 8, false );
			zap.frames( frames, c+0 );

			die = new Animation( 10, false );
			die.frames( frames, c+0 );

			play( idle );
		}

		{
			boltType = MagicMissile.RAINBOW;
		}

		@Override
		protected int texOffset() {
			return 120;
		}

		@Override
		protected Emitter createEmitter() {
			Emitter emitter = emitter();
			emitter.pour(SparkParticle.STATIC, 0.06f );
			return emitter;
		}

		@Override
		public void zap( int cell ) {
			turnTo( ch.pos , cell );
			play( zap );

			((YogFist)ch).onZapComplete();
			parent.add( new Beam.LightRay(center(), DungeonTilemap.raisedTileCenterToWorld(cell)));
		}
		@Override
		public int blood() {
			return 0xFFFFFFFF;
		}

	}

	public static class Dark extends FistSprite {

		public Dark() {
			super();

			int c = 0;

			texture( Assets.Sprites.EMPEROR_BLADE );

			TextureFilm frames = new TextureFilm( texture, 72, 44 );

			idle = new Animation( 9, true );
			idle.frames( frames, c+0 );

			run = new Animation( 8, true );
			run.frames( frames, c+0 );

			attack = new Animation( 18, false );
			attack.frames( frames, c+1 );

			zap = new Animation( 8, false );
			zap.frames( frames, c+1 );

			die = new Animation( 10, false );
			die.frames( frames, c+1 );

			play( idle );//이 구문이 없을경우 스프라이트 오류가 남(찔러용 그거)
		}

		{
			boltType = MagicMissile.SHADOW;
		}

		@Override
		protected int texOffset() {
			return 150;
		}

		@Override
		protected Emitter createEmitter() {
			Emitter emitter = emitter();
			emitter.pour(ShadowParticle.MISSILE, 0.06f );
			return emitter;
		}

		@Override
		public int blood() {
			return 0xFF4A2F53;
		}

	}

}
