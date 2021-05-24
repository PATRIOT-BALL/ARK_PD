package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MissileSprite;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Jackinthebox extends Skill {
    private static Char throwingChar;

    public void doSkill() {
        GameScene.selectCell(Bomb);
    }

    protected CellSelector.Listener Bomb = new CellSelector.Listener() {
        @Override
        public void onSelect(Integer target) {
                if (target != null && target != curUser.pos) {
                    Ballistica route = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
                    int cell = route.collisionPos;

                    if (Actor.findChar( target ) != null && target != curUser.pos)
                        cell = route.path.get(route.dist-1);

                    final int dest = cell;

                CellEmitter.get( curUser.pos ).burst( Speck.factory( Speck.WOOL ), 10 );
                throwBomb(curUser, dest);
                updateQuickslot();


                Sample.INSTANCE.play(Assets.Sounds.SKILL_BEEP);
                Dungeon.level.occupyCell(curUser);
                Dungeon.observe();
                GameScene.updateFog();

                curUser.spendAndNext(Actor.TICK);
            }
        }

        @Override
        public String prompt() {
            return Messages.get(Jackinthebox.class, "prompt");
        }

        public boolean throwBomb(final Char thrower, final Integer target) {

            int targetCell = target;

            if (targetCell == -1) {
                return false;
            }

            final int finalTargetCell = targetCell;
            throwingChar = thrower;
                final Jackinthebox.BombAbility.BombItem item = new Jackinthebox.BombAbility.BombItem();
            thrower.sprite.zap(finalTargetCell);
            ((MissileSprite) thrower.sprite.parent.recycle(MissileSprite.class)).
                    reset(thrower.sprite,
                            finalTargetCell,
                            item,
                            new Callback() {
                                @Override
                                public void call() {
                                    if (Dungeon.level.map[finalTargetCell] != Terrain.CHASM)
                                    {
                                    item.onThrow(finalTargetCell);}
                                }
                            });
            return true;
        }
    };

    public static class BombAbility extends Buff  {

        public int bombPos = -1;
        private int timer = 3;

        private ArrayList<Emitter> smokeEmitters = new ArrayList<>();

        @Override
        public boolean act() {

            if (smokeEmitters.isEmpty()){
                fx(true);
            }

            PointF p = DungeonTilemap.raisedTileCenterToWorld(bombPos);
            if (timer == 3) {
                FloatingText.show(p.x, p.y, bombPos, "3...", CharSprite.NEUTRAL);
                PathFinder.buildDistanceMap( bombPos, BArray.not( Dungeon.level.solid, null ), 2 );
                for (int cell = 0; cell < PathFinder.distance.length; cell++) {

                    if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                        Char ch = Actor.findChar(cell);
                        if (ch != null && !(ch instanceof Shopkeeper)) {
                            Buff.prolong( ch, Cripple.class, 2 );
                        }
                    }
                }
            } else if (timer == 2){
                FloatingText.show(p.x, p.y, bombPos, "2...", CharSprite.WARNING);
            } else if (timer == 1){
                FloatingText.show(p.x, p.y, bombPos, "1...", CharSprite.NEGATIVE);
            }
            else {
                PathFinder.buildDistanceMap( bombPos, BArray.not( Dungeon.level.solid, null ), 2 );
                for (int cell = 0; cell < PathFinder.distance.length; cell++) {

                    if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                        Char ch = Actor.findChar(cell);
                        if (ch != null&& !(ch instanceof Shopkeeper)) {
                            int dmg = Random.NormalIntRange(10 + (Dungeon.hero.lvl * 2), 10 + (Dungeon.depth * 2) + (Dungeon.hero.lvl * 3));

                            if (dmg > 0) {
                                ch.damage(dmg, com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb.class);
                                Buff.prolong( ch, Paralysis.class, 15 );
                                Buff.prolong( ch, Vulnerable.class, 15 );
                            }

                            if (ch == Dungeon.hero && !ch.isAlive()) {
                                Dungeon.fail(Jackinthebox.class);
                            }
                        }

                        Heap h = Dungeon.level.heaps.get(cell);
                        if (h != null) {
                            for (Item i : h.items.toArray(new Item[0])) {
                                if (i instanceof Jackinthebox.BombAbility.BombItem) {
                                    h.remove(i);
                                }
                            }
                        }
                    }

                }
                Sample.INSTANCE.play(Assets.Sounds.BLAST);
                Sample.INSTANCE.play(Assets.Sounds.BLAST);
                detach();
                return true;
            }

            timer--;
            spend(TICK);
            return true;
        }

        @Override
        public void fx(boolean on) {
            if (on && bombPos != -1){
                PathFinder.buildDistanceMap( bombPos, BArray.not( Dungeon.level.solid, null ), 2 );
                for (int i = 0; i < PathFinder.distance.length; i++) {
                    if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                        Emitter e = CellEmitter.get(i);
                        e.pour( SmokeParticle.FACTORY, 0.25f );
                        smokeEmitters.add(e);
                    }
                }
            } else if (!on) {
                for (Emitter e : smokeEmitters){
                    e.burst(BlastParticle.FACTORY, 2);
                }
            }
        }


        public static class BombItem extends Item {
            {
                dropsDownHeap = true;
                unique = true;

                image = ItemSpriteSheet.TENGU_BOMB;
            }

            @Override
            public boolean doPickUp( Hero hero ) {
                GLog.w( Messages.get(this, "cant_pickup") );
                return false;
            }

            @Override
            protected void onThrow(int cell) {
                super.onThrow(cell);
                if (throwingChar != null){
                    Buff.append(throwingChar, Jackinthebox.BombAbility.class).bombPos = cell;
                    throwingChar = null;
                } else {
                    Buff.append(curUser, Jackinthebox.BombAbility.class).bombPos = cell;
                }
            }

            @Override
            public Emitter emitter() {
                Emitter emitter = new Emitter();
                emitter.pos(7.5f, 3.5f);
                emitter.fillTarget = false;
                emitter.pour(SmokeParticle.SPEW, 0.05f);
                return emitter;
            }
        }
}
}