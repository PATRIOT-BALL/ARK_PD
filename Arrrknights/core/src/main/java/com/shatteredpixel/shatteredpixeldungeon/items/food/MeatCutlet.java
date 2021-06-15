package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WellFed;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.AnnihilationGear;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class MeatCutlet extends Food {
    {
        image = ItemSpriteSheet.STEAK;
        energy = Hunger.HUNGRY/20f;
    }

    @Override
    public int value() {
        return 8 * quantity;
    }

    @Override
    protected void satisfy(Hero hero) {
        super.satisfy(hero);
        effect(hero);
    }

    public static void effect(Hero hero){
        AnnihilationGear Gear = hero.belongings.getItem(AnnihilationGear.class);
        if (hero.belongings.getItem(AnnihilationGear.class) != null)
        {
            Gear.SPCharge(1);
        }
    }
}
