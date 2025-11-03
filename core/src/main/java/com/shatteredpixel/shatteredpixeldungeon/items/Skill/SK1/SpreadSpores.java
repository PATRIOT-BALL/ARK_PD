package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.CorrosiveGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.HealingGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ChargrilledMeat;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MissileSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class SpreadSpores extends Skill {
    @Override
    public void doSkill() {
        GameScene.selectCell(Shot);}

    protected CellSelector.Listener Shot = new  CellSelector.Listener() {
        @Override
        public void onSelect( Integer target ) {
            if (target != null) {
                int targetCell = target;
                Char mob = Actor.findChar(target);
                if (mob != null) {
                    mob.sprite.emitter().burst( ElmoParticle.FACTORY, 5 );
                    Buff.affect(mob, Silence.class, 6f);
                    Buff.affect(mob, Roots.class, 1f);
                    CorrosiveGas gas = Blob.seed(targetCell, 40 + (Dungeon.hero.lvl * 5), CorrosiveGas.class);
                    gas.setStrength(3);
                    GameScene.add(gas);
                }
                Sample.INSTANCE.play(Assets.Sounds.GAS);
                curUser.sprite.zap(targetCell);
                curUser.spendAndNext(1);
            }
        }


        @Override
        public String prompt() {
            return Messages.get(Thoughts.class, "prompt");
        }


    };
}
