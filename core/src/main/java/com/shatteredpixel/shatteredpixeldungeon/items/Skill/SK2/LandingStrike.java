package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.CursingTrap;
import com.watabou.noosa.audio.Sample;

public class LandingStrike extends Skill {
    public void doSkill() {
        CursingTrap.curse(curUser);
        int curs = 0;
        if (curUser.belongings.weapon != null) if(curUser.belongings.weapon.cursed) curs++;
        if (curUser.belongings.armor != null) if(curUser.belongings.armor.cursed) curs++;
        if (curUser.belongings.artifact != null) if(curUser.belongings.artifact.cursed) curs++;
        if (curUser.belongings.ring != null) if(curUser.belongings.ring.cursed) curs++;
        if (curUser.belongings.misc != null)  if(curUser.belongings.misc.cursed) curs++;

        Buff.affect(curUser, Bless.class, 150 + curs * 250f);

        CellEmitter.get( curUser.pos ).start( ShadowParticle.CURSE, 0.05f, 10 );
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
    }
}
