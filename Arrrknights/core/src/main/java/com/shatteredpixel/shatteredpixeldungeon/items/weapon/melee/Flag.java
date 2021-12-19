package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Twilight;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SkillBook;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Flag extends MeleeWeapon  {
    public static final String AC_ZAP = "ZAP";
    {
        image = ItemSpriteSheet.FLAG;
        hitSound = Assets.Sounds.HIT;
        hitSoundPitch = 1f;

        defaultAction = AC_ZAP;

        tier = 3;
    }

    private int backchane = 0;

    @Override
    public int max(int lvl) {
        return  3*(tier+2) +   //15 + 3
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
                Dungeon.hero.sprite.emitter().burst(Speck.factory(Speck.HEALING), 2 + buffedLvl() / 2);

                if (Dungeon.hero.belongings.getItem(SkillBook.class) != null) {
                    SkillBook Item = Dungeon.hero.belongings.getItem(SkillBook.class);
                    Item.SetCharge(5+level() * 2);
                }
                int heal = Dungeon.hero.HT / 10;
                Dungeon.hero.HP = Math.min(Dungeon.hero.HP + heal, Dungeon.hero.HT);
                Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, "+%dHP", heal);
                Sample.INSTANCE.play(Assets.Sounds.SKILL_BASIC);
                charge = 0;
            }
        }

        updateQuickslot();
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (Random.Int(15) < 2 + backchane) {
            Ballistica trajectory = new Ballistica(curUser.pos, defender.pos, Ballistica.STOP_TARGET);
            trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
            WandOfBlastWave.throwChar(defender, trajectory, 2+(level() / 5)); // 넉백 효과
            backchane = 0;
        }
        else backchane++;

        SPCharge(4);
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
}
