package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Combo;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class FlametailSword extends MeleeWeapon {
    {
        image = ItemSpriteSheet.BATTLE_AXE;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 0.9f;

        tier = 4;
        DLY = 0.9f;
    }

    @Override
    public int max(int lvl) {
        return  4*(tier) + 2 +   //20 base, down from 25
                lvl*(tier+1);   //scaling unchanged
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (attacker.buff(FlametaillBuff.class) != null) {
                attacker.attack(defender);
                defender.sprite.bloodBurstA( defender.sprite.center(), 4 );
                defender.sprite.flash();
                if (attacker instanceof Hero && Dungeon.hero.subClass == HeroSubClass.GLADIATOR) {
                    Buff.affect(attacker, Combo.class).bounshit(defender);
            }
                Buff.detach(attacker, FlametaillBuff.class);
        }
        return super.proc(attacker, defender, damage);
    }

    public static class FlametaillBuff extends Buff {
    }
}
