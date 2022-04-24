package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Identification;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.ExoticPotion;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ExoticScroll;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.InventoryStone;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfIntuition;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.IconButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.IconTitle;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.Image;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class Snowsants_Coin extends Item {
    private static final String AC_USE = "USE";
    private static final float TIME_TO_ACTIVE = 1;

    {
        image = ItemSpriteSheet.ARTIFACT_WALLET;
        bones = false;
    }

    protected String inventoryTitle = Messages.get( StoneOfIntuition.class, "inv_title");
    protected WndBag.Mode mode = WndBag.Mode.ALL;

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_USE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_USE)) {
            curItem = detach(hero.belongings.backpack);
            activate(curUser.pos);
        }
    }

    protected void activate(int cell) {
        GameScene.selectItem(itemSelector, mode, inventoryTitle);
    }

    protected void onItemSelected(Item item) {

        GameScene.show(new WndGuess(item));

    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    protected static WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect(Item item) {

            //FIXME this safety check shouldn't be necessary
            //it would be better to eliminate the curItem static variable.
            if (!(curItem instanceof Snowsants_Coin)) {
                return;
            }

            if (item != null) {

                ((Snowsants_Coin)curItem).onItemSelected(item);

            } else {
                curItem.collect(curUser.belongings.backpack);
            }
        }
    };

        private Class curGuess = null;

        public class WndGuess extends Window {

            private static final int WIDTH = 120;
            private static final int BTN_SIZE = 20;

            public WndGuess(final Item item) {

                ScrollOfIdentify reward = new ScrollOfIdentify();
                StoneOfIntuition reward2 = new StoneOfIntuition();

                IconTitle titlebar = new IconTitle();
                titlebar.icon(new ItemSprite(ItemSpriteSheet.ORE, null));
                titlebar.label(Messages.titleCase(Messages.get(Snowsants_Coin.class, "name")));
                titlebar.setRect(0, 0, WIDTH, 0);
                add(titlebar);

                RenderedTextBlock text = PixelScene.renderTextBlock(6);
                text.text(Messages.get(StoneOfIntuition.WndGuess.class, "text"));
                text.setPos(0, titlebar.bottom());
                text.maxWidth(WIDTH);
                add(text);

                final RedButton guess = new RedButton("") {
                    @Override
                    protected void onClick() {
                        super.onClick();
                        curUser.sprite.operate(curUser.pos);
                        if (item.getClass() == curGuess) {
                            if (item instanceof Ring) {
                                ((Ring) item).setKnown();
                            } else {
                                item.identify();
                            }

                            if (reward.doPickUp( Dungeon.hero )) {
                                GLog.i( Messages.get(Dungeon.hero, "you_now_have", reward.name()) );
                            } else {
                                Dungeon.level.drop( reward, curUser.pos ).sprite.drop();
                            }

                            if (reward2.doPickUp( Dungeon.hero )) {
                                GLog.i( Messages.get(Dungeon.hero, "you_now_have", reward2.name()) );
                            } else {
                                Dungeon.level.drop( reward2, curUser.pos ).sprite.drop();
                            }

                            GLog.p(Messages.get(StoneOfIntuition.WndGuess.class, "correct"));
                            curUser.sprite.parent.add(new Identification(curUser.sprite.center().offset(0, -16)));
                        } else {
                            if (reward2.doPickUp( Dungeon.hero )) {
                                GLog.i( Messages.get(Dungeon.hero, "you_now_have", reward2.name()) );
                            } else {
                                Dungeon.level.drop( reward2, curUser.pos ).sprite.drop();
                            }

                            GLog.n(Messages.get(StoneOfIntuition.WndGuess.class, "incorrect"));
                        }

                        curGuess = null;
                        hide();
                    }
                };
                guess.visible = false;
                guess.icon(new ItemSprite(item));
                guess.enable(false);
                guess.setRect(0, 100, WIDTH, 20);
                add(guess);

                float left;
                float top = text.bottom() + 5;
                int rows;
                int placed = 0;

                final ArrayList<Class<? extends Item>> unIDed = new ArrayList<>();
                if (item.isIdentified()) {
                    hide();
                    return;
                } else if (item instanceof Potion) {
                    if (item instanceof ExoticPotion) {
                        for (Class<? extends Item> i : Potion.getUnknown()) {
                            unIDed.add(ExoticPotion.regToExo.get(i));
                        }
                    } else {
                        unIDed.addAll(Potion.getUnknown());
                    }
                } else if (item instanceof Scroll) {
                    if (item instanceof ExoticScroll) {
                        for (Class<? extends Item> i : Scroll.getUnknown()) {
                            unIDed.add(ExoticScroll.regToExo.get(i));
                        }
                    } else {
                        unIDed.addAll(Scroll.getUnknown());
                    }
                } else if (item instanceof Ring) {
                    unIDed.addAll(Ring.getUnknown());
                } else {
                    hide();
                    return;
                }

                if (unIDed.size() <= 5) {
                    rows = 1;
                    top += BTN_SIZE / 2f;
                    left = (WIDTH - BTN_SIZE * unIDed.size()) / 2f;
                } else {
                    rows = 2;
                    left = (WIDTH - BTN_SIZE * ((unIDed.size() + 1) / 2)) / 2f;
                }

                for (final Class<? extends Item> i : unIDed) {

                    IconButton btn = new IconButton() {
                        @Override
                        protected void onClick() {
                            curGuess = i;
                            guess.visible = true;
                            guess.text(Messages.titleCase(Messages.get(curGuess, "name")));
                            guess.enable(true);
                            super.onClick();
                        }
                    };
                    Image im = new Image(Assets.Sprites.ITEM_ICONS);
                    im.frame(ItemSpriteSheet.Icons.film.get(Reflection.newInstance(i).icon));
                    im.scale.set(2f);
                    btn.icon(im);
                    btn.setRect(left + placed * BTN_SIZE, top, BTN_SIZE, BTN_SIZE);
                    add(btn);

                    placed++;
                    if (rows == 2 && placed == ((unIDed.size() + 1) / 2)) {
                        placed = 0;
                        if (unIDed.size() % 2 == 1) {
                            left += BTN_SIZE / 2f;
                        }
                        top += BTN_SIZE;
                    }
                }

                resize(WIDTH, 120);

            }


            @Override
            public void onBackPressed() {
                super.onBackPressed();
                new Snowsants_Coin().collect();
            }
        }
}
