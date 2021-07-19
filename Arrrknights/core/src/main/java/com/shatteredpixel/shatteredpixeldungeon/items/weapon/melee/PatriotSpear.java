package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;


import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PatriotSpear extends MeleeWeapon {
    {
        image = ItemSpriteSheet.BATTLE_AXE;
        hitSound = Assets.Sounds.HIT_SPEAR;
        hitSoundPitch = 1.01f;

        tier = 5;
        DLY = 2f; // 공격속도 50%
    }

    @Override
    public int max(int lvl) {
        return  2*(tier+1) +    //12
                lvl*(tier-3);   // +2
    }


    @Override
    public int proc(Char attacker, Char defender, int damage) {

        for (int count = 0; count<3; count++) {
            if (defender.isAlive()) {
                defender.damage(attacker.damageRoll() - defender.drRoll(), attacker);
                defender.sprite.burst(CharSprite.NEGATIVE, 10);
            }
        }

        return super.proc(attacker, defender, damage);
    }

    @Override
    public int defenseFactor( Char owner ) {
        return 4+2*buffedLvl();     //4 extra defence, plus 2 per level;
    }

    public String statsInfo(){
        if (isIdentified()){
            return Messages.get(this, "stats_desc", 4+2*buffedLvl());
        } else {
            return Messages.get(this, "typical_stats_desc", 4);
        }
    }
}
