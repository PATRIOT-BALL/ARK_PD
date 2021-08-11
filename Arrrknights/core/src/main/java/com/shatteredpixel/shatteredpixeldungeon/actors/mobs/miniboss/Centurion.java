package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Necromancer;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Skeleton;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.levels.PrisonLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.SewerLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Fadeleaf;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BombtailSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SarkazSniperEliteSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SarkazSniperSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Sarkaz_CenturionSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Sarkaz_SwordsmanSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class Centurion extends Mob {
    {
        spriteClass = Sarkaz_CenturionSprite.class;

        properties.add(Property.MINIBOSS);
        properties.add(Property.SARKAZ);

        HP = HT = 85;
        defenseSkill = 0;
        baseSpeed = 1f;

        EXP = 8;
        maxLvl = -1;

        loot = Generator.Category.SEED;
        lootChance = 1.0f;

        state = HUNTING;
    }

    private int skillcooldown = 8;

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(4, 16);
    }

    @Override
    public int attackSkill( Char target ) {
        return 12;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 0);
    }

    @Override
    public float speed() {
        return super.speed() * 0.5f;
    }

    private static final String COOLDOWN = "skillcooldown";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(COOLDOWN, skillcooldown);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        skillcooldown = bundle.getInt(COOLDOWN);
    }

    @Override
    public boolean act() {
        while (skillcooldown <= 0) {

            ArrayList<Integer> respawnPoints = new ArrayList<>();
            for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                int p = Dungeon.hero.pos + PathFinder.NEIGHBOURS8[i];
                if (Actor.findChar( p ) == null && Dungeon.level.passable[p]) {
                    respawnPoints.add( p );
                }
            }

            if (respawnPoints.size() > 0) {
                int index = Random.index( respawnPoints );

                Mob summon = Reflection.newInstance(Centurion.CenturionMinion.class);
                GameScene.add(summon);
                ScrollOfTeleportation.appear( summon, respawnPoints.get( index ) );
                Actor.addDelayed(new Pushing(summon, pos, summon.pos), -1);
                CellEmitter.get(summon.pos).burst(ShadowParticle.CURSE, 4);
                summon.beckon(Dungeon.hero.pos);
                skillcooldown = Random.Int(6, 8);

            }
            else break;
        }

        if (skillcooldown > 0) {
             skillcooldown -= 1;
        }

        return super.act();
    }

    public static void spawn(PrisonLevel level) {
        if (Dungeon.depth >= 9 && !Dungeon.bossLevel()) {

            Centurion centinel = new Centurion();
            do {
                centinel.pos = level.randomRespawnCell(centinel);
            } while (centinel.pos == -1);
            level.mobs.add(centinel);
        }
    }

    @Override
    public void die(Object cause) {
        GLog.w(Messages.get(Centurion.class, "die"));
        Dungeon.mboss9 = 0;
        super.die(cause);
    }

    public static class CenturionMinion extends Mob {

        {
            state = WANDERING;

            spriteClass = Sarkaz_SwordsmanSprite.class;

            maxLvl = -15;
            EXP = 5;

            HP=HT=22;
            baseSpeed = 1f;

            properties.add(Property.SARKAZ);
        }

        @Override
        public int damageRoll() {
            return Random.NormalIntRange(4, 14);
        }

        @Override
        public int attackSkill(Char target) { return 10; }

        @Override
        public int drRoll() { return Random.NormalIntRange(0, 3);
        }

        @Override
        public float speed() {
            return super.speed() * 1.5f;
        }
    }
}