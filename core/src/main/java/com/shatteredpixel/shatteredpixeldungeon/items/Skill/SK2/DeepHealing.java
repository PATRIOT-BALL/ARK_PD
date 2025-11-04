package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class DeepHealing extends Skill {
    public void doSkill() {

        Dungeon.hero.HP = Dungeon.hero.HT;
        Buff.affect(Dungeon.hero, Healing.class).setHeal(Dungeon.hero.HT, 0.01f, 1);
        identify();

        curUser.sprite.centerEmitter().start( Speck.factory( Speck.HEALING ), 0.75f, 3 );
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );

        Dungeon.hero.SK2 = null;
        Dungeon.hero.SetSkill2Num(0);
    }
}
