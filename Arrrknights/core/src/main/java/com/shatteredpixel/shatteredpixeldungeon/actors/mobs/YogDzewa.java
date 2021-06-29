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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PurpleParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.DewVial;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SkillBook;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.PotionBandolier;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.ScrollHolder;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.VelvetPouch;
import com.shatteredpixel.shatteredpixeldungeon.items.food.FrozenCarpaccio;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorruption;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Ursus_InfantrySprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.YogSprite;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class YogDzewa extends Mob {

	{
		spriteClass = YogSprite.class;

		HP = HT = 1000;

		EXP = 50;

		//so that allies can attack it. States are never actually used.
		state = HUNTING;

		viewDistance = 12;

		properties.add(Property.BOSS);
		properties.add(Property.IMMOVABLE);
	}

	private int phase = 0;

	private float abilityCooldown;
	private static final int MIN_ABILITY_CD = 10;
	private static final int MAX_ABILITY_CD = 15;

	private float summonCooldown;
	private static final int MIN_SUMMON_CD = 10;
	private static final int MAX_SUMMON_CD = 15;

	private ArrayList<Class> fistSummons = new ArrayList<>();
	{
		Random.pushGenerator(Dungeon.seedCurDepth());
			fistSummons.add(Random.Int(2) == 0 ? YogFist.BurningFist.class : YogFist.SoiledFist.class);
			fistSummons.add(Random.Int(2) == 0 ? YogFist.RottingFist.class : YogFist.RustedFist.class);
			fistSummons.add(Random.Int(2) == 0 ? YogFist.BrightFist.class : YogFist.DarkFist.class);
			Random.shuffle(fistSummons);
		Random.popGenerator();
	}

	private static final int SUMMON_DECK_SIZE = 4;
	private ArrayList<Class> regularSummons = new ArrayList<>();
	{
		for (int i = 0; i < SUMMON_DECK_SIZE; i++){
			if (i >= Statistics.spawnersAlive){
				regularSummons.add(Larva.class);
			} else {
				regularSummons.add(YogRipper.class);
			}
		}
		Random.shuffle(regularSummons);
	}

	private ArrayList<Integer> targetedCells = new ArrayList<>();

	@Override
	protected boolean act() {
		//char logic
		if (fieldOfView == null || fieldOfView.length != Dungeon.level.length()){
			fieldOfView = new boolean[Dungeon.level.length()];
		}
		Dungeon.level.updateFieldOfView( this, fieldOfView );

		throwItems();

		//mob logic
		enemy = chooseEnemy();

		enemySeen = enemy != null && enemy.isAlive() && fieldOfView[enemy.pos] && enemy.invisible <= 0;
		//end of char/mob logic

		if (phase == 0){
			if (Dungeon.hero.viewDistance >= Dungeon.level.distance(pos, Dungeon.hero.pos)) {
				Dungeon.observe();
			}
			if (Dungeon.level.heroFOV[pos]) {
				notice();
			}
		}

		if (phase == 4 && findFist() == null){
			yell(Messages.get(this, "phase4_end"));
			summonCooldown = -15; //summon a burst of minions!
			phase = 5;
		}

		if (phase == 0){
			spend(TICK);
			return true;
		} else {

			boolean terrainAffected = false;
			HashSet<Char> affected = new HashSet<>();
			//delay fire on a rooted hero
			if (!Dungeon.hero.rooted) {
				for (int i : targetedCells) {
					Ballistica b = new Ballistica(pos, i, Ballistica.WONT_STOP);
					//shoot beams
					sprite.parent.add(new Beam.DeathRay(sprite.center(), DungeonTilemap.raisedTileCenterToWorld(b.collisionPos)));
					for (int p : b.path) {
						Char ch = Actor.findChar(p);
						if (ch != null && (ch.alignment != alignment || ch instanceof Bee)) {
							affected.add(ch);
						}
						if (Dungeon.level.flamable[p]) {
							Dungeon.level.destroy(p);
							GameScene.updateMap(p);
							terrainAffected = true;
						}
					}
				}
				if (terrainAffected) {
					Dungeon.observe();
				}
				for (Char ch : affected) {
					if (Dungeon.isChallenged(Challenges.DECISIVE_BATTLE)) ch.damage(Random.NormalIntRange(30, 45), new Eye.DeathGaze());
					else ch.damage(Random.NormalIntRange(20, 30), new Eye.DeathGaze());

					if (Dungeon.isChallenged(Challenges.SPECIAL_BOSS) && Dungeon.mboss9 == 1) Buff.affect(ch, Paralysis.class, 20f);

					if (Dungeon.level.heroFOV[pos]) {
						ch.sprite.flash();
						CellEmitter.center(pos).burst(PurpleParticle.BURST, Random.IntRange(1, 2));
					}
					if (!ch.isAlive() && ch == Dungeon.hero) {
						Dungeon.fail(getClass());
						GLog.n(Messages.get(Char.class, "kill", name()));
					}
				}
				targetedCells.clear();
			}

			if (abilityCooldown <= 0){

				int beams = 1 + (HT - HP)/400;
				HashSet<Integer> affectedCells = new HashSet<>();
				for (int i = 0; i < beams; i++){

					int targetPos = Dungeon.hero.pos;
					if (i != 0){
						do {
							targetPos = Dungeon.hero.pos + PathFinder.NEIGHBOURS8[Random.Int(8)];
						} while (Dungeon.level.trueDistance(pos, Dungeon.hero.pos)
								> Dungeon.level.trueDistance(pos, targetPos));
					}
					targetedCells.add(targetPos);
					Ballistica b = new Ballistica(pos, targetPos, Ballistica.WONT_STOP);
					affectedCells.addAll(b.path);
				}

				//remove one beam if multiple shots would cause every cell next to the hero to be targeted
				boolean allAdjTargeted = true;
				for (int i : PathFinder.NEIGHBOURS9){
					if (!affectedCells.contains(Dungeon.hero.pos + i) && Dungeon.level.passable[Dungeon.hero.pos + i]){
						allAdjTargeted = false;
						break;
					}
				}
				if (allAdjTargeted){
					targetedCells.remove(targetedCells.size()-1);
				}
				for (int i : targetedCells){
					Ballistica b = new Ballistica(pos, i, Ballistica.WONT_STOP);
					for (int p : b.path){
						sprite.parent.add(new TargetedCell(p, 0xFF0000));
						affectedCells.add(p);
					}
				}

				//don't want to overly punish players with slow move or attack speed
				spend(GameMath.gate(TICK, Dungeon.hero.cooldown(), 3*TICK));
				Dungeon.hero.interrupt();

				abilityCooldown += Random.NormalFloat(MIN_ABILITY_CD, MAX_ABILITY_CD);
				abilityCooldown -= (phase - 1);

			} else {
				spend(TICK);
			}

			while (summonCooldown <= 0){

				Class<?extends Mob> cls = regularSummons.remove(0);
				Mob summon = Reflection.newInstance(cls);
				regularSummons.add(cls);

				int spawnPos = -1;
				for (int i : PathFinder.NEIGHBOURS8){
					if (Actor.findChar(pos+i) == null){
						if (spawnPos == -1 || Dungeon.level.trueDistance(Dungeon.hero.pos, spawnPos) > Dungeon.level.trueDistance(Dungeon.hero.pos, pos+i)){
							spawnPos = pos + i;
						}
					}
				}

				if (spawnPos != -1) {
					summon.pos = spawnPos;
					GameScene.add( summon );
					Actor.addDelayed( new Pushing( summon, pos, summon.pos ), -1 );
					if (Dungeon.isChallenged(Challenges.SPECIAL_BOSS) && Dungeon.mboss4 == 1) Buff.affect(summon, Adrenaline.class, 50f);
					summon.beckon(Dungeon.hero.pos);

					summonCooldown += Random.NormalFloat(MIN_SUMMON_CD, MAX_SUMMON_CD);
					summonCooldown -= (phase - 1);
					if (findFist() != null){
						summonCooldown += MIN_SUMMON_CD - (phase - 1);
					}
				} else {
					break;
				}
			}

		}

		if (summonCooldown > 0) summonCooldown--;
		if (abilityCooldown > 0) abilityCooldown--;

		//extra fast abilities and summons at the final 100 HP
		if (phase == 5 && abilityCooldown > 2){
			abilityCooldown = 2;
		}
		if (phase == 5 && summonCooldown > 3){
			summonCooldown = 3;
		}

		return true;
	}

	@Override
	public boolean isAlive() {
		return super.isAlive() || phase != 5;
	}

	@Override
	public boolean isInvulnerable(Class effect) {
		return phase == 0 || findFist() != null;
	}

	@Override
	public void damage( int dmg, Object src ) {

		int preHP = HP;
		super.damage( dmg, src );

		if (phase == 0 || findFist() != null) return;

		if (phase < 4) {
			HP = Math.max(HP, HT - 300 * phase);
		} else if (phase == 4) {
			HP = Math.max(HP, 100);
		}
		int dmgTaken = preHP - HP;

		if (dmgTaken > 0) {
			abilityCooldown -= dmgTaken / 10f;
			summonCooldown -= dmgTaken / 10f;
		}

		if (phase < 4 && HP <= HT - 300*phase){

			phase++;

			updateVisibility(Dungeon.level);
			if (phase == 2) {if (Dungeon.isChallenged(Challenges.SPECIAL_BOSS)) yell(Messages.get(this, "phase2_ch"));
			else GLog.n(Messages.get(this, "phase2"));}
			else if (phase == 3) {if (Dungeon.isChallenged(Challenges.SPECIAL_BOSS)) yell(Messages.get(this, "phase3_ch"));
			else GLog.n(Messages.get(this, "phase3"));}
			else if (phase == 4)  GLog.n(Messages.get(this, "phase4"));
			sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "invulnerable"));

			YogFist fist = (YogFist) Reflection.newInstance(fistSummons.remove(0));
			fist.pos = Dungeon.level.exit;

			CellEmitter.get(Dungeon.level.exit-1).burst(ShadowParticle.UP, 25);
			CellEmitter.get(Dungeon.level.exit).burst(ShadowParticle.UP, 100);
			CellEmitter.get(Dungeon.level.exit+1).burst(ShadowParticle.UP, 25);

			if (abilityCooldown < 5) abilityCooldown = 5;
			if (summonCooldown < 5) summonCooldown = 5;

			int targetPos = Dungeon.level.exit + Dungeon.level.width();
			if (Actor.findChar(targetPos) == null){
				fist.pos = targetPos;
			} else if (Actor.findChar(targetPos-1) == null){
				fist.pos = targetPos-1;
			} else if (Actor.findChar(targetPos+1) == null){
				fist.pos = targetPos+1;
			}

			GameScene.add(fist, 4);
			if (Dungeon.isChallenged(Challenges.SPECIAL_BOSS)) Buff.affect(fist, Bless.class, 300f);
			Actor.addDelayed( new Pushing( fist, Dungeon.level.exit, fist.pos ), -1 );
		}

		LockedFloor lock = Dungeon.hero.buff(LockedFloor.class);
		if (lock != null) lock.addTime(dmgTaken);

	}

	public void updateVisibility( Level level ){
		if (phase > 1 && isAlive()){
			level.viewDistance = 4 - (phase-1);
		} else {
			level.viewDistance = 4;
		}
		level.viewDistance = Math.max(1, level.viewDistance);
		if (Dungeon.hero != null) {
			if (Dungeon.hero.buff(Light.class) == null) {
				Dungeon.hero.viewDistance = level.viewDistance;
			}
			Dungeon.observe();
		}
	}

	private YogFist findFist(){
		for ( Char c : Actor.chars() ){
			if (c instanceof YogFist){
				return (YogFist) c;
			}
		}
		return null;
	}

	@Override
	public void beckon( int cell ) {
	}

	@Override
	public void aggro(Char ch) {
		for (Mob mob : (Iterable<Mob>)Dungeon.level.mobs.clone()) {
			if (Dungeon.level.distance(pos, mob.pos) <= 4 &&
					(mob instanceof Larva || mob instanceof RipperDemon)) {
				mob.aggro(ch);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void die( Object cause ) {

		for (Mob mob : (Iterable<Mob>)Dungeon.level.mobs.clone()) {
			if (mob instanceof Larva || mob instanceof RipperDemon) {
				mob.die( cause );
			}
		}

		updateVisibility(Dungeon.level);

		GameScene.bossSlain();
		Dungeon.level.unseal();
		super.die( cause );

		yell( Messages.get(this, "defeated") );
		if (Dungeon.isChallenged(Challenges.SPECIAL_BOSS)) Badges.validateroaringflare();
		if (Dungeon.isChallenged(Challenges.SPECIAL_BOSS) && Dungeon.mboss4 == 1 && Dungeon.mboss9 == 1 && Dungeon.mboss14 == 1 && Dungeon.mboss19 == 1)
		Badges.validateeviltimeend();
	}

	@Override
	public void notice() {
		if (!BossHealthBar.isAssigned()) {
			BossHealthBar.assignBoss(this);
			if (Dungeon.isChallenged(Challenges.SPECIAL_BOSS)) yell(Messages.get(this, "notice_ch"));
			else yell(Messages.get(this, "notice"));
			if (Dungeon.isChallenged(Challenges.SPECIAL_BOSS) && Dungeon.mboss14 == 1) {
				Dungeon.hero.SK1 = null;
				Dungeon.hero.SK2 = null;
				Dungeon.hero.SK3 = null;
				Dungeon.hero.updateHT(true);
			}
				if (Dungeon.isChallenged(Challenges.SPECIAL_BOSS) && Dungeon.mboss19 == 1) {
					Buff.affect(Dungeon.hero, Doom.class);
				}
				for (Char ch : Actor.chars()) {
					if (ch instanceof DriedRose.GhostHero) {
						((DriedRose.GhostHero) ch).sayBoss();
					}
				}
				if (phase == 0) {
					phase = 1;
					summonCooldown = Random.NormalFloat(MIN_SUMMON_CD, MAX_SUMMON_CD);
					abilityCooldown = Random.NormalFloat(MIN_ABILITY_CD, MAX_ABILITY_CD);
				}
			}
		}


	@Override
	public String description() {
		String desc = super.description();

		if (Statistics.spawnersAlive > 0){
			desc += "\n\n" + Messages.get(this, "desc_spawners");
		}

		return desc;
	}

	{
		immunities.add( Terror.class );
		immunities.add( Amok.class );
		immunities.add( Charm.class );
		immunities.add( Sleep.class );
		immunities.add( Vertigo.class );
		immunities.add( Frost.class );
		immunities.add( Paralysis.class );
	}

	private static final String PHASE = "phase";

	private static final String ABILITY_CD = "ability_cd";
	private static final String SUMMON_CD = "summon_cd";

	private static final String FIST_SUMMONS = "fist_summons";
	private static final String REGULAR_SUMMONS = "regular_summons";

	private static final String TARGETED_CELLS = "targeted_cells";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(PHASE, phase);

		bundle.put(ABILITY_CD, abilityCooldown);
		bundle.put(SUMMON_CD, summonCooldown);

		bundle.put(FIST_SUMMONS, fistSummons.toArray(new Class[0]));
		bundle.put(REGULAR_SUMMONS, regularSummons.toArray(new Class[0]));

		int[] bundleArr = new int[targetedCells.size()];
		for (int i = 0; i < targetedCells.size(); i++){
			bundleArr[i] = targetedCells.get(i);
		}
		bundle.put(TARGETED_CELLS, bundleArr);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		phase = bundle.getInt(PHASE);
		if (phase != 0) BossHealthBar.assignBoss(this);

		abilityCooldown = bundle.getFloat(ABILITY_CD);
		summonCooldown = bundle.getFloat(SUMMON_CD);

		fistSummons.clear();
		Collections.addAll(fistSummons, bundle.getClassArray(FIST_SUMMONS));
		regularSummons.clear();
		Collections.addAll(regularSummons, bundle.getClassArray(REGULAR_SUMMONS));

		for (int i : bundle.getIntArray(TARGETED_CELLS)){
			targetedCells.add(i);
		}
	}

	public static class Larva extends Mob {

		{
			spriteClass = Ursus_InfantrySprite.class;

			HP = HT = 25;
			defenseSkill = 12;
			viewDistance = Light.DISTANCE;

			EXP = 5;
			maxLvl = -2;

			immunities.add(Silence.class);
		}

		@Override
		public int attackSkill( Char target ) {
			return 30;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange( 15, 25 );
		}

		@Override
		public int drRoll() {
			return Random.NormalIntRange(0, 4);
		}

	}

	//used so death to yog's ripper demons have their own rankings description and are more aggro
	public static class YogRipper extends RipperDemon {
	}
}
