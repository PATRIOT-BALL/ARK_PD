package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class RingKit extends Item {
    private static final String AC_APPLY = "APPLY";
    private static final float TIME_TO_UPGRADE = 2;

    {
        image = ItemSpriteSheet.KIT;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_APPLY);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_APPLY)) {
            curUser = hero;
            GameScene.selectItem(itemSelector, WndBag.Mode.RING, Messages.get(this, "prompt"));
        }
    }

        private final WndBag.Listener itemSelector = new WndBag.Listener() {
            @Override
            public void onSelect(Item item) {
                if (item != null) {
                    RingKit.this.upgrade((Ring) item);
                }
            }
        };


    private void upgrade(Ring item) {
        item.upgrade();

        this.detach(Dungeon.hero.belongings.backpack);

        curUser.sprite.operate(curUser.pos);
        Sample.INSTANCE.play(Assets.Sounds.EVOKE);
        Dungeon.hero.spendAndNext(1f);
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }
}
