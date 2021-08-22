package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLightning;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.TenguDartTrap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class Thunderbolt extends MissileWeapon {
    {
        image = ItemSpriteSheet.LISKARM_DOLL;
        hitSound = Assets.Sounds.LIGHTNING;
        hitSoundPitch = 0.8f;

        tier = 3;
        baseUses = 12;
        sticky = false;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        // 적의 체력이 100%라면 2배의 피해를 주지만, 체력이 감소할수록 피해 배율이 감소합니다.
        damage *= 2f * (defender.HP / defender.HT);

        return super.proc(attacker, defender, damage);
    }
}
