package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.ArmorUpKit;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Help_LancetSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_AstesiaSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_jessicatSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class LANCET2 extends NPC {
    {
        spriteClass = Help_LancetSprite.class;
        properties.add(Char.Property.IMMOVABLE);
        properties.add(Property.NPC);

        state = SLEEPING;
    }

    @Override
    public int defenseSkill(Char enemy) {
        return INFINITE_EVASION;
    }

    @Override
    public void damage(int dmg, Object src) {
    }

    @Override
    protected boolean act() {
        if (state == SLEEPING) {
            yell(Messages.get(this, "help"));
            state = PASSIVE;
        }
        return super.act();
    }

    @Override
    public boolean interact(Char c) {
        sprite.turnTo(pos, c.pos);
        GLog.n( Messages.get(this, "thank") );
        Dungeon.level.drop(new ArmorUpKit(), pos).sprite.drop(pos);
        die(this);
        return true;
    }

    public static void spawn(Level level, int ppos) {
        LANCET2 perro = new LANCET2();
        do {
            perro.pos = ppos;
        } while (perro.pos == -1);
        level.mobs.add(perro);
    }
}
