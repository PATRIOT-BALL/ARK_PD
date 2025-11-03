package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Nervous extends Skill {
    public void doSkill() {
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]) {
                mob.sprite.centerEmitter().start( Speck.factory( Speck.BONE ), 0.3f, 3 );
                Buff.prolong(mob, Paralysis.class, 12f);
            }
        }

        identify();

        curUser.sprite.centerEmitter().start( Speck.factory( Speck.BONE ), 0.3f, 3 );
        Sample.INSTANCE.play( Assets.Sounds.HIT_WALL2 );
    }
}
