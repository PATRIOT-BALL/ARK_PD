package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.FanaticSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Eunectes extends Mob {
    {
        spriteClass = FanaticSprite.class;

        HT = HP = 800;
        defenseSkill = 25;

        state = HUNTING;

        properties.add(Property.BOSS);
        immunities.add(Silence.class);
    }

    private boolean isBarrier = false;

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 48, 60 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 45;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 24);
    }

    @Override
    public void damage(int dmg, Object src) {
        if (dmg > HT / 2) {
            dmg = HT / 2;
        }

        super.damage(dmg, src);

        if (HT / 2 >= HP && !isBarrier) {
            Buff.affect(this, Barrier.class).setShield(400);
            isBarrier = true;
        }
    }

    @Override
    protected boolean act() {
        if (buff(Barrier.class) != null) {
            HP = Math.min(HP + 8, HT);
        }
        return super.act();
    }

    @Override
    public void notice() {
        if (!BossHealthBar.isAssigned()) {
            BossHealthBar.assignBoss(this);
            yell(Messages.get(this, "notice"));
        }
    }

    @Override
    public void die(Object cause) {
        int thispos = pos;
        super.die(cause);

        TheBigUglyThing newboss = new TheBigUglyThing();
        newboss.pos = thispos;
        GameScene.add(newboss, 1f);
        yell(Messages.get(this, "die"));
        GameScene.flash( 0x80FFFFFF );
    }

    private static final String BARRIER = "isBarrier";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( BARRIER, isBarrier );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        isBarrier = bundle.getBoolean(BARRIER);
    }
}
