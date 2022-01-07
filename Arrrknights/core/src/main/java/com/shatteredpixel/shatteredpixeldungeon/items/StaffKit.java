package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.MetalShard;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfWarp;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ExoticScroll;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Alchemize;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfAbsinthe;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfAngelina;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfBreeze;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfCorrupting;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfGreyy;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfLeaf;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfLena;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfMayer;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfMudrock;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfPodenco;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfShining;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfSkyfire;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfSnowsant;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfSussurro;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfVigna;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfWeedy;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorrosion;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorruption;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfDisintegration;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfHallucination;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLightning;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLivingEarth;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfPrismaticLight;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfRegrowth;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfSilence;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfTransfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfWarding;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class StaffKit extends Item {
    private static final String AC_APPLY = "APPLY";
    private static final String AC_RING = "RING";
    private static final float TIME_TO_UPGRADE = 2;

    {
        image = ItemSpriteSheet.KIT;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_APPLY);
        actions.add(AC_RING);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_APPLY)) {
            curUser = hero;
            GameScene.selectItem(itemSelector, WndBag.Mode.WAND, Messages.get(this, "prompt"));
        }
        else  if (action.equals(AC_RING)) {

            curUser = hero;
            if (curUser.belongings.getItem(MagesStaff.class) != null)
            {
                MagesStaff Item = curUser.belongings.getItem(MagesStaff.class);
                StaffKit.this.upgrade_staff(Item);
            }
            else GLog.w( Messages.get(this, "fail_ring") );

        }
    }

    private void upgrade(Wand wand) {

        detach(curUser.belongings.backpack);

        curUser.sprite.centerEmitter().start(Speck.factory(Speck.KIT), 0.05f, 10);
        curUser.spend(TIME_TO_UPGRADE);
        curUser.busy();

        Wand n;
        if (wand instanceof WandOfMagicMissile) {
            n = new StaffOfAbsinthe(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfLightning) {
            n = new StaffOfGreyy(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfDisintegration) {
            n = new StaffOfVigna(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfCorrosion)
        {
            n = new StaffOfBreeze(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfLivingEarth)
        {
            n = new StaffOfMudrock(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfFrost)
        {
            n = new StaffOfLeaf(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfPrismaticLight)
        {
            n = new StaffOfShining(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfFireblast)
        {
            n = new StaffOfSkyfire(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfWarding)
        {
            n = new StaffOfMayer(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfTransfusion)
        {
            n = new StaffOfAngelina(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfBlastWave)
        {
            n = new StaffOfWeedy(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfCorruption)
        {
            n = new StaffOfCorrupting(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfSilence)
        {
            n = new StaffOfSnowsant(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfRegrowth)
        {
            n = new StaffOfLena(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfHealing)
        {
            n = new StaffOfSussurro(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else if (wand instanceof WandOfHallucination)
        {
            n = new StaffOfPodenco(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            int level = wand.level();
            if (wand.curseInfusionBonus) level--;
            n.upgrade(level);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            n.collect();
            wand.detach( curUser.belongings.backpack );
        }
        else {
            GLog.w( Messages.get(this, "fail") );
            StaffKit S = new StaffKit();
            S.quantity(1).collect();
        }

        curUser.sprite.operate(curUser.pos);
        Sample.INSTANCE.play(Assets.Sounds.EVOKE);
    }

    private void upgrade_staff(MagesStaff Staff) {

        detach(curUser.belongings.backpack);

        curUser.sprite.centerEmitter().start(Speck.factory(Speck.KIT), 0.05f, 10);
        curUser.spend(TIME_TO_UPGRADE);
        curUser.busy();

        Wand wand = Staff.GetWand();
        Wand n;
        if (wand instanceof WandOfMagicMissile) {
            n = new StaffOfAbsinthe(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else if (wand instanceof WandOfLightning) {
            n = new StaffOfGreyy(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else if (wand instanceof WandOfDisintegration) {
            n = new StaffOfVigna(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else if (wand instanceof WandOfCorrosion)
        {
            n = new StaffOfBreeze(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else if (wand instanceof WandOfLivingEarth)
        {
            n = new StaffOfMudrock(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else if (wand instanceof WandOfFrost)
        {
            n = new StaffOfLeaf(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else if (wand instanceof WandOfPrismaticLight)
        {
            n = new StaffOfShining(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else if (wand instanceof WandOfFireblast)
        {
            n = new StaffOfSkyfire(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else if (wand instanceof WandOfWarding)
        {
            n = new StaffOfMayer(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else if (wand instanceof WandOfTransfusion)
        {
            n = new StaffOfAngelina(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else if (wand instanceof WandOfBlastWave)
        {
            n = new StaffOfWeedy(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else if (wand instanceof WandOfCorruption)
        {
            n = new StaffOfCorrupting(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else if (wand instanceof WandOfSilence)
        {
            n = new StaffOfSnowsant(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else if (wand instanceof WandOfRegrowth)
        {
            n = new StaffOfLena(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else if (wand instanceof WandOfHealing)
        {
            n = new StaffOfSussurro(); // 이 부분이랑 조건만 바꾸면 됨.
            n.level(0);
            n.levelKnown = wand.levelKnown;
            n.cursedKnown = wand.cursedKnown;
            n.cursed = wand.cursed;
            n.curseInfusionBonus = wand.curseInfusionBonus;

            wand = n;
            Staff.staffkitwand(wand);
        }
        else {
            GLog.w( Messages.get(this, "fail") );
            StaffKit S = new StaffKit();
            S.quantity(1).collect();
        }

        curUser.sprite.operate(curUser.pos);
        Sample.INSTANCE.play(Assets.Sounds.EVOKE);
    }

    private final WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect(Item item) {
            if (item != null) {
                StaffKit.this.upgrade((Wand) item);
            }
        }
    };

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe {

        @Override
        public boolean testIngredients(ArrayList<Item> ingredients) {
            boolean shard = false;
            boolean arcne = false;
            for (Item i : ingredients){
                if (i instanceof MetalShard || i instanceof Wand){
                    shard = true;
                    //if it is a regular or exotic potion
                } else if (i instanceof ArcaneCatalyst) {
                    arcne = true;
                }
            }

            return shard && arcne;
        }

        @Override
        public int cost(ArrayList<Item> ingredients) {
            return 15;
        }

        @Override
        public Item brew(ArrayList<Item> ingredients) {

            for (Item i : ingredients){
                i.quantity(i.quantity()-1);
            }

            return sampleOutput(null);
        }

        @Override
        public Item sampleOutput(ArrayList<Item> ingredients) {
            return new StaffKit();
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
