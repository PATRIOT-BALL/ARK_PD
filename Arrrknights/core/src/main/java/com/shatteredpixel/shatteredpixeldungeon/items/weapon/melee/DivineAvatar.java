package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SkillBook;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSunLight;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class DivineAvatar extends MeleeWeapon {
    public static final String AC_ZAP = "ZAP";
    {
        image = ItemSpriteSheet.BABY_KNIGHT;
        hitSound = Assets.Sounds.HIT_SWORD;
        hitSoundPitch = 0.86f;

        defaultAction = AC_ZAP;

        tier = 5;
    }

    @Override
    public int max(int lvl) {
        return  5*(tier) +   //25 + 5
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
            if (charge >= chargeCap && Dungeon.hero.belongings.weapon == this) {
                for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                    if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]
                    && !mob.properties().contains(Char.Property.BOSS) && !mob.properties().contains(Char.Property.MINIBOSS)
                    && mob.state != mob.PASSIVE) {
                       mob.state = mob.SLEEPING;
                    }
                }

                int heal = Dungeon.hero.HT / 5;
                Dungeon.hero.HP = Math.min(Dungeon.hero.HP + heal, Dungeon.hero.HT);
                Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, "+%dHP", heal);

                GameScene.flash( 0x80FFFFFF );
                Sample.INSTANCE.play(Assets.Sounds.SKILL_BABYNIGHT);
                charge = 0;
            }
        }

        updateQuickslot();
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {

        if (defender.buff(Blindness.class) != null) {
            Buff.affect(attacker, Bless.class, 2f);
        }

        if (Random.Int(25) < 3 + level()) {
            if (Dungeon.hero.belongings.getItem(RingOfSunLight.class) != null) {
                if (Dungeon.hero.belongings.getItem(RingOfSunLight.class).isEquipped(Dungeon.hero)) {
                    SPCharge(3);
                }}
            SPCharge(5);

            Buff.affect(defender, Blindness.class, 5f);
        }

        updateQuickslot();

        return super.proc(attacker, defender, damage);
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

    @Override
    public String desc() {
        String info = Messages.get(this, "desc");
        if (Dungeon.hero.belongings.getItem(RingOfSunLight.class) != null) {
            if (Dungeon.hero.belongings.getItem(RingOfSunLight.class).isEquipped(Dungeon.hero))
                info += "\n\n" + Messages.get( DivineAvatar.class, "setbouns");}

        return info;
    }
}
