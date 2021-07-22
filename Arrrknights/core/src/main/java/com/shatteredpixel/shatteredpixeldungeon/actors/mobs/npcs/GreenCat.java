package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss.Kaltsit;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.levels.RhodesLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.RhodesLevel3;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.SurfaceScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GreenCatSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

public class GreenCat extends NPC {
    {
        HP=HT=1000;
        spriteClass = GreenCatSprite.class;
        properties.add(Property.IMMOVABLE);
        properties.add(Property.NPC);
    }

    private boolean seenBefore = false;

    @Override
    public void damage( int dmg, Object src ) {
        // flee();
    }

    @Override
    public void add( Buff buff ) {
      // flee();
    }

    public void flee() {
        if (Dungeon.depth == 29) { this.yell(Messages.get(this, "fury"));
        sprite.killAndErase();
        CellEmitter.get( pos ).burst( ElmoParticle.FACTORY, 6 );

        destroy();

        Kaltsit not = new Kaltsit();
        not.pos = this.pos;
        GameScene.add(not);}
    }

    @Override
    protected Char chooseEnemy() {
        return null;
    }

    @Override
    protected boolean act() {

        if (Dungeon.level.heroFOV[pos] && Dungeon.hero.belongings.getItem(Amulet.class) != null) {
            if (!seenBefore) {
                yell( Messages.get(this, "wellcom", Dungeon.hero.heroClass.title() ) );
            }
            Notes.add( Notes.Landmark.GREENCAT );
            seenBefore = true;
        } else {
            seenBefore = false;
        }

        return super.act();
    }

    @Override
    public boolean interact(Char c) {
        sprite.turnTo(pos, c.pos);
        if (Dungeon.hero.belongings.getItem(Amulet.class) == null) {
            Game.runOnRenderThread(new Callback() {
                @Override
                public void call() {
                    GameScene.show(new WndMessage(Messages.get(Hero.class, "leave", Dungeon.hero.heroClass.title())));
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

    public static void spawn(RhodesLevel level, int ppos) {
        GreenCat Cat = new GreenCat();
        do {
            Cat.pos = ppos;
        } while (Cat.pos == -1);
        level.mobs.add(Cat);
    }

    public static void spawn(RhodesLevel3 level, int ppos) {
        GreenCat Cat = new GreenCat();
        do {
            Cat.pos = ppos;
        } while (Cat.pos == -1);
        level.mobs.add(Cat);
    }
}