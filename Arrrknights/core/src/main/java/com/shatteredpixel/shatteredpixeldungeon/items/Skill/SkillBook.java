package com.shatteredpixel.shatteredpixeldungeon.items.Skill;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ActiveOriginium;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class SkillBook extends Item {
    private static final String AC_SKL1 = "SKL1";
    private static final String AC_SKL2 = "SKL2";
    private static final String AC_SKL3 = "SKL3";

    {
        image = ItemSpriteSheet.ARTIFACT_GREAVES;

        stackable = true;

    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_SKL1);
        actions.add(AC_SKL2);
        actions.add(AC_SKL3);
        return actions;
    }

    @Override
    public void execute (Hero hero, String action ){

        super.execute(hero, action);
        if (action.equals(AC_SKL1)) {
            hero.busy();
            hero.sprite.operate(hero.pos);


            if (hero.SK1 != null){
            hero.SK1.doSkill();}
        }


    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }
}
