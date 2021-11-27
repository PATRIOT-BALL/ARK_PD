package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Gluttony extends MeleeWeapon {
    public static final String AC_ZAP = "ZAP";
    {
        image = ItemSpriteSheet.CARNEL;
        hitSound = Assets.Sounds.HIT_GLUTONY;
        hitSoundPitch = 1f;

        tier = 4;
        DLY = 0.75f;

        defaultAction = AC_ZAP;
    }

    @Override
    public int max(int lvl) {
        return  4*(tier) + 2 +  //18durable_projectiles + 4
                lvl*(tier); }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_ZAP);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_ZAP)) {
            if (!isEquipped(hero)) return;

            if (!cursed) {
                if (charge >= 50) {
                    for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
                        if (Dungeon.level.adjacent(mob.pos, hero.pos) && mob.alignment != Char.Alignment.ALLY) {
                            int dmg = hero.damageRoll();
                            dmg *= 1.6f;
                            CellEmitter.get( mob.pos ).burst( Speck.factory( Speck.WOOL ), 3 );
                            mob.damage(dmg, this);
                        }
                    }
                    CellEmitter.get( curUser.pos ).burst( Speck.factory( Speck.WOOL ), 10 );
                    charge -= 50;
                    Sample.INSTANCE.play(Assets.Sounds.BLAST);
                    hero.sprite.zap(hero.pos);
                    Invisibility.dispel();
                    updateQuickslot();
                    hero.spendAndNext(1f);
                }
            }
            else {
                Buff.affect(hero, Roots.class, 5f);
                cursedKnown = true;
                charge -= 50;
                Invisibility.dispel();
                updateQuickslot();
                hero.spendAndNext(1f);
            }
        }
    }

    @Override
    public String status() {
        if (chargeCap == 100)
            return Messages.format("%d%%", charge);
        return null;
    }
}
