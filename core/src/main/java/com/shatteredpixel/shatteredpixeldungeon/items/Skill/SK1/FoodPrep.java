package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Lens;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3.TerminationT;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ChargrilledMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GooBlob;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MissileSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class FoodPrep extends Skill {
    private static final String[] LINE_KEYS = {"YAM1", "YAM2"};

    public void doSkill()  { GameScene.selectCell(Food); }

    protected CellSelector.Listener Food = new CellSelector.Listener() {
        public void onSelect( Integer target ) {
            if (target != null) {
                int targetCell = target;
                Char mob = Actor.findChar(target);
                if (mob != null) {
                    int dmg = Random.NormalIntRange(3,8);
                    mob.sprite.emitter().burst( ElmoParticle.FACTORY, 5 );
                    Buff.affect(mob, Burning.class).reignite(mob);
                    Dungeon.level.drop( new ChargrilledMeat(), mob.pos ).sprite.drop( mob.pos );
                    curUser.sprite.showStatus( CharSprite.POSITIVE, Messages.get(Hero.class, Random.element( LINE_KEYS )) );
                    mob.damage(dmg, mob);
                }
                curUser.sprite.zap(targetCell);
                curUser.spendAndNext(1);
            }
        }

        @Override
        public String prompt() {
            return Messages.get(FoodPrep.class, "prompt");
        }
    };
    }
