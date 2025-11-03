package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GolemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HaundSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Tiacauh_BraveSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class TiacauhBrave extends Mob {
    {
        spriteClass = Tiacauh_BraveSprite.class;

        HP = HT = 165;
        defenseSkill = 18;

        EXP = 20;
        maxLvl = 37;

        loot = Generator.Category.SCROLL;
        lootChance = 0.4f;

        immunities.add(Silence.class);
        immunities.add(Terror.class);
    }

    private boolean isAttack = false;

    @Override
    public int damageRoll() {

        if (Dungeon.isChallenged(Challenges.TACTICAL_UPGRADE))  return Random.NormalIntRange( 44, 57 );
        return Random.NormalIntRange( 35, 57 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 47;
    }

    @Override
    public int drRoll()
    {
        if (!isAttack) return Random.NormalIntRange(20, 50);
        return Random.NormalIntRange(0, 18);
    }

    @Override
    public void damage(int dmg, Object src) {
        if (src == Burning.class) dmg *= 2;
        super.damage(dmg, src);
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if (!isAttack) {
        isAttack = true;
        if (Dungeon.isChallenged(Challenges.TACTICAL_UPGRADE)) Buff.affect(enemy, Hex.class, 5f);
        Buff.affect(enemy, Vulnerable.class, 3f);}
        return super.attackProc(enemy, damage);
    }

    private static final String ATTACK = "isAttack";


    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( ATTACK, isAttack );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        isAttack = bundle.getBoolean(ATTACK);
    }
}
