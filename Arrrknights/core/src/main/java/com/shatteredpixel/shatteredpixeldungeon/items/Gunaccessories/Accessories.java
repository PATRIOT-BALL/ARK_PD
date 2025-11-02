package com.shatteredpixel.shatteredpixeldungeon.items.Gunaccessories;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GunWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;

import java.util.ArrayList;

public class Accessories extends Item {
    public static final String AC_AFFIX = "AFFIX";

    {
        image = ItemSpriteSheet.BOMB;

        stackable = false;
        bones = false;
    }

    protected float ACCcorrectionvalue = 1f;
    protected float DLYcorrectionvalue = 1f;
    protected float DMGcorrectionvalue = 1f;
    protected int SavingChancevalue = 0;

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_AFFIX);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_AFFIX)) {
            GameScene.selectItem(itemSelector, WndBag.Mode.GUN, Messages.get(this, "prompt"));
            this.detach(hero.belongings.backpack);

            hero.spendAndNext(1f);
        }
    }

    private Accessories Affix() {
        return this;
    }

    private final WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect(final Item item) {
            if (item != null) {
                if (item instanceof GunWeapon) {
                   if(!((GunWeapon) item).affixAccessories(Affix())) {
                       Affix().collect();
                   }
                }
            }
            else {
                Affix().collect();
            }
        }
    };

    public float GetACCcorrectionvalue() {
        return ACCcorrectionvalue;
    }

    public float GetDLYcorrectionvalue() {
        return DLYcorrectionvalue;
    }

    public float GetDMGcorrectionvalue() {
        return DMGcorrectionvalue;
    }

    public int GetSavingChance() {
        return SavingChancevalue;
    }

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
        return 40;
    }
}
