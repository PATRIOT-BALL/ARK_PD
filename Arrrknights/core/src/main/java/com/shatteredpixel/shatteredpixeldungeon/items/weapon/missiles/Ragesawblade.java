package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Skeleton;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Ragesawblade extends MissileWeapon {
    private static ItemSprite.Glowing COL = new ItemSprite.Glowing( 0xFF4500);
    {
        image = ItemSpriteSheet.THROWING_STONE;
        hitSound = Assets.Sounds.HIT_CHAINSAW;
        hitSoundPitch = 1.1f;

        bones = false;

        tier = 3;
        baseUses = 5;
        sticky = false;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        Buff.affect(defender, Amok.class, 3f);
        return super.proc(attacker, defender, damage);
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return COL;
    }

    @Override
    public int value() {
        return super.value() * 2;
    }
}
