package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Niansword extends MeleeWeapon {
    {
        image = ItemSpriteSheet.NIANSWORD;
        hitSound = Assets.Sounds.HIT_RINGOUT;
        hitSoundPitch = 1f;

        tier = 5;
    }


    @Override
    public int max(int lvl) {
        return  Math.round(2.5f*(tier+1)) +
                lvl*(tier-2);
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        return super.proc(attacker, defender, damage);
    }

    @Override
    public int defenseFactor( Char owner ) {
        return 2+buffedLvl() / 2;    // 0 + 0.
    }

    public String statsInfo(){
        if (isIdentified()){
            return Messages.get(this, "stats_desc", 2+buffedLvl() / 2, 3+3*buffedLvl());
        } else {
            return Messages.get(this, "typical_stats_desc", 2,3);
        }

    }
}
