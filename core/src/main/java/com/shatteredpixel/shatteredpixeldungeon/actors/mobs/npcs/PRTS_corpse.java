package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Rose;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.sprites.PRTS_corpseSprite;

public class PRTS_corpse extends NPC{

    {
        spriteClass = PRTS_corpseSprite.class;
        properties.add(Char.Property.IMMOVABLE);
        properties.add(Property.NPC);
    }

    @Override
    public int defenseSkill(Char enemy) {
        return INFINITE_EVASION;
    }

    @Override
    public void damage(int dmg, Object src) {
    }

    @Override
    public boolean interact(Char c) {
        sprite.turnTo(pos, c.pos);

        if(Dungeon.hero.belongings.getItem(Rose.class) != null) {
            Item item = Dungeon.hero.belongings.getItem(Rose.class);
            if (item != null) item.detachAll(Dungeon.hero.belongings.backpack);

            destroy();

            Dungeon.isPray = true;

            die(this);
            CellEmitter.get( pos ).burst( Speck.factory( Speck.DISCOVER ), 8 );
        }

        return true;
    }

    public static void spawn(Level level, int ppos) {
        PRTS_corpse PRTS = new PRTS_corpse();
        do {
            PRTS.pos = ppos;
        } while (PRTS.pos == -1);
        level.mobs.add(PRTS);
    }
}
