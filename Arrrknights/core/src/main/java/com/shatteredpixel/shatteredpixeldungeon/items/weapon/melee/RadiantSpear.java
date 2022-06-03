package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RadiantKnight;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSunLight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfTenacity;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class RadiantSpear extends MeleeWeapon {
    {
        image = ItemSpriteSheet.NEARL_SPEAR;
        hitSound = Assets.Sounds.HIT_SPEAR;
        hitSoundPitch = 1.18f;

        tier = 5;
    }

    @Override
    public int max(int lvl) {
        return  5*(tier) +   //25 + 5
                lvl*(tier); }


    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (defender.buff(Blindness.class) != null) {
            Buff.affect(defender, Vulnerable.class, 3f); }

        if (defender.buff(Vulnerable.class) != null) {
            if (Dungeon.hero.belongings.getItem(RingOfSunLight.class) != null && Dungeon.hero.belongings.getItem(RingOfHaste.class) != null) {
                if (Dungeon.hero.belongings.getItem(RingOfSunLight.class).isEquipped(Dungeon.hero) && Dungeon.hero.belongings.getItem(RingOfHaste.class).isEquipped(Dungeon.hero)) {
                    damage += attacker.damageRoll() / 2;
                    return super.proc(attacker, defender, damage);
                }}
          damage += attacker.damageRoll() / 4;
        }

        return super.proc(attacker, defender, damage);
    }

    @Override
    public String desc() {
        String info = Messages.get(this, "desc");
        if (Dungeon.hero.belongings.getItem(RingOfSunLight.class) != null && Dungeon.hero.belongings.getItem(RingOfHaste.class) != null) {
            if (Dungeon.hero.belongings.getItem(RingOfSunLight.class).isEquipped(Dungeon.hero) && Dungeon.hero.belongings.getItem(RingOfHaste.class).isEquipped(Dungeon.hero))
                info += "\n\n" + Messages.get( RadiantSpear.class, "setbouns");}

        return info;
    }
}
