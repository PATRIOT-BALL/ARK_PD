package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
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
        defenseSkill = 60;

        state = PASSIVE;

        properties.add(Property.BOSS);
        properties.add(Property.IMMOVABLE);
        immunities.add( Paralysis.class );
        immunities.add( Amok.class );
        immunities.add( Sleep.class );
        immunities.add( Terror.class );
        immunities.add( Vertigo.class );
    }

    int summoncooldown = 0;

    @Override
    public void beckon(int cell) {
        //do nothing
    }


    @Override
    public void damage(int dmg, Object src) {

        if (src != this) dmg = 0;

        super.damage(dmg, src);
    }

    @Override
    protected boolean act() {

        if (summoncooldown <= 0) SummonEnemy();
        else summoncooldown--;

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
        int summonpos1 = 71;
        if (Random.Int(4) != 0)
            summonEnemy1 = new SummonRunner();
        else
            summonEnemy1 = new SummonOcto();

        Mob summonEnemy2;
        int summonpos2 = 75;
        if (Random.Int(4) != 0)
            summonEnemy2 = new SummonRunner();
        else
            summonEnemy2 = new SummonLeef();

        Mob summonEnemy3;
        int summonpos3 = 170;
        if (Random.Int(4) != 0)
            summonEnemy3 = new SummonRunner();
        else
            summonEnemy3 = new SummonLeef();

        Mob summonEnemy4;
        int summonpos4 = 187;
        if (Random.Int(4) != 0)
            summonEnemy4 = new SummonRunner();
        else
            summonEnemy4 = new SummonOcto();


        summonEnemy1.pos = summonpos1;
        GameScene.add(summonEnemy1, 1f);
        if (summonpos1 == Dungeon.hero.pos)  ScrollOfTeleportation.teleportChar_unobstructed(summonEnemy1);

        summonEnemy2.pos = summonpos2;
        GameScene.add(summonEnemy2, 1f);
        if (summonpos2 == Dungeon.hero.pos)  ScrollOfTeleportation.teleportChar_unobstructed(summonEnemy2);

        summonEnemy3.pos = summonpos3;
        GameScene.add(summonEnemy3, 1f);
        if (summonpos3 == Dungeon.hero.pos)  ScrollOfTeleportation.teleportChar_unobstructed(summonEnemy3);

        summonEnemy4.pos = summonpos4;
        GameScene.add(summonEnemy4, 1f);
        if (summonpos4 == Dungeon.hero.pos)  ScrollOfTeleportation.teleportChar_unobstructed(summonEnemy4);

        for (Mob mob : Dungeon.level.mobs) {
            mob.beckon( Dungeon.hero.pos );
        }

        this.damage(250,this);
        summoncooldown = 10;

    }

    // 보스 소환물 (3종) 한번에 4마리 소환
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
