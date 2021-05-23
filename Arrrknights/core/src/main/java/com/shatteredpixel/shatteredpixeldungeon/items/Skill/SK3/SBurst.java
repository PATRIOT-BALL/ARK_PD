package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SeethingBurst;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;

public class SBurst extends Skill {
    public void doSkill() {
        Buff.affect(curUser, SeethingBurst.class, 15);
    }
}
