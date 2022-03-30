package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.EnragedSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ZealotSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class MudrockZealot extends Mob {
    {
        spriteClass = ZealotSprite.class;

        HP = HT = 50;
        defenseSkill = 6;

        EXP = 7;
        maxLvl = 18;

        loot = Generator.Category.STONE;
        lootChance = 0.1f;

        immunities.add(Silence.class);
    }

    boolean barrier = true;

    @Override
    public int damageRoll() {
        if (barrier) return Random.NormalIntRange(7, 21);
        return Random.NormalIntRange(5, 16);
    }

    @Override
    public int attackSkill(Char target) {
        return 18;
    }

    @Override
    public int drRoll() {
        if (barrier) {
            if (Dungeon.isChallenged(Challenges.TACTICAL_UPGRADE)) return Random.NormalIntRange(8, 14);
            return Random.NormalIntRange(4, 12);
        }
        return Random.NormalIntRange(0, 5);
    }

    @Override
    public void damage(int dmg, Object src) {
        if (src instanceof Wand && barrier) {
            barrier = false;
            sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "brack"));
        }
        super.damage(dmg, src);
    }

    private static final String BARR = "barrier";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(BARR, barrier);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        barrier = bundle.getBoolean(BARR);
    }
}
