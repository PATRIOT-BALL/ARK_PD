package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MissileSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class HotBlade extends Skill {
    public void doSkill() {

        final HotBlade.blade blade = new HotBlade.blade();

        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]) {
                dadadada(blade, mob.pos);
                dohit(mob);
                dohit(mob);
                mob.sprite.burst(CharSprite.NEGATIVE, 10);
            }
        }

        Invisibility.dispel();
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
        curUser.spendAndNext(1f);
    }

        public void dadadada(HotBlade.blade blade, int cell) {
            ((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class)).
                    reset(curUser.sprite,
                            cell,
                            blade,
                            new Callback() {
                                @Override
                                public void call() {
                                    curUser.sprite.Sattack(cell);
                                    blade.onThrow(cell);

                                    curUser.speed();
                                }
                            });
        }


    public class blade extends MissileWeapon {

        {
            image = ItemSpriteSheet.SHURIKEN;

            hitSound = Assets.Sounds.HIT_SLASH;
            hitSoundPitch = 1.17f;
        }

        @Override
        protected void onThrow( int cell ) {
            Char enemy = Actor.findChar( cell );
            parent = null;

        }

        @Override
        public float accuracyFactor(Char owner) {
            return Float.POSITIVE_INFINITY;
        }
    }

    public void dohit(final Char enemy) {
        int min =  Math.max(1, 5 + (Dungeon.hero.lvl / 4) + (RingOfSharpshooting.levelDamageBonus(Dungeon.hero) / 2 ) );
        int max =  Math.max(1, 8 + (Dungeon.hero.lvl / 2) + (RingOfSharpshooting.levelDamageBonus(Dungeon.hero)) );
        int dmg = Random.NormalIntRange(min, max);
        dmg -= enemy.drRoll();
        enemy.damage(dmg, enemy);
    }
}