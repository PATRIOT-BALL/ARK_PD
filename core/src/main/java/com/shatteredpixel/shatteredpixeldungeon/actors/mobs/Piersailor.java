package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DefenderSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.PiersailorSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Random;

public class Piersailor extends Mob {
    {
        spriteClass = PiersailorSprite.class;

        HP = HT = 180;
        defenseSkill = 0; //see damage()

        maxLvl = 30;
        EXP = 15;

        immunities.add(Silence.class);
    }

    public int damageRoll() {
        return Random.NormalIntRange(36, 48);
    }

    @Override
    public int drRoll() {
        if (Dungeon.isChallenged(Challenges.TACTICAL_UPGRADE)) return Random.NormalIntRange(4, 24);
        return Random.NormalIntRange(2, 20);
    }

    @Override
    public int attackSkill(Char target) {
        return 35;
    }


    @Override
    protected boolean act() {
        if (Dungeon.level.map[this.pos] == Terrain.WATER && state == HUNTING) {
            if (Dungeon.isChallenged(Challenges.TACTICAL_UPGRADE)) damage(HT/40, this);
            else damage(HT/20, this);

            if (!isAlive()) return true;
        }
        return super.act();
    }

    public static Piersailor spawnAt( int pos ) {
        if (!Dungeon.level.solid[pos] && Actor.findChar( pos ) == null) {
            Piersailor w = new Piersailor();
            w.HP = w.HT / 3;
            w.pos = pos;
            w.state = w.HUNTING;
            GameScene.add( w, 1f );

            CellEmitter.get(pos).burst(Speck.factory(Speck.WOOL), 4);

            if (Dungeon.level.map[w.pos] == Terrain.CHASM) {
                Chasm.mobFall(w);
                Statistics.enemiesSlain++;
                Badges.validateMonstersSlain();
                Statistics.qualifiedForNoKilling = false;

                if (w.EXP > 0 && Dungeon.hero.lvl <= w.maxLvl) {
                    Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Messages.get(w, "exp", w.EXP));
                    Dungeon.hero.earnExp(w.EXP, w.getClass());
                } else {
                    Dungeon.hero.earnExp(0, w.getClass());
                }
            }


                return w;
        } else {
            return null;
        } }

}
