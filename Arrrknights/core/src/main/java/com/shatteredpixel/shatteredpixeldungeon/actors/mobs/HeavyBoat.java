package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.AncientKin;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DefenderSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GolemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeavyBoatSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.TargetHealthIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class HeavyBoat extends Mob {
    {
        spriteClass = HeavyBoatSprite.class;

        HP = HT = 300;
        defenseSkill = 0; //see damage()
        baseSpeed = 1f;

        EXP = 15;

        loot = Generator.Category.RING;
        lootChance = 1f;
        immunities.add(Silence.class);
    }

    public int damageRoll() {
        if (Dungeon.level.map[this.pos] != Terrain.WATER) return Random.NormalIntRange(25, 40);
        return Random.NormalIntRange(45, 60);
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
    public float speed() {
        if (Dungeon.level.map[this.pos] != Terrain.WATER) return super.speed();
        return super.speed() * 2;
    }

    @Override
    protected boolean act() {
        if (Dungeon.level.map[this.pos] != Terrain.WATER && state == HUNTING) {
            damage(HT/15, this);
            if (!isAlive()) return true;
        }

        boolean heroKilled = false;
        if (Dungeon.level.map[this.pos] == Terrain.WATER && state == HUNTING) {
            for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                Char ch = findChar(pos + PathFinder.NEIGHBOURS8[i]);
                if (ch != null && ch.isAlive() && ch instanceof Hero) {
                    int damage = damageRoll() / 2;
                    damage = Math.max(1, damage - ch.drRoll());
                    ch.damage(damage, this);
                    if (ch == Dungeon.hero && !ch.isAlive()) {
                        heroKilled = true;
                    }
                }
            }
        }

        if (heroKilled) {
            Dungeon.fail( getClass() );
            GLog.n( Messages.get(this, "bang") );
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
