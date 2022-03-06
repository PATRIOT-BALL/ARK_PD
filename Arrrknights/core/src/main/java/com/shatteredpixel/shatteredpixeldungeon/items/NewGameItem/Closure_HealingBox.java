package com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sungrass;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Closure_HealingBox extends ClosuresBox {
    {
        image = ItemSpriteSheet.LOCKED_CHEST;
        stackable = true;

        OpenLevel = 6;
    }

    @Override
    public void execute(Hero hero, String action ) {

        super.execute(hero, action);

        if (action.equals(AC_OPEN)) {

            Item item = new Sungrass.Seed();
            if (!item.doPickUp( Dungeon.hero )) {
                Dungeon.level.drop( item, hero.pos ).sprite.drop();
            }

            hero.spend(1);
            hero.busy();

            detach(hero.belongings.backpack);
            Badges.UseHealBox();

            hero.sprite.operate(hero.pos);
        }
    }

    @Override
    public int value() {
        return 50 * quantity;
    }
}
