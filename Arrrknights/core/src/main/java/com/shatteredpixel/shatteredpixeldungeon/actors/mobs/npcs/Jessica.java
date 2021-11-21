package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.NormalMagazine;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_jessicatSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

public class Jessica extends NPC {
    {
       spriteClass = NPC_jessicatSprite.class;
        properties.add(Char.Property.IMMOVABLE);
        properties.add(Property.NPC);
   }

   public static boolean QuestClear = false;
    private boolean firstrun = false;

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
        if (!QuestClear) {
            if (firstrun && Dungeon.hero.belongings.getItem(NormalMagazine.class) != null) {
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(Jessica.class, "result")));
                    }
                });
                new Gold(800).doPickUp(Dungeon.hero);
                NormalMagazine m = Dungeon.hero.belongings.getItem(NormalMagazine.class);
                m.detachAll(Dungeon.hero.belongings.backpack);
                QuestClear = true;
        }
            else {
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(Jessica.class, "quest")));
                    }
                });
                firstrun = true;
            }
        }
        else {
            sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "say"));
        }

        return true;
    }

    @Override
    protected boolean act() {
        sprite.turnTo(pos, 0);
        return super.act();
    }

    public static void spawn(Level level, int ppos) {
        Jessica cat = new Jessica();
        do {
            cat.pos = ppos;
        } while (cat.pos == -1);
        level.mobs.add(cat);
    }

    private static final String QUEST = "QuestClear";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( QUEST, QuestClear );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        QuestClear = bundle.getBoolean( QUEST );
    }
}
