package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MeatPower_Stewed;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WellFed;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class Sandvich extends Food {
    {
        image = ItemSpriteSheet.STEWED;
        energy = Hunger.HUNGRY/3f;
    }

    private int eat = 0; // 3일때 사용시 아이템 제거

    @Override
    protected void satisfy(Hero hero) {
        super.satisfy(hero);
        eat++;
        effect(hero);

        if (eat < 4) {
            Item result = this;
            if (result == null){
                //This shouldn't ever trigger
                GLog.n( Messages.get(this, "nothing") );
                curItem.collect( curUser.belongings.backpack );
            } else {
                    if (!result.collect()){
                        Dungeon.level.drop(result, curUser.pos).sprite.drop();
                    }
                }
        }
    }

    public static void effect(Hero hero){
        hero.HP = Math.min( hero.HP + hero.HT / 3, hero.HT );
        hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
    }

    @Override
    public int value() {
        return 20 * quantity;
    }
}
