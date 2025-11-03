package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.SmokeScreen;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;

public class CoverSmoke extends Skill {
    public void doSkill() {
        identify();
        Sample.INSTANCE.play(Assets.Sounds.SHATTER);
        Sample.INSTANCE.play(Assets.Sounds.SKILL_BASIC);

        Buff.affect(curUser, Invisibility.class, Invisibility.DURATION/3f);
        Buff.affect(curUser, Stamina.class, 5f);
        GameScene.add(Blob.seed(Dungeon.hero.pos, 600, SmokeScreen.class));

        curUser.spendAndNext(1f);
    }

}
