package com.shatteredpixel.shatteredpixeldungeon.items.wands.SP;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SkillBook;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAmplified;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class StaffOfSuzuran extends DamageWand {
    {
        image = ItemSpriteSheet.WAND_SUZRAN;
    }

    public int min(int lvl){ return 3+lvl; }

    public int max(int lvl){
        return 4+3*lvl+ RingOfAmplified.DamageBonus(Dungeon.hero) * 3;
    }

    @Override
    protected void onZap( Ballistica bolt ) {

        Char ch = Actor.findChar( bolt.collisionPos );
        if (ch != null) {

            processSoulMark(ch, chargesPerCast());
            if (ch instanceof Mob) {
                if (((Mob) ch).state == ((Mob) ch).SLEEPING) {
                    Buff.affect(ch, MagicalSleep.class);
                    CellEmitter.get(ch.pos).start( Speck.factory( Speck.NOTE ), 0.1f, 2 );
                    Sample.INSTANCE.play(Assets.Sounds.RAY, 1, Random.Float(0.87f, 1.15f));
                }
                else {
                    ch.damage(damageRoll(), this);
                    Sample.INSTANCE.play(Assets.Sounds.HIT_MAGIC, 1, Random.Float(0.87f, 1.15f));
                    ch.sprite.burst(0xFFFFFFFF, buffedLvl() / 2 + 2);
                }
            }
            else {
                ch.damage(damageRoll(), this);
                Sample.INSTANCE.play(Assets.Sounds.HIT_MAGIC, 1, Random.Float(0.87f, 1.15f));
                ch.sprite.burst(0xFFFFFFFF, buffedLvl() / 2 + 2);
            }

        } else {
            Dungeon.level.pressCell(bolt.collisionPos);
        }
    }

    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        if (Dungeon.hero.belongings.getItem(SkillBook.class) != null) {
            SkillBook Item = Dungeon.hero.belongings.getItem(SkillBook.class);
            Item.SetCharge(1);
            SpellSprite.show(attacker, SpellSprite.CHARGE);
        }
    }

    @Override
    protected void fx(Ballistica bolt, Callback callback) {
        MagicMissile.boltFromChar(curUser.sprite.parent,
                MagicMissile.FORCE,
                curUser.sprite,
                bolt.collisionPos,
                callback);
        Sample.INSTANCE.play(Assets.Sounds.ZAP);
    }

    protected int initialCharges() {
        return 2;
    }

}
