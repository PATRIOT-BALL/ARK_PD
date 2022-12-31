package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_GavialSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_IreneSprite;

public class NPC_Irene extends NPC {
    {
        spriteClass = NPC_IreneSprite.class;
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
            if (Dungeon.extrastage_See) {
                Dungeon.extrastage_See = false;
                sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "no"));
            } else {
                Dungeon.extrastage_See = true;
                sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "ok"));
            }
        return true;
    }

    public static void spawn(Level level, int ppos) {
        NPC_Irene perro = new NPC_Irene();
        do {
            perro.pos = ppos;
        } while (perro.pos == -1);
        level.mobs.add(perro);
    }
}
