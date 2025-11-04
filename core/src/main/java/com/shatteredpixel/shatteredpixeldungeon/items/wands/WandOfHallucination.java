package com.shatteredpixel.shatteredpixeldungeon.items.wands;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hallucination;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class WandOfHallucination extends Wand {
    {
        image = ItemSpriteSheet.WAND_PODENCO;
    }

    @Override
    protected void onZap( Ballistica bolt ) {

        Char ch = Actor.findChar( bolt.collisionPos );
        if (ch != null) {

            processSoulMark(ch, chargesPerCast());
            if (ch.buff(Cripple.class) != null) Buff.affect(ch, Hallucination.class).set(Hallucination.DURATION);
            else Buff.affect(ch, Cripple.class, 4 + buffedLvl());
            Sample.INSTANCE.play( Assets.Sounds.HIT_MAGIC, 1, Random.Float(1.33f, 1.47f) );

            ch.sprite.burst(0xFFFFFFFF, buffedLvl() / 2 + 2);

        } else {
            Dungeon.level.pressCell(bolt.collisionPos);
        }
    }

    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        if (defender.buff(Hallucination.class) != null)
        {
            int dmg = damage / 4;
            defender.damage(dmg, curUser);
        }

    }

    protected int initialCharges() {
        return 2;
    }
}
