package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;

public class NigetRaid extends Skill {
    public void doSkill() {
        Buff.affect(curUser, Invisibility.class, Invisibility.DURATION/2f);
        Buff.affect(curUser, ArtifactRecharge.class).set(40);
        Buff.affect(curUser, Stamina.class,10);
    }
}
