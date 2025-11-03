package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class FlashShield extends Skill {
    public void doSkill() {
        for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
            if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]) {
                Buff.prolong(mob, Paralysis.class, 4f);
                Buff.prolong(mob, Silence.class, 25f);
            }
        }

        identify();

        GameScene.flash(0x00C0C0C0);
        curUser.sprite.centerEmitter().start(Speck.factory(Speck.LIGHT), 0.3f, 3);
        Sample.INSTANCE.play(Assets.Sounds.SKILL_BASIC);
    }
}
