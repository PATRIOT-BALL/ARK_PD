package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ExecutMode;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ReflowBuff;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.watabou.noosa.audio.Sample;

public class Reflow extends Skill {
    public void doSkill() {
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
        Buff.affect(curUser, ReflowBuff.class, 10);
    }

}
