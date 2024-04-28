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

package com.shatteredpixel.shatteredpixeldungeon.journal;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookCamouflage;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookChainHook;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookCrimsonCutter;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookExecutionMode;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookFate;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookFierceGlare;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookFoodPrep;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookHikari;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookHotBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookLive;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookPhantomMirror;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookPowerfulStrike;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookShinkageryu;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookSoul;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookSpreadSpores;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookTacticalChanting;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookThoughts;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookWhispers;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookWolfSpirit;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.Bookpanorama;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookBenasProtracto;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookChargingPS;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookCoverSmoke;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookDawn;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookDeepHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookDreamland;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookEmergencyDefibrillator;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookFlashShield;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookGenesis;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookJackinthebox;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookLandingStrike;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookMentalBurst;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookNervous;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookNeverBackDown;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookPredators;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookReflow;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookRockfailHammer;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookSpikes;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookWolfPack;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.Bookancientkin;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3.BookEveryone;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3.BookNigetRaid;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3.BookSBurst;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3.BookShadowAssault;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3.BookSharpness;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3.BookSoaringFeather;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3.BookSun;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3.BookTerminationT;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3.BookTrueSilverSlash;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3.BookYourWish;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.AlchemyKit;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CustomeSet;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.EtherealChains;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HornOfPlenty;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.IsekaiItem;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.MasterThievesArmband;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.SandalsOfNature;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.UnstableSpellbook;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.WoundsofWar;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfParalyticGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfPurity;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAccuracy;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAmplified;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAssassin;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfCommand;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfDominate;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfElements;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEvasion;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfForce;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfFuror;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMistress;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSunLight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfTenacity;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTerror;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfWarp;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfAbsinthe;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfAngelina;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfBreeze;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfCorrupting;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfGreyy;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfLeaf;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfLena;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfMayer;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfMudrock;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfPodenco;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfPurgatory;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfShining;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfSkyfire;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfSnowsant;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfSussurro;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfSuzuran;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfTime;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfVigna;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfWeedy;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorrosion;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorruption;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfDisintegration;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfHallucination;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLightning;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLivingEarth;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfPrismaticLight;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfRegrowth;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfSilence;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfTransfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfWarding;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssassinsBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.BattleAxe;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Beowulf;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.C1_9mm;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CatGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrabGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Crossbow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DP27;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Dagger;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Dirk;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DivineAvatar;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.EX42;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Echeveria;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Enfild;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Enfild2;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Firmament;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Flag;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Decapitator;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlameKatana;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FlametailSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FolkSong;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gamzashield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gauntlet;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KazemaruWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KollamSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.LoneJourney;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Ots03;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Pkp;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SG_CQB;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SanktaBet;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShadowFirmament;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gloves;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gluttony;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GoldDogSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DeepAbyss;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Greatshield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Greatsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Halberd;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.HandAxe;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ImageoverForm;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KRISSVector;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.M1887;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MetallicUnion;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MinosFury;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.NEARL_AXE;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Naginata;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Niansword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Longsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Sig553;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Suffering;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ThermiteBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MidnightSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PatriotSpear;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.R4C;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RadiantSpear;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RhodesSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RoundShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RunicBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SHISHIOH;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Sai;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Castlebreaker;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SakuraSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Scythe;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Shortsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SnowHunter;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Spear;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Sword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SwordofArtorius;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Laevateinn;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Destreza;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Usg;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WarJournalist;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WintersScar;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WornShortsword;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public enum Catalog {

	WEAPONS,
	SKILLBOOK,
	WANDS,
	RINGS,
	ARTIFACTS,
	POTIONS,
	SCROLLS;

	private LinkedHashMap<Class<? extends Item>, Boolean> seen = new LinkedHashMap<>();

	public Collection<Class<? extends Item>> items(){
		return seen.keySet();
	}

	public boolean allSeen(){
		for (Class<?extends Item> item : items()){
			if (!seen.get(item)){
				return false;
			}
		}
		return true;
	}

	static {
		WEAPONS.seen.put( WornShortsword.class,             false);
		WEAPONS.seen.put( Gloves.class,                     false);
		WEAPONS.seen.put( Dagger.class,                     false);
		WEAPONS.seen.put( MagesStaff.class,                 false);
		WEAPONS.seen.put( EX42.class,                     false);
		WEAPONS.seen.put( NEARL_AXE.class,               false);
		WEAPONS.seen.put( Sig553.class,               false);

		WEAPONS.seen.put( Shortsword.class,                 false);
		WEAPONS.seen.put( HandAxe.class,                    false);
		WEAPONS.seen.put( Spear.class,                      false);
		WEAPONS.seen.put( Dirk.class,                       false);
		WEAPONS.seen.put( Enfild.class,                   false);
		WEAPONS.seen.put( MidnightSword.class,                   false);
		WEAPONS.seen.put( Halberd.class,                       false);
		WEAPONS.seen.put( FlameKatana.class,                       false);
		WEAPONS.seen.put( Usg.class,                   false);

		WEAPONS.seen.put( Sword.class,                      false);
		WEAPONS.seen.put( ThermiteBlade.class,                       false);
		WEAPONS.seen.put( Castlebreaker.class,                   false);
		WEAPONS.seen.put( RoundShield.class,                false);
		WEAPONS.seen.put( Sai.class,                        false);
		WEAPONS.seen.put( Destreza.class,                       false);
		WEAPONS.seen.put( SHISHIOH.class,                      false);
		WEAPONS.seen.put( Flag.class,                false);
		WEAPONS.seen.put( DP27.class,                false);
		WEAPONS.seen.put( C1_9mm.class,                false);

		WEAPONS.seen.put( Longsword.class,                  false);
		WEAPONS.seen.put( BattleAxe.class,                  false);
		WEAPONS.seen.put( RunicBlade.class,                 false);
		WEAPONS.seen.put( AssassinsBlade.class,             false);
		WEAPONS.seen.put( CrabGun.class,                       false);
		WEAPONS.seen.put( Crossbow.class,                   false);
		WEAPONS.seen.put( M1887.class,                       false);
		WEAPONS.seen.put( Naginata.class,                       false);
		WEAPONS.seen.put( Scythe.class,                false);
		WEAPONS.seen.put( FolkSong.class,                false);
		WEAPONS.seen.put( SnowHunter.class,                false);
		WEAPONS.seen.put( FlametailSword.class,                false);
		WEAPONS.seen.put( MetallicUnion.class,                false);
		WEAPONS.seen.put( WarJournalist.class,                false);
		WEAPONS.seen.put( KazemaruWeapon.class,                   false);
		WEAPONS.seen.put( Beowulf.class,                   false);
		WEAPONS.seen.put( Ots03.class,                   false);
		//WEAPONS.seen.put( SG_CQB.class,                   false);

		WEAPONS.seen.put( Greatsword.class,                 false);
		WEAPONS.seen.put( Laevateinn.class,                  false);
		WEAPONS.seen.put( ShadowFirmament.class,                     false);
		WEAPONS.seen.put( DeepAbyss.class,                   false);
		WEAPONS.seen.put( Greatshield.class,                false);
		WEAPONS.seen.put( Gauntlet.class,                   false);
		WEAPONS.seen.put( Decapitator.class,                      false);
		WEAPONS.seen.put( WintersScar.class,                   false);
		WEAPONS.seen.put( SwordofArtorius.class,                   false);
		WEAPONS.seen.put( DivineAvatar.class,                false);
		WEAPONS.seen.put( R4C.class,                false);
		WEAPONS.seen.put( Pkp.class,                   false);
		WEAPONS.seen.put( RadiantSpear.class,                false);
		WEAPONS.seen.put( KRISSVector.class,                false);
		WEAPONS.seen.put( SakuraSword.class,                false);
		WEAPONS.seen.put( LoneJourney.class,                   false);
		WEAPONS.seen.put( Echeveria.class,                   false);
		WEAPONS.seen.put( Suffering.class,                   false);


		WEAPONS.seen.put( RhodesSword.class,               false);
		WEAPONS.seen.put( Firmament.class,                       false);
		WEAPONS.seen.put( Gamzashield.class,               false);
		WEAPONS.seen.put( Enfild2.class,                       false);
		WEAPONS.seen.put( GoldDogSword.class,                false);
		WEAPONS.seen.put( Gluttony.class,                       false);
		WEAPONS.seen.put( SanktaBet.class,                   false);
		WEAPONS.seen.put( Niansword.class,                   false);
		WEAPONS.seen.put( PatriotSpear.class,                   false);
		WEAPONS.seen.put( CatGun.class,                   false);
		WEAPONS.seen.put( MinosFury.class,                   false);
		WEAPONS.seen.put( ImageoverForm.class,                   false);
		WEAPONS.seen.put( KollamSword.class,                   false);

		SKILLBOOK.seen.put( BookPowerfulStrike.class,      false);
		SKILLBOOK.seen.put( BookTacticalChanting.class,    false);
		SKILLBOOK.seen.put( BookExecutionMode.class,       false);
		SKILLBOOK.seen.put( BookThoughts.class,              false);
		SKILLBOOK.seen.put( BookHikari.class,              false);
		SKILLBOOK.seen.put( BookFate.class,                false);
		SKILLBOOK.seen.put( Bookpanorama.class,            false);
		SKILLBOOK.seen.put( BookFoodPrep.class,            false);
		SKILLBOOK.seen.put( BookChainHook.class,            false);
		SKILLBOOK.seen.put( BookWhispers.class,            false);
		SKILLBOOK.seen.put( BookCrimsonCutter.class,            false);
		SKILLBOOK.seen.put( BookShinkageryu.class,             false);
		SKILLBOOK.seen.put( BookFierceGlare.class,             false);
		SKILLBOOK.seen.put( BookCamouflage.class,              false);
		SKILLBOOK.seen.put( BookWolfSpirit.class,              false);
		SKILLBOOK.seen.put( BookHotBlade.class,              false);
		SKILLBOOK.seen.put( BookSpreadSpores.class,              false);
		SKILLBOOK.seen.put( BookPhantomMirror.class,              false);
		SKILLBOOK.seen.put( BookLive.class,              false);
		SKILLBOOK.seen.put( BookSoul.class,              false);

		SKILLBOOK.seen.put( BookWolfPack.class,               false);
		SKILLBOOK.seen.put( BookMentalBurst.class,              false);
		SKILLBOOK.seen.put( BookReflow.class,                false);
		SKILLBOOK.seen.put( BookNervous.class,              false);
		SKILLBOOK.seen.put( BookDawn.class,              false);
		SKILLBOOK.seen.put( BookEmergencyDefibrillator.class,  false);
		SKILLBOOK.seen.put( BookJackinthebox.class,            false);
		SKILLBOOK.seen.put( BookRockfailHammer.class,          false);
		SKILLBOOK.seen.put( BookChargingPS.class,           false);
        SKILLBOOK.seen.put( BookNeverBackDown.class,          false);
        SKILLBOOK.seen.put( BookCoverSmoke.class,           false);
        SKILLBOOK.seen.put( BookBenasProtracto.class,          false);
        SKILLBOOK.seen.put( Bookancientkin.class,          false);
        SKILLBOOK.seen.put( BookLandingStrike.class,          false);
		SKILLBOOK.seen.put( BookDreamland.class,              false);
		SKILLBOOK.seen.put( BookDeepHealing.class,              false);
		SKILLBOOK.seen.put( BookSpikes.class,              false);
		SKILLBOOK.seen.put( BookFlashShield.class,              false);
		SKILLBOOK.seen.put( BookGenesis.class,              false);
		SKILLBOOK.seen.put( BookPredators.class,              false);

        SKILLBOOK.seen.put( BookSBurst.class,          false);
		SKILLBOOK.seen.put( BookShadowAssault.class,          false);
        SKILLBOOK.seen.put( BookNigetRaid.class,          false);
		SKILLBOOK.seen.put( BookSoaringFeather.class,          false);
		SKILLBOOK.seen.put( BookYourWish.class,              false);
		SKILLBOOK.seen.put( BookSun.class,          false);
        SKILLBOOK.seen.put( BookTerminationT.class,          false);
        SKILLBOOK.seen.put( BookTrueSilverSlash.class,          false);
		SKILLBOOK.seen.put( BookEveryone.class,          false);
		SKILLBOOK.seen.put( BookSharpness.class,              false);

		WANDS.seen.put( WandOfMagicMissile.class,           false);
		WANDS.seen.put( StaffOfAbsinthe.class,               false);
		WANDS.seen.put( WandOfLightning.class,              false);
		WANDS.seen.put( StaffOfGreyy.class,               false);
		WANDS.seen.put( WandOfDisintegration.class,         false);
		WANDS.seen.put( StaffOfVigna.class,               false);
		WANDS.seen.put( WandOfFireblast.class,              false);
		WANDS.seen.put( StaffOfSkyfire.class,               false);
		WANDS.seen.put( WandOfCorrosion.class,              false);
		WANDS.seen.put( StaffOfBreeze.class,               false);
		WANDS.seen.put( WandOfBlastWave.class,              false);
		WANDS.seen.put( StaffOfWeedy.class,             false);
		WANDS.seen.put( WandOfLivingEarth.class,            false);
		WANDS.seen.put( StaffOfMudrock.class,               false);
		WANDS.seen.put( WandOfFrost.class,                  false);
		WANDS.seen.put( StaffOfLeaf.class,               false);
		WANDS.seen.put( WandOfPrismaticLight.class,         false);
		WANDS.seen.put( StaffOfShining.class,               false);
		WANDS.seen.put( WandOfWarding.class,                false);
		WANDS.seen.put( StaffOfMayer.class,               false);
		WANDS.seen.put( WandOfTransfusion.class,            false);
		WANDS.seen.put( StaffOfAngelina.class,               false);
		WANDS.seen.put( WandOfCorruption.class,             false);
		WANDS.seen.put( StaffOfCorrupting.class,             false);
		WANDS.seen.put( WandOfRegrowth.class,               false);
		WANDS.seen.put( StaffOfLena.class,             false);
		WANDS.seen.put( WandOfSilence.class,               false);
		WANDS.seen.put( StaffOfSnowsant.class,             false);
		WANDS.seen.put( WandOfHealing.class,             false);
		WANDS.seen.put( StaffOfSussurro.class,             false);
		WANDS.seen.put( WandOfHallucination.class,             false);
		WANDS.seen.put( StaffOfPodenco.class,             false);
		WANDS.seen.put( StaffOfTime.class,             false);
		WANDS.seen.put( StaffOfSuzuran.class,             false);
		WANDS.seen.put( StaffOfPurgatory.class,             false);

		RINGS.seen.put( RingOfAccuracy.class,               false);
		RINGS.seen.put( RingOfEnergy.class,                 false);
		RINGS.seen.put( RingOfElements.class,               false);
		RINGS.seen.put( RingOfEvasion.class,                false);
		RINGS.seen.put( RingOfForce.class,                  false);
		RINGS.seen.put( RingOfFuror.class,                  false);
		RINGS.seen.put( RingOfHaste.class,                  false);
		RINGS.seen.put( RingOfMight.class,                  false);
		RINGS.seen.put( RingOfSharpshooting.class,          false);
		RINGS.seen.put( RingOfTenacity.class,               false);
		RINGS.seen.put( RingOfWealth.class,                 false);
		RINGS.seen.put( RingOfSunLight.class,               false);
		RINGS.seen.put( RingOfAmplified.class,               false);
		RINGS.seen.put( RingOfDominate.class,               false);
		RINGS.seen.put( RingOfAssassin.class,               false);
		RINGS.seen.put( RingOfMistress.class,               false);
		RINGS.seen.put( RingOfCommand.class,               false);

		//ARTIFACTS.seen.put( AlchemistsToolkit.class,        false);
		//ARTIFACTS.seen.put( CapeOfThorns.class,             false);
		ARTIFACTS.seen.put( ChaliceOfBlood.class,           false);
		ARTIFACTS.seen.put( CloakOfShadows.class,           false);
		ARTIFACTS.seen.put( DriedRose.class,                false);
		ARTIFACTS.seen.put( EtherealChains.class,           false);
		ARTIFACTS.seen.put( HornOfPlenty.class,             false);
		//ARTIFACTS.seen.put( LloydsBeacon.class,             false);
		ARTIFACTS.seen.put( MasterThievesArmband.class,     false);
		ARTIFACTS.seen.put( SandalsOfNature.class,          false);
		ARTIFACTS.seen.put( TalismanOfForesight.class,      false);
		ARTIFACTS.seen.put( TimekeepersHourglass.class,     false);
		ARTIFACTS.seen.put( UnstableSpellbook.class,        false);
		ARTIFACTS.seen.put( CustomeSet.class,        false);
		ARTIFACTS.seen.put( IsekaiItem.class,        false);
		ARTIFACTS.seen.put( AlchemyKit.class,        false);
		ARTIFACTS.seen.put( WoundsofWar.class,        false);

		POTIONS.seen.put( PotionOfHealing.class,            false);
		POTIONS.seen.put( PotionOfStrength.class,           false);
		POTIONS.seen.put( PotionOfLiquidFlame.class,        false);
		POTIONS.seen.put( PotionOfFrost.class,              false);
		POTIONS.seen.put( PotionOfToxicGas.class,           false);
		POTIONS.seen.put( PotionOfParalyticGas.class,       false);
		POTIONS.seen.put( PotionOfPurity.class,             false);
		POTIONS.seen.put( PotionOfLevitation.class,         false);
		POTIONS.seen.put( PotionOfMindVision.class,         false);
		POTIONS.seen.put( PotionOfInvisibility.class,       false);
		POTIONS.seen.put( PotionOfExperience.class,         false);
		POTIONS.seen.put( PotionOfHaste.class,              false);

		SCROLLS.seen.put( ScrollOfIdentify.class,           false);
		SCROLLS.seen.put( ScrollOfUpgrade.class,            false);
		SCROLLS.seen.put( ScrollOfRemoveCurse.class,        false);
		SCROLLS.seen.put( ScrollOfMagicMapping.class,       false);
		SCROLLS.seen.put( ScrollOfTeleportation.class,      false);
		SCROLLS.seen.put( ScrollOfRecharging.class,         false);
		SCROLLS.seen.put( ScrollOfMirrorImage.class,        false);
		SCROLLS.seen.put( ScrollOfTerror.class,             false);
		SCROLLS.seen.put( ScrollOfLullaby.class,            false);
		SCROLLS.seen.put( ScrollOfRage.class,               false);
		SCROLLS.seen.put( ScrollOfRetribution.class,        false);
		SCROLLS.seen.put( ScrollOfTransmutation.class,      false);
		SCROLLS.seen.put( ScrollOfWarp.class,      false);
	}

	public static LinkedHashMap<Catalog, Badges.Badge> catalogBadges = new LinkedHashMap<>();
	static {
		catalogBadges.put(WEAPONS, Badges.Badge.ALL_WEAPONS_IDENTIFIED);
		catalogBadges.put(SKILLBOOK, Badges.Badge.ALL_SKILLBOOK_IDENTIFIED);
		catalogBadges.put(WANDS, Badges.Badge.ALL_WANDS_IDENTIFIED);
		catalogBadges.put(RINGS, Badges.Badge.ALL_RINGS_IDENTIFIED);
		catalogBadges.put(ARTIFACTS, Badges.Badge.ALL_ARTIFACTS_IDENTIFIED);
		catalogBadges.put(POTIONS, Badges.Badge.ALL_POTIONS_IDENTIFIED);
		catalogBadges.put(SCROLLS, Badges.Badge.ALL_SCROLLS_IDENTIFIED);
	}

	public static boolean isSeen(Class<? extends Item> itemClass){
		for (Catalog cat : values()) {
			if (cat.seen.containsKey(itemClass)) {
				return cat.seen.get(itemClass);
			}
		}
		return false;
	}

	public static void setSeen(Class<? extends Item> itemClass){
		for (Catalog cat : values()) {
			if (cat.seen.containsKey(itemClass) && !cat.seen.get(itemClass)) {
				cat.seen.put(itemClass, true);
				Journal.saveNeeded = true;
			}
		}
		Badges.validateItemsIdentified();
	}

	private static final String CATALOG_ITEMS = "catalog_items";

	public static void store( Bundle bundle ){

		Badges.loadGlobal();

		ArrayList<Class> seen = new ArrayList<>();

		//if we have identified all items of a set, we use the badge to keep track instead.
		if (!Badges.isUnlocked(Badges.Badge.ALL_ITEMS_IDENTIFIED)) {
			for (Catalog cat : values()) {
				if (!Badges.isUnlocked(catalogBadges.get(cat))) {
					for (Class<? extends Item> item : cat.items()) {
						if (cat.seen.get(item)) seen.add(item);
					}
				}
			}
		}

		bundle.put( CATALOG_ITEMS, seen.toArray(new Class[0]) );

	}

	public static void restore( Bundle bundle ){

		Badges.loadGlobal();

		//logic for if we have all badges
		if (Badges.isUnlocked(Badges.Badge.ALL_ITEMS_IDENTIFIED)){
			for ( Catalog cat : values()){
				for (Class<? extends Item> item : cat.items()){
					cat.seen.put(item, true);
				}
			}
			return;
		}

		//catalog-specific badge logic
		for (Catalog cat : values()){
			if (Badges.isUnlocked(catalogBadges.get(cat))){
				for (Class<? extends Item> item : cat.items()){
					cat.seen.put(item, true);
				}
			}
		}

		//general save/load
		//includes "catalogs" for pre-0.8.2 saves
		if (bundle.contains("catalogs") || bundle.contains(CATALOG_ITEMS)) {
			List<Class> seenClasses = new ArrayList<>();
			if (bundle.contains(CATALOG_ITEMS)) {
				seenClasses = Arrays.asList(bundle.getClassArray(CATALOG_ITEMS));
			}
			List<String> seenItems = new ArrayList<>();
			if (bundle.contains("catalogs")) {
				Journal.saveNeeded = true; //we want to overwrite with the newer storage format
				seenItems = Arrays.asList(bundle.getStringArray("catalogs"));
			}

			for (Catalog cat : values()) {
				for (Class<? extends Item> item : cat.items()) {
					if (seenClasses.contains(item) || seenItems.contains(item.getSimpleName())) {
						cat.seen.put(item, true);
					}
				}
			}
		}
	}

}
