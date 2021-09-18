package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BugSprite;
import com.watabou.utils.Random;

public class LavaSlug extends Mob {
    {
        spriteClass = BugSprite.class;

        HP = HT = 150;
        defenseSkill = 27;

        maxLvl = 34;
        EXP = 19;
        immunities.add(Silence.class);
        immunities.add(Burning.class);
        immunities.add(WandOfFireblast.class);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 32, 46 );
    }

    @Override
    public int attackSkill( Char target ) {return 40; }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 12);
    }


    @Override
    public int attackProc(Char enemy, int damage) {

        if (Random.Int(5) == 0) {
            enemy.damage(8, this);
            Buff.affect(enemy, Burning.class).reignite(enemy, 3f);
        }

        return super.attackProc(enemy, damage);
    }
}
