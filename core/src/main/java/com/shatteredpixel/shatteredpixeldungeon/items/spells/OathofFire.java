package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.audio.Sample;

public class OathofFire extends Spell {

    {
        image = ItemSpriteSheet.OATH_FIRE;

        unique = true;
    }


    @Override
    protected void onCast(Hero hero) {
        hero.sprite.operate(hero.pos);
        Sample.INSTANCE.play(Assets.Sounds.BURNING );
        Buff.affect(hero, OathFireBuff.class, OathFireBuff.DURATION);

        detach( curUser.belongings.backpack );
        updateQuickslot();
        hero.spendAndNext( 1f );
    }

    @Override
    public int value() {
        //prices of ingredients, divided by output quantity
        return quantity * 15;
    }


    public static class OathFireBuff extends FlavourBuff {
        {
            type = buffType.POSITIVE;
            announced = true;
        }

        public static final float DURATION = 100f;

        {
            immunities.add(Burning.class);
        }

        @Override
        public int icon() {
            return BuffIndicator.FIRE;
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

    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{PotionOfLiquidFlame.class, ForceCatalyst.class};
            inQuantity = new int[]{1, 1};

            cost = 8;

            output = OathofFire.class;
            outQuantity = 4;
        }

    }
}
