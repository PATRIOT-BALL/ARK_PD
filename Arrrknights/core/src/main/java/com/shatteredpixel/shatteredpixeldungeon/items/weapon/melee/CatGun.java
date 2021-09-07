package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CustomeSet;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAggression;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Mon3terSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Rock_CrabSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class CatGun extends MeleeWeapon {
    public static final String AC_ZAP = "ZAP";
    {
        image = ItemSpriteSheet.CATGUN;
        hitSound = Assets.Sounds.ATK_SPIRITBOW;
        hitSoundPitch = 1f;
        defaultAction = AC_ZAP;

        tier = 5;
        RCH = 2;
    }

    private int charge = 100;
    private int chargeCap = 100;

    @Override
    public int max(int lvl) {
        return  4*(tier-1) +    //16 + 4
                lvl*(tier-1); }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        SPCharge(Random.IntRange(2,3));
        return super.proc(attacker, defender, damage);
    }

    public void SPCharge(int n) {
        charge += n;
        if (chargeCap < charge) charge = chargeCap;
        updateQuickslot();
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_ZAP);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_ZAP)) {
            if (charge >= chargeCap) {
                ArrayList<Integer> respawnPoints = new ArrayList<>();

                for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                    int p = hero.pos + PathFinder.NEIGHBOURS8[i];
                    if (Actor.findChar(p) == null && Dungeon.level.passable[p]) {
                        respawnPoints.add(p);
                    }
                }
                int spawnd = 0;
                while (respawnPoints.size() > 0 && spawnd == 0) {
                    int index = Random.index(respawnPoints);

                    CatGun.Mon3tr tr = new CatGun.Mon3tr();
                    tr.setting(buffedLvl());
                    GameScene.add(tr);
                    ScrollOfTeleportation.appear(tr, respawnPoints.get(index));

                    respawnPoints.remove(index);
                    Sample.INSTANCE.play( Assets.Sounds.SKILL_MON1 );
                    spawnd++;
                }
                charge = 0;
            }
        }
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


    public class Mon3tr extends Mob {
        {
            spriteClass = Mon3terSprite.class;
            baseSpeed = 1f;

            state = HUNTING;
            immunities.add(Silence.class);
            alignment = Alignment.ALLY;
        }

        private int level = 0;
        private int blinkCooldown = 0;

        @Override
        protected boolean getCloser( int target ) {
            if (fieldOfView[target] && Dungeon.level.distance( pos, target ) > 2 && blinkCooldown <= 0) {

                blink( target );
                spend( -1 / speed() );
                return true;

            } else {

                blinkCooldown--;
                return super.getCloser( target );

            }
        }

        private void blink( int target ) {

            Ballistica route = new Ballistica( pos, target, Ballistica.PROJECTILE);
            int cell = route.collisionPos;

            //can't occupy the same cell as another char, so move back one.
            if (Actor.findChar( cell ) != null && cell != this.pos)
                cell = route.path.get(route.dist-1);

            if (Dungeon.level.avoid[ cell ]){
                ArrayList<Integer> candidates = new ArrayList<>();
                for (int n : PathFinder.NEIGHBOURS8) {
                    cell = route.collisionPos + n;
                    if (Dungeon.level.passable[cell] && Actor.findChar( cell ) == null) {
                        candidates.add( cell );
                    }
                }
                if (candidates.size() > 0)
                    cell = Random.element(candidates);
                else {
                    blinkCooldown = 1;
                    return;
                }
            }

            ScrollOfTeleportation.appear( this, cell );
            Sample.INSTANCE.play( Assets.Sounds.SKILL_MON2 );

            blinkCooldown = 1;
        }

        @Override
        protected boolean act() {
            if (this.buff(StoneOfAggression.Aggression.class) == null) {
                Buff.prolong(this, StoneOfAggression.Aggression.class, StoneOfAggression.Aggression.DURATION);}

            CustomeSet.CustomSetBuff setBuff = Dungeon.hero.buff( CustomeSet.CustomSetBuff.class);
            if (setBuff != null) {
                int n = 50 + setBuff.itemLevel() * 3;
                damage(HT/n, this);
            }
            else damage(HT/50, this);
            return super.act();
        }

        @Override
        public int attackProc(Char enemy, int damage) {
            CustomeSet.CustomSetBuff setBuff = Dungeon.hero.buff( CustomeSet.CustomSetBuff.class);
            if (setBuff != null) {
                int n = 20 + setBuff.itemLevel() * 3;
                damage(HT/n, this);
            }
            else damage(HT/20, this);
            Sample.INSTANCE.play( Assets.Sounds.HIT_BREAK );

            if (Dungeon.hero.belongings.weapon instanceof CatGun) {
                ((CatGun)Dungeon.hero.belongings.weapon).SPCharge(1);
            }
            return super.attackProc(enemy, damage);
        }

        @Override
        public int damageRoll() {
            return Random.NormalIntRange( 18 + level * 4, 24 + level * 6 );
        }

        @Override
        public int attackSkill( Char target ) {
            return 25 + level * 3;
        }

        @Override
        public int drRoll() {
            return Random.NormalIntRange(level / 2, 2 + level);
        }

        public void setting(int setlvl)
        {
            CustomeSet.CustomSetBuff setBuff = Dungeon.hero.buff( CustomeSet.CustomSetBuff.class);
            int itembuff = 0;
            if (setBuff != null) itembuff = setBuff.itemLevel();
            HP=HT=120 + setlvl * 20;
            defenseSkill = 10 + setlvl * 2;
            level = setlvl + itembuff / 2;
        }

        private static final String BLINK = "blinkcooldown";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(BLINK, blinkCooldown);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            blinkCooldown = bundle.getInt(BLINK);
        }
    }

}
