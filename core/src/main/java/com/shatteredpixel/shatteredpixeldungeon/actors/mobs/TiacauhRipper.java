package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.sprites.EnragedSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Tiacauh_RipperSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Tiacauh_fanaticSprite;
import com.watabou.utils.Random;

public class TiacauhRipper extends Mob {
    {
        spriteClass = Tiacauh_RipperSprite.class;

        HP = HT = 85;
        defenseSkill = 45;

        EXP = 16;
        maxLvl = 34;

        immunities.add(Silence.class);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 24, 38 );
    }

    @Override
    protected float attackDelay() {
        return super.attackDelay() * 0.4f;
    }

    @Override
    public int attackSkill( Char target ) {
        return 38;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 16);
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        int dmgbouns = enemy.drRoll() / 4;
        dmgbouns = Math.min(dmgbouns, 9);
        damage += dmgbouns;
        return super.attackProc(enemy, damage);

    }

    @Override
    public void damage(int dmg, Object src) {
        if (src == Burning.class) dmg *= 2;
        super.damage(dmg, src);
    }
}
