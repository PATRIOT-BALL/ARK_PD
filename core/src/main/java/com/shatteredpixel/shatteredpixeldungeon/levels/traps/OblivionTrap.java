package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hallucination;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Oblivion;
import com.watabou.noosa.audio.Sample;

public class OblivionTrap extends Trap {
    {
        color = 2;
        shape = SP1;

        canBeHidden = true;
    }


    @Override
    public void activate() {

        Char c = Actor.findChar( pos );

        if (c != null && !c.flying) {
            Buff.affect(c, Oblivion.class, Oblivion.DURATION);
        }

        if (Dungeon.level.heroFOV[pos]) {
            Sample.INSTANCE.play( Assets.Sounds.TRAP );
        }

    }
}
