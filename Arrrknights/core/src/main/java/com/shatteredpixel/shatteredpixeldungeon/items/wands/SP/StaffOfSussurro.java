package com.shatteredpixel.shatteredpixeldungeon.items.wands.SP;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAmplified;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class StaffOfSussurro extends DamageWand {
    {
        image = ItemSpriteSheet.WAND_SUSSURRO;
    }

    public int min(int lvl) {
        return 1 + lvl;
    }

    public int max(int lvl) { return 3 + lvl + RingOfAmplified.DamageBonus(Dungeon.hero);
    }

    private int totChrgUsed = 0;
    private int HealCount = 0;

    private int chargeLimit(int heroLvl) {
            float lvl = buffedLvl();
            return Math.round(8 + Dungeon.hero.lvl + (15 * lvl));
        }

    @Override
    protected void onZap(Ballistica bolt) {

        Char ch = Actor.findChar(bolt.collisionPos);
        if (ch != null) {

            processSoulMark(ch, chargesPerCast());
            ch.damage(damageRoll(), this);
            HealCount++;
            Sample.INSTANCE.play(Assets.Sounds.HIT_MAGIC, 1, Random.Float(0.87f, 1.15f));

            ch.sprite.burst(0xFFFFFFFF, buffedLvl() / 2 + 2);

        } else {
            Dungeon.level.pressCell(bolt.collisionPos);
        }

        if (HealCount == 2) {
            if (totChrgUsed < chargeLimit(Dungeon.hero.lvl)) {
                Heal(false);
            } else Heal(true);
        }
    }

    private void Heal(boolean isLimit) {
        if (Dungeon.hero.HP < Dungeon.hero.HT) {
            int Heal = 2 + buffedLvl();
            if (isLimit == false) {
                totChrgUsed++;
            } else {
                Heal /= 2;
            }
            Dungeon.hero.HP = Math.min(Dungeon.hero.HP + Heal, Dungeon.hero.HT);
            Dungeon.hero.sprite.emitter().burst(Speck.factory(Speck.HEALING), 2 + buffedLvl() / 2);
            Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, "+%dHP", Heal);
        }

        HealCount = 0;
    }

    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        if (Random.Int(4) < 2)
            Buff.prolong( defender, Weakness.class, 1f+staff.buffedLvl() / 2);
    }

    protected int initialCharges() {
        return 3;
    }

    @Override
    public String statsDesc() {
        int chargeLeft = chargeLimit(Dungeon.hero.lvl) - totChrgUsed;
        if (levelKnown)
            return Messages.get(this, "stats_desc", min(), max(), 2 + buffedLvl(), Math.max(chargeLeft, 0));
        else
            return Messages.get(this, "stats_desc", 2, 3, 2, chargeLimit(Dungeon.hero.lvl));
    }

    private static final String TOTAL = "totChrgUsed";
    private static final String OVER = "HealCount";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(TOTAL, totChrgUsed);
        bundle.put(OVER, HealCount);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        totChrgUsed = bundle.getInt(TOTAL);
        HealCount = bundle.getInt(OVER);
    }
}
