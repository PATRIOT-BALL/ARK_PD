package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HornOfPlenty;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Naginata extends MeleeWeapon {
    {
        image = ItemSpriteSheet.NAGINATA;
        hitSound = Assets.Sounds.HIT_SPEAR;
        hitSoundPitch = 1.18f;

        tier = 4;
        DLY = 1.25f;
        RCH = 2;

        bones = false;
    }

    @Override
    public int max(int lvl) {
        if (Dungeon.hero.belongings.getItem(HornOfPlenty.class) != null) {
            if (Dungeon.hero.belongings.getItem(HornOfPlenty.class).isEquipped(Dungeon.hero)) {
              return 5*(tier) + // 20 + 6
                     lvl*(tier+2);
            }}
        return  5*(tier) +   //20 + 5
                lvl*(tier+1);
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        return super.proc(attacker, defender, damage);
    }

    @Override
    public String desc() {
        String info = Messages.get(this, "desc");
        if (Dungeon.hero.belongings.getItem(HornOfPlenty.class) != null) {
            if (Dungeon.hero.belongings.getItem(HornOfPlenty.class).isEquipped(Dungeon.hero))
                info += "\n\n" + Messages.get( Naginata.class, "setbouns");}

        return info;
    }
}
