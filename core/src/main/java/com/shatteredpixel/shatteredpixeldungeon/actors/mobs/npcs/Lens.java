package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ActiveOriginium;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CivilianSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LensSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Lens extends NPC {

    private static final String[] LINE_KEYS = {"T1", "T2", "T3", "T4"};

    {
        spriteClass = LensSprite.class;
        baseSpeed = 2f;
        state = WANDERING;
        flying = false;

        viewDistance = 5;
    }

    public Lens() {
        super();
        HT=HP = 45;
        defenseSkill = 5 + Dungeon.hero.lvl;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 1);
    }

    @Override
    protected Char chooseEnemy() {
        return null;
    }

    @Override
    protected boolean act() {

        damage(1, this);
        PathFinder.buildDistanceMap(pos, BArray.not(Dungeon.level.solid, null), 4);
        for (int cell = 0; cell < PathFinder.distance.length; cell++) {
            if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                if (!Dungeon.level.insideMap(cell)) {
                    continue;
                }
                    if (Dungeon.level.discoverable[cell])
                        Dungeon.level.mapped[cell] = true;

                    int terr = Dungeon.level.map[cell];
                    if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

                        Dungeon.level.discover(cell);

                        GameScene.discoverTile(cell, terr);
                        ScrollOfMagicMapping.discover(cell);


                }

            }}
        GameScene.updateFog();
        return super.act();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public boolean interact(Char c) {
        sprite.attack(0);
        sprite.showStatus( CharSprite.NEUTRAL, Messages.get(this, Random.element( LINE_KEYS )) );
        return true;
    }
}
