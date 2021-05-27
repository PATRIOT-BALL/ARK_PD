package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Chains;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.EtherealChains;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.sun.net.httpserver.Filter;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class ChainHook extends Skill {
    public void doSkill() {
        GameScene.selectCell(caster);}

    private CellSelector.Listener caster = new CellSelector.Listener(){

        @Override
        public void onSelect(Integer target) {
            if (target != null && (Dungeon.level.visited[target] || Dungeon.level.mapped[target])){

                //chains cannot be used to go where it is impossible to walk to
                PathFinder.buildDistanceMap(target, BArray.or(Dungeon.level.passable, Dungeon.level.avoid, null));
                if (PathFinder.distance[curUser.pos] == Integer.MAX_VALUE){
                    GLog.w( Messages.get(ChainHook.class, "fail") );
                    Buff.affect(curUser, Stamina.class, 3f);
                    return;
                }

                final Ballistica chain = new Ballistica(curUser.pos, target, Ballistica.STOP_TARGET);

                if (Actor.findChar( chain.collisionPos ) != null){
                    chainEnemy( chain, curUser, Actor.findChar( chain.collisionPos ));
                } else {
                    chainLocation( chain, curUser );
                }
                throwSound();
                Sample.INSTANCE.play( Assets.Sounds.CHAINS );
            } Buff.affect(curUser, Stamina.class, 3f);

        }

        @Override
        public String prompt() {
            return Messages.get(ChainHook.class, "prompt");
        }
    };

    //pulls an enemy to a position along the chain's path, as close to the hero as possible
    private void chainEnemy(Ballistica chain, final Hero hero, final Char enemy ){

        if (enemy.properties().contains(Char.Property.IMMOVABLE)) {
            int dmg = Random.NormalIntRange(4, curUser.STR * 2);
            enemy.damage(dmg,enemy);
        }

        int bestPos = -1;
        for (int i : chain.subPath(1, chain.dist)){
            //prefer to the earliest point on the path
            if (!Dungeon.level.solid[i]
                    && Actor.findChar(i) == null
                    && (!Char.hasProp(enemy, Char.Property.LARGE) || Dungeon.level.openSpace[i])){
                bestPos = i;
                break;
            }
        }

        if (bestPos == -1) {
            GLog.i(Messages.get(this, "does_nothing"));
            return;
        }

        final int pulledPos = bestPos;

        hero.busy();
        hero.sprite.parent.add(new Chains(hero.sprite.center(), enemy.sprite.center(), new Callback() {
            public void call() {
                Actor.add(new Pushing(enemy, enemy.pos, pulledPos, new Callback() {
                    public void call() {
                        enemy.pos = pulledPos;
                        Dungeon.level.occupyCell(enemy);
                        Dungeon.observe();
                        GameScene.updateFog();
                        hero.spendAndNext(1f);
                    }
                }));
                int dmg = Random.NormalIntRange(4, 6 + curUser.STR);
                enemy.damage(dmg,enemy);
                Buff.affect(enemy, Paralysis.class, 2f);
                hero.next();
            }
        }));
    }

    //pulls the hero along the chain to the collisionPos, if possible.
    private void chainLocation( Ballistica chain, final Hero hero ){

        //don't pull if rooted
        if (hero.rooted){
            GLog.w( Messages.get(ChainHook.class, "fail") );
            Buff.affect(curUser, Stamina.class, 6f);
            return;
        }

        //don't pull if the collision spot is in a wall
        if (Dungeon.level.solid[chain.collisionPos]){
            GLog.i( Messages.get(ChainHook.class, "fail"));
            Buff.affect(curUser, Stamina.class, 6f);
            return;
        }

        //don't pull if there are no solid objects next to the pull location
        boolean solidFound = false;
        for (int i : PathFinder.NEIGHBOURS8){
            if (Dungeon.level.solid[chain.collisionPos + i]){
                solidFound = true;
                break;
            }
        }
        if (!solidFound){
            GLog.i( Messages.get(ChainHook.class, "fail") );
            Buff.affect(curUser, Stamina.class, 6f);
            return;
        }

        final int newHeroPos = chain.collisionPos;

        hero.busy();
        hero.sprite.parent.add(new Chains(hero.sprite.center(), DungeonTilemap.raisedTileCenterToWorld(newHeroPos), new Callback() {
            public void call() {
                Actor.add(new Pushing(hero, hero.pos, newHeroPos, new Callback() {
                    public void call() {
                        hero.pos = newHeroPos;
                        Dungeon.level.occupyCell(hero);
                        hero.spendAndNext(1f);
                        Dungeon.observe();
                        GameScene.updateFog();
                    }
                }));
                Buff.affect(hero, Stamina.class, 3f);
                hero.next();
            }
        }));
    }
}
