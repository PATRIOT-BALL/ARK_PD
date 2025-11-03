package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Transmuting;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookExecutionMode;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookHikari;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookPowerfulStrike;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookTacticalChanting;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookThoughts;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookWhispers;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookGenesis;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.CorpseDust;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Gamza;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.MetalShard;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Nmould;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAdvanceguard;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.SuperAdvanceguard;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfTime;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CatGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrabGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Firmament;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ImageoverForm;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PatriotSpear;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SanktaBet;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Shortsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SwordofArtorius;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WintersScar;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.PurgatoryKnife;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Thunderbolt;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Random;

public class Avantgardeform extends InventorySpell {
    {
        image = ItemSpriteSheet.AVANT_TRANS;
        mode = WndBag.Mode.ALL;

        stackable = true;
    }

    @Override
    protected void onItemSelected(Item item) {
        Item result;

        int ItemLevel = item.level();
        result = changeAvant(item, ItemLevel);

        if (result == null) {
            //This shouldn't ever trigger
            GLog.n(Messages.get(ScrollOfTransmutation.class, "nothing"));
            curItem.collect(curUser.belongings.backpack);
        }
        else {
            if (item.isEquipped(Dungeon.hero)) {
                item.cursed = false; //to allow it to be unequipped
                ((EquipableItem) item).doUnequip(Dungeon.hero, false);
                ((EquipableItem) result).doEquip(Dungeon.hero);
            } else {
                item.detach(Dungeon.hero.belongings.backpack);
                if (!result.collect()) {
                    Dungeon.level.drop(result, curUser.pos).sprite.drop();
                }
            }
            if (result.isIdentified()) {
                Catalog.setSeen(result.getClass());
            }
            Transmuting.show(curUser, item, result);
            curUser.sprite.emitter().start(Speck.factory(Speck.CHANGE), 0.2f, 10);
            GLog.p(Messages.get(ScrollOfTransmutation.class, "morph"));
        }
    }


    private Item changeAvant(Item item, int Level) {
        if (item.isEquipped(Dungeon.hero)) return null;
        if (item instanceof Runestone) { // 아이템이 돌일 경우
            if (Random.Int(2) == 0) item = new SuperAdvanceguard();
            else item = new StoneOfAdvanceguard();
        } else if (item instanceof BookPowerfulStrike || item instanceof BookTacticalChanting || item instanceof BookExecutionMode || item instanceof BookThoughts
                || item instanceof BookHikari) {
            if (Random.IntRange(0,21) < 12) item = new BookWhispers();
            else item = new BookGenesis();
        } else if (item instanceof SwordofArtorius || item instanceof WintersScar) {
            item = new PatriotSpear();
            item.level(Level);
            item.identify();
        }
        else if (item instanceof Wand) {
                item = new StaffOfTime();
            item.level(Level);
                item.identify();
        }else if (item instanceof Shortsword) {
            item = new Firmament();
            item.level(Level);
                item.identify();
        } else if (item instanceof PurgatoryKnife) {
            item = new ImageoverForm();
            item.identify();
        } else if (item instanceof Gamza) {
            if (Random.IntRange(0,100) < 31) { item = new Nmould();}
            else item = new Thunderbolt();
        } else if (item instanceof CrabGun) {
            item = new CatGun();
            item.level(Level);
            item.identify();
        }
        else {
            item = null;
        }

        return item;
    }

    @Override
    public int value() {
        //prices of ingredients, divided by output quantity
        return Math.round(quantity * ((30 + 100) / 3f));
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{WeaponTransform.class, ScrollOfUpgrade.class, ScrollOfTransmutation.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 30;

            output = Avantgardeform.class;
            outQuantity = 1;
        }

    }
}
