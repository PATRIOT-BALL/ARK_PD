package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.watabou.noosa.audio.Sample;

public class Camouflage extends Skill {
    public void doSkill() {
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
        Buff.affect(curUser, Healing.class).setHeal(curUser.HT / 10, 0.25f, 1);
        Buff.affect(curUser, Invisibility.class, Invisibility.DURATION/6f);

        CellEmitter.get(curUser.pos).burst(Speck.factory(Speck.BUBBLE), 10);

    }
}
