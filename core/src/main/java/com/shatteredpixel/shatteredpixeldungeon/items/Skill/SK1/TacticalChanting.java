package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.watabou.noosa.audio.Sample;

public class TacticalChanting extends Skill {

    public void doSkill() {
        int Value = 3 + (curUser.lvl /4);
        Buff.affect(curUser, Recharging.class, Value);
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
    }
}
