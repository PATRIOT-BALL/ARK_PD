package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.AntiMagic;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

public class RadiantKnight extends FlavourBuff {
    {
        type = buffType.POSITIVE;
        announced = true;
    }

    public static final float DURATION = 20f;

    {
        immunities.add(Blindness.class);
    }

    @Override
    public int icon() {
        return BuffIndicator.WEAPON;
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (DURATION - visualcooldown()) / DURATION);
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", dispTurns());
    }

    @Override
    public void fx(boolean on) {
        if (on) target.sprite.add(CharSprite.State.HIKARI);
        else target.sprite.remove(CharSprite.State.HIKARI);
    }

}
