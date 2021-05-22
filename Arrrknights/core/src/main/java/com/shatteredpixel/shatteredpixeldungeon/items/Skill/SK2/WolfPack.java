package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.RogueArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class WolfPack extends Skill {
    public void doSkill() {
        GameScene.selectCell(teleporter);
    }

    protected CellSelector.Listener teleporter = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer target) {
            if (target != null) {

                PathFinder.buildDistanceMap(curUser.pos, BArray.not(Dungeon.level.solid, null), 8);

                if (PathFinder.distance[target] == Integer.MAX_VALUE ||
                        !Dungeon.level.heroFOV[target] ||
                        Actor.findChar(target) != null) {

                    GLog.w(Messages.get(WolfPack.class, "fov"));
                    return;
                }

                updateQuickslot();

                Camera.main.shake(2, 0.5f);
                CellEmitter.get( curUser.pos ).burst( Speck.factory( Speck.WOOL ), 10 );
                ScrollOfTeleportation.appear( curUser, target );

                for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
                    if (Dungeon.level.adjacent(mob.pos, curUser.pos) && mob.alignment != Char.Alignment.ALLY) {
                        dohit(mob);
                        Buff.prolong( mob, Paralysis.class, 2 );
                        if (mob.state == mob.HUNTING) mob.state = mob.WANDERING;
                        mob.sprite.emitter().burst( Speck.factory( Speck.LIGHT ), 4 );
                    }
                }
                Buff.affect(curUser, Invisibility.class, Invisibility.DURATION/5f);

                Sample.INSTANCE.play( Assets.Sounds.PUFF );
                Dungeon.level.occupyCell(curUser );
                Dungeon.observe();
                GameScene.updateFog();

                curUser.spendAndNext( Actor.TICK );
            }
        }

        public void dohit(final Char enemy) {
            int dmg = Random.NormalIntRange(curUser.STR(), (curUser.STR() * 4) - 25);
            enemy.damage(dmg, enemy);
        }

        @Override
        public String prompt() {
            return Messages.get(WolfPack.class, "prompt");
        }
    };
}
