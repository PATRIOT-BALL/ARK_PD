package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DobermannSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.WeedySprite;

public class Weedy extends NPC {
    {
        spriteClass = WeedySprite.class;
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
        sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "say"));

        return true;
    }

    public static void spawn(Level level, int ppos) {
        Weedy weedy = new Weedy();
        do {
            weedy.pos = ppos;
        } while (weedy.pos == -1);
        level.mobs.add(weedy);
    }
}
