package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DobermannSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Dobermann_shadowSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndEazyMode;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

public class Dobermann extends NPC {
    {
        spriteClass = DobermannSprite.class;
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
        Dobermann perro = new Dobermann();
        do {
            perro.pos = ppos;
        } while (perro.pos == -1);
        level.mobs.add(perro);
    }
}
