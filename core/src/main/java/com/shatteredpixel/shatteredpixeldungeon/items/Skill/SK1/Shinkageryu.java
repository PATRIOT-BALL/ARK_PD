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

public class Shinkageryu extends Skill {
    public void doSkill() {
        int dmg = curUser.HP / 2;

        curUser.HP /= 2;
        Buff.affect(curUser, Barrier.class).incShield(dmg);
        Buff.affect(curUser, Adrenaline.class, 2 + dmg / 4);

        Camera.main.shake(2, 0.25f);
        CellEmitter.get( curUser.pos ).burst( Speck.factory( Speck.BONE ), 10 );
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
    }
}
