package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ExecutMode;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.watabou.noosa.audio.Sample;

public class ExecutionMode extends Skill {
    public void doSkill() {
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
        Buff.affect(curUser, ExecutMode.class, 15);
        Buff.affect(curUser, Stamina.class,4); }
}
