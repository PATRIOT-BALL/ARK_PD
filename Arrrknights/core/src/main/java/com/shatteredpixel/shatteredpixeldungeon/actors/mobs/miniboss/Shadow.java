package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ParalyticGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.StenchGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Camouflage;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.AntiMagic;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ShadowSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import javax.jws.soap.SOAPBinding;

public class Shadow extends Mob {
    {
        viewDistance = Light.DISTANCE;

        //for doomed resistance
        EXP = 30;
        maxLvl = -2;
        state = HUNTING;

        properties.add(Property.MINIBOSS);
    }

    protected int ShadowLevel = 0;
    protected int ExitPos = 0;
    protected boolean UseSkill = false;

    private static final int[] HPTable = {25, 45, 80, 130, 175, 220, 255};
    private static final int[] MinDamageTable = {2, 4, 7, 12, 20, 27, 33};
    private static final int[] MaxDamageTable = {8, 14, 21, 30, 40, 44, 52};
    private static final int[] drTable = {1, 3, 6, 10, 16, 20, 24};
    private static final int[] attackSkillTable = {5, 9, 15, 20, 32, 40, 50};
    private static final int[] defenseSkillTable = {1, 4, 8, 12, 18, 22, 26};

    private static final String SHAODW_LEVEL = "ShadowLevel";
    private static final String EXIT_POS = "ExitPos";
    private static final String SKILL = "UseSkill";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(SHAODW_LEVEL, ShadowLevel);
        bundle.put(EXIT_POS, ExitPos);
        bundle.put(SKILL, UseSkill);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        ShadowLevel = bundle.getInt(SHAODW_LEVEL);
        ExitPos = bundle.getInt(EXIT_POS);
        UseSkill = bundle.getBoolean(SKILL);
    }

    @Override
    public int attackSkill(Char target) {
        return attackSkillTable[ShadowLevel];
    }

    @Override
    public int defenseSkill(Char enemy) {
        return defenseSkillTable[ShadowLevel];
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(MinDamageTable[ShadowLevel], MaxDamageTable[ShadowLevel]);
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, drTable[ShadowLevel]);
    }

    @Override
    protected boolean act() {
        if (!Dungeon.level.locked) {
            Dungeon.level.seal();
        }
        return super.act();
    }

    @Override
    public void die(Object cause) {
        Dungeon.level.unseal();
        super.die(cause);
    }

    public static Shadow random(){
        int c = Random.Int(6);
        switch (c) {
            default: case 0:
                return new TypeBlaze();
            case 1:
                return new TypeAmiya();
            case 2:
                return new TypeRed();
            case 3:
                return new TypeGrey();
            case 4:
                return new TypeRose();
            case 5:
                return new TypeNearl();
        }
    }

    public static Shadow random_HighLevel(){
        int c = Random.Int(4);
        switch (c) {
            default: case 0:
                return new TypeJessica();
            case 1:
                return new TypeSKD();
            case 2:
                return new TypeSPT();
            case 3:
                return new TypeGRN();
        }
    }

    public static void Spawn(Level lv, int pos) {
        int level;
        switch (Dungeon.depth) {
            default: case 4:
            level = 0;
            break;
            case 9:
                level = 1;
                break;
            case 14:
                level = 2;
                break;
            case 19:
                level = 3;
                break;
            case 24:
                level = 4;
                break;
            case 34:
                level = 5;
                break;
            case 39:
                level = 6;
                break;
        }

        Shadow shadow;
        if (level < 3) shadow = Shadow.random();
        else shadow = Shadow.random_HighLevel();

        shadow.ShadowLevel = level;
        shadow.HT = shadow.HP = HPTable[level];

        shadow.ExitPos = pos;
        shadow.pos = pos;

        lv.mobs.add(shadow);
    }

    public static class TypeBlaze extends Shadow {
        {
            spriteClass = ShadowSprite.ShadowBlaze.class;
        }

        @Override
        public int attackProc(Char enemy, int damage) {
            if (HT / 2 >= HP) damage *= 1.4f;
            return super.attackProc(enemy, damage);
        }

        @Override
        public void damage(int dmg, Object src) {
            if (HT / 2 >= HP) dmg *= 0.6f;
            super.damage(dmg, src);
        }
    }

    public static class TypeAmiya extends Shadow {
        {
            spriteClass = ShadowSprite.ShadowAmiya.class;
            resistances.addAll(AntiMagic.RESISTS);
        }
    }

    public static class TypeRed extends Shadow {
        {
            spriteClass = ShadowSprite.ShadowRed.class;
        }

        @Override
        public int defenseSkill(Char enemy) {
            return defenseSkillTable[ShadowLevel] * 2;
        }

        @Override
        protected boolean act() {
            if (!Dungeon.level.locked) {
                Dungeon.level.seal();
            }

            PathFinder.buildDistanceMap(this.pos, BArray.not(Dungeon.level.solid, null), 2);
            for (int cell = 0; cell < PathFinder.distance.length; cell++) {
                if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                    Char ch = Actor.findChar(cell);
                    if (ch instanceof Hero) {
                        boolean buffchack = false;
                        if (ch.buff(Invisibility.class) != null)  {
                            Buff.detach(ch, Invisibility.class);
                            buffchack = true;
                        }
                        if (ch.buff(CloakOfShadows.cloakStealth.class) != null) {
                            Buff.detach(ch, CloakOfShadows.cloakStealth.class);
                            buffchack = true;
                        }

                        if (buffchack) GLog.w(Messages.get(TypeRed.class, "antiinvisibility"));
                    }}}
            return super.act();
        }
    }

    public static class TypeGrey extends Shadow {
        {
            spriteClass = ShadowSprite.ShadowGrey.class;
        }

        @Override
        protected boolean canAttack(Char enemy) {
            return this.fieldOfView[enemy.pos] && Dungeon.level.distance(this.pos, enemy.pos) <= 4;
        }
    }

    public static class TypeRose extends Shadow {
        {
            spriteClass = ShadowSprite.ShadowRose.class;
        }

        @Override
        public void damage(int dmg, Object src) {
            dmg *= 0.75f;
            super.damage(dmg, src);
        }

        @Override
        protected boolean act() {
            if (enemy instanceof Hero && !UseSkill) {
                UseSkill = true;

                CellEmitter.get( enemy.pos).burst((BlastParticle.FACTORY), 10 );

                Ballistica trajectory = new Ballistica(this.pos, enemy.pos, Ballistica.STOP_TARGET);
                trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
                WandOfBlastWave.throwChar(enemy, trajectory, 3); // 넉백 효과

                Buff.affect(enemy, Paralysis.class, 2f);
            }
            return super.act();
        }
    }

    public static class TypeNearl extends Shadow {
        {
            spriteClass = ShadowSprite.ShadowNearl.class;
            immunities.add(Blindness.class);
        }

        @Override
        public int attackProc(Char enemy, int damage) {
            damage *= 1.2f;
            return super.attackProc(enemy, damage);
        }

        @Override
        protected boolean act() {
            if (enemy instanceof Hero && !UseSkill) {
                UseSkill = true;

                Buff.affect(enemy, Blindness.class, 5f);
                Buff.affect(enemy, Silence.class, 5f);
                GameScene.flash( 0x80FFFFFF );
            }
            return super.act();
        }
    }

    public static class TypeChen extends Shadow {
        {
            spriteClass = ShadowSprite.ShadowNearl.class;
            properties.add(Property.NO_KNOCKBACK);
            immunities.add(Vertigo.class);
        }
    }

    public static class TypeJessica extends Shadow {
        {
            spriteClass = ShadowSprite.ShadowJessi.class;
            immunities.add(ToxicGas.class);
            immunities.add(ParalyticGas.class);
        }

        @Override
        protected boolean canAttack(Char enemy) {
            return this.fieldOfView[enemy.pos] && Dungeon.level.distance(this.pos, enemy.pos) <= 4;
        }

        @Override
        protected boolean act() {
            if (Dungeon.isChallenged(Challenges.TACTICAL_UPGRADE) && Camouflage.CamoFlageEnemy(this)) Buff.affect(this, Camouflage.class, 10f);
            return super.act();
        }

        @Override
        public void damage(int dmg, Object src) {
            super.damage(dmg, src);

            if (HP < HT / 2 && !UseSkill && isAlive()) {
                GameScene.add(Blob.seed(pos, 300, ToxicGas.class));
                GameScene.add(Blob.seed(pos, 300, ParalyticGas.class));

                PathFinder.buildDistanceMap(this.pos, BArray.not(Dungeon.level.solid, null), 5);
                for (int cell = 0; cell < PathFinder.distance.length; cell++) {
                    if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                        Char ch = Actor.findChar(cell);
                        if (ch instanceof Mob && ch.alignment == alignment.ENEMY) {
                            Buff.affect(ch, BlobImmunity.class, 20f);
                        }}}

                int i;
                do {
                    i = Random.Int(Dungeon.level.length());
                } while (Dungeon.level.heroFOV[i]
                        || Dungeon.level.solid[i]
                        || Actor.findChar(i) != null
                        || PathFinder.getStep(i, Dungeon.level.exit, Dungeon.level.passable) == -1);
                CellEmitter.get(pos).burst(Speck.factory(Speck.WOOL), 10);
                ScrollOfTeleportation.appear(this, i);
                GameScene.flash( 0x80FFFFFF );
                Sample.INSTANCE.play(Assets.Sounds.LIGHTNING);
                UseSkill = true;
            }
        }
    }

    public static class TypeSKD extends Shadow {
        {
            spriteClass = ShadowSprite.ShadowSkd.class;
            immunities.add(Burning.class);
            immunities.add(Paralysis.class);
            flying = true;
        }

        @Override
        public int attackProc(Char enemy, int damage) {
            damage *= 1.4f;
            return super.attackProc(enemy, damage);
        }

        @Override
        public void damage(int dmg, Object src) {
            dmg *= 0.6f;
            super.damage(dmg, src);
        }

        @Override
        public void die(Object cause) {
            if (!UseSkill && !(cause instanceof Chasm)) {
                new Flare(8, 32).color(0xFFFF66, true).show(sprite, 2f);
                CellEmitter.get(this.pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);

                HP = HT / 4;
                Buff.affect(this, Barrier.class).setShield(HT / 2);
                PotionOfHealing.cure(this);
                UseSkill = true;
                return;
            }

            super.die(cause);
        }
    }


    public static class TypeSPT extends Shadow {
        {
            spriteClass = ShadowSprite.ShadowSPT.class;
            immunities.add(Blindness.class);
        }

        @Override
        public void damage(int dmg, Object src) {
            dmg *= 0.5f;
            super.damage(dmg, src);
        }

        @Override
        protected boolean act() {
            if (HP < HT / 4) HP += ShadowLevel * 3;
            else if (HP < HT / 2) HP += ShadowLevel * 2;
            else HP += ShadowLevel;

            HP = Math.min(HP, HT);
            return super.act();
        }
    }

    public static class TypeGRN extends Shadow {
        {
            spriteClass = ShadowSprite.ShadowGrani.class;
            immunities.add(Roots.class);
        }

        @Override
        public float speed() {
            return super.speed() * 2f;
        }

        @Override
        public int attackSkill(Char target) {
            return attackSkillTable[ShadowLevel] * 2;
        }

        @Override
        public int defenseSkill(Char enemy) {
            return defenseSkillTable[ShadowLevel] * 2;
        }

        @Override
        public int attackProc(Char enemy, int damage) {
            if (Random.Int(4) == 0) {
                Ballistica trajectory = new Ballistica(this.pos, enemy.pos, Ballistica.STOP_TARGET);
                trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
                WandOfBlastWave.throwChar(enemy, trajectory, 2); // 넉백 효과
            }

            return super.attackProc(enemy, damage);
        }
    }
}
