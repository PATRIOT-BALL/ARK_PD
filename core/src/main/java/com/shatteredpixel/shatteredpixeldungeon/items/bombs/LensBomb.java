package com.shatteredpixel.shatteredpixeldungeon.items.bombs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Oblivion;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class LensBomb extends Bomb {
    {
        image = ItemSpriteSheet.LENS_BOMB;
    }

    @Override
    public void explode(int cell) {
        super.explode(cell);

        if (Dungeon.level.heroFOV[cell]) {
            new Flare(10, 64).show(Dungeon.hero.sprite.parent, DungeonTilemap.tileCenterToWorld(cell), 2f);
        }

        ArrayList<Char> affected = new ArrayList<>();

        PathFinder.buildDistanceMap( cell, BArray.not( Dungeon.level.solid, null ), 2 );
        for (int i = 0; i < PathFinder.distance.length; i++) {
            if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                Char ch = Actor.findChar(i);
                if (ch != null) {
                    affected.add(ch);

                }
            }
        }

        for (Char ch : affected){
                ch.sprite.emitter().start( ShadowParticle.UP, 1f, 10 );
            Buff.affect(ch, Oblivion.class, Oblivion.DURATION * 2);
        }

        Sample.INSTANCE.play( Assets.Sounds.READ );
    }

    @Override
    public int value() {
        //prices of ingredients
        return quantity * (20 + 30);
    }
}
