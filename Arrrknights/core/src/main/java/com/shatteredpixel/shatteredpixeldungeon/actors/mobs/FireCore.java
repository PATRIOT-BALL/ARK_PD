package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfBlink;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SpawnerSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class FireCore extends Mob{
    {
        spriteClass = SpawnerSprite.class;

        HP = HT = 160;
        defenseSkill = 0;

        EXP = 40;
        maxLvl = 38;

        state = PASSIVE;

        loot = Generator.Category.POTION;
        lootChance = 1f;

        properties.add(Property.IMMOVABLE);
        properties.add(Property.MINIBOSS);
    }

    public boolean spawnRecorded = false;

    @Override
    public int defenseProc(Char enemy, int damage) {
        Buff.affect(enemy, Burning.class).reignite(enemy);
        return super.defenseProc(enemy, damage);
    }

    @Override
    protected boolean act() {
        if (!spawnRecorded) {
            Statistics.coreAlive++;
            spawnRecorded = true;
        }
        return super.act();
    }

    @Override
    public void die(Object cause) {
        if (spawnRecorded){
            Statistics.coreAlive--;
        }
        GLog.h(Messages.get(this, "on_death"));
        super.die(cause);
    }
}
