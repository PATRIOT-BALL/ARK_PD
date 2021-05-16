package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class EarthGuardianSprite extends MobSprite {

	public EarthGuardianSprite() {
		super();

		texture( Assets.Sprites.GUARDIAN );

		TextureFilm frames = new TextureFilm( texture, 32, 32 );

		idle = new Animation( 2, true );
		idle.frames( frames, 0, 0, 0, 0 );

		run = new Animation( 15, true );
		run.frames( frames, 0 );

		attack = new Animation( 12, false );
		attack.frames( frames, 0 );

		die = new Animation( 5, false );
		die.frames( frames, 0 );

		play( idle );
	}

	@Override
	public int blood() {
		return 0xFF966400;
	}

}
