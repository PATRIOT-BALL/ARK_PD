package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Bee;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PurpleParticle;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Door;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class SakuraSword extends MeleeWeapon {
    public static final String AC_SAKURA = "SAKURA";
    {
        image = ItemSpriteSheet.SAKURA_FUBUKI;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1.3f;

        defaultAction = AC_SAKURA;

        tier = 5;
        DLY = 1f;
        RCH = 1;
        ACC = 1f;
    }

    private boolean ExMode = false;
    private int attackcount = 0;
    private int charge = 100;
    private int chargeCap = 100;

    @Override
    public int max(int lvl) {
        return  5*(tier) +    //25 + 5
                lvl*(tier);   //scaling unchanged
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_SAKURA);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_SAKURA) && charge >= chargeCap && isEquipped(hero) && !ExMode) {
            ExMode = true;
            RCH = 8;
            ACC = 3f;
            GameScene.flash( 0x80FFFFFF );
        }
    }


    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (ExMode) {
             // 이펙트 효과
            BeamEffects(attacker, defender);
            
            Ballistica trajectory = new Ballistica(attacker.pos, defender.pos, Ballistica.STOP_TARGET);
            trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
            moveChar(attacker, trajectory, 1, defender.pos, false, false); // 자신이 이동효과

            Buff.affect(defender, SakuraMark.class);
            attackcount++;

            if (attackcount == 3) {
                for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                    if (mob.alignment != Char.Alignment.ALLY && mob.buff(SakuraMark.class) != null) {
                        mob.damage(damageRoll(attacker), attacker);
                        CellEmitter.center( mob.pos ).burst( PurpleParticle.BURST, Random.IntRange( 11,17 ) );
                        Buff.detach(mob, SakuraMark.class);
                    }
                }
                attackcount = 0;
            }

            charge-=9;
            if (charge <= 0) {
                charge = 0;
                ExMode = false;
                RCH = 1;
                ACC = 1f;
            }
            updateQuickslot();
        }
        else {
            SPCharge(4);
        }
        return super.proc(attacker, defender, damage);
    }

    private void BeamEffects(Char attacker, Char defender) {
        int dis = Dungeon.level.distance(attacker.pos, defender.pos) -1;

        Ballistica beam = new Ballistica(attacker.pos, defender.pos, Ballistica.WONT_STOP);
        int maxDistance = Math.min(dis, beam.dist);
        int cell = beam.path.get(Math.min(beam.dist, maxDistance));
        for (int c : beam.subPath(1, maxDistance)) {
            CellEmitter.center(c).burst(PurpleParticle.BURST, Random.IntRange(3, 5));
        }
    }

    private void SPCharge(int n) {
        charge += n;
        if (chargeCap < charge) charge = chargeCap;
        updateQuickslot();
    }

    @Override
    public String status() {

        //if the artifact isn't IDed, or is cursed, don't display anything
        if (!isIdentified() || cursed) {
            return null;
        }
        //display as percent
        if (chargeCap == 100)
            return Messages.format("%d%%", charge);
        //otherwise, if there's no charge, return null.
        return null;
    }

    private void moveChar(final Char ch, final Ballistica trajectory, int power, int enemypos,
                          boolean closeDoors, boolean collideDmg){
        if (ch.properties().contains(Char.Property.BOSS)) {
            power /= 2;
        }

        int dist = Math.min(trajectory.dist, power);

        boolean collided = dist == trajectory.dist;

        if (dist == 0 || ch.properties().contains(Char.Property.IMMOVABLE)) return;

        //large characters cannot be moved into non-open space
        if (Char.hasProp(ch, Char.Property.LARGE)) {
            for (int i = 1; i <= dist; i++) {
                if (!Dungeon.level.openSpace[trajectory.path.get(i)]){
                    dist = i-1;
                    collided = true;
                    break;
                }
            }
        }

        if (Actor.findChar(trajectory.path.get(dist)) != null){
            dist--;
            collided = true;
        }

        if (dist < 0) return;

        final int newPos = trajectory.path.get(dist);

        if (newPos == enemypos) return;

        final int finalDist = dist;
        final boolean finalCollided = collided && collideDmg;
        final int initialpos = ch.pos;

        Actor.addDelayed(new Pushing(ch, ch.pos, newPos, new Callback() {
            public void call() {
                if (initialpos != ch.pos) {
                    //something caused movement before pushing resolved, cancel to be safe.
                    ch.sprite.place(ch.pos);
                    return;
                }
                int oldPos = ch.pos;
                ch.pos = newPos;
                if (finalCollided && ch.isAlive()) {
                    ch.damage(Random.NormalIntRange(finalDist, 2*finalDist), this);
                    Paralysis.prolong(ch, Paralysis.class, 1 + finalDist/2f);
                }
                if (closeDoors && Dungeon.level.map[oldPos] == Terrain.OPEN_DOOR){
                    Door.leave(oldPos);
                }
                Dungeon.level.occupyCell(ch);
                if (ch == Dungeon.hero){
                    //FIXME currently no logic here if the throw effect kills the hero
                    Dungeon.observe();
                }
            }
        }), -1);
    }


    private static final String MODE = "ExMode";
    private static final String CHARGE = "charge";
    private static final String RCHSAVE = "RCH";
    private static final String ACCSAVE = "ACC";
    private static final String SAKURA = "DLY";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(MODE, ExMode);
        bundle.put(RCHSAVE, RCH);
        bundle.put(ACCSAVE, ACC);
        bundle.put(SAKURA, attackcount);
        bundle.put(CHARGE, charge);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        ExMode = bundle.getBoolean(MODE);
        RCH = bundle.getInt(RCHSAVE);
        ACC = bundle.getFloat(ACCSAVE);
        attackcount = bundle.getInt(SAKURA);
        if (chargeCap > 0) charge = Math.min(chargeCap, bundle.getInt(CHARGE));
        else charge = bundle.getInt(CHARGE);
    }

    public static class SakuraMark extends Buff {

    }
}
