package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class BookBenasProtracto extends Item {
    private static final String AC_USE = "USE";

    {
        image = ItemSpriteSheet.SKILL_CHIP2;

        stackable = true;

    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_USE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);
        if (action.equals(AC_USE)) {
            this.identify();
            hero.busy();
            hero.sprite.operate(hero.pos);
            detach(hero.belongings.backpack);

            hero.SK2 = new BenasProtracto();
            hero.SetSkill2Num(10);
        }

    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() { return true; }

    @Override
    public int value() {
        return 45;
    }
}
