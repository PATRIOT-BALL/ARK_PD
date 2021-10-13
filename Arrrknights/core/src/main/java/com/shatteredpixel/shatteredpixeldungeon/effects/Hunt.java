package com.shatteredpixel.shatteredpixeldungeon.effects;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.Visual;

public class Hunt extends Image {
    private static final float TIME_TO_FADE = 1.35f;

    private float time;

    public Hunt() {
        super( Effects.get( Effects.Type.WOUND ) );
        hardlight(1f, 0f, 0);
        origin.set( width / 2, height / 2 );
    }

    public void reset( int p ) {
        revive();

        x = (p % Dungeon.level.width()) * DungeonTilemap.SIZE + (DungeonTilemap.SIZE - width) / 2;
        y = (p / Dungeon.level.width()) * DungeonTilemap.SIZE + (DungeonTilemap.SIZE - height) / 2;

        time = TIME_TO_FADE;
    }

    public void reset(Visual v) {
        revive();

        point(v.center(this));

        time = TIME_TO_FADE;
    }

    @Override
    public void update() {
        super.update();

        if ((time -= Game.elapsed) <= 0) {
            kill();
        } else {
            float p = time / TIME_TO_FADE;
            alpha( p );
            scale.x = 1;
            scale.y = 1.5f - p;
            angle = 90f;
        }
    }

    public static void hit( Char ch ) {
        hit( ch, 0 );
    }

    public static void hit( Char ch, float angle ) {
        if (ch.sprite.parent != null) {
            Hunt w = (Hunt) ch.sprite.parent.recycle(Hunt.class);
            ch.sprite.parent.bringToFront(w);
            w.reset(ch.sprite);
            w.angle = angle;
        }
    }

    public static void hit( int pos ) {
        hit( pos, 0 );
    }

    public static void hit( int pos, float angle ) {
        Group parent = Dungeon.hero.sprite.parent;
        Hunt w = (Hunt)parent.recycle( Hunt.class );
        parent.bringToFront( w );
        w.reset( pos );
        w.angle = angle;
    }
}
