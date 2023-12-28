package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Camouflage;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Bug_ASprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Sea_LeefSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Sea_RunnerSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class SeaLeef extends Mob {
    {
        spriteClass = Sea_LeefSprite.class;

        HP = HT = 135;
        EXP = 18;
        maxLvl = 37;

        defenseSkill = 30;

        loot = Gold.class;
        lootChance = 0.28f;

        properties.add(Property.SEA);
    }

    int dmgbouns = 0;

    @Override
    public int damageRoll() { return Random.NormalIntRange(16 + (dmgbouns / 2), 24 + dmgbouns); }

    @Override
    public int attackSkill( Char target ) {
        return 37;
    }

    @Override
    protected float attackDelay() {
        return super.attackDelay() * 0.5f;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 10);
    }

    @Override
    protected boolean act() {
        if (Dungeon.level.map[this.pos] == Terrain.SEE_TEEROR1 || Dungeon.level.map[this.pos] == Terrain.SEE_TEEROR2) {
            Buff.affect(this, Camouflage.class, 10f);
        }
        return super.act();
    }

    @Override
    public int attackProc(Char enemy, int damage) {

        dmgbouns = Math.min(dmgbouns+3, 60);

        return super.attackProc(enemy, damage);
    }

    private static final String DMG = "dmgbouns";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(DMG, dmgbouns);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        dmgbouns = bundle.getInt(DMG);
    }
}
