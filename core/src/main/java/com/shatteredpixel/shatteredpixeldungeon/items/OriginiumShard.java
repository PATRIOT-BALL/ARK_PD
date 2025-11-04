package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ActiveOriginium;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;


public class OriginiumShard extends Item {
    private static final String TXT_UPGRADED = "OriginiumShard";

    private static final float TIME_TO_UPGRADE = 1;

    private static final String AC_USE = "USE";

    {
        image = ItemSpriteSheet.ORIGINIUM;

        stackable = true;

    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_USE);
        return actions;
    }

        @Override
        public void execute (Hero hero, String action ){

            super.execute(hero, action);
            if (action.equals(AC_USE)) {
                hero.busy();

                hero.sprite.operate(hero.pos);

                detach(hero.belongings.backpack);

                Buff.affect(hero, ActiveOriginium.class).set(hero.HT * 0.1f);
                Sample.INSTANCE.play(Assets.Sounds.BURNING);
            }


        }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public int value() {
        return 20 * quantity;
    }
    }
