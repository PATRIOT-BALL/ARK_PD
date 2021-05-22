package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ExecutMode;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;

public class ExecutionMode extends Skill {
    public void doSkill() {
        Buff.affect(curUser, ExecutMode.class, 10);
        Buff.affect(curUser, Stamina.class,3); }
}
