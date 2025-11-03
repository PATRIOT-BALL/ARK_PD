package com.shatteredpixel.shatteredpixeldungeon.items.wands.SP;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Chains;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class StaffOfSnowsant extends Wand {
    private static ItemSprite.Glowing COL = new ItemSprite.Glowing(0xFF1493);
    {
        image = ItemSpriteSheet.WAND_SNOWSANT;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return COL;
    }

    @Override
    protected void onZap( Ballistica bolt ) {

        Char ch = Actor.findChar( bolt.collisionPos );
        if (ch != null) {


            processSoulMark(ch, chargesPerCast());
            Buff.affect(ch, Silence.class, 2f+buffedLvl());
            chainEnemy(bolt, curUser, ch);
            Sample.INSTANCE.play( Assets.Sounds.HIT_MAGIC, 1, Random.Float(0.65f, 0.75f) );

            ch.sprite.burst(0xFFFFFFFF, buffedLvl() / 2 + 2);

        } else {
            Dungeon.level.pressCell(bolt.collisionPos);
        }
    }

    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        if (defender.buff(Silence.class) != null)
        {
            int dmg = damage / 5;
            defender.damage(dmg, curUser);
        }
    }

    private void chainEnemy(Ballistica chain, final Hero hero, final Char enemy ){

        if (enemy.properties().contains(Char.Property.IMMOVABLE)) {
            return;
        }

        int bestPos = -1;
        for (int i : chain.subPath(1, chain.dist)){
            //prefer to the earliest point on the path
            if (!Dungeon.level.solid[i]
                    && Actor.findChar(i) == null
                    && (!Char.hasProp(enemy, Char.Property.LARGE) || Dungeon.level.openSpace[i])){
                bestPos = i;
                break;
            }
        }

        if (bestPos == -1) {
            return;
        }

        final int pulledPos = bestPos;

         Talent.onArtifactUsed(hero);
         updateQuickslot();


        hero.busy();
        hero.sprite.parent.add(new Chains(hero.sprite.center(), enemy.sprite.center(), new Callback() {
            public void call() {
                Actor.add(new Pushing(enemy, enemy.pos, pulledPos, new Callback() {
                    public void call() {
                        enemy.pos = pulledPos;
                        Dungeon.level.occupyCell(enemy);
                        Dungeon.observe();
                        GameScene.updateFog();
                    }
                }));
                hero.next();
            }
        }));
    }


    protected int initialCharges() {
        return 1;
    }

}
