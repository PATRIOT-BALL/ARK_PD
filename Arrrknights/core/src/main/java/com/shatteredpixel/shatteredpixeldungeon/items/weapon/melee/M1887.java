package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class M1887 extends MeleeWeapon {
    {
        image = ItemSpriteSheet.ENFILD;
        hitSound = Assets.Sounds.SKILL_BEEP;
        hitSoundPitch = 1f;

        tier = 4;
        ACC = 0.9f; // -10%
    }

    @Override
    public int max(int lvl) {
        return  4*(tier-1) +    //12 + 3 (one target = x2)
                lvl*(tier-1);
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        int target = 1;
        for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
            if (Dungeon.level.adjacent(mob.pos, defender.pos) && mob.alignment != Char.Alignment.ALLY) {
                mob.damage(Dungeon.hero.damageRoll()-mob.drRoll(), this);
                target++;
            }}

        if (target == 1) damage *= 2;

        return super.proc(attacker, defender, damage);
    }

}
