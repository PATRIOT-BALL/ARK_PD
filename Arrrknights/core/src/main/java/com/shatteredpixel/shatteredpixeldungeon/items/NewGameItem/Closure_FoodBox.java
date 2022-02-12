package com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Closure_FoodBox extends ClosuresBox {
    {
        image = ItemSpriteSheet.CHEST;
        stackable = true;

        OpenLevel = 0;
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute(hero, action);

        if (action.equals(AC_OPEN)) {

            Food item = new Food();
            if (!item.doPickUp( Dungeon.hero )) {
                Dungeon.level.drop( item, hero.pos ).sprite.drop();
            }

            hero.spend(1);
            hero.busy();

            detach(hero.belongings.backpack);

            hero.sprite.operate(hero.pos);
        }
    }

    @Override
    public int value() {
        return 5 * quantity;
    }
}
