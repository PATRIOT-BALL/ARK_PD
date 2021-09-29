package com.shatteredpixel.shatteredpixeldungeon.items.stones;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WaterOfAdvanceguard;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.WellWater;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

import java.awt.Dialog;

public class SuperAdvanceguard extends Runestone {
    private static ItemSprite.Glowing COL = new ItemSprite.Glowing( 0xE6E6FA );
    {
        image = ItemSpriteSheet.GOLD;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return COL;
    }

    @Override
    protected void activate(int cell) {
        if (Dungeon.level.map[cell] == Terrain.EMPTY_WELL) {
            Dungeon.level.map[cell] = Terrain.WELL;
            GameScene.updateMap(cell);
            WellWater.seed(cell, 1, WaterOfAdvanceguard.class, Dungeon.level);
            GameScene.updateMap(cell);
            GLog.h(Messages.get(this, "advanceguard"));
        }
        else if (cell == Dungeon.hero.pos) {
            Item item = Dungeon.hero.belongings.getItem(Ankh.class);
            if (item != null) item.detachAll(Dungeon.hero.belongings.backpack);
            Dungeon.hero.sprite.killAndErase();
            Dungeon.hero.die(this);

            Dungeon.level.map[cell] = Terrain.WELL;
            GameScene.updateMap(cell);
            WellWater.seed(cell, 1, WaterOfAdvanceguard.class, Dungeon.level);
            GameScene.updateMap(cell);
            GLog.h(Messages.get(this, "advanceguard"));
        }
        else {
            Dungeon.level.drop(new SuperAdvanceguard(), cell).sprite.drop();;
        }
    }
}
