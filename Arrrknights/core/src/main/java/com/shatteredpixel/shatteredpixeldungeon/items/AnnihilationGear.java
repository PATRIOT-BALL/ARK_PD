package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Rose_Force;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3.TerminationT;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HandclapSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MissileSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElementDecl;

public class AnnihilationGear extends Item {

    public static final String AC_ACTIVE	= "active";

    {
        image = ItemSpriteSheet.ARTIFACT_ROSEMARY;

        defaultAction = AC_ACTIVE;
        unique = true;
    }

    public int charge = 3;
    public int chargeCap = 3;

    public int min() { return 4 + Dungeon.hero.lvl; }

    public int max() { return 6 + Dungeon.hero.lvl + buffedLvl() + (Dungeon.hero.pointsInTalent(Talent.RHODES_WEAPON) * 4); }

    @Override
    public String desc() { return Messages.get(this, "desc", min(), max()); }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_ACTIVE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_ACTIVE)) {
            if (charge > 0) GameScene.selectCell(Shot);
            else GLog.w(Messages.get(this, "nocharge"));
        }
    }

    public void SPCharge(int n) {
        charge += n;
        if (chargeCap < charge) charge = chargeCap;
        updateQuickslot();
    }

    public void discharge() {
        charge--;
        updateQuickslot();
    }

    @Override
    public String status() {
        return charge + "/" + chargeCap;
    }

    @Override
    public Item upgrade() {
        charge++; chargeCap++;
        chargeCap = Math.min(chargeCap,10);
        return super.upgrade();
    }

    @Override
    public boolean isUpgradable() {
        return true;
    }

    private static final String CHARGE = "charge";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(CHARGE, charge);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        if (chargeCap > 0) charge = Math.min(chargeCap, bundle.getInt(CHARGE));
        else charge = bundle.getInt(CHARGE);
    }


    protected CellSelector.Listener Shot = new  CellSelector.Listener() {
        @Override
        public void onSelect( Integer target ) {
            if (target != null && target != curUser.pos) {
                int targetCell = target;

                final int finalTargetCell = targetCell;

                final AnnihilationGear.Spriteex Arrow = new AnnihilationGear.Spriteex();

                curUser.sprite.zap(targetCell);

                ((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class)).
                        reset(curUser.sprite,
                                finalTargetCell,
                                Arrow,
                                new Callback() {
                                    @Override
                                    public void call() {
                                        Arrow.onThrow(target);
                                        charge--;
                                        updateQuickslot();
                                    }
                                });
            }
        }

        @Override
        public String prompt() {
            return Messages.get(AnnihilationGear.class, "prompt");
        }


    };


public class Spriteex extends MissileWeapon {

    {
        image = ItemSpriteSheet.TRIDENT;

        hitSound = Assets.Sounds.HIT_SLASH;
    }

    @Override
    protected void onThrow( int cell ) {
        Char enemy = Actor.findChar( cell );
        parent = null;
        Splash.at( cell, 0xCC99FFFF, 1 );
        isHit(cell);

    }

    protected void isHit(int target)
    {
        Char mob = Actor.findChar(target);
        if (mob != null) {
            dohit(mob);
            CellEmitter.center(target).burst(BlastParticle.FACTORY, 10);
            Sample.INSTANCE.play(Assets.Sounds.BLAST);
        }
        else {
            CellEmitter.center(target).burst(BlastParticle.FACTORY, 10);
            Sample.INSTANCE.play(Assets.Sounds.BLAST);
        }

        Invisibility.dispel();
        curUser.spendAndNext(1);
    }

    @Override
    public float accuracyFactor(Char owner) {
        return Float.POSITIVE_INFINITY;
    }
}

    public void dohit(final Char enemy) {
        //Ballistica trajectory = new Ballistica(curUser.pos, enemy.pos, Ballistica.STOP_TARGET);
       // trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size()-1), Ballistica.PROJECTILE);
        // WandOfBlastWave.throwChar(enemy, trajectory, 4); // 넉백 효과

        int dmg = Random.NormalIntRange(min(), max());
        if (curUser.buff(Rose_Force.class) != null) dmg *= 1.3f;
        enemy.damage(dmg, enemy);

        // 서브 직업이 파괴라면, 집중 버프 부여
        if (curUser.subClass == HeroSubClass.DESTROYER)
            Buff.affect(curUser, Rose_Force.class, Rose_Force.DURATION);
    }
}
