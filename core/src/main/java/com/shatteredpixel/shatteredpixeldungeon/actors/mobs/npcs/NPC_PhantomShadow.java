package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.QuestCat;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_PhantomShadowSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_PhantomSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class NPC_PhantomShadow extends NPC {
    {
        spriteClass = NPC_PhantomShadowSprite.class;
        properties.add(Char.Property.IMMOVABLE);
        properties.add(Property.NPC);
    }

    public static boolean Clear = false;

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
        QuestCat result = new QuestCat();

        if (result.doPickUp( Dungeon.hero )) {
            GLog.i( Messages.get(Dungeon.hero, "you_now_have", result.name()) );
        } else {
            Dungeon.level.drop( result, this.pos ).sprite.drop();
        }

        die(this);
        return true;
    }

    public static void spawn(Level level, int a) {
        int ppos = Random.Int(3);
        switch (ppos) {
            case 0: default:
                if (a ==  0) ppos = 2999;
                else if (a ==  1) ppos = 151;
                else if (a ==  2) ppos = 1140;
                break;
            case 1:
                if (a ==  0) ppos = 4220;
                else if (a ==  1) ppos = 618;
                else if (a ==  2) ppos = 744;
                break;
            case 2:
                if (a ==  0) ppos = 3689;
                else if (a ==  1) ppos = 273;
                else if (a ==  2) ppos = 847;
                break;
        }
        NPC_PhantomShadow cat = new NPC_PhantomShadow();
        do {
            cat.pos = ppos;
        } while (cat.pos == -1);
        level.mobs.add(cat);
    }

    private static final String QUEST = "Clear";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( QUEST, Clear );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        Clear = bundle.getBoolean( QUEST );
    }
}
