package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Door;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.TargetHealthIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class DeepSeaPredators extends Skill {
    public void doSkill() { GameScene.selectCell(Slash); }

    protected CellSelector.Listener Slash = new CellSelector.Listener() {
        public void onSelect(Integer target) {
            if (target != null) {
                Char mob = Actor.findChar(target);
                if (mob != null) {
                    Dungeon.hero.sprite.zap(mob.pos);

                    int dmg = Dungeon.hero.STR * 4 + 12;
                    mob.damage(Random.NormalIntRange(dmg, dmg*2), this);
                    mob.sprite.burst(CharSprite.NEGATIVE, 35);
                    Camera.main.shake(1, 0.5f);
                    CellEmitter.get( curUser.pos ).burst( Speck.factory( Speck.WOOL ), 12 );
                    Sample.INSTANCE.play(Assets.Sounds.HIT_SLASH, 1.32f);


                    Ballistica trajectory = new Ballistica(Dungeon.hero.pos, mob.pos, Ballistica.STOP_TARGET);
                    trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
                    moveChar(Dungeon.hero, trajectory, 1, mob.pos, false, false); // 자신이 이동효과

                    if (!mob.isAlive()) Buff.affect(Dungeon.hero, Adrenaline.class, 4f);

                    Dungeon.hero.spendAndNext(1f);
                }

            }
        }


        @Override
        public String prompt() {
            return Messages.get(DeepSeaPredators.class, "prompt");
        }
    };


    private void moveChar(final Char ch, final Ballistica trajectory, int power, int enemypos,
                          boolean closeDoors, boolean collideDmg){
        if (ch.properties().contains(Char.Property.BOSS)) {
            power /= 2;
        }

        int dist = Math.min(trajectory.dist, power);

        boolean collided = dist == trajectory.dist;

        if (dist == 0 || ch.properties().contains(Char.Property.IMMOVABLE)) return;

        //large characters cannot be moved into non-open space
        if (Char.hasProp(ch, Char.Property.LARGE)) {
            for (int i = 1; i <= dist; i++) {
                if (!Dungeon.level.openSpace[trajectory.path.get(i)]){
                    dist = i-1;
                    collided = true;
                    break;
                }
            }
        }

        if (Actor.findChar(trajectory.path.get(dist)) != null){
            dist--;
            collided = true;
        }

        if (dist < 0) return;

        final int newPos = trajectory.path.get(dist);

        if (newPos == enemypos) return;

        final int finalDist = dist;
        final boolean finalCollided = collided && collideDmg;
        final int initialpos = ch.pos;

        Actor.addDelayed(new Pushing(ch, ch.pos, newPos, new Callback() {
            public void call() {
                if (initialpos != ch.pos) {
                    //something caused movement before pushing resolved, cancel to be safe.
                    ch.sprite.place(ch.pos);
                    return;
                }
                int oldPos = ch.pos;
                ch.pos = newPos;
                if (finalCollided && ch.isAlive()) {
                    ch.damage(Random.NormalIntRange(finalDist, 2*finalDist), this);
                    Paralysis.prolong(ch, Paralysis.class, 1 + finalDist/2f);
                }
                if (closeDoors && Dungeon.level.map[oldPos] == Terrain.OPEN_DOOR){
                    Door.leave(oldPos);
                }
                Dungeon.level.occupyCell(ch);
                if (ch == Dungeon.hero){
                    //FIXME currently no logic here if the throw effect kills the hero
                    Dungeon.observe();
                }
            }
        }), -1);
    }
}
