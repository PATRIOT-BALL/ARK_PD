package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DefenderSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Random;

public class Piersailor extends Mob {
    {
        spriteClass = DefenderSprite.class;

        HP = HT = 180;
        defenseSkill = 0; //see damage()
        baseSpeed = 1f;

        maxLvl = 30;
        EXP = 15;

        immunities.add(Silence.class);
    }

    public int damageRoll() {
        return Random.NormalIntRange(32, 48);
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(2, 20);
    }

    @Override
    public int attackSkill(Char target) {
        return 35;
    }


    @Override
    protected boolean act() {
        if (Dungeon.level.map[this.pos] == Terrain.WATER && state != SLEEPING) {
            damage(HT/10, this);
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

            return w;
        } else {
            return null;
        } }

}
