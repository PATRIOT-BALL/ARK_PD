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
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.RectF;

public class HeroSprite extends CharSprite {
	
	private static final int FRAME_WIDTH	= 32;
	private static final int FRAME_HEIGHT	= 34;
	
	private static final int RUN_FRAMERATE	= 20;
	
	private static TextureFilm tiers;
	
	private Animation fly;
	private Animation read;

	public HeroSprite() {
		super();

		switch (Dungeon.hero.CharSkin) {
			case 0: default:
			texture(Dungeon.hero.heroClass.spritesheet());
			break;
			case 1: texture(Assets.Sprites.TALRU_FIGHT);
			break;
			case 2: texture(Assets.Sprites.FNOVA);
			break;
			case 3: texture(Assets.Sprites.SKD); // 바병슼
			break;
			case 4: texture(Assets.Sprites.SSR); // 수수로
			break;
		}
		updateArmor();

		link( Dungeon.hero );

		if (ch.isAlive())
			idle();
		else
			die();
	}
	
	public void updateArmor() {
		switch (Dungeon.hero.CharSkin) {
			case 0: default:
				texture(Dungeon.hero.heroClass.spritesheet());
				break;
			case 1: texture(Assets.Sprites.TALRU_FIGHT);
				break;
			case 2: texture(Assets.Sprites.FNOVA);
				break;
			case 3: texture(Assets.Sprites.SKD); // 바병슼
				break;
			case 4: texture(Assets.Sprites.SSR); // 수수로
				break;
		}
		TextureFilm film = new TextureFilm(tiers(), Dungeon.hero.tier(), 32, 32);

		idle = new Animation( 1, true );
		idle.frames( film, 0 );
		
		run = new Animation( RUN_FRAMERATE, true );
		run.frames( film, 1, 2, 3, 4, 5, 6, 7, 8 );
		
		die = new Animation( 10, false );
		die.frames( film, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36 );
		
		attack = new Animation( 18, false );
		attack.frames( film, 9, 10, 11, 12, 13, 14, 15, 16, 17 );

		Sattack = new Animation(15, false);
		Sattack.frames(film, 9, 11, 14, 16);
		
		zap = attack.clone();
		
		operate = new Animation( 8, false );
		operate.frames( film, 37, 38, 37, 38 );
		
		fly = new Animation( 1, true );
		fly.frames( film, 39, 40 );

		read = new Animation( 10, false );
		read.frames( film, 18, 19, 20, 21, 22, 23, 24, 25, 25, 25 );
		
		if (Dungeon.hero.isAlive())
			idle();
		else
			die();
	}
	
	@Override
	public void place( int p ) {
		super.place( p );
		Camera.main.panTo(center(), 5f);
	}

	@Override
	public void move( int from, int to ) {
		super.move( from, to );
		if (ch != null && ch.flying) {
			play( fly );
		}
		Camera.main.panFollow(this, 20f);
	}

	@Override
	public void idle() {
		super.idle();
		if (ch != null && ch.flying) {
			play( fly );
		}
	}

	@Override
	public void jump( int from, int to, Callback callback ) {
		super.jump( from, to, callback );
		play( fly );
	}

	public void read() {
		animCallback = new Callback() {
			@Override
			public void call() {
				idle();
				ch.onOperateComplete();
			}
		};
		play( read );
	}

	@Override
	public void bloodBurstA(PointF from, int damage) {
		//Does nothing.

		/*
		 * This is both for visual clarity, and also for content ratings regarding violence
		 * towards human characters. The heroes are the only human or human-like characters which
		 * participate in combat, so removing all blood associated with them is a simple way to
		 * reduce the violence rating of the game.
		 */
	}

	@Override
	public void update() {
		sleeping = ch.isAlive() && ((Hero)ch).resting;
		
		super.update();
	}
	
	public void sprint( float speed ) {
		run.delay = 1f / speed / RUN_FRAMERATE;
	}
	
	public static TextureFilm tiers() {
		if (tiers == null) {
			SmartTexture texture = TextureCache.get( Assets.Sprites.RED );
			tiers = new TextureFilm( texture, texture.width, FRAME_HEIGHT );
		}
		
		return tiers;
	}
	
	public static Image avatar( HeroClass cl, int armorTier ) {
		
		RectF patch = tiers().get( armorTier );
		Image avatar = new Image( cl.spritesheet() );
		RectF frame = avatar.texture.uvRect( 1, 0, FRAME_WIDTH, FRAME_HEIGHT );
		frame.shift( patch.left, patch.top );
		avatar.frame( frame );
		
		return avatar;
	}

	public static Image avatar_de( HeroClass cl, int armorTier ) {

		RectF patch = tiers().get( armorTier );
		Image avatar = new Image( cl.spritesheet_de() );
		RectF frame = avatar.texture.uvRect( 1, 0, FRAME_WIDTH, FRAME_HEIGHT );
		frame.shift( patch.left, patch.top );
		avatar.frame( frame );

		return avatar;
	}
}
