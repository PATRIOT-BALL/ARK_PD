package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;

public class SoulAbsorption extends Skill {
    public void doSkill() {

        Buff.affect(curUser, SoulBuff.class);

        CellEmitter.get( curUser.pos ).burst( Speck.factory( Speck.STEAM ), 10 );
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
    }


    public static class SoulBuff extends Buff {
    }
}
