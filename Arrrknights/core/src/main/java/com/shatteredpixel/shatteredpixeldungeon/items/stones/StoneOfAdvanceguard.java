package com.shatteredpixel.shatteredpixeldungeon.items.stones;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GooBlob;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class StoneOfAdvanceguard extends Runestone {
    {
        image = ItemSpriteSheet.GOLD;
    }

    @Override
    protected void activate(int cell) {
        Char mob = Actor.findChar(cell);
        if (mob != null) {
            if (mob instanceof Hero) {
                Item item = Dungeon.hero.belongings.getItem(Ankh.class);
                if (item != null) item.detachAll(Dungeon.hero.belongings.backpack);
                mob.sprite.killAndErase();
                mob.die(this);
                Dungeon.level.drop(new Gold(200), cell).sprite.drop();
                GLog.p(Messages.get(this, "hit"));
                for (int i = 0; i < 5; i++){
                    int ofs;
                    do {
                        ofs = PathFinder.NEIGHBOURS8[Random.Int(8)];
                    } while (!Dungeon.level.passable[mob.pos + ofs]);
                    Dungeon.level.drop( new Gold(1), mob.pos + ofs ).sprite.drop( mob.pos );
                }
            }
            else if (mob.properties().contains(Char.Property.BOSS) || mob.properties().contains(Char.Property.MINIBOSS)) {
                GLog.h(Messages.get(this, "fail"));
            } else {
                mob.destroy();
                mob.sprite.killAndErase();
                Dungeon.level.drop(new Gold(200), cell).sprite.drop();;
                GLog.p(Messages.get(this, "hit"));
            }
        }
        else {
            Dungeon.level.drop(new StoneOfAdvanceguard(), cell).sprite.drop();;
        }
    }
}
