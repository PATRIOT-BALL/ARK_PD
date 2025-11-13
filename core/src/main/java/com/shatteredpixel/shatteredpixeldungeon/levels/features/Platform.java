package com.shatteredpixel.shatteredpixeldungeon.levels.features;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.levels.CityLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.SeaBossLevel2;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.List;

public abstract class Platform implements Bundlable {

    public String platformName = Messages.get(this, "name");

    public int image;
    public int pos;

    protected Class<? extends Platform.Generator> platformClass;

    public void trigger() {

        Char ch = Actor.findChar(pos);
        activate(ch);
    }

    public abstract void activate( Char ch );

    public void destroy() {
        Dungeon.level.destroyPlatform( pos );

        if (Dungeon.level.heroFOV[pos]) {
            CellEmitter.get( pos ).burst(FlameParticle.FACTORY, 6);
        }
    }

    private static final String POS = "pos";

    @Override
    public void restoreFromBundle(Bundle bundle) {
        pos = bundle.getInt( POS );
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        bundle.put( POS, pos );
    }

    public String desc() {
        String desc = Messages.get(this, "desc");
        return desc;
    }

    public static class Generator extends Item {

        {
            stackable = true;
            defaultAction = AC_THROW;
        }

        protected Class<? extends Platform> platformClass;

        public List<Platform> generate(int pos, Level level ) {
            if (level != null && level.heroFOV != null && level.heroFOV[pos]) {
                Sample.INSTANCE.play(Assets.Sounds.GRASS);
            }

            List<Platform> platforms = new ArrayList<>();
            for (int n : PathFinder.NEIGHBOURS9) {
                int c = pos + n;
                if (c >= 0 && c < Dungeon.level.length()) {
                    if (Dungeon.level.heroFOV[c]) {
                        CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
                    }
                }

                Platform platform = Reflection.newInstance(platformClass);
                platform.pos = c;
                platforms.add(platform);
            }
            return platforms;
        }

        @Override
        public boolean isUpgradable() {
            return false;
        }

        @Override
        public boolean isIdentified() {
            return true;
        }

        @Override
        public int value() {
            return 5 * quantity;
        }

        @Override
        public String desc() {
            String desc = Messages.get(platformClass, "desc");
            return desc;
        }

        @Override
        public String info() {
            return Messages.get( Platform.class, "info", desc() );
        }
    }
}
