package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class BookSoaringFeather extends Item {
    private static final String AC_USE = "USE";

    {
        image = ItemSpriteSheet.SKILL_CHIP3;

        stackable = true;

    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_USE);
        return actions;
    }

    @Override
    public void execute (Hero hero, String action ){

        super.execute(hero, action);
        if (action.equals(AC_USE)) {
            this.identify();
            hero.busy();
            hero.sprite.operate(hero.pos);
            detach(hero.belongings.backpack);

            hero.SK3 = new SoaringFeather();
            hero.SetSkill3Num(2);
            ((HeroSprite)hero.sprite).updateArmor();
            hero.spendAndNext(1f);
        }


    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() { return true; }
}