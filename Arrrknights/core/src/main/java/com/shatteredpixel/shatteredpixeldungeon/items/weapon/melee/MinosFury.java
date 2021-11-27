package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.W0502;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Firebomb;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Gamza;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Nmould;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Nullshield;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfSuzuran;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MinosFury extends MeleeWeapon {
    public static final String AC_EAT = "EAT";

    {
        image = ItemSpriteSheet.VULCAN;
        tier = 5;

        bones = false;
    }

    private int drbouns = 0;

    @Override
    public int max(int lvl) {
        return  4*(tier) +     //20 + 5
                lvl*(tier);                   //+3 per level, down from +6
    }

    @Override
    public int defenseFactor( Char owner ) {
        return 0+drbouns;
    }

    public String statsInfo(){
        if (isIdentified()){
            return Messages.get(this, "stats_desc", 0+drbouns);
        } else {
            return Messages.get(this, "typical_stats_desc", 0);
        }
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_EAT);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_EAT)) {
            GameScene.selectItem(itemSelector, WndBag.Mode.WEAPON, Messages.get(Nullshield.class, "prompt"));
        }
    }

    private final WndBag.Listener itemSelector = new WndBag.Listener()  {
        @Override
        public void onSelect(final Item item) {
            if (item != null && !item.isEquipped(Dungeon.hero) && drbouns < 20) {
                drbouns += 2;
                item.detach(Dungeon.hero.belongings.backpack);
            }
        }
    };

    private static final String DR = "drbouns";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(DR, drbouns);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        drbouns = bundle.getInt(DR);
    }
}
