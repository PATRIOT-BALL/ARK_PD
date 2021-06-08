package com.shatteredpixel.shatteredpixeldungeon.items.quest;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gamzashield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Niansword;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;

import java.util.ArrayList;

public class Nullshield extends Item {
    private static final String AC_USE = "USE";
    {
        image = ItemSpriteSheet.NULL_DEF;

        stackable = false;
        unique = true;
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
            GameScene.selectItem(itemSelector, WndBag.Mode.ALL, Messages.get(this, "prompt"));
            detach(curUser.belongings.backpack);
        }
    }

    private final WndBag.Listener itemSelector = new WndBag.Listener()  {
        @Override
        public void onSelect(final Item item) {
            if (item != null) {

                if (item instanceof Gamza)
                {
                   GLog.h(Messages.get(Nullshield.class, "suc"));
                   Gamzashield gma = new Gamzashield();
                   gma.enchant(Weapon.Enchantment.randomUncommon());
                   gma.collect();
                   gma.identify();
                   item.detach( curUser.belongings.backpack );
                }
                else if (item instanceof Nmould)
                {
                    GLog.h(Messages.get(Nullshield.class, "suc"));
                    Niansword nya = new Niansword();
                    nya.enchant(Weapon.Enchantment.randomUncommon());
                    nya.identify();
                    nya.collect();
                    item.detach( curUser.belongings.backpack );
                }
                else {
                    new Nullshield().collect();
                    GLog.h(Messages.get(Nullshield.class, "fail"));}
            }
        }
    };

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }
}
