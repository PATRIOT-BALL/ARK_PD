package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MephistoSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Skadi_mulaSprite;
import com.watabou.utils.Random;

public class SeaBoss2 extends Mob {
    {
        spriteClass = Skadi_mulaSprite.class;

        HP = HT = 1000;
        defenseSkill = 2;

        properties.add(Property.BOSS);
        immunities.add(Sleep.class);
    }

    @Override
    protected boolean act() {
        return super.act();
    }

    @Override
    public int attackSkill(Char target) {
        return 0;
    }

    private void SummonEnemy()
    {

    }

    // 보스 소환물 (3종) 한번에 2마리 소환
    // 기본 소환은 런너 (확률 4/6)
    // 각각 1/6확률로 Octo, Leaf가 소환될 수 있음
    public static class SummonRunner extends SeaRunner {
        {
            state = HUNTING;

            immunities.add(Drowsy.class);
            immunities.add(MagicalSleep.class);
            immunities.add(Corruption.class);

            //no loot or exp
            maxLvl = -5;
        }
    }

    public static class SummonOcto extends Sea_Octo {
        {
            state = HUNTING;

            immunities.add(Drowsy.class);
            immunities.add(MagicalSleep.class);
            immunities.add(Corruption.class);

            //no loot or exp
            maxLvl = -5;
        }
    }

    public static class SummonLeef extends SeaLeef {
        {
            state = HUNTING;

            immunities.add(Drowsy.class);
            immunities.add(MagicalSleep.class);
            immunities.add(Corruption.class);

            //no loot or exp
            maxLvl = -5;
        }
    }
}
