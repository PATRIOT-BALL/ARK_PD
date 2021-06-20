package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SpikesBuff;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.watabou.noosa.audio.Sample;

public class Spikes extends Skill {
    @Override
    public void doSkill() {
        curUser.sprite.emitter().burst( ElmoParticle.FACTORY, 5 );
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
        Buff.affect(curUser, SpikesBuff.class, SpikesBuff.DURATION);
        curUser.spendAndNext(1f);

    }
}
