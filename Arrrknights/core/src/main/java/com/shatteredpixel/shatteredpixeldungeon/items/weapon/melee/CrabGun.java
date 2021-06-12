package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Brute;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.MirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.AncientKin;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Bug_ASprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HaundSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class CrabGun extends MeleeWeapon {
    {
        image = ItemSpriteSheet.ENFILD;
        hitSound = Assets.Sounds.SKILL_BEEP;
        hitSoundPitch = 1f;

        tier = 3;
        RCH = 2;
    }

    private int charge = 0;
    private int chargeCap = 100;

    @Override
    public int max(int lvl) {
        return  4*(tier-1) +    //8 + 2 (one target = x2)
                lvl*(tier-1);
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (charge >= chargeCap) {
            ArrayList<Integer> respawnPoints = new ArrayList<>();

            for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                int p = defender.pos + PathFinder.NEIGHBOURS8[i];
                if (Actor.findChar( p ) == null && Dungeon.level.passable[p]) {
                    respawnPoints.add( p );
                }
            }
            int spawnd = 0;
            while (respawnPoints.size() > 0 && spawnd == 0) {
                int index = Random.index( respawnPoints );

                CrabGun.MetalCrab crab = new CrabGun.MetalCrab();
                crab.setting(buffedLvl());
                GameScene.add( crab );
                ScrollOfTeleportation.appear( crab, respawnPoints.get( index ) );

                respawnPoints.remove( index );
                spawnd++;
            }
        }
        else SPCharge(Random.IntRange(3,7+buffedLvl() / 3));
        return super.proc(attacker, defender, damage);
    }

    public void SPCharge(int n) {
        charge += n;
        if (chargeCap < charge) charge = chargeCap;
        updateQuickslot();
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

    public class MetalCrab extends Mob {
        {
            spriteClass = Bug_ASprite.class;
            baseSpeed = 1f;

            immunities.add(Silence.class);
            alignment = Alignment.ALLY;
        }

        private int crabLevel = 0;

        @Override
        public int damageRoll() {
            return Random.NormalIntRange( 2 + Dungeon.depth / 2, 6 + Dungeon.depth + crabLevel );
        }

        @Override
        public int attackSkill( Char target ) {
            return 10 + Dungeon.depth + crabLevel;
        }

        @Override
        public int drRoll() {
            return Random.NormalIntRange(0, 3 + crabLevel);
        }

        public void setting(int setlvl)
        {
            HP=HT=20 + setlvl * 8;
            defenseSkill = 1 + setlvl * 2;
            crabLevel = setlvl;
        }
    }
}