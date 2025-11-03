package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ReflowBuff;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;

public class BreaktheDawn extends Skill {
    public void doSkill() {
        Buff.affect(curUser, BreakBuff.class, 20);
        GameScene.flash( 0x80FFFFFF );
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
        curUser.spendAndNext( 1 );
    }

    public static class BreakBuff extends FlavourBuff {
    }
}
