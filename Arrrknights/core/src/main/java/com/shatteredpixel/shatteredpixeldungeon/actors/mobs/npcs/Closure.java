package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Torch;
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
        HP=HT=60;
        properties.add(Property.IMMOVABLE);
        properties.add(Property.NPC);
    }
    private static final String AC_ADD = "ADD";

    @Override
    public int defenseSkill(Char enemy) {
        return INFINITE_EVASION;
    }

    @Override
    public void damage(int dmg, Object src) {
    }

    @Override
    public boolean interact(Char c) {
        if (Dungeon.hero.CharSkin == 0) {
            for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
                if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]) {
                    if (mob instanceof SkinModel) {
                        int a = ((SkinModel) mob).Skin;
                        switch (a) {
                            case 0:
                          //      if (Badges.isUnlocked(Badges.Badge.EVILTIME_END)) {
                                    Dungeon.hero.CharSkin = Hero.TALULAH;
                                    sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "hi"));
                          //      } else {sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "no"));
                         //           Game.runOnRenderThread(new Callback() {
                           //             @Override
                          //              public void call() {
                          //                  GameScene.show(new WndMessage(Messages.get(Closure.class, "fail_skin1", Dungeon.hero.heroClass.title())));
                         //               }});}
                       //         break;
                            case 1:
                               /* if (Badges.isUnlocked(Badges.Badge.VICTORY_ALL_CLASSES)) {
                                    Dungeon.hero.CharSkin = Hero.F_NOVA;
                                    sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "hi"));
                                } else {sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "no"));
                                    Game.runOnRenderThread(new Callback() {
                                        @Override
                                        public void call() {
                                            GameScene.show(new WndMessage(Messages.get(Closure.class, "fail_skin2", Dungeon.hero.heroClass.title())));
                                        }});}*/
                            sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "incomplete"));
                                break;
                        }
                    }
                }
            }
        }
        else {  sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "hihi"));
            Dungeon.hero.CharSkin = 0;
        }
       // sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "no"));

        ((HeroSprite) Dungeon.hero.sprite).updateArmor();
        GameScene.updateplayeravater();

        return true;
    }

    public static void spawn(RhodesLevel level, int poss) {
        Closure WhatYourName = new Closure();
        do {
            WhatYourName.pos = poss;
        } while (WhatYourName.pos == -1);
        level.mobs.add(WhatYourName);
    }

    }
