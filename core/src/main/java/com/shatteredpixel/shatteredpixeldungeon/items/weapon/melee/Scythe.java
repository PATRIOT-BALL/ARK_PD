package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfFuror;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Scythe extends MeleeWeapon {
    {
        image = ItemSpriteSheet.SICKEL;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.17f;

        tier = 4;
    }

    private int HealCount = 0;

    @Override
    public int max(int lvl) {
        return  4*(tier) - 2 +   //14 + 4
                lvl*(tier); }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        HealCount++;
        int extratarget = 0;
        if (attacker instanceof Hero) {
            for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
                if (Dungeon.level.adjacent(mob.pos, defender.pos) && mob.alignment != Char.Alignment.ALLY) {
                    int dmg = Dungeon.hero.damageRoll() - Math.max(defender.drRoll(), defender.drRoll());
                    mob.damage(dmg, this);
                    extratarget++;
                    HealCount++;
                }
            }
        }

        float bounsdmg = Math.min(1.5f, 1f+(extratarget*0.1f));

        if (Dungeon.hero.belongings.getItem(RingOfFuror.class) != null) {
            if (Dungeon.hero.belongings.getItem(RingOfFuror.class).isEquipped(Dungeon.hero) && defender.properties().contains(Char.Property.BOSS))
                bounsdmg += 0.35f;
        }

        damage = Math.round(damage * bounsdmg);

        Heal(attacker);

        return super.proc(attacker, defender, damage);
    }

    @Override
    public int damageRoll(Char owner) {
        if (owner instanceof Hero) {
            Hero hero = (Hero)owner;
            Char enemy = hero.enemy();
            if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
                //deals 67% toward max to max on surprise, instead of min to max.
                int diff = max() - min();
                int damage = augment.damageFactor(Random.NormalIntRange(
                        min() + Math.round(diff*0.25f),
                        max()));
                int exStr = hero.STR() - STRReq();
                if (exStr > 0) {
                    damage += Random.IntRange(0, exStr);
                }
                return damage;
            }
        }
        return super.damageRoll(owner);
    }


    private void Heal(Char attacker) {
        if (HealCount >= 8) {
            int heal = 3 + level();

            if (attacker instanceof Hero) {
            if (Dungeon.hero.lvl > 7 && Dungeon.depth < 5) {
                heal /= 4;
            }
            else if (Dungeon.hero.lvl > 12 && Dungeon.depth < 10) {
                heal /= 4;
            }
            else if (Dungeon.hero.lvl > 18 && Dungeon.depth < 15) {
                heal /= 4;
            }
            else if (Dungeon.hero.lvl > 23 && Dungeon.depth < 20) {
                heal /= 4;
            }}

            attacker.HP = Math.min(attacker.HP + heal, attacker.HT);
            attacker.sprite.emitter().burst(Speck.factory(Speck.HEALING), 2);
            attacker.sprite.showStatus(CharSprite.POSITIVE, "+%dHP", heal);
            HealCount = 0;
        }
    }

    @Override
    public String status() {
        if (this.isIdentified()) return HealCount + "/" + 8;
    else return null;}

    @Override
    public String desc() {
        String info = Messages.get(this, "desc");
        if (Dungeon.hero.belongings.getItem(RingOfFuror.class) != null) {
            if (Dungeon.hero.belongings.getItem(RingOfFuror.class).isEquipped(Dungeon.hero))
                info += "\n\n" + Messages.get( Scythe.class, "setbouns");}

        return info;
    }


    private static final String HEALPOINT = "HealCount";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(HEALPOINT, HealCount);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        HealCount = bundle.getInt(HEALPOINT);
    }
}
