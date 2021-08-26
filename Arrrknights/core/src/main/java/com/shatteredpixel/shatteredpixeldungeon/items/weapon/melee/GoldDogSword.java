package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class GoldDogSword extends MeleeWeapon {
    public static final String AC_ZAP = "ZAP";
    {
        image = ItemSpriteSheet.GOLDDOG;
        hitSound = Assets.Sounds.HIT_KNIFE;
        hitSoundPitch = 1.17f;

        defaultAction = AC_ZAP;

        tier = 4;
    }

    private int charge = 0;
    private int chargeCap = 12;

    @Override
    public int max(int lvl) {
        return  4*(tier) +   //16 + 4
                lvl*(tier);
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_ZAP);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_ZAP) && Dungeon.hero.belongings.weapon == this) {
            if (charge <= 0) {
                charge = chargeCap;
                hero.sprite.showStatus(CharSprite.POSITIVE, Messages.get(GoldDogSword.class, "charge"));
                updateQuickslot();
                curUser.spendAndNext(3f);
            } else
                hero.sprite.showStatus(CharSprite.NEGATIVE, Messages.get(GoldDogSword.class, "charge_fail"));
        }
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (charge > 0) {
            damage *= 1 + (charge/12);
            charge--;
            updateQuickslot(); }
        return super.proc(attacker, defender, damage);
    }

    @Override
    public String status() {
        int a = (100 * charge) / 12;
        if (this.isIdentified()) return "+" + a + "%";
        else return null;}

    private static final String CHARGE = "charge";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(CHARGE, charge);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        if (chargeCap > 0) charge = Math.min(chargeCap, bundle.getInt(CHARGE));
        else charge = bundle.getInt(CHARGE);
    }
}
