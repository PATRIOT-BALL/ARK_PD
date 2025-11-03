package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Lens;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.WolfPack;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;

public class Panorama extends Skill {
    public void doSkill() {
        GameScene.selectCell(teleporter);
    }

    protected CellSelector.Listener teleporter = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer target) {
            if (target != null && target != curUser.pos) {
                Ballistica route = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
                int cell = route.collisionPos;

                if (Actor.findChar(target) != null && target != curUser.pos)
                    cell = route.path.get(route.dist - 1);

                Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
                Lens Lens = new Lens();
                Lens.pos = cell;
                Buff.affect(Dungeon.hero, TalismanOfForesight.CharAwareness.class, 999).charID = Lens.id();
                GameScene.add(Lens, 1f);
                CellEmitter.get( Lens.pos ).burst( Speck.factory( Speck.WOOL ), 10 );
                Dungeon.level.occupyCell(Lens);
            }

            curUser.spendAndNext( 1 );
        }

        @Override
        public String prompt() {
            return Messages.get(Panorama.class, "prompt");
        }
    };
}
