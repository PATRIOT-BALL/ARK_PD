package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CustomeSet;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.WoundsofWar;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAssassin;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class WintersScar extends MeleeWeapon {
    {
        image = ItemSpriteSheet.WINTER;
        hitSound = Assets.Sounds.FROST;
        hitSoundPitch = 1.12f;

        tier = 5;
    }

    @Override
    public int max(int lvl) {
        return  4*(tier+1) +    //20 base
                lvl*(tier+1);
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (defender.buff(Chill.class) != null) damage = Math.round(damage * 1.25f);

        if (defender instanceof Mob && ((Mob) defender).surprisedBy(attacker)) {
            Buff.affect(defender, Chill.class, 4f);
        }
        return super.proc(attacker, defender, damage);
    }

    @Override
    public int damageRoll(Char owner) {
        if (owner instanceof Hero) {
            Hero hero = (Hero)owner;
            Char enemy = hero.enemy();
            if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
                //deals 33% toward max to max on surprise, instead of min to max.
                int diff = max() - min();
                int damage;
                if (setbouns()) {
                    damage = augment.damageFactor(Random.NormalIntRange(
                            min() + Math.round(diff*0.45f),
                            max()));
                }
                else {damage = augment.damageFactor(Random.NormalIntRange(
                        min() + Math.round(diff*0.25f),
                        max()));}
                int exStr = hero.STR() - STRReq();
                if (exStr > 0) {
                    damage += Random.IntRange(0, exStr);
                }
                return damage;
            }
        }
        return super.damageRoll(owner);
    }

    private boolean setbouns() {
        if (Dungeon.hero.belongings.getItem(RingOfAssassin.class) != null && Dungeon.hero.belongings.getItem(WoundsofWar.class) != null) {
            if (Dungeon.hero.belongings.getItem(RingOfAssassin.class).isEquipped(Dungeon.hero) && Dungeon.hero.belongings.getItem(WoundsofWar.class).isEquipped(Dungeon.hero))
                return true;
        }
        return false;
    }

    @Override
    public String desc() {
        String info = Messages.get(this, "desc");
        if (setbouns()) info += "\n\n" + Messages.get( this, "setbouns");

        return info;
    }
}
