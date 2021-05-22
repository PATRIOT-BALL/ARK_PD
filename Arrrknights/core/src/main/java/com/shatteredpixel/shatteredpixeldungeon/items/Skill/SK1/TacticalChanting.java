package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;

public class TacticalChanting extends Skill {

    public void doSkill() {
        int Value = 10 + (curUser.lvl /2);
        Buff.affect(curUser, Recharging.class, Value);
    }
}
