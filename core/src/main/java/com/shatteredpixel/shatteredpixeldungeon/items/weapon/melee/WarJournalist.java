package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Twilight;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.WoundsofWar;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMistress;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class WarJournalist extends MeleeWeapon {
    public static final String AC_ZAP = "ZAP";
    {
        image = ItemSpriteSheet.SCENE;
        hitSound = Assets.Sounds.HIT;
        hitSoundPitch = 1.03f;

        defaultAction = AC_ZAP;

        tier = 4;
        RCH = 3;    //lots of extra reach
    }

    @Override
    public int max(int lvl) {
        return  3*(tier+1) +    //15 + 4
                lvl*(tier);
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_ZAP);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        boolean active = false;

        if (action.equals(AC_ZAP)) {
            if (charge >= chargeCap && Dungeon.hero.belongings.weapon == this) {
                for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                    if (mob.alignment == Char.Alignment.ENEMY && !mob.properties().contains(Char.Property.BOSS) && !mob.properties().contains(Char.Property.MINIBOSS)) {
                        Buff.affect(mob, Blindness.class, 3f);
                        Buff.affect(mob, PanoramaBuff.class, PanoramaBuff.DURATION);
                        Buff.append(Dungeon.hero, TalismanOfForesight.CharAwareness.class, PanoramaBuff.DURATION).charID = mob.id();
                      active = true;}
                    if (active) break;
                }

                GameScene.flash( 0x80FFFFFF );
                Sample.INSTANCE.play( Assets.Sounds.LIGHTNING, 1f, 1.32f );
                charge = 0;
                hero.spendAndNext(1f);
            }
        }
        updateQuickslot();
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (isSetbouns()) SPCharge(3);
        else SPCharge(2);
        return super.proc(attacker, defender, damage);
    }

    @Override
    public String desc() {
        String info = Messages.get(this, "desc");
        if (isSetbouns()) info += "\n\n" + Messages.get( WarJournalist.class, "setbouns");

        return info;
    }

    @Override
    public String status() {

        //if the artifact isn't IDed, or is cursed, don't display anything
        if (!isIdentified() || cursed) {
            return null;
        }
        //display as percent
        if (chargeCap == 100)
            return Messages.format("%d%%", charge);

        //otherwise, if there's no charge, return null.
        return null;
    }

    private boolean isSetbouns() {
        if (Dungeon.hero.belongings.getItem(WoundsofWar.class) != null) {
            if (Dungeon.hero.belongings.getItem(WoundsofWar.class).isEquipped(Dungeon.hero))
                return true;
        }
        return false;
    }

    public static class PanoramaBuff extends FlavourBuff {

        private static final float DURATION = 20f;
        private static final float SET_DURATION = 15f;

        private boolean isDie = true;

        @Override
        public boolean act() {
            ((Mob) target).state = ((Mob) target).HUNTING;
            ((Mob) target).beckon(Dungeon.hero.pos);
            return super.act();
        }

        public void Complete() {
            isDie = false;
        }

        @Override
        public void detach() {
            super.detach();

            if(isDie) target.damage(999999, this);
            else target.sprite.showStatus( CharSprite.NEGATIVE, "$%$!!");
        }

        private static final String ISDIE = "left";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(ISDIE, isDie);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            isDie = bundle.getBoolean(ISDIE);
        }
    }
}
