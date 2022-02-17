package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.secret;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.MiniShopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Bonk;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Honeypot;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.MerchantsBeacon;
import com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem.Certificate;
import com.shatteredpixel.shatteredpixeldungeon.items.OriginiumShard;
import com.shatteredpixel.shatteredpixeldungeon.items.PortableCover;
import com.shatteredpixel.shatteredpixeldungeon.items.RandomBox;
import com.shatteredpixel.shatteredpixeldungeon.items.Stylus;
import com.shatteredpixel.shatteredpixeldungeon.items.Torch;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.LeatherArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.MailArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.PlateArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ScaleArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.MagicalHolster;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.PotionBandolier;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.ScrollHolder;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.VelvetPouch;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.food.SmallRation;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfHolyFuror;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.AquaBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.OathofFire;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAugmentation;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Enfild;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.PurgatoryKnife;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Ragesawblade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.TippedDart;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;

public class MiniShopRoom extends SecretRoom {
        private ArrayList<Item> itemsToSpawn;

        @Override
        public int minWidth() {
            return Math.max(6, (int)(Math.sqrt(itemCount())+2));
        }

        @Override
        public int minHeight() {
            return Math.max(6, (int)(Math.sqrt(itemCount())+2));
        }

        public int itemCount(){
            if (itemsToSpawn == null) itemsToSpawn = generateItems();
            return itemsToSpawn.size();
        }

        public void paint(Level level ) {

            Painter.fill( level, this, Terrain.WALL );
            Painter.fill( level, this, 1, Terrain.EMPTY_SP );

            placeShopkeeper( level );

            placeItems( level );

            for (Door door : connected.values()) {
                door.set( Door.Type.REGULAR );
            }

        }

        protected void placeShopkeeper( Level level ) {

            int pos = level.pointToCell(center());

            Mob shopkeeper = new MiniShopkeeper();
            shopkeeper.pos = pos;
            level.mobs.add( shopkeeper );

        }

        protected void placeItems( Level level ){

            if (itemsToSpawn == null){
                itemsToSpawn = generateItems();
            }

            Point itemPlacement = new Point(entrance());
            if (itemPlacement.y == top){
                itemPlacement.y++;
            } else if (itemPlacement.y == bottom) {
                itemPlacement.y--;
            } else if (itemPlacement.x == left){
                itemPlacement.x++;
            } else {
                itemPlacement.x--;
            }

            for (Item item : itemsToSpawn) {

                if (itemPlacement.x == left+1 && itemPlacement.y != top+1){
                    itemPlacement.y--;
                } else if (itemPlacement.y == top+1 && itemPlacement.x != right-1){
                    itemPlacement.x++;
                } else if (itemPlacement.x == right-1 && itemPlacement.y != bottom-1){
                    itemPlacement.y++;
                } else {
                    itemPlacement.x--;
                }

                int cell = level.pointToCell(itemPlacement);

                if (level.heaps.get( cell ) != null) {
                    do {
                        cell = level.pointToCell(random());
                    } while (level.heaps.get( cell ) != null || level.findMob( cell ) != null);
                }

                level.drop( item, cell ).type = Heap.Type.FOR_SALE;
            }

        }

        protected static ArrayList<Item> generateItems() {

            ArrayList<Item> itemsToSpawn = new ArrayList<>();

            itemsToSpawn.add( new MerchantsBeacon() );
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.POTION ) );
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.SCROLL ) );
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.RING ) );
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.STONE ) );
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.SEED ) );
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.N_INGREDINETS ) );
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.N_INGREDINETS ) );
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.N_INGREDINETS ) );
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.N_INGREDINETS ) );

            if (Random.Int(8) == 0) {
                itemsToSpawn.add( new ScrollOfTransmutation() );
            }
            else itemsToSpawn.add( new PotionOfHealing() );

            return itemsToSpawn;
        }
}
