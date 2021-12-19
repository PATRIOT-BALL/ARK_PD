package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Gunaccessories.Accessories;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.QuestCat;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.AquaBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ForceCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.PhaseShift;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Recycle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.NormalMagazine;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_PhantomSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_jessicatSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.Calendar;
import java.util.Locale;

public class NPC_Phantom extends NPC {
    {
        spriteClass = NPC_PhantomSprite.class;
        properties.add(Char.Property.IMMOVABLE);
        properties.add(Property.NPC);
    }

    public static boolean QuestClear;

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
        if (!QuestClear && Dungeon.hero.belongings.getItem(QuestCat.class) != null) QuestReward();
        else {
            sprite.showStatus( CharSprite.NEUTRAL, "?");
        }
        return true;
    }

    private void QuestReward() {
        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {
                GameScene.show(new WndMessage(Messages.get(NPC_Phantom.class, "result")));
            }
        });

        Item result1;
        if (Dungeon.QuestCatPoint == 0) result1 = new AquaBlast().quantity(2);
        else result1 = new ForceCatalyst();

        Item result2;
        if (Dungeon.QuestCatPoint == 0) result2 = new Recycle().quantity(2);
        else result2 = new PhaseShift().quantity(3);

        if (result1.doPickUp( Dungeon.hero )) {
            GLog.i( Messages.get(Dungeon.hero, "you_now_have", result1.name()) );
        } else {
            Dungeon.level.drop( result1, this.pos ).sprite.drop();
        }

        if (result2.doPickUp( Dungeon.hero )) {
            GLog.i( Messages.get(Dungeon.hero, "you_now_have", result2.name()) );
        } else {
            Dungeon.level.drop( result2, this.pos ).sprite.drop();
        }

        QuestCat m = Dungeon.hero.belongings.getItem(QuestCat.class);
        m.detachAll(Dungeon.hero.belongings.backpack);
        QuestClear = true;
    }

    public static void spawn(Level level, int ppos) {
        NPC_Phantom cat = new NPC_Phantom();
        do {
            cat.pos = ppos;
        } while (cat.pos == -1);
        level.mobs.add(cat);
    }
}
