package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DobermannSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.PRTS_Sprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class PRTS extends NPC {
    private int hint = 0;

    {
        spriteClass = PRTS_Sprite.class;
        properties.add(Char.Property.IMMOVABLE);
        properties.add(Property.NPC);
    }

    public PRTS() {
        hint = Random.IntRange(0,11);
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

        switch (hint) {
            case 0: default:
                Game.runOnRenderThread(new Callback() {
                @Override
                public void call() {
                    GameScene.show(new WndMessage(Messages.get(PRTS.class, "hint1")));
                }
            });
                break;
            case 1:
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(PRTS.class, "hint2")));
                    }
                });
                break;
            case 2:
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(PRTS.class, "hint3")));
                    }
                });
                break;
            case 3:
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(PRTS.class, "hint4")));
                    }
                });
                break;
            case 4:
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(PRTS.class, "hint5")));
                    }
                });
                break;
            case 5:
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(PRTS.class, "hint6")));
                    }
                });
                break;
            case 6:
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(PRTS.class, "hint7")));
                    }
                });
                break;
            case 7:
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(PRTS.class, "hint8")));
                    }
                });
                break;
            case 8:
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(PRTS.class, "hint9")));
                    }
                });
                break;
            case 9:
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(PRTS.class, "hint10")));
                    }
                });
                break;
            case 10:
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show(new WndMessage(Messages.get(PRTS.class, "hint11")));
                    }
                });
                break;
        }

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
        hint = bundle.getInt(HINT);

    }
}
