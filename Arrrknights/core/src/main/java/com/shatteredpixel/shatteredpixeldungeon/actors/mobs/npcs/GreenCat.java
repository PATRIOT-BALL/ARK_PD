package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss.Sentinel;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NPC;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.levels.GroundLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.SewerLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.SurfaceScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GreenCatSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Guard_operSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

public class GreenCat extends NPC {
    {
        spriteClass = GreenCatSprite.class;
    }

    @Override
    public int defenseSkill( Char enemy ) {
        return INFINITE_EVASION;
    }

    @Override
    public void damage( int dmg, Object src ) {
    }

    @Override
    public boolean interact(Char c) {
        sprite.turnTo( pos, c.pos );
        if (Dungeon.hero.belongings.getItem(Amulet.class) == null) {
            Game.runOnRenderThread(new Callback() {
                @Override
                public void call() {
                    GameScene.show(new WndMessage(Messages.get(Hero.class, "leave",Dungeon.hero.heroClass.title()) ));
                }
            });
        } else {
            Badges.silentValidateHappyEnd();
            Dungeon.win(Amulet.class);
            Dungeon.deleteGame(GamesInProgress.curSlot, true);
            Game.switchScene(SurfaceScene.class);
        }
        return true;
    }

    public static void spawn(GroundLevel level) {
            GreenCat Cat = new GreenCat();
            do {
                Cat.pos = level.exit;
            } while (Cat.pos == -1);
            level.mobs.add(Cat);
    }
}