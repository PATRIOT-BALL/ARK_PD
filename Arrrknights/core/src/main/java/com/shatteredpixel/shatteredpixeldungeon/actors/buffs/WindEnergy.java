package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

public class WindEnergy extends Buff implements ActionIndicator.Action {
    private int Energy = 0;

    private static final int EnergyCap = 150;
    private static final String ENERGY = "Energy";

    {
        type = buffType.POSITIVE;
    }

    @Override
    public int icon() { return BuffIndicator.PARALYSIS; }

    @Override
    public float iconFadePercent() {
        if (Energy < 100) return 1f;
        else return 0f;
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", Energy);
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(ENERGY, Energy);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        Energy = bundle.getInt(ENERGY);
        if (Energy >= 100) ActionIndicator.setAction(this);
    }

    public void GetEnergy(int value) {
        if (target.buff(StomeCharge.class) != null) return;

        Energy = Math.min(EnergyCap, Energy+value);
        if (Energy >= 100) ActionIndicator.setAction(this);
    }

    public void UseEnergy(int value) {
        if (target.buff(StomeCharge.class) != null) return;

        Energy = Math.max(0, Energy-value);
        if (Energy < 100) ActionIndicator.clearAction(this);
    }

    public int Eneray() { return  Energy;}
    public void Eneray(int n) { Energy = n; }

    @Override
    public Image getIcon() { return Icons.get(Icons.TALENT); }

    @Override
    public void doAction() {

        Hero hero = Dungeon.hero;
        if (hero == null) return;

        if (Energy < 100) return;

        UseEnergy(100);

        CellEmitter.get( target.pos ).burst( Speck.factory( Speck.DUST ), 10 );
        Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
        Buff.affect(target, StomeCharge.class, StomeCharge.DURATION);
    }
}
