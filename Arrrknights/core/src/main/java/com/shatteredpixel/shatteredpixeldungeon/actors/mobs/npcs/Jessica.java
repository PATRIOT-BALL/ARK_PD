package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Gunaccessories.Accessories;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookPowerfulStrike;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MeatPie;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Pasty;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.NormalMagazine;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_jessicatSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import java.util.Calendar;
import java.util.Locale;

public class Jessica extends NPC {
    {
       spriteClass = NPC_jessicatSprite.class;
        properties.add(Char.Property.IMMOVABLE);
        properties.add(Property.NPC);
   }

   public static boolean AnotherQuest_Jees;

   static {
       final Calendar calendar = Calendar.getInstance(Locale.KOREA);
       if (calendar.get(Calendar.HOUR) >= 12) AnotherQuest_Jees = true;
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
        if (AnotherQuest_Jees) JessQuest2(c);
        else JessQuest1(c);

        return true;
    }

    public void JessQuest1(Char c) {
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
    }

    public void JessQuest2(Char c) {
        if (!QuestClear) {
            if (firstrun && Dungeon.hero.belongings.getItem(MeatPie.class) != null) {
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(Jessica.class, "result2")));
                    }
                });
                Accessories result;
                result = (Accessories) Generator.random(Generator.Category.ACCESSORIES);

                if (result.doPickUp( Dungeon.hero )) {
                    GLog.i( Messages.get(Dungeon.hero, "you_now_have", result.name()) );
                } else {
                    Dungeon.level.drop( result, this.pos ).sprite.drop();
                }
                new Gold(900).doPickUp(Dungeon.hero);

                MeatPie m = Dungeon.hero.belongings.getItem(MeatPie.class);
                m.detachAll(Dungeon.hero.belongings.backpack);
                QuestClear = true;
            }
            else {
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(Jessica.class, "quest2")));
                    }
                });
                firstrun = true;
            }
        }
        else {
            sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "say"));
        }
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
}
