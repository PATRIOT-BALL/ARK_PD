package com.shatteredpixel.shatteredpixeldungeon.items.wands.SP;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.ChainHook;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAmplified;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class StaffOfLeaf extends DamageWand {
    private static ItemSprite.Glowing COL = new ItemSprite.Glowing( 0x87CEFA );
    {
        image = ItemSpriteSheet.WAND_FROST;
    }

    public int min(int lvl){
        return 1;
    }

    public int max(int lvl){
        return 5+5*lvl+ RingOfAmplified.DamageBonus(Dungeon.hero) * 5;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return COL;
    }

    @Override
    protected void onZap(Ballistica bolt) {

        Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
        if (heap != null) {
            heap.freeze();
        }

        Char ch = Actor.findChar(bolt.collisionPos);
        if (ch != null){

            int damage = damageRoll();

            if (ch.buff(Frost.class) != null){
                return; //do nothing, can't affect a frozen target
            }
            if (ch.buff(Chill.class) != null){
                //6.67% less damage per turn of chill remaining, to a max of 10 turns (50% dmg)
                float chillturns = Math.min(10, ch.buff(Chill.class).cooldown());
                damage = (int)Math.round(damage * Math.pow(1.0333f, chillturns));
            } else {
                ch.sprite.burst( 0xFF99CCFF, buffedLvl() / 2 + 2 );
            }

            processSoulMark(ch, chargesPerCast());
            ch.damage(damage, this);
            Sample.INSTANCE.play( Assets.Sounds.FROST, 1, 1.1f * Random.Float(0.87f, 1.15f) );

            if (ch.isAlive()){
                if (Dungeon.level.water[ch.pos])
                    Buff.affect(ch, Chill.class, 5+buffedLvl());
                else
                    Buff.affect(ch, Chill.class, 3+buffedLvl());
            }
        } else {
            Dungeon.level.pressCell(bolt.collisionPos);
        }
    }

    @Override
    protected void fx(Ballistica bolt, Callback callback) {
        MagicMissile.boltFromChar(curUser.sprite.parent,
                MagicMissile.FROST,
                curUser.sprite,
                bolt.collisionPos,
                callback);
        Sample.INSTANCE.play(Assets.Sounds.ZAP);
    }

    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        Chill chill = defender.buff(Chill.class);
        if (chill != null && Random.IntRange(2, (int)Chill.DURATION) <= chill.cooldown()){
            //need to delay this through an actor so that the freezing isn't broken by taking damage from the staff hit.
            new FlavourBuff(){
                {actPriority = VFX_PRIO;}
                public boolean act() {
                    Buff.affect(target, Frost.class, Frost.DURATION);
                    return super.act();
                }
            }.attachTo(defender);
        }
    }

}