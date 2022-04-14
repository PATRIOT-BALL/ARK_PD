package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

public class Heat extends Buff implements ActionIndicator.Action {
    public enum State{
        NORMAL, OVERHEAT
    }
    private Heat.State state = Heat.State.NORMAL;

    private float power = 0;
    private final float powercap = 100;
    private int overheatlife = 0;

    public float power() { return power;}
    public Heat.State state() {return state;}

    private static final String STATE = "state";
    private static final String POWER = "power";
    private static final String HEAL = "overheatlife";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(STATE, state);
        bundle.put(POWER, power);
        bundle.put(HEAL, overheatlife);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        state = bundle.getEnum(STATE, Heat.State.class);
        power = bundle.getFloat(POWER);
        overheatlife = bundle.getInt(HEAL);

        if (power >= 50 && state != State.OVERHEAT) ActionIndicator.setAction(this);
    }

    public void Timeproc(float time) {
        if (state == State.NORMAL) {
            power = Math.min(power + (time * 2.5f), powercap);
        } else {
            power = Math.max(0, power - (time * 5f));
            if (power == 0) {
                int Heal = overheatlife;
                if (Dungeon.hero.hasTalent(Talent.HEAT_OF_RECOVERY)) {
                    Heal *= 1f + (Dungeon.hero.pointsInTalent(Talent.HEAT_OF_RECOVERY) * 0.2f);
                }
                target.HP = Math.min(target.HP + Heal, target.HT);
                target.sprite.showStatus(CharSprite.POSITIVE, "+%dHP", Heal);

                state = State.NORMAL;
                overheatlife = 0;
            }
        }

        if (power >= 50 && state != State.OVERHEAT) ActionIndicator.setAction(this);
        else ActionIndicator.clearAction(this);
    }

    @Override
    public int icon() { return BuffIndicator.FIRE; }

    @Override
    public float iconFadePercent() {
        if (state == State.OVERHEAT) return 0f;
        else if (power >= 50) return 0.5f;
        else return 1f;
    }

    @Override
    public Image getIcon() { return Icons.get(Icons.COMBO); }

    @Override
    public void doAction() {

        Hero hero = Dungeon.hero;
        if (hero == null) return;

        if (state == State.OVERHEAT) return;
        if (power < 50) return;

        CellEmitter.get( target.pos ).burst(FlameParticle.FACTORY, 10 );
        Sample.INSTANCE.play( Assets.Sounds.BURNING, 1.35f, 1.16f );

        state = State.OVERHEAT;

        int reduHP = target.HT;
        reduHP *= power / 200;

        int reduValue = reduHP - target.HP;
        overheatlife = Math.min(reduHP, reduHP - reduValue);
        overheatlife *= 0.5f;
        overheatlife = Math.max(overheatlife, 0);


        target.HP = Math.max(1, target.HP - reduHP);



        target.sprite.showStatus(CharSprite.NEGATIVE, "%d", reduHP);
        target.sprite.showStatus(CharSprite.NEGATIVE, toString());

        ActionIndicator.clearAction(this);
    }

    public void powerDown() {
        power -= 5;
    }

    @Override
    public String toString() {
        switch (state){
            case NORMAL: default:
                return Messages.get(this, "normal");
            case OVERHEAT:
                return Messages.get(this, "overheat");
        }
    }

    @Override
    public String desc() {
        switch (state) {
            case NORMAL:
            default:
                return Messages.get(this, "normal_desc", Math.floor(power));
            case OVERHEAT:
                return Messages.get(this, "overheat_desc", Math.floor(power));
        }
    }
}
