package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SkillBook;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class RandomBox extends Item {
    private static final String AC_ADD = "ADD";
    {
        image = ItemSpriteSheet.CHEST;
        stackable = true;

        defaultAction = AC_ADD;

    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_ADD );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_ADD )) {

            hero.spend( 1 );
            hero.busy();

            hero.sprite.operate( hero.pos );

            detach( hero.belongings.backpack );
            Randomreward(Random.IntRange(0,9)); }
        }


    public void Randomreward(int i) {
        if (i < 6) {
            if (Random.Int(5) < 3) { GetGold_low();
            } else GetGold_high(); }
        else if (i < 8) { GetSkill(); }
        else GetWeapon();
    }

    public void GetGold_low()
    { new Gold(Random.IntRange(100,550)).doPickUp(Dungeon.hero); }

    public void GetGold_high()
    { new Gold(Random.IntRange(350,800)).doPickUp(Dungeon.hero); }

    public void GetSkill(){
        int chance = Random.IntRange(0, 50);

        chance += Random.IntRange(-10,Dungeon.hero.lvl);
        chance += Random.IntRange(0,Dungeon.hero.STR);
        chance += Random.IntRange(0,Dungeon.depth);

        if (chance > 70)
        {
            Item n = Generator.random( Generator.Category.SKL_T3 );
            n.doPickUp(Dungeon.hero);
        }
        else if (chance > Random.IntRange(30,70)){
            Item n = Generator.random( Generator.Category.SKL_T2 );
            n.doPickUp(Dungeon.hero);
        }
        else { Item n = Generator.random( Generator.Category.SKL_T1 );
            n.doPickUp(Dungeon.hero);
        }
    }

    public void GetWeapon() {
        int chance = Random.IntRange(0, 50);

        chance += Random.IntRange(-5, Dungeon.hero.STR);
        chance += Random.IntRange(0, Dungeon.hero.belongings.weapon.buffedLvl() * 7);

        Weapon n;

        if (chance > 65) { Generator.Category c = Generator.wepTiers[4];
        n = (MeleeWeapon)Reflection.newInstance(c.classes[Random.chances(c.probs)]);}
        else if (chance > 50) { Generator.Category c = Generator.wepTiers[3];
            n = (MeleeWeapon)Reflection.newInstance(c.classes[Random.chances(c.probs)]);}
        else if (chance > 35) { Generator.Category c = Generator.wepTiers[2];
            n = (MeleeWeapon)Reflection.newInstance(c.classes[Random.chances(c.probs)]);}
        else { Generator.Category c = Generator.wepTiers[1];
            n = (MeleeWeapon)Reflection.newInstance(c.classes[Random.chances(c.probs)]);}

        int upchacne = Random.IntRange(0,25);

        upchacne += Random.IntRange(-10, Dungeon.depth);
        upchacne += Random.IntRange(-5, Dungeon.hero.belongings.armor.buffedLvl() * 3);

        if(upchacne > 35) n.level(4);
        else if(upchacne > 20) n.level(2);
        else if (upchacne > 12) n.level(1);
        else if (upchacne < -8) n.level(-2);
        else if(upchacne < 0) n.level(-1);
        else n.level(0);


        n.doPickUp(Dungeon.hero);
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public int value() {
        return 30;
    }
}
