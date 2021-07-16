package com.shatteredpixel.shatteredpixeldungeon.items.quest;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookFlashShield;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Enfild;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Enfild2;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gamzashield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
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
                    Dungeon.level.drop(gma, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                   gma.identify();
                   item.detach( curUser.belongings.backpack );
                    detach(curUser.belongings.backpack);
                }
                else if (item instanceof Nmould)
                {
                    GLog.h(Messages.get(Nullshield.class, "suc"));
                    Niansword nya = new Niansword();
                    nya.enchant(Weapon.Enchantment.randomUncommon());
                    nya.identify();
                    Dungeon.level.drop(nya, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                    item.detach( curUser.belongings.backpack );
                    detach(curUser.belongings.backpack);
                }
                else if (item instanceof Scroll)
                {
                    GLog.h(Messages.get(Nullshield.class, "suc"));
                    Niansword nya = new Niansword();
                    nya.enchant(Weapon.Enchantment.randomUncommon());
                    nya.identify();
                    Dungeon.level.drop(nya, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                    item.detach( curUser.belongings.backpack );
                    detach(curUser.belongings.backpack);
                }
                else if (item instanceof Enfild)
                {
                    if (curUser.belongings.weapon != item) {
                        GLog.h(Messages.get(Nullshield.class, "suc"));
                        Enfild2 nya = new Enfild2();
                        nya.identify();

                        int level = item.level();
                        if (((Enfild) item).curseInfusionBonus) level--;
                        if (level > 0) {
                            nya.upgrade( level );
                        } else if (level < 0) {
                            nya.degrade( -level );
                        }

                        nya.enchantment = ((Enfild) item).enchantment;
                        nya.curseInfusionBonus = ((Enfild) item).curseInfusionBonus;
                        nya.levelKnown = ((Enfild) item).levelKnown;
                        nya.cursedKnown = ((Enfild) item).cursedKnown;
                        nya.cursed = ((Enfild) item).cursed;
                        nya.augment = ((Enfild) item).augment;
                        Dungeon.level.drop(nya, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
                        item.detach(curUser.belongings.backpack);
                        detach(curUser.belongings.backpack);
                    }
                    else GLog.h(Messages.get(Nullshield.class, "fail_weapon"));
                }
                else {

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

    @Override
    public int value() {
        return 75;
    }
}
