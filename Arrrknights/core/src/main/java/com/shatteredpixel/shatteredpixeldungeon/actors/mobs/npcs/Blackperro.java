package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SkillBook;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSunLight;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.levels.RhodesLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DobermannSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GreenCatSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndEazyMode;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndImp;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

import java.text.DecimalFormat;

public class Blackperro extends NPC {
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
        if (Challenges.activeChallenges() == 0 && Dungeon.hero.lvl == 1) {
            if (Dungeon.eazymode == -1) {
            Game.runOnRenderThread(new Callback() {
                @Override
                public void call() {
                    GameScene.show( new WndEazyMode( Blackperro.this ) );
                }
            });}
            else  sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "active"));
        }
         else {
            sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "no"));
        }
        return true;
    }

    public static void spawn(RhodesLevel level, int ppos) {
        Blackperro perro = new Blackperro();
        do {
            perro.pos = ppos;
        } while (perro.pos == -1);
        level.mobs.add(perro);
    }

}
