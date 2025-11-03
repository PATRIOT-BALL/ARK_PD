package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BugSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LavaSlugSprite;
import com.watabou.utils.Random;

public class LavaSlug extends Mob {
    {
        spriteClass = LavaSlugSprite.class;

        HP = HT = 155;
        defenseSkill = 27;

        maxLvl = 34;
        EXP = 19;
        immunities.add(Silence.class);
        immunities.add(Burning.class);
        immunities.add(WandOfFireblast.class);
        properties.add(Property.INFECTED);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 31, 45 );
    }

    @Override
    public int attackSkill( Char target ) {return 40; }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 12);
    }


    @Override
    public int attackProc(Char enemy, int damage) {

        if (Random.Int(3) == 0) {
            enemy.damage(damageRoll() / 3, this);
            Buff.affect(enemy, Burning.class).reignite(enemy, 3f);
        }

        if ( Dungeon.level.map[enemy.pos] == Terrain.WATER) {
            Level.set(enemy.pos, Terrain.EMPTY);
            GameScene.updateMap(enemy.pos);
            CellEmitter.get(enemy.pos).burst(Speck.factory(Speck.STEAM), 10);
        }

        return super.attackProc(enemy, damage);
    }
}
