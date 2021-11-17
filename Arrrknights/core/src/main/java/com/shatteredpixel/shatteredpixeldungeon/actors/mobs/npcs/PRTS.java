package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.PRTS_Sprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class PRTS extends NPC {
    private String hint;

    private static final String[] LINE_KEYS = {
            "hint1", "hint2", "hint3", "hint4", "hint5", "hint6", "hint7",
            "hint8", "hint9", "hint10", "hint11", "hint12", "hint13", "hint14",
    };

    {
        spriteClass = PRTS_Sprite.class;
        properties.add(Char.Property.IMMOVABLE);
        properties.add(Property.NPC);
    }

    public PRTS() { hint = Random.element(LINE_KEYS);
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

        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {
                GameScene.show(new WndMessage(Messages.get(PRTS.class, hint)));
            }
        });

        return true;
    }

    private static final String HINT = "hint";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( HINT, hint );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        hint = bundle.getString(HINT);

    }
}
