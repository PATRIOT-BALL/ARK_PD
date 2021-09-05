package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.Bonk;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BreakerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LancerSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Raider extends Succubus {
    {
        spriteClass = BreakerSprite.class;

        HP = HT = 110;
        defenseSkill = 25;
        viewDistance = Light.DISTANCE;

        EXP = 30;
        maxLvl = 28;
        baseSpeed = 1f;

        immunities.add(Silence.class);
        resistances.add(Cripple.class);
    }

    private int ASPlus;

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(24, 30);
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        damage = super.attackProc(enemy, damage);
        if (ASPlus > 5) {
            Ballistica trajectory = new Ballistica(this.pos, enemy.pos, Ballistica.STOP_TARGET);
            trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
            WandOfBlastWave.throwChar(enemy, trajectory, ASPlus/6); // 넉백 효과
        }
        ASPlus = 0;
        Buff.detach(this, Acceleration.class);

        return damage;
    }

    @Override
    public void move(int step) {
        super.move(step);
        if (state == HUNTING && buff(Acceleration.class) == null) {
            Buff.affect(this, Acceleration.class, 15f);
        }
        if (buff(Acceleration.class) != null) ASPlus += 3;
        if (ASPlus > 30) ASPlus = 30;
    }

    @Override
    protected boolean act() {
        if (paralysed == 1) ASPlus = 0;
        return super.act();
    }

    @Override
    public float speed() {
        return super.speed() * 1 + (ASPlus * (ASPlus / 3) / 30);
    }

    private static final String SPEEDSKILL = "asplus";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(SPEEDSKILL, ASPlus);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        ASPlus = bundle.getInt(SPEEDSKILL);
    }
}
