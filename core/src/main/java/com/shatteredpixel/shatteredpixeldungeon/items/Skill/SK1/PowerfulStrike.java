package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Kinetic;
import com.watabou.noosa.audio.Sample;

public class PowerfulStrike extends Skill {
    public void doSkill()
    {
        int value = (curUser.STR * 2 + curUser.lvl) - 12;
        Buff.affect(curUser, Kinetic.ConservedDamage.class).setBonus(value);
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
    }
}
