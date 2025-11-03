package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class SHISHIOH extends MeleeWeapon {
    {
        image = ItemSpriteSheet.SHISHIOH;
        hitSound = Assets.Sounds.HIT_KNIFE;
        hitSoundPitch = 1.22f;

        tier = 3;
        ACC = 1.2f;

        bones = false;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        int thisHP = attacker.HP;
        int thisHT = attacker.HT;
        if (thisHP <= thisHT/4) {
            damage *= 1.5f;

            if (Random.Int(2) < 1) {
                int healAmt = Math.round(damage * 0.1f);
                healAmt = Math.min(healAmt, attacker.HT - attacker.HP);

                if (healAmt > 0 && attacker.isAlive()) {

                    attacker.HP += healAmt;
                    attacker.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f, 1);
                    attacker.sprite.showStatus(CharSprite.POSITIVE, Integer.toString(healAmt));

                }
            }
        }
        else if (thisHP <= thisHT / 2){
            damage *= 1.3f;
            if (Random.Int(4) < 1) {
                int healAmt = Math.round(damage * 0.1f);
                healAmt = Math.min(healAmt, attacker.HT - attacker.HP);

                if (healAmt > 0 && attacker.isAlive()) {

                    attacker.HP += healAmt;
                    attacker.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f, 1);
                    attacker.sprite.showStatus(CharSprite.POSITIVE, Integer.toString(healAmt));

                }
            }
        }
        else if (thisHP <= thisHT - thisHT/4) {
            damage *= 1.1f;
        }
        return super.proc(attacker, defender, damage);
    }
}
