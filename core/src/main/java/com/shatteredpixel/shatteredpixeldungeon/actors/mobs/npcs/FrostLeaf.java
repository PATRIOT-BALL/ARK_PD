package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfIcyTouch;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfLeaf;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.NormalMagazine;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_FrostLeafSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_jessicatSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class FrostLeaf extends NPC {
    {
        spriteClass = NPC_FrostLeafSprite.class;
        properties.add(Char.Property.IMMOVABLE);
        properties.add(Property.NPC);
    }

    public static boolean QuestClear;
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
            if (firstrun &&  Dungeon.hero.belongings.getItem(ElixirOfIcyTouch.class) != null) {
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(FrostLeaf.class, "result")));
                    }
                });
                // 요구 : 차가운 숨결 용액
                // 보상 : 서리막대 스태프 (+1 강화)
                StaffOfLeaf le = new StaffOfLeaf();
                le.level(1);
                le.identify();

                if (le.doPickUp( Dungeon.hero )) {
                    GLog.i( Messages.get(Dungeon.hero, "you_now_have", le.name()) );
                } else {
                    Dungeon.level.drop( le, Dungeon.hero.pos ).sprite.drop();
                }

                ElixirOfIcyTouch m = Dungeon.hero.belongings.getItem(ElixirOfIcyTouch.class);
                m.detachAll(Dungeon.hero.belongings.backpack);
                QuestClear = true;
            }
            else {
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(FrostLeaf.class, "quest")));
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
        return super.act();
    }

    public static void spawn(Level level, int ppos) {
        FrostLeaf usa = new FrostLeaf();
        do {
            usa.pos = ppos;
        } while (usa.pos == -1);
        level.mobs.add(usa);
    }
}
