package com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.WeaponTransform;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Closure_RingBox extends ClosuresBox {
    {
        image = ItemSpriteSheet.CRYSTAL_CHEST;
        stackable = true;

        OpenLevel = 11;
    }

    @Override
    public void execute(Hero hero, String action ) {

        super.execute(hero, action);

        if (action.equals(AC_OPEN)) {

            Item item = Generator.random( Generator.Category.RING);
            item.cursed = false;
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
        return 150 * quantity;
    }
}
