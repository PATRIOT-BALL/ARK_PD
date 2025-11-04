package com.shatteredpixel.shatteredpixeldungeon.items.artifacts;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Honeypot;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.CausticBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfHolyFuror;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfWarp;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Alchemize;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.AquaBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.BeaconOfReturning;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.FeatherFall;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ForceCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.MagicalArmord;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.OathofFire;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.PhaseShift;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ReclaimTrap;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Recycle;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.WildEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Arrays;

public class AlchemyKit extends Artifact {
    {
        image = ItemSpriteSheet.ARTIFACT_THORNS;
        levelCap = 10;

        charge = 0;
        chargeCap = 100;
    }

    public static final String AC_CRATE = "CRATE";
    public static final String AC_ADD = "ADD";
    private int resultValue = 0;

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (!cursed && isEquipped(hero)) {
        actions.add(AC_CRATE);
        actions.add(AC_ADD); }
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_CRATE)) {
            if (charge < chargeCap) GLog.h(Messages.get(AlchemyKit.class, "chargeless"));
            else {
                CrateItem();
            }
        }

        if (action.equals(AC_ADD)) {
            GameScene.selectItem(itemSelector, WndBag.Mode.ALCHEMYKIT_ONLY, Messages.get(this, "prompt"));
        }
    }

    private final WndBag.Listener itemSelector = new WndBag.Listener()  {
        @Override
        public void onSelect(final Item item) {
            if (item != null) {
                getCharge(AddItem(item));
                item.detach( curUser.belongings.backpack );
            }
        }};

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

    // common등급 보상은 4개가 지급됩니다.
    private static final Class<?>[] common = new Class<?>[]{
            AquaBlast.class, Alchemize.class};

    // uncommon등급 보상은 3개가 지급됩니다.
    private static final Class<?>[] uncommon = new Class<?>[]{
            Recycle.class, PhaseShift.class, Bomb.class};

    // rare등급 보상은 2개가 지급됩니다.
    private static final Class<?>[] rare = new Class<?>[]{
            FeatherFall.class, WildEnergy.class, Honeypot.class, BeaconOfReturning.class, StoneOfEnchantment.class, MagicalArmord.class, OathofFire.class, ForceCatalyst.class};

    // veryrare등급 보상은 1개가 지급됩니다.
    private static final Class<?>[] veryrare = new Class<?>[]{
            ScrollOfWarp.class, ReclaimTrap.class, PotionOfHolyFuror.class, ScrollOfTransmutation.class, CausticBrew.class};

    private static final float[] typeChances = new float[]{
            20, //6.67% each
            40, //13.3% each
            30,  //6% each
            10, // 2% each
    };

    private Item RandomItem() {
        switch(Random.chances(typeChances)){
            case 0: default:
                ArrayList<Class<?>> RandCommon = new ArrayList<>(Arrays.asList(common));
                resultValue = 4;
                return (Item) Reflection.newInstance(Random.element(RandCommon));
            case 1:
                ArrayList<Class<?>> RandUnCommon = new ArrayList<>(Arrays.asList(uncommon));
                resultValue = 3;
                return (Item) Reflection.newInstance(Random.element(RandUnCommon));
            case 2:
                ArrayList<Class<?>> RandRare = new ArrayList<>(Arrays.asList(rare));
                resultValue = 2;
                return (Item) Reflection.newInstance(Random.element(RandRare));
            case 3:
                ArrayList<Class<?>> RandVRare = new ArrayList<>(Arrays.asList(veryrare));
                resultValue = 1;
                return (Item) Reflection.newInstance(Random.element(RandVRare));
        }
    }

    private void CrateItem() {
        Item result = RandomItem();
        result.quantity(resultValue);
        Dungeon.level.drop(result, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);

        charge = 0;
        if (level() < levelCap) upgrade();

        GLog.h(Messages.get(AlchemyKit.class, "getItem"));
    }

    private int AddItem(Item item) {
        if (item != null) {
            if (!item.isEquipped(Dungeon.hero)) {
                int result = 0;
                result += level(); // 유물 10레벨이면 기본적으로 10의 충전이 보장됨

                if (item instanceof MeleeWeapon) {
                    result += ((Weapon) item).STRReq() * 2;
                    result += ((Weapon) item).level() * 3;

                    if (item.isIdentified()) {
                        result += 5;
                    }
                    if (!item.cursed) {
                        result += 5;
                    }

                    return result;
                } else if (item instanceof Armor) {
                    result += ((Armor) item).STRReq() * 2;
                    result += ((Armor) item).level() * 3;

                    if (item.isIdentified()) {
                        result += 5;
                    }
                    if (!item.cursed) {
                        result += 5;
                    }

                    return result;
                } else if (item instanceof Ring) {
                    result += 30;
                    result += ((Ring) item).level() * 8;

                    if (item.isIdentified()) {
                        result += 5;
                    }
                    if (!item.cursed) {
                        result += 10;
                    }

                    return result;
                } else if (item instanceof Wand) {
                    result += 25;
                    result += ((Wand) item).level() * 12;

                    if (item.isIdentified()) {
                        result += 5;
                    }
                    if (!item.cursed) {
                        result += 5;
                    }

                    return result;
                } else if (item instanceof MissileWeapon) {
                    result += 20;
                    return result;
                } else if (item instanceof Plant.Seed || item instanceof Runestone) {
                    result += 10;
                    return result;
                }
            }
        }
        return 0;
    }

    public void getCharge(int gaincharge) {
        charge += gaincharge;
        charge = Math.min(charge, chargeCap);
    }

    @Override
    public String desc() {
        String desc = super.desc();

        if (isEquipped(Dungeon.hero)) {
            if (cursed)
                desc += "\n\n";
            desc += Messages.get(this, "desc_cursed");
        }
        return desc;
    }

    @Override
    protected ArtifactBuff passiveBuff() { return new AlchemyKit.AlchemyBuff();
    }


    public class AlchemyBuff extends ArtifactBuff {
        @Override
        public void charge(Hero target, float amount) {
            charge += Math.round(3*amount);
            charge = Math.min(charge, chargeCap);
            updateQuickslot();
        }
    }
}
