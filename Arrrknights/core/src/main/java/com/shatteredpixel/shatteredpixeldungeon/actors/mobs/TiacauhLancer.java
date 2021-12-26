package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.sprites.StrikerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Tiacauh_lancerSprite;
import com.watabou.utils.Random;

public class TiacauhLancer extends Mob {
    {
        spriteClass =  Tiacauh_lancerSprite.class;

        HP = HT = 110;
        defenseSkill = 15;

        EXP = 14;
        maxLvl = 30;

        immunities.add(Silence.class);
    }

    @Override
    protected boolean canAttack(Char enemy) {
        return this.fieldOfView[enemy.pos] && Dungeon.level.distance(this.pos, enemy.pos) <= 3;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(30,38);
    }

    @Override
    protected float attackDelay() {
        return super.attackDelay() * 0.5f;
    }

    @Override
    public int attackSkill( Char target ) {
        return 36;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 16); }

}
