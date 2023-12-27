package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MephistoSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Skadi_mulaSprite;
import com.watabou.utils.Random;

public class SeaBoss2 extends Mob {
    {
        spriteClass = Skadi_mulaSprite.class;

        HP = HT = 1500;
        defenseSkill = 2;

        properties.add(Property.BOSS);
        immunities.add(Sleep.class);
    }

    int summoncooldown = 0;

    @Override
    protected boolean act() {
        return super.act();
    }

    // summon pos = 50, 54

    @Override
    public int attackSkill(Char target) {
        return 0;
    }

    private void SummonEnemy()
    {
        Mob summonEnemy1;
        int summonpos1 = 50;
        if (Random.Int(4) != 0)
            summonEnemy1 = new SummonRunner();
        else
            summonEnemy1 = new SummonOcto();

        Mob summonEnemy2;
        int summonpos2 = 54;
        if (Random.Int(4) != 0)
            summonEnemy2 = new SummonRunner();
        else
            summonEnemy2 = new SummonLeef();


        summonEnemy1.pos = summonpos1;
        GameScene.add(summonEnemy1, 1f);
        if (summonpos1 == Dungeon.hero.pos)  ScrollOfTeleportation.teleportChar_unobstructed(summonEnemy1);

        summonEnemy2.pos = summonpos2;
        GameScene.add(summonEnemy2, 1f);
        if (summonpos2 == Dungeon.hero.pos)  ScrollOfTeleportation.teleportChar_unobstructed(summonEnemy2);

        summoncooldown = 5;

    }

    // 보스 소환물 (3종) 한번에 2마리 소환
    // 기본 소환은 런너 (확률 75%)
    // 각각 25%확률로 Octo, Leaf가 소환될 수 있음
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
