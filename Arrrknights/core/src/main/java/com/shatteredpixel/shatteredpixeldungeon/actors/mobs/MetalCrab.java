package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.AntiMagic;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BugSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Rock_CrabSprite;
import com.watabou.utils.Random;

public class MetalCrab extends Mob{
    {
        spriteClass = Rock_CrabSprite.class;

        HP = HT = 150;
        defenseSkill = 0;

        maxLvl = 35;
        EXP = 19;
        immunities.add(Silence.class);
        immunities.add(Poison.class);
        immunities.add(Bleeding.class);
        immunities.add(Roots.class);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 36, 46 );
    }

    @Override
    public int attackSkill( Char target ) {return 38; }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(16, 32);
    }


    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Roots.class, 1f);
        return super.attackProc(enemy, damage);
    }
}
