package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfTenacity;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class FolkSong extends MeleeWeapon {
    {
        image = ItemSpriteSheet.SPECTER;
        hitSound = Assets.Sounds.HIT_CHAINSAW2;
        hitSoundPitch = 1f;

        tier = 4;
    }

    @Override
    public int max(int lvl) {
        return  5*(tier) + 2 +  // 22 + 4
                lvl*(tier);
    }


    @Override
    public int proc(Char attacker, Char defender, int damage) {
        float dmgbouns = 1f;
        if (Dungeon.level.map[attacker.pos] == Terrain.WATER) {
            dmgbouns += 0.15f;
        }
        if (Dungeon.level.map[defender.pos] == Terrain.WATER) {
            dmgbouns += 0.15f;
        }

        damage *= dmgbouns;
        return super.proc(attacker, defender, damage);
    }

    @Override
    public String desc() {
        String info = Messages.get(this, "desc");
        if (Dungeon.hero.belongings.getItem(RingOfTenacity.class) != null) {
            if (Dungeon.hero.belongings.getItem(RingOfTenacity.class).isEquipped(Dungeon.hero))
                info += "\n\n" + Messages.get( FolkSong.class, "setbouns");}

        return info;
    }
}
