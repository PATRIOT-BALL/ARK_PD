package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RadiantKnight;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;

public class ShiningSun extends Skill {
    public void doSkill() {
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]) {
                Buff.affect(mob, Vulnerable.class, 40f);
            }
        }

        if (Dungeon.hero.buff(RadiantKnight.class) != null) {
            Buff.detach(Dungeon.hero, RadiantKnight.class);
            Buff.affect(Dungeon.hero, RadiantKnight.class, RadiantKnight.DURATION);
        }
        else {
            Buff.affect(Dungeon.hero, RadiantKnight.class, RadiantKnight.DURATION);
        }

        identify();
        GameScene.flash( 0x80FFFFFF );
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BABYNIGHT );
    }
}
