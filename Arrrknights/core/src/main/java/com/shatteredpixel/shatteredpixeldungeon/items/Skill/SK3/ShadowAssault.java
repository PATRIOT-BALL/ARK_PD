package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.WarriorArmor;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

public class ShadowAssault extends Skill {
    public void doSkill() {
        GameScene.selectCell(leaper);
    }

    protected CellSelector.Listener leaper = new  CellSelector.Listener() {

        @Override
        public void onSelect( Integer target ) {
            if (target != null && target != curUser.pos) {

                Ballistica route = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
                int cell = route.collisionPos;

                //can't occupy the same cell as another char, so move back one.
                if (Actor.findChar( cell ) != null && cell != curUser.pos)
                    cell = route.path.get(route.dist-1);

                updateQuickslot();

                final int dest = cell;
                curUser.busy();
                curUser.sprite.jump(curUser.pos, cell, new Callback() {
                    @Override
                    public void call() {
                        curUser.move(dest);
                        Dungeon.level.occupyCell(curUser);
                        Dungeon.observe();
                        GameScene.updateFog();


                        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                            Char mob = Actor.findChar(curUser.pos + PathFinder.NEIGHBOURS8[i]);
                            if (mob != null && mob != curUser && mob.alignment != Char.Alignment.ALLY) {
                                curUser.sprite.attack(mob.pos, new Callback() {
                                    @Override
                                    public void call() {
                                        Sample.INSTANCE.play(Assets.Sounds.HIT_SLASH);
                                        mob.sprite.burst(CharSprite.NEGATIVE, 10);
                                        for (int a = 0; a<10; a+=1) {
                                            doAttack(mob);
                                        }
                                    }
                                });
                            }
                        }

                        CellEmitter.center(dest).burst(Speck.factory(Speck.WOOL), 10);
                        Buff.affect(curUser, Recharging.class, 10);
                        Buff.affect(curUser, Bless.class, 60);
                        Camera.main.shake(2, 0.5f);

                        Invisibility.dispel();
                        curUser.spendAndNext(1);
                    }
                });
            }
        }

        private void doAttack(final Char enemy){
            if (enemy.isAlive()) curUser.attack(enemy);
        }

        @Override
        public String prompt() {
            return Messages.get(WarriorArmor.class, "prompt");
        }
    };
}
