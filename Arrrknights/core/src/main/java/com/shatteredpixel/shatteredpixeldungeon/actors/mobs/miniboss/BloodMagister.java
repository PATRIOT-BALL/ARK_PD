package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
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
import com.watabou.utils.Random;

public class BloodMagister extends Mob {
    {
        spriteClass = Blood_ShamanSprite.class;

        properties.add(Property.MINIBOSS);
        immunities.add(ScrollOfPsionicBlast.class);

        HP = HT = 200;
        defenseSkill = 15;
        baseSpeed = 2f;

        EXP = 12;
        maxLvl = -1;

        loot = Generator.Category.SEED;
        lootChance = 1.0f;

    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(25, 40);
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
    public boolean act() {
        if (buff(rage.class) == null && state == HUNTING)
        {
            Buff.affect(this, rage.class);
        }
        else if (buff(rage.class) != null)
        {
            damage(4,this);
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
    public void die(Object cause) {
        GLog.w(Messages.get(BloodMagister.class, "die"));
        Dungeon.mboss14 = 0;
        super.die(cause);
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
