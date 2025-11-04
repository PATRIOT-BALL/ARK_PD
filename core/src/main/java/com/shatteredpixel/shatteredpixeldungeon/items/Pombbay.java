package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SnowParticle;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class Pombbay extends Item {
    public static final String AC_DRINK = "DRINK";
    {
        image = ItemSpriteSheet.POMBBAY;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_DRINK);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_DRINK)) {
            curUser = hero;
            curUser.sprite.showStatus( CharSprite.NEGATIVE, Messages.get(Pombbay.class,"ssibal") );
            this.detach(curUser.belongings.backpack);
            Sample.INSTANCE.play(Assets.Sounds.SHATTER);
        }
    }

    @Override
    protected void onThrow( int cell ) {
        if (Dungeon.level.map[cell] == Terrain.WELL || Dungeon.level.pit[cell]) {
            super.onThrow( cell );

        } else  {
            Dungeon.level.pressCell( cell );
            CellEmitter.get( cell ).burst(SnowParticle.FACTORY, 66 );
            Sample.INSTANCE.play(Assets.Sounds.SHATTER);
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

}
