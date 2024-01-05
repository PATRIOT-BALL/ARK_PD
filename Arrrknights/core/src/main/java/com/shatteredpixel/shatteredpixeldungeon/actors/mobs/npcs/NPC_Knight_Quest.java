package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KNIGHT_NPC_SUMMON;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.AceSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class NPC_Knight_Quest extends NPC {

    {
        spriteClass = AceSprite.class;
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

        Item reward = new KNIGHT_NPC_SUMMON();

        if (reward.doPickUp( Dungeon.hero )) {
            GLog.i( Messages.get(Dungeon.hero, "you_now_have", reward.name()) );
        } else {
            Dungeon.level.drop( reward, this.pos ).sprite.drop();
        }

        die(this);

        return true;
    }

    public static void spawn(Level level, int ppos) {
        NPC_Knight_Quest perro = new NPC_Knight_Quest();
        do {
            perro.pos = ppos;
        } while (perro.pos == -1);
        level.mobs.add(perro);
    }
}
