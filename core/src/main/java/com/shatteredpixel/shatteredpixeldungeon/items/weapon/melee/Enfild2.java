package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

public class Enfild2 extends MeleeWeapon {
    {
        image = ItemSpriteSheet.ANDREANA;
        hitSound = Assets.Sounds.HIT_SNIPER;
        hitSoundPitch = 0.9f;

        tier = 3;
        DLY = 3f; //0.33x speed
        RCH = 50;    //extra reach

        //also cannot surprise attack, see Hero.canSurpriseAttack
    }

    @Override
    public int min(int lvl) {
       return 8+lvl;
    }

    @Override
    public int max(int lvl) {
        return  8*(tier+1) +    // 48 + 15
                lvl*(tier+10);   //scaling unchanged
    }

    @Override
    public int value() { return super.value() + 100; }

    @Override
    public boolean isUpgradable() { return true; }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (charge >= chargeCap) {
            damage *= 1.3f;
            charge = 0;
        }
        else SPCharge(20);
        return super.proc(attacker, defender, damage);
    }

    @Override
    public int STRReq(int lvl) {
        return STRReq(tier+2, lvl); //20 base strength req, up from 18
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
}
