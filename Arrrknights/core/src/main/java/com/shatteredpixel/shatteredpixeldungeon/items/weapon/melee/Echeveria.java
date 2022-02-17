package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hallucination;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CustomeSet;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Door;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DroneSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Rock_CrabSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Echeveria extends MeleeWeapon{
    public static final String AC_SUMON = "SUMON";
    {
        image = ItemSpriteSheet.SCENE;
        hitSound = Assets.Sounds.ATK_SPIRITBOW;
        hitSoundPitch = 1.32f;

        tier = 5;
        RCH = 2;

        defaultAction = AC_SUMON;
        usesTargeting = true;
    }

    protected int collisionProperties = Ballistica.MAGIC_BOLT;

    @Override
    public int max(int lvl) {
        return  3*(tier-1) +            	//12 + 3
                lvl*(tier-2);
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_SUMON);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_SUMON)) {
            if (!isEquipped(hero)) return;
            if (charge >= 50) GameScene.selectCell(zapper);
        }
    }

    protected static CellSelector.Listener zapper = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer target) {

            if (target != null) {

                final Echeveria ss;
                if (curItem instanceof Echeveria) {
                    ss = (Echeveria) Echeveria.curItem;

                    Ballistica shot = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
                    int cell = shot.collisionPos;

                    if (target == curUser.pos || cell == curUser.pos) {
                        return;
                    }

                    curUser.sprite.zap(cell);

                    //attempts to target the cell aimed at if something is there, otherwise targets the collision pos.
                    if (Actor.findChar(target) != null)
                        QuickSlotButton.target(Actor.findChar(target));
                    else
                        QuickSlotButton.target(Actor.findChar(cell));

                    if (ss.tryToZap(curUser, target)) {
                        ss.fx(shot, new Callback() {
                            public void call() {
                                ss.onZap(shot);
                            }
                        });
                    }

                }
            }
        }

        @Override
        public String prompt() {
            return Messages.get(MidnightSword.class, "prompt");
        }
    };

    protected void fx( Ballistica bolt, Callback callback ) {
        MagicMissile.boltFromChar( curUser.sprite.parent,
                MagicMissile.FORCE,
                curUser.sprite,
                bolt.collisionPos,
                callback);
        Sample.INSTANCE.play( Assets.Sounds.ZAP );
    }

    public boolean tryToZap(Hero owner, int target) {
        return true;
    }

    protected void onZap( Ballistica bolt ) {
        ArrayList<Integer> respawnPoints = new ArrayList<>();
        Char ch = Actor.findChar( bolt.collisionPos );
        if (ch != null && ch instanceof Mob) {
            for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                int p = ch.pos + PathFinder.NEIGHBOURS8[i];
                if (Actor.findChar(p) == null && Dungeon.level.passable[p]) {
                    respawnPoints.add(p);
                }
            }
            int spawnd = 0;
            while (respawnPoints.size() > 0 && spawnd == 0) {
                int index = Random.index(respawnPoints);

                Echeveria.PinkdogDrone drone = new Echeveria.PinkdogDrone();
                drone.setting(buffedLvl(), (Mob)ch);
                GameScene.add(drone);
                ScrollOfTeleportation.appear(drone, respawnPoints.get(index));

                respawnPoints.remove(index);
                spawnd++;
            }



        } else {
            Dungeon.level.pressCell(bolt.collisionPos);
        }

        charge -=50;
        updateQuickslot();

        if (setbouns()) curUser.spendAndNext(0.5f);
        else curUser.spendAndNext(1f);
    }

    @Override
    public String status() {
        if (chargeCap == 100)
            return Messages.format("%d%%", charge);
        return null;
    }

    private boolean setbouns() {
        if (Dungeon.hero.belongings.getItem(RingOfWealth.class) != null) {
            if (Dungeon.hero.belongings.getItem(RingOfWealth.class).isEquipped(Dungeon.hero))
                return true;
        }
        return false;
    }

    @Override
    public String desc() {
        String info = Messages.get(this, "desc");
        if (setbouns()) info += "\n\n" + Messages.get( Echeveria.class, "setbouns");

        return info;
    }


    public static class PinkdogDrone extends Mob {
        {
            HP = 20;
            defenseSkill = 5;

            spriteClass = Rock_CrabSprite.class;
            baseSpeed = 2f;

            immunities.add(Silence.class);
            alignment = Alignment.ALLY;
            flying = true;

            state = HUNTING;
        }

        Mob LockTarget;
        int targetId = -1;
        int AttackCount = 1;

        @Override
        public int damageRoll() {
            return Random.IntRange(1, 2+maxLvl);
        }

        @Override
        public int attackSkill(Char target) {
            return INFINITE_ACCURACY;
        }

        public void setting(int setlvl, Mob target)
        {
            maxLvl = setlvl;
            LockTarget = target;
        }

        @Override
        protected boolean act() {
            if (AttackCount >= 6) { die(this); return true;}

            if (targetId != -1){
                Actor ch = Actor.findById(targetId);
                targetId = -1;
                if (ch instanceof Mob){
                    LockTarget = (Mob) ch;
                }}

            if (LockTarget != null && LockTarget.isAlive()) enemy = LockTarget;
            else die(this);
            return super.act();
        }

        @Override
        protected boolean canAttack(Char enemy) {
            return this.fieldOfView[enemy.pos] && Dungeon.level.distance(this.pos, enemy.pos) <= 4;
        }

        @Override
        public boolean attack(Char enemy) {
            if (enemy == null) return false;

            boolean visibleFight = Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[enemy.pos];

            if (enemy.isInvulnerable(getClass())) {

                if (visibleFight) {
                    enemy.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "invulnerable"));

                    Sample.INSTANCE.play(Assets.Sounds.HIT_PARRY, 1f, Random.Float(0.96f, 1.05f));
                }

                return false;
            }
            else if (hit( this, enemy, true )) {

                int dmg = damageRoll() * AttackCount;
                enemy.damage( dmg, this );
                enemy.sprite.flash();

                Ballistica trajectory = new Ballistica(this.pos, enemy.pos, Ballistica.STOP_TARGET);
                trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
                moveChar(this, trajectory, 1, enemy.pos, false, false); // 자신이 이동효과

                AttackCount++;

                if (Dungeon.level.heroFOV[pos]) Sample.INSTANCE.play(Assets.Sounds.HIT_MAGIC, 1f, Random.Float(0.96f, 1.05f));
            } else {
                enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
            }
            return true;
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



        public static final String TARGET = "targetId";
        public static final String ATTACK = "AttackCount";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(ATTACK, AttackCount);
            if (LockTarget != null) bundle.put(TARGET, LockTarget.id());
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            if (bundle.contains( TARGET )){
            targetId = bundle.getInt(TARGET);}
            AttackCount = bundle.getInt(ATTACK);
            enemySeen = true;
        }
    }
}
