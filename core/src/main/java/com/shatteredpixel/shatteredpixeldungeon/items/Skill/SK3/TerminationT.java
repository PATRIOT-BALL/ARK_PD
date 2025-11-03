package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.Jackinthebox;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.WarriorArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfFuror;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HandclapSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MissileSprite;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class TerminationT extends Skill {

    @Override
    public void doSkill() {
        GameScene.selectCell(Shot);}

        protected CellSelector.Listener Shot = new  CellSelector.Listener() {
            @Override
            public void onSelect( Integer target ) {
                if (target != null && target != curUser.pos) {
                    int targetCell = target;

                    final int finalTargetCell = targetCell;

                    image = ItemSpriteSheet.SPIRIT_ARROW;
                    final TerminationT.SpiritArrow Arrow = new TerminationT.SpiritArrow();

                    curUser.sprite.zap(targetCell);

                    ((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class)).
                            reset(curUser.sprite,
                                    finalTargetCell,
                                    Arrow,
                                    new Callback() {
                                        @Override
                                        public void call() {
                                                Arrow.onThrow(target);
                                        }
                                    });
                }
            }

            @Override
            public String prompt() {
                return Messages.get(TerminationT.class, "prompt");
            }


    };


    public class SpiritArrow extends MissileWeapon {

        {
            image = ItemSpriteSheet.DISPLACING_DART;

            hitSound = Assets.Sounds.HIT_ARROW;
        }

        @Override
        protected void onThrow( int cell ) {
            Char enemy = Actor.findChar( cell );
            parent = null;
                Splash.at( cell, 0xCC99FFFF, 1 );
                isHit(cell);

        }

         protected void isHit(int target)
         {
             Char mob = Actor.findChar(target);
             if (mob != null) {
                 dohit(mob);
                 mob.sprite.burst(CharSprite.NEGATIVE, 10);
                 CellEmitter.center(target).burst(HandclapSprite.GooParticle.FACTORY, 60);
                 Camera.main.shake(5, 0.5f);
                 Sample.INSTANCE.play(Assets.Sounds.SKILL_CROSSBOW);
             }
             else {
                 CellEmitter.center(target).burst(HandclapSprite.GooParticle.FACTORY, 60);
                 Camera.main.shake(5, 0.5f);
                 Sample.INSTANCE.play(Assets.Sounds.SKILL_CROSSBOW);
             }

             Invisibility.dispel();
             curUser.spendAndNext(1);
        }

        @Override
        public float accuracyFactor(Char owner) {
                return Float.POSITIVE_INFINITY;
            }
        }

        public void dohit(final Char enemy) {
            Ballistica trajectory = new Ballistica(curUser.pos, enemy.pos, Ballistica.STOP_TARGET);
            trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size()-1), Ballistica.PROJECTILE);
            int dmg = Random.NormalIntRange(35 + (curUser.STR() * 5), 50 + (curUser.STR() * 5) + (enemy.HT / 5));
            WandOfBlastWave.throwChar(enemy, trajectory, 4); // 넉백 효과
            Buff.prolong( enemy, Vulnerable.class, 600 );
            Buff.prolong( enemy, Paralysis.class, 1 );
            enemy.damage(dmg, enemy);
        }
    }

