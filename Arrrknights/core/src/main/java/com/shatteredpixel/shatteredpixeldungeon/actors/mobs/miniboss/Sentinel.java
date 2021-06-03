package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.SewerLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ImpSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SarkazSniperSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Sentinel extends Mob {
    {
        spriteClass = SarkazSniperSprite.class;

        properties.add(Property.MINIBOSS);
        properties.add(Property.SARKAZ);

        HP = HT = 14;
        defenseSkill = 1;
        baseSpeed = 1f;

        EXP = 8;
        maxLvl = -1;

        state = WANDERING;

        loot = Generator.Category.SEED;
        lootChance = 1.0f;
    }

    private boolean skill = false;

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 2);
    }

    @Override
    protected Char chooseEnemy() {
        return null;
    }

    @Override
    public int defenseProc(Char enemy, int damage) {
        if (HP >= 0 && skill != true) {
            for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
                mob.beckon(this.pos);
                Buff.affect(mob, Bless.class, 60f);
            }

            this.sprite.centerEmitter().start(Speck.factory(Speck.SCREAM), 0.3f, 3);
            Sample.INSTANCE.play(Assets.Sounds.HIT_CHAINSAW2);
            skill = true;
            GLog.w(Messages.get(this, "hit"));
        }
        return super.defenseProc(enemy, damage);
    }


    @Override
    public void die(Object cause) {
        GLog.w(Messages.get(Sentinel.class, "die"));
        Dungeon.mboss4 = 0;
        super.die(cause);
    }

    public static void spawn(SewerLevel level) {
        if ( Dungeon.depth >= 4 && !Dungeon.bossLevel()) {

            Sentinel sentinel = new Sentinel();
            do {
                sentinel.pos = level.randomRespawnCell(sentinel);
            } while (sentinel.pos == -1);
            level.mobs.add(sentinel);
        }
    }
}


