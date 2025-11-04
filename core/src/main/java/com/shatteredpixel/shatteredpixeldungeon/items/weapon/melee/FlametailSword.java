package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChenCombo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Combo;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class FlametailSword extends MeleeWeapon {
    {
        image = ItemSpriteSheet.FLAMETAIL;
        hitSound = Assets.Sounds.HIT_SPEAR;
        hitSoundPitch = 0.9f;

        tier = 4;
    }

    @Override
    public int max(int lvl) {
        return  4*(tier) +   //16 + 4, 조건부 2타
                lvl*(tier);   //scaling unchanged
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        boolean da = false;
        if (attacker.buff(FlametaillBuff.class) != null) {
            Buff.detach(attacker, FlametaillBuff.class);
            da = true;
        }
        if (da) {
                attacker.attack(defender);
                defender.sprite.bloodBurstA( defender.sprite.center(), 4 );
                defender.sprite.flash();
                if (attacker instanceof Hero && Dungeon.hero.subClass == HeroSubClass.GLADIATOR) {
                    Buff.affect(attacker, Combo.class).bounshit(defender);
                }
                else if (attacker instanceof Hero && Dungeon.hero.heroClass == HeroClass.CHEN) {
                    Buff.affect(attacker, ChenCombo.class).bounshit(defender);
                }
                damage *= 1.5f;
        }
        return super.proc(attacker, defender, damage);
    }

    public static class FlametaillBuff extends Buff {
    }
}
