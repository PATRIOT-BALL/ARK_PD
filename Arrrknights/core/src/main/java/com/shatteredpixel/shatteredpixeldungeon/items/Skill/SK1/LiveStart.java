package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;

public class LiveStart extends Skill {
    public void doSkill() {
        for (Mob mob : Dungeon.level.mobs) {
            if (mob.alignment != Char.Alignment.ALLY && mob.state != mob.PASSIVE) {
                mob.beckon(Dungeon.hero.pos);
                Buff.affect(mob, LiveBuff.class, 300f);
            }
        }
        curUser.sprite.centerEmitter().start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
        Sample.INSTANCE.play( Assets.Sounds.SKILL_SORA );
        GameScene.flash( 0x80FFFFFF );

        Invisibility.dispel();
        identify();
        curUser.spendAndNext(1f);
    }


    public static class LiveBuff extends FlavourBuff {
    }
}
