package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.levels.CavesLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.PrisonLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Blood_ShamanSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LancerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SarkazSniperEliteSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class BloodMagister extends Mob {
    {
        spriteClass = Blood_ShamanSprite.class;

        properties.add(Property.MINIBOSS);
        immunities.add(ScrollOfPsionicBlast.class);

        HP = HT = 300;
        defenseSkill = 15;
        baseSpeed = 2f;

        EXP = 12;
        maxLvl = -1;

        loot = Generator.Category.SEED;
        lootChance = 1.0f;

    }

    private int attackpower = 0;
    private boolean powerdown = false;

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(16 + attackpower / 2, 24 + attackpower);
    }

    @Override
    public int attackSkill( Char target ) {
        return 25;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(4, 12);
    }

    @Override
    protected float attackDelay() {
        return super.attackDelay() * 0.5f;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        attackpower +=6;
        return super.attackProc(enemy, damage);
    }

    @Override
    public boolean act() {
        if (buff(rage.class) == null && state == HUNTING)
        {
            Buff.affect(this, rage.class);
        }
        else if (buff(rage.class) != null && buff(MagicalSleep.class) == null)
        {
            if (HP >= 16) damage(15,this);
            else powerdown = true;
        }
        return super.act();
    }

    public static void spawn(CavesLevel level) {
        if (Dungeon.depth >= 14 && !Dungeon.bossLevel()) {

            BloodMagister Magister = new BloodMagister();
            do {
                Magister.pos = level.randomRespawnCell(Magister);
            } while (Magister.pos == -1);
            level.mobs.add(Magister);
        }
    }

    @Override
    public float speed() {
        if (powerdown) return super.speed() / 4;
        else return super.speed();
    }

    @Override
    public void die(Object cause) {
        GLog.w(Messages.get(BloodMagister.class, "die"));
        Dungeon.mboss14 = 0;
        super.die(cause);
    }

    private static final String POWER   = "attackpower";
    private static final String PDOWN   = "powerdown";
    @Override

    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( POWER, attackpower );
        bundle.put( PDOWN, powerdown );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        attackpower = bundle.getInt(POWER);
        powerdown = bundle.getBoolean(PDOWN);

    }

    public static class rage extends Buff {

        {
            type = buffType.NEGATIVE;
            announced = true;
        }

        @Override
        public int icon() {
            return BuffIndicator.BERSERK;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0.25f, 1.5f, 1f);
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc");
        }
    }
}
