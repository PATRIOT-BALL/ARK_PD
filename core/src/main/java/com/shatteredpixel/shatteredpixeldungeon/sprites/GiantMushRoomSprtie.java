package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class GiantMushRoomSprtie extends MobSprite {
    public GiantMushRoomSprtie() {
        super();

        texture(Assets.Sprites.GIANT_MUSHROOM);

        TextureFilm frames = new TextureFilm(texture, 32, 36);

        idle = new Animation(2, true);
        idle.frames(frames, 0, 0, 0);

        run = new Animation(10, true);
        run.frames(frames, 0);

        attack = new Animation(15, false);
        attack.frames(frames, 0, 0);

        die = new Animation(10, false);
        die.frames(frames, 0);

        this.turnTo(0, 0);

        play(idle);
    }
}
