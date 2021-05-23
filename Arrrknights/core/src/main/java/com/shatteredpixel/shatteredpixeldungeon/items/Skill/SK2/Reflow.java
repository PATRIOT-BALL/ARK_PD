package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ExecutMode;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ReflowBuff;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;

public class Reflow extends Skill {
    public void doSkill() {
        Buff.affect(curUser, ReflowBuff.class, 20);
    }

}
