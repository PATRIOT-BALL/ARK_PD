package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Kinetic;

public class PowerfulStrike extends Skill {
    public void doSkill()
    {
        int value = (curUser.STR + curUser.lvl);
        Buff.affect(curUser, Kinetic.ConservedDamage.class).setBonus(value);
    }
}
