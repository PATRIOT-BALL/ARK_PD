package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.AncientKin;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DefenderSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GolemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.TargetHealthIndicator;
import com.watabou.utils.Random;

public class HeavyBoat extends Mob {
    {
        spriteClass = GolemSprite.class;

        HP = HT = 300;
        defenseSkill = 0; //see damage()
        baseSpeed = 1f;

        EXP = 15;

        loot = Generator.Category.RING;
        lootChance = 1f;
        immunities.add(Silence.class);
    }

    public int damageRoll() {
        if (Dungeon.level.map[this.pos] != Terrain.WATER) return Random.NormalIntRange(30, 45);
        return Random.NormalIntRange(40, 60);
    }

    @Override
    public int drRoll() {
        if (Dungeon.level.map[this.pos] != Terrain.WATER) return Random.NormalIntRange(1, 12);
        return Random.NormalIntRange(2, 24);
    }

    @Override
    public int attackSkill(Char target) {
        return 35;
    }

    @Override
    protected float attackDelay() {
        return super.attackDelay() * 1.5f;
    }

    @Override
    protected boolean act() {
        if (Dungeon.level.map[this.pos] != Terrain.WATER && state != SLEEPING) {
            damage(HT/10, this);
        }
        return super.act();
    }

    @Override
    public void die(Object cause) {
        int mypos = this.pos;
        super.die(cause);

        Piersailor.spawnAt(mypos);
    }
}
