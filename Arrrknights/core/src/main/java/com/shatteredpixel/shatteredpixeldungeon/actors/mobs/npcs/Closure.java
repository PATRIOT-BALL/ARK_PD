package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Torch;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.RhodesLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ClosureSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.StatusPane;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

public class Closure extends NPC {
    {
        spriteClass = ClosureSprite.class;
        HP=HT=1;
        properties.add(Property.IMMOVABLE);
        properties.add(Property.NPC);
    }
    private static final String AC_ADD = "ADD";


    @Override
    public void die(Object cause) {
        sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "die"));
        super.die(cause);
    }

    @Override
    public boolean interact(Char c) {
        if (Dungeon.hero.CharSkin == 0) {
            for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
                if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]) {
                    if (mob instanceof SkinModel) {
                        int a = Dungeon.skin_ch;
                        switch (a) {
                            case 0:
                                if (Badges.isUnlocked(Badges.Badge.EVILTIME_END)) {
                                    if(!Badges.isUnlocked(Badges.Badge.SKIN_TALU)) {Badges.validatetaluskin();}
                                    sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "hi"));
                                    break;
                                } else {sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "no"));
                                    Game.runOnRenderThread(new Callback() {
                                        @Override
                                        public void call() {
                                            GameScene.show(new WndMessage(Messages.get(Closure.class, "fail_skin1", Dungeon.hero.heroClass.title())));
                                        }});}
                                break;
                            case 1:
                                if (Badges.isUnlocked(Badges.Badge.FRAGGING)) {
                                    if(!Badges.isUnlocked(Badges.Badge.SKIN_NOVA)) { Badges.validatenovaskin();}
                                    sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "hi"));
                                } else {sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "no"));
                                    Game.runOnRenderThread(new Callback() {
                                        @Override
                                        public void call() {
                                            GameScene.show(new WndMessage(Messages.get(Closure.class, "fail_skin2", Dungeon.hero.heroClass.title())));
                                        }});}
                                break;
                            case 2: // 바병슼 스킨
                                if (Badges.isUnlocked(Badges.Badge.GAMES_PLAYED_2)) {
                                    if(!Badges.isUnlocked(Badges.Badge.SKIN_BABOSKADI)) {Badges.validateskadiskin();}
                                    sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "hi"));
                                    break;
                                } else {sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "no"));
                                    Game.runOnRenderThread(new Callback() {
                                        @Override
                                        public void call() {
                                            GameScene.show(new WndMessage(Messages.get(Closure.class, "fail_skin3", Dungeon.hero.heroClass.title())));
                                        }});}
                                break;
                            case 3: // 쑤수로 스킨
                                if (Badges.isUnlocked(Badges.Badge.ALL_POTIONS_IDENTIFIED)) {
                                    if(!Badges.isUnlocked(Badges.Badge.SKIN_SUSUU)) {Badges.validatesusuuskin();}
                                    sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "hi"));
                                    break;
                                } else {sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "no"));
                                    Game.runOnRenderThread(new Callback() {
                                        @Override
                                        public void call() {
                                            GameScene.show(new WndMessage(Messages.get(Closure.class, "fail_skin4", Dungeon.hero.heroClass.title())));
                                        }});}
                                break;
                            case 4: // 그라니 스킨
                                if (Badges.isUnlocked(Badges.Badge.GAMES_PLAYED_1)) {
                                    if(!Badges.isUnlocked(Badges.Badge.SKIN_GRN)) {Badges.validategrnskin();}
                                    sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "hi"));
                                    break;
                                } else {sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "no"));
                                    Game.runOnRenderThread(new Callback() {
                                        @Override
                                        public void call() {
                                            GameScene.show(new WndMessage(Messages.get(Closure.class, "fail_skin5", Dungeon.hero.heroClass.title())));
                                        }});}
                                break;
                            case 5: // 제껄룩 스킨
                                if (Badges.isUnlocked(Badges.Badge.UNLOCK_MAGE)) {
                                    if(!Badges.isUnlocked(Badges.Badge.SKIN_JESSI)) {Badges.validatejessiskin();}
                                    sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "hi"));
                                    break;
                                } else {sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "no"));
                                    Game.runOnRenderThread(new Callback() {
                                        @Override
                                        public void call() {
                                            GameScene.show(new WndMessage(Messages.get(Closure.class, "fail_skin6", Dungeon.hero.heroClass.title())));
                                        }});}
                                break;
                            case 6: // 꼬지리 스킨
                                if (Badges.isUnlocked(Badges.Badge.HAPPY_END)) {
                                    if(!Badges.isUnlocked(Badges.Badge.SKIN_LAPPY)) {Badges.validatelappyskin();}
                                    sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "hi"));
                                    break;
                                } else {sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "no"));
                                    Game.runOnRenderThread(new Callback() {
                                        @Override
                                        public void call() {
                                            GameScene.show(new WndMessage(Messages.get(Closure.class, "fail_skin7", Dungeon.hero.heroClass.title())));
                                        }});}
                                break;
                            case 7: // 리프 스킨
                                if (Badges.isUnlocked(Badges.Badge.CHAMPION_1)) {
                                    if(!Badges.isUnlocked(Badges.Badge.SKIN_LEAF)) {Badges.validateleafskin();}
                                    sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "hi"));
                                    break;
                                } else {sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "no"));
                                    Game.runOnRenderThread(new Callback() {
                                        @Override
                                        public void call() {
                                            GameScene.show(new WndMessage(Messages.get(Closure.class, "fail_skin8", Dungeon.hero.heroClass.title())));
                                        }});}
                                break;

                        }
                    }
                }
            }
        }
        else {  sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "hihi")); }
      //  sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "no"));

        ((HeroSprite) Dungeon.hero.sprite).updateArmor();
        GameScene.updateplayeravater();

        return true;
    }

    public static void spawn(Level level, int poss) {
        Closure WhatYourName = new Closure();
        do {
            WhatYourName.pos = poss;
        } while (WhatYourName.pos == -1);
        level.mobs.add(WhatYourName);
    }

    }
