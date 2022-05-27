/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.items.weapon;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Berserk;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Camouflage;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ReflowBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RevealedArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WindEnergy;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfFuror;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Blindweed;
import com.shatteredpixel.shatteredpixeldungeon.plants.Dreamfoil;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sorrowmoss;
import com.shatteredpixel.shatteredpixeldungeon.plants.Starflower;
import com.shatteredpixel.shatteredpixeldungeon.plants.Stormvine;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SpiritBow extends Weapon {
	
	public static final String AC_SHOOT		= "SHOOT";
	public static final String AC_SEED		= "SEED";

	{
		image = ItemSpriteSheet.SPIRIT_BOW;
		
		defaultAction = AC_SHOOT;
		usesTargeting = true;
		
		unique = true;
		bones = false;
	}
	
	public boolean sniperSpecial = false;
	public float sniperSpecialBonusDamage = 0f;
	protected WndBag.Mode mode = WndBag.Mode.SEED;
	public int EatSeed = 0;
	public int SeedHit = 0; // 2일때 적중시키면 보호막 생성
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_EQUIP);
		actions.add(AC_SHOOT);
		if (hero.subClass == HeroSubClass.WARDEN && EatSeed < 45) actions.add(AC_SEED);
		return actions;
	}
	
	@Override
	public void execute(Hero hero, String action) {
		
		super.execute(hero, action);
		
		if (action.equals(AC_SHOOT)) {
			
			curUser = hero;
			curItem = this;
			GameScene.selectCell( shooter );

		}

		if (action.equals(AC_SEED)) {
			GameScene.selectItem(itemSelector, mode, Messages.get(this, "prompt"));
		}

	}
	
	@Override
	public String info() {
		String info = desc();
		
		info += "\n\n" + Messages.get( SpiritBow.class, "stats",
				Math.round(augment.damageFactor(min())),
				Math.round(augment.damageFactor(max())),
				STRReq());
		
		if (STRReq() > Dungeon.hero.STR()) {
			info += " " + Messages.get(Weapon.class, "too_heavy");
		} else if (Dungeon.hero.STR() > STRReq()){
			info += " " + Messages.get(Weapon.class, "excess_str", Dungeon.hero.STR() - STRReq());
		}
		
		switch (augment) {
			case SPEED:
				info += "\n\n" + Messages.get(Weapon.class, "faster");
				break;
			case DAMAGE:
				info += "\n\n" + Messages.get(Weapon.class, "stronger");
				break;
			case OVERLOAD:
				info += "\n\n" + Messages.get(Weapon.class, "overload");
				break;
			case NONE:
		}
		
		if (enchantment != null && (cursedKnown || !enchantment.curse())){
			info += "\n\n" + Messages.get(Weapon.class, "enchanted", enchantment.name());
			info += " " + Messages.get(enchantment, "desc");
		}
		
		if (cursed && isEquipped( Dungeon.hero )) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed");
		} else if (!isIdentified() && cursedKnown){
			info += "\n\n" + Messages.get(Weapon.class, "not_cursed");
		}
		
		info += "\n\n" + Messages.get(MissileWeapon.class, "distance");

		if (Dungeon.hero.subClass==HeroSubClass.WARDEN) {
			info+="\n\n"+Messages.get(SpiritBow.class, "seed", EatSeed, 45);
			if (EatSeed == 45) info+="\n\n"+Messages.get(SpiritBow.class, "power3");
			else if (EatSeed >= 30) info+="\n\n"+Messages.get(SpiritBow.class, "power2");
			else if (EatSeed >= 15) info+="\n\n"+Messages.get(SpiritBow.class, "power1");
			else info+="\n\n"+Messages.get(SpiritBow.class, "power0");
		}
		
		return info;
	}
	
	@Override
	public int STRReq(int lvl) {
		return STRReq(1, lvl); //tier 1
	}
	
	@Override
	public int min(int lvl) {
		return 1 + Dungeon.hero.lvl/5
				+ RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
				+ (curseInfusionBonus ? 1 : 0);
	}
	
	@Override
	public int max(int lvl) {
		if (Dungeon.hero.buff(ReflowBuff.class) != null) {
			return 6 + (int)(Dungeon.hero.lvl/2.5f)
					+ 2*RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
					+ (curseInfusionBonus ? 2 : 0)
			        + Dungeon.hero.lvl /2 + Dungeon.hero.STR /4
					+ Dungeon.hero.pointsInTalent(Talent.IMPROVED_CROSSBOW) * 2
					+ EatSeed / 9;
		}
		else return 6 + (int)(Dungeon.hero.lvl/2.5f)
				+ 2*RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
				+ (curseInfusionBonus ? 2 : 0)
				+ Dungeon.hero.pointsInTalent(Talent.IMPROVED_CROSSBOW) * 2
				+ EatSeed / 9;
	}

	@Override
	public int targetingPos(Hero user, int dst) {
		return knockArrow().targetingPos(user, dst);
	}
	
	private int targetPos;
	
	@Override
	public int damageRoll(Char owner) {
		int damage = augment.damageFactor(super.damageRoll(owner));
		
		if (owner instanceof Hero) {
			int exStr = ((Hero)owner).STR() - STRReq();
			if (exStr > 0) {
				damage += Random.IntRange( 0, exStr );
			}
		}

		if (sniperSpecial){
			damage = Math.round(damage * (1f + sniperSpecialBonusDamage + Dungeon.hero.pointsInTalent(Talent.SNIPING)*0.15f));

			switch (augment){
				case NONE:
				case OVERLOAD:
					damage = Math.round(damage * 0.75f);
					break;
				case SPEED:
					damage = Math.round(damage * 1.1f);
					break;
				case DAMAGE:
					//as distance increases so does damage, capping at 3x:
					//1.20x|1.35x|1.52x|1.71x|1.92x|2.16x|2.43x|2.74x|3.00x
					int distance = Dungeon.level.distance(owner.pos, targetPos) - 1;
					float multiplier = Math.min(3f, 1.2f * (float)Math.pow(1.125f, distance));
					damage = Math.round(damage * multiplier);
					break;
			}
		}
		else if (curUser.subClass == HeroSubClass.SNIPER) {
			damage = Math.round(damage * 1.1f);
		}
		
		return damage;
	}
	
	@Override
	public float speedFactor(Char owner) {
		if (sniperSpecial){
			switch (augment){
				case NONE:
				case OVERLOAD: default:
					return 0f;
				case SPEED:
					return 1f * RingOfFuror.attackDelayMultiplier(owner);
				case DAMAGE:
					return 2f * RingOfFuror.attackDelayMultiplier(owner);
			}
		} else {
			return super.speedFactor(owner);
		}
	}

	@Override
	public int level() {
		return (Dungeon.hero == null ? 0 : Dungeon.hero.lvl/5) + (curseInfusionBonus ? 1 : 0);
	}

	@Override
	public int buffedLvl() {
		//level isn't affected by buffs/debuffs
		return level();
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	public SpiritArrow knockArrow(){
		return new SpiritArrow();
	}

	public class SpiritArrow extends MissileWeapon {

		{
			image = ItemSpriteSheet.SPIRIT_ARROW;

			hitSound = Assets.Sounds.HIT_ARROW;
		}

		@Override
		public int damageRoll(Char owner) {
			return SpiritBow.this.damageRoll(owner);
		}

		@Override
		public boolean hasEnchant(Class<? extends Enchantment> type, Char owner) {
			return SpiritBow.this.hasEnchant(type, owner);
		}

		@Override
		public int proc(Char attacker, Char defender, int damage) {
			if (Random.Int(10) < Dungeon.hero.pointsInTalent(Talent.POINT_BLANK)) {
				Buff.affect(defender, Vertigo.class, 2f);
			}
			if (EatSeed >= 30) SeedHit++;

			if (SeedHit == 5) {Buff.affect(Dungeon.hero, Barrier.class).setShield(Dungeon.hero.HT/8);
			SeedHit = 0;}

			if (EatSeed >= 45) {
				if (Random.Int(7) == 0) {
					int Chance = Random.Int(7);
					switch (Chance) {
						case 0: new Blindweed().activate(defender); break;
						case 1:	new FlavourBuff(){
							{actPriority = VFX_PRIO;}
							public boolean act() {
								Buff.affect( defender, Sleep.class );
								return super.act();
							}
						}.attachTo(defender); // 꿈풀 효과
						break;
						case 2: new Sorrowmoss().activate(defender); break;
						case 3:
							new Stormvine().activate(defender); break;
						case 4: new Starflower().activate(attacker); break;
						case 5: new Swiftthistle().activate(attacker); break;
						case 6: new Dreamfoil().activate(attacker); break;
						default:
					}
				}
			}

			if (Dungeon.hero.subClass == HeroSubClass.STOME) {
				WindEnergy wind = Buff.affect(Dungeon.hero, WindEnergy.class);
				wind.GetEnergy(15); }

			if (Random.Int(2) == 0) {
				Buff.detach(defender, Camouflage.class);
			}

			return SpiritBow.this.proc(attacker, defender, damage);
		}

		@Override
		public float speedFactor(Char user) {
			return SpiritBow.this.speedFactor(user);
		}

		@Override
		public float accuracyFactor(Char owner) {
			float accFactor = super.accuracyFactor(owner);
			if (sniperSpecial && SpiritBow.this.augment == Augment.DAMAGE){
				return Float.POSITIVE_INFINITY;
			} else {
				if (SpiritBow.this.augment == Augment.OVERLOAD) accFactor = 0.65f;
				return accFactor;
			}
		}
		
		@Override
		public int STRReq(int lvl) {
			return SpiritBow.this.STRReq(lvl);
		}

		@Override
		protected void onThrow( int cell ) {
			Char enemy = Actor.findChar( cell );
			if (enemy == null || enemy == curUser) {
				parent = null;
				Splash.at( cell, 0xCC99FFFF, 1 );
			} else {
				if (!curUser.shoot( enemy, this )) {
					Splash.at(cell, 0xCC99FFFF, 1);
				}
				if (sniperSpecial && SpiritBow.this.augment == Augment.SPEED)
				{
					Buff.affect(enemy, Cripple.class, 2f);
				}
				sniperSpecial = false;
			}
		}

		@Override
		public void throwSound() {
			Sample.INSTANCE.play( Assets.Sounds.ATK_SPIRITBOW, 1, Random.Float(0.87f, 1.15f) );
		}

		int flurryCount = -1;
		
		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			SpiritBow.this.targetPos = cell;

			if (user.hasTalent(Talent.BOMB_ARROW)
					&& user.buff(Talent.bombshotooldown.class) == null){
				int shotPos = throwPos(user, dst);
				if (Actor.findChar(shotPos) == null) {
					for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
						if (Dungeon.level.adjacent(mob.pos, shotPos) && mob.alignment != Char.Alignment.ALLY) {
							float dmg = damageRoll(user) * (0.5f + user.pointsInTalent(Talent.BOMB_ARROW) * 0.5f);
							mob.damage((int)dmg, this);
							CellEmitter.center(mob.pos).burst(BlastParticle.FACTORY, 10);
						}
					}
					Buff.affect(user, Talent.bombshotooldown.class, 40 - (user.pointsInTalent(Talent.BOMB_ARROW) * 10));
				}
			}

				if (user.hasTalent(Talent.SEER_SHOT)
						&& user.buff(Talent.SeerShotCooldown.class) == null){
					int shotPos = throwPos(user, dst);
					if (Actor.findChar(shotPos) == null) {
						RevealedArea a = Buff.affect(user, RevealedArea.class, user.pointsInTalent(Talent.SEER_SHOT));
						for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
							if (Dungeon.level.adjacent(mob.pos, shotPos) && mob.alignment != Char.Alignment.ALLY) {
								Buff.affect(mob, Roots.class, 3f);
							}
						}
						a.depth = Dungeon.depth;
						a.pos = shotPos;
						Buff.affect(user, Talent.SeerShotCooldown.class, 80 - (user.pointsInTalent(Talent.SEER_SHOT) * 20));
						Camera.main.shake(2, 0.5f);
					}
				}

			if (user.hasTalent(Talent.ARTS_FOCUS)
					&& user.buff(Talent.SilShotCooldown.class) == null){
				int shotPos = throwPos(user, dst);
				if (Actor.findChar(shotPos) == null) {
					for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
						if (Dungeon.level.adjacent(mob.pos, shotPos) && mob.alignment != Char.Alignment.ALLY) {
							Buff.affect(mob, Silence.class,3f);
						}
					}
					Buff.affect(user, Talent.SilShotCooldown.class, 80 - (user.pointsInTalent(Talent.ARTS_FOCUS) * 20));
				}
			}

				super.cast(user, dst);
			}
		}

	
	private CellSelector.Listener shooter = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer target ) {
			if (target != null) {
				knockArrow().cast(curUser, target);
			}
		}
		@Override
		public String prompt() {
			return Messages.get(SpiritBow.class, "prompt");
		}
	};

	protected WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null && item instanceof Plant.Seed) {
				if (EatSeed < 45) {
					Hero hero = Dungeon.hero;
					hero.sprite.operate(hero.pos);
					Sample.INSTANCE.play(Assets.Sounds.PLANT);
					hero.busy();
					hero.spend(1f);
					EatSeed++;
					item.detach(hero.belongings.backpack);
				}
			else GLog.w( Messages.get(SpiritBow.class, "seedfail") );
			}
		}
	};

	private static final String SEEDS = "EatSeed";
	private static final String HIT = "SeedHit";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put(SEEDS, EatSeed);
		bundle.put(HIT, SeedHit);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		EatSeed = bundle.getInt(SEEDS);
		SeedHit = bundle.getInt(HIT);
	}
}
