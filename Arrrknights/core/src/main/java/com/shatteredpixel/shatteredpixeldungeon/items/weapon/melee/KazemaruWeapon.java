package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BeeSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class KazemaruWeapon extends MeleeWeapon {
    {
        image = ItemSpriteSheet.DUSK;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.11f;

        tier = 4;
    }

    @Override
    public int max(int lvl) {
        return  5*(tier) +
                lvl*(tier+1);
    }




    public static class KazemaruSummon extends Mob {
        {
            spriteClass = BeeSprite.class;

            flying = true;
            state = WANDERING;
            alignment = Alignment.ALLY;
        }


        public void GetWeaponLvl(int wlvl) {
            maxLvl = wlvl;
        }
    }
}
