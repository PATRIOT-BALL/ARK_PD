package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LoneJourney extends MeleeWeapon {
    public static final String AC_SP = "SP";

    {
        image = ItemSpriteSheet.SHURIKEN;
        hitSound = Assets.Sounds.HIT_SPEAR;
        hitSoundPitch = 1f;

        defaultAction = AC_SP;

        tier = 5;
        RCH = 2;
    }

    //Essentially it's a tier 4 weapon, with tier 3 base max damage, and tier 5 scaling.
    //equal to tier 4 in damage at +5

    @Override
    public int max(int lvl) {
        return 4 * (tier) +                    // 20 + 4
                lvl * (tier - 1);
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (attacker.buff(JourneyBuff_ice.class) != null) {
            Buff.detach(attacker, JourneyBuff_ice.class);
            Buff.affect(defender, Roots.class, 1f);
        }
        else if (attacker.buff(JourneyBuff_fire.class) != null) {
            Buff.detach(attacker, JourneyBuff_fire.class);
            damage *= 1.3f;
            Buff.affect(defender, Burning.class).reignite(defender, 3f);
        }
        else if (attacker.buff(JourneyBuff_heavy.class) != null) {
            Buff.detach(attacker, JourneyBuff_heavy.class);
            Buff.affect(defender, Silence.class, 2f);
        }

        SPCharge(20);
        updateQuickslot();
        return super.proc(attacker, defender, damage);
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_SP);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_SP) && this.isEquipped(hero)) {
            GameScene.show(
                    new WndOptions(Messages.get(this, "name"),
                            Messages.get(this, "wnddesc"),
                            Messages.get(this, "special1"),
                            Messages.get(this, "special2"),
                            Messages.get(this, "special3")) {

                        @Override
                        protected void onSelect(int index) {
                            if (index == 0) {
                                Buff.affect(hero, JourneyBuff_ice.class);
                                charge = 0;
                                updateQuickslot();
                            }
                            else if (index == 1) {
                                Buff.affect(hero, JourneyBuff_fire.class);
                                charge = 0;
                                updateQuickslot();
                            }
                            else if (index == 2) {
                                Buff.affect(hero, JourneyBuff_heavy.class);
                                charge = 0;
                                updateQuickslot();
                            }
                        }
                    });
        }
    }

    @Override
    public String status() {

        //if the artifact isn't IDed, or is cursed, don't display anything
        if (!isIdentified() || cursed) {
            return null;
        }
        //display as percent
        if (chargeCap == 100)
            return Messages.format("%d%%", charge);


        //otherwise, if there's no charge, return null.
        return null;
    }

    public static class JourneyBuff_ice extends Buff{}
    public static class JourneyBuff_fire extends Buff{}
    public static class JourneyBuff_heavy extends Buff{}
}