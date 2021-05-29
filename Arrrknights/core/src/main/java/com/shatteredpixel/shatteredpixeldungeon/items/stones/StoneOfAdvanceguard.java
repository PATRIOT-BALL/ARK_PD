package com.shatteredpixel.shatteredpixeldungeon.items.stones;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class StoneOfAdvanceguard extends Runestone {
    {
        image = ItemSpriteSheet.GOLD;
    }

    @Override
    protected void activate(int cell) {
        Char mob = Actor.findChar(cell);
        if (mob != null) {
            if (mob.properties().contains(Char.Property.BOSS) || mob.properties().contains(Char.Property.MINIBOSS)) {
                GLog.h(Messages.get(this, "fail"));
            } else {
                mob.destroy();
                mob.sprite.killAndErase();
                Dungeon.level.drop(new Gold(200), cell).sprite.drop();;
                GLog.p(Messages.get(this, "hit"));
            }
        }
    }
}
