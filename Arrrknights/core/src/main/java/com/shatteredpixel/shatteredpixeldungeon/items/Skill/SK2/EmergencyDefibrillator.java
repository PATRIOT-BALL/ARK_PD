package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.watabou.noosa.audio.Sample;

public class EmergencyDefibrillator extends Skill {
    public void doSkill() {
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
        Buff.affect(curUser, ArcaneArmor.class).set(curUser.lvl, 8);
        Buff.affect(curUser, Healing.class).setHeal((curUser.HT / 4),0.45f,0);
    }
}
