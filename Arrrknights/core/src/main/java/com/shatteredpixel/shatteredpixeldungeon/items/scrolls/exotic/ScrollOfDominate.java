package com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MindVision;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MidnightSword;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class ScrollOfDominate extends ExoticScroll {
    {
        icon = ItemSpriteSheet.Icons.SCROLL_DOMINATE;
        unique = true;
    }

    @Override
    public void doRead() {

        float oldtime = 0;
        if (Dungeon.hero.buff(MindVision.class) != null)
        {
            oldtime = Dungeon.hero.buff(MindVision.class).visualcooldown();
            Dungeon.hero.buff(MindVision.class).detach();
        }

        for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
            if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]) {
                if (!mob.isImmune(Corruption.class)) {
                    Buff.affect(mob, Corruption.class);

                    boolean droppingLoot = mob.alignment != Char.Alignment.ALLY;

                    if (mob.buff(Corruption.class) != null){
                        if (droppingLoot) mob.rollToDropLoot();
                        Statistics.enemiesSlain++;
                        Badges.validateMonstersSlain();
                        Statistics.qualifiedForNoKilling = false;
                        if (mob.EXP > 0 && curUser.lvl <= mob.maxLvl) {
                            curUser.sprite.showStatus(CharSprite.POSITIVE, Messages.get(mob, "exp", mob.EXP));
                            curUser.earnExp(mob.EXP, mob.getClass());
                        } else {
                            curUser.earnExp(0, mob.getClass());
                        }
                    }
                }
            }


            identify();

            curUser.sprite.centerEmitter().start(Speck.factory(Speck.BONE), 0.3f, 3);
            Sample.INSTANCE.play(Assets.Sounds.RAY);

            readAnimation();
        }

        if (oldtime != 0) Buff.affect(Dungeon.hero, MindVision.class, oldtime);
    }
}