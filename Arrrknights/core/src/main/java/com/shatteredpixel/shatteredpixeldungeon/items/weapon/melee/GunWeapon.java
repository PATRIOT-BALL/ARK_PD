package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Camouflage;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChenShooterBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CloserangeShot;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Combo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Momentum;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SnipersMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.Bonk;
import com.shatteredpixel.shatteredpixeldungeon.items.Gunaccessories.Accessories;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.IsekaiItem;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Thunderbolt;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.UpMagazine;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class GunWeapon extends MeleeWeapon {
    public static final String AC_ZAP = "ZAP";
    public static final String AC_RELOAD = "RELOAD";
    public static final String AC_REMOVE = "REMOVE";

    protected int bulletTier = 3;
    protected int bullet = 5;
    protected int bulletMax = 25;
    protected boolean specialFire = false; // 특수 사격 여부
    protected boolean gamza = false; // 썬더볼트 장착 여부
    protected float FIRE_ACC_MULT = 1f;
    protected float FIRE_DELAY_MULT = 1f;
    protected float FIRE_DAMAGE_MULT = 1f;

    protected float RELOAD_DELAY = 3f;

    @Override
    public int max(int lvl) {
        return  3*(tier) +    // 3티어 기준 9+1, 5티어는 15+3
                lvl*(tier-2);
    }

    public int fireMin() {
        return (int) ((3 + level()) * FIRE_DAMAGE_MULT);
    }
    public int fireMax() {
        return (int) (3 + fireMin() + ((level() + 1) * bulletTier) * FIRE_DAMAGE_MULT);
    }

    public int fireDamageRoll() {
        return Random.Int(fireMin(), fireMax());
    }

    public Accessories gunAccessories;

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (attacker instanceof Hero) {
            if (Dungeon.hero.subClass == HeroSubClass.GLADIATOR) {
                if (Random.Int(4) < 1) {
                    bullet = Math.min(bullet +1, bulletMax);
                    updateQuickslot();
                }
            }
        }
        return super.proc(attacker, defender, damage);
    }

    protected void specialFire(Char ch) { }

    protected float fireAccuracyFactor(float acc) {
        if (gunAccessories != null) {
            acc *= gunAccessories.GetACCcorrectionvalue();

            if (Dungeon.hero.hasTalent(Talent.SHARPSHOOTER)) {
                acc += Dungeon.hero.pointsInTalent(Talent.SHARPSHOOTER) * 0.2f;
            }

            if (Dungeon.hero.hasTalent(Talent.BLITZKRIEG)) {
                acc += (Dungeon.hero.pointsInTalent(Talent.BLITZKRIEG) * 0.1f);
            }
        }

        CloserangeShot closerrange = Dungeon.hero.buff(CloserangeShot.class);
        if (closerrange != null && Dungeon.hero.hasTalent(Talent.PINPOINT)) {
            acc += Dungeon.hero.pointsInTalent(Talent.PINPOINT) * 0.2f;
        }

        return acc;
    }

    protected float fireDelayFactor(float dly) {
        if (gunAccessories != null) dly *= gunAccessories.GetDLYcorrectionvalue();
        return dly;
    }

    protected int fireDamageFactor(int dmg) {
        float accessoriesbouns = 1f;
        if (gunAccessories != null) accessoriesbouns = gunAccessories.GetDMGcorrectionvalue();

        float talentbouns = 1f;
        if (Dungeon.hero.hasTalent(Talent.PROJECTILE_MOMENTUM) && Dungeon.hero.buff(Momentum.class) != null &&  Dungeon.hero.buff(Momentum.class).freerunning()) {
            talentbouns += (Dungeon.hero.pointsInTalent(Talent.PROJECTILE_MOMENTUM) * 0.1f); }

        if (Dungeon.hero.hasTalent(Talent.BLITZKRIEG)) {
            talentbouns += (Dungeon.hero.pointsInTalent(Talent.BLITZKRIEG) * 0.1f);
        }

        CloserangeShot closerRange = Dungeon.hero.buff(CloserangeShot.class);
        if (closerRange != null) {
            if (closerRange.state()){
                talentbouns += 0.5f;
                if (Dungeon.hero.hasTalent(Talent.ZERO_RANGE_SHOT)) talentbouns += Dungeon.hero.pointsInTalent(Talent.ZERO_RANGE_SHOT) * 0.1f;
            }
        }

        dmg *= accessoriesbouns * talentbouns;
        return dmg;
    }

    public boolean affixAccessories(Accessories accessories) {
        if (gunAccessories != null) return false;
        else {
            gunAccessories = accessories;
            return true;
        }
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_ZAP);
        actions.add(AC_RELOAD);
        if (gunAccessories != null) actions.add(AC_REMOVE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_ZAP)) {

            if (Dungeon.hero.belongings.weapon != this) {
                GLog.n(Messages.get(this, "not_equipped"));
                QuickSlotButton.cancel();
                return;
            } else if (this.cursed) {
                Buff.affect(Dungeon.hero, Burning.class).reignite(Dungeon.hero, 4f);
                cursedKnown = true;
                bullet -= 1;
            } else {
                curUser = hero;
                curItem = this;
                cursedKnown = true;
                GameScene.selectCell(zapper);
            }
        }

        if (action.equals(AC_RELOAD)) {
            curUser = hero;
            GameScene.selectItem(itemSelector, WndBag.Mode.MISSILEWEAPON, Messages.get(this, "prompt"));
        }

        if (action.equals(AC_REMOVE)) {
            curUser = hero;
            Accessories ac = gunAccessories;
            if (ac.doPickUp( Dungeon.hero )) {
                GLog.i( Messages.get(Dungeon.hero, "you_now_have", ac.name()) );
            } else {
                Dungeon.level.drop( ac, curUser.pos ).sprite.drop();
            }
            gunAccessories = null;
            curUser.spendAndNext(1f);
        }
    }

    public void reload(int tier, boolean sp) {
        bulletTier = tier;
        bullet = bulletMax;

        specialFire = sp;

        if (Dungeon.hero.subClass == HeroSubClass.FREERUNNER) Dungeon.hero.spendAndNext(RELOAD_DELAY / 2);
        else Dungeon.hero.spendAndNext(RELOAD_DELAY);
        Dungeon.hero.sprite.operate( Dungeon.hero.pos );
    }

    protected static CellSelector.Listener zapper = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer target) {

            if (target != null) {

                final GunWeapon ss;
                if (curItem instanceof GunWeapon) {
                    ss = (GunWeapon) GunWeapon.curItem;

                    Ballistica shot = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
                    int cell = shot.collisionPos;

                    if (target == curUser.pos || cell == curUser.pos) {
                        GLog.i(Messages.get(DP27.class, "self_target"));
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
            return Messages.get(DP27.class, "prompt");
        }
    };

    protected void fx( Ballistica bolt, Callback callback ) {
        MagicMissile.boltFromChar( curUser.sprite.parent,
                MagicMissile.GUN_SHOT,
                curUser.sprite,
                bolt.collisionPos,
                callback);
        Sample.INSTANCE.play( Assets.Sounds.ZAP_GUN );
    }

    public boolean tryToZap(Hero owner, int target) {

        if (bullet >= 1) {
            return true;
        } else {
            GLog.w(Messages.get(this, "fizzles"));
            return false;
        }
    }


    protected void onZap( Ballistica bolt ) {
        CloserangeShot closerRange = Dungeon.hero.buff(CloserangeShot.class);

        Char ch = Actor.findChar( bolt.collisionPos );
        float oldacc = ACC;
        boolean pala = false;

        if (ch != null) {
            int dmg = fireDamageFactor(fireDamageRoll());

            // 사격 스롯 판정
            if (Dungeon.hero.subClass == HeroSubClass.SNIPER) dmg -= (ch.drRoll() / 2);
            else dmg -= ch.drRoll();

            if (this instanceof R4C && Dungeon.hero.belongings.getItem(IsekaiItem.class) != null) {
                if (Dungeon.hero.belongings.getItem(IsekaiItem.class).isEquipped(Dungeon.hero)) {
                    if (ch.buff(Paralysis.class) != null) {pala = true;}
                }}

            ACC = fireAccuracyFactor(FIRE_ACC_MULT);
            if (ch.hit(Dungeon.hero, ch, false)) {

                // 첸 특성
                if (Dungeon.hero.hasTalent(Talent.TARGET_FOCUSING)) {
                    if (Random.Int(3) < Dungeon.hero.pointsInTalent(Talent.TARGET_FOCUSING)) {
                        Buff.detach(ch, Camouflage.class);
                    }
                }

                ch.damage(dmg, this);
                Sample.INSTANCE.play(Assets.Sounds.HIT_GUN, 1, Random.Float(0.87f, 1.15f));

                if (specialFire) specialFire(ch);
                if (this instanceof C1_9mm) {
                    if (Random.Int(8) == 0) Buff.affect(ch, Chill.class, 2f);
                }

                ch.sprite.burst(0xFFFFFFFF, buffedLvl() / 2 + 2);

                // 사격 그레이스롯 판정
                int bonusTurns = Dungeon.hero.hasTalent(Talent.SHARED_UPGRADES) ? this.buffedLvl() : 0;
                if (Dungeon.hero.subClass == HeroSubClass.SNIPER) Buff.prolong(Dungeon.hero, SnipersMark.class, SnipersMark.DURATION).set(ch.id(), bonusTurns);

                // 연계 블레이즈 판정
                if (Dungeon.hero.subClass == HeroSubClass.GLADIATOR) {
                    Buff.affect(Dungeon.hero, Combo.class).hit(ch);

                    if (Dungeon.hero.hasTalent(Talent.CLEAVE)) {
                        if (Random.Int(10) < Dungeon.hero.pointsInTalent(Talent.CLEAVE)) {
                            Buff.affect(Dungeon.hero, Combo.class).hit(ch);
                        }
                    }
                }

                // 산사수 첸 판정
                if (Dungeon.hero.subClass == HeroSubClass.SPSHOOTER && ch.isAlive() && Dungeon.hero.buff(ChenShooterBuff.TACMoveCooldown.class) == null) {
                        Buff.prolong(Dungeon.hero, ChenShooterBuff.class, 5f).set(ch.id());
                }

                if (closerRange != null && ch.isAlive() && closerRange.state()) {
                    if (Dungeon.hero.hasTalent(Talent.WATER_PLAY) && Random.Int(5) < Dungeon.hero.pointsInTalent(Talent.WATER_PLAY)) {
                        Buff.affect(ch, Blindness.class, 1f);
                    }

                    if (Dungeon.hero.hasTalent(Talent.TAC_SHOT) && Dungeon.hero.buff(ChenShooterBuff.TACMove_tacshot.class) != null) {
                        int min = Dungeon.hero.pointsInTalent(Talent.TAC_SHOT) / 2;
                        int max = 1 + Dungeon.hero.pointsInTalent(Talent.TAC_SHOT) / 3;

                        Ballistica trajectory = new Ballistica(curUser.pos, ch.pos, Ballistica.STOP_TARGET);
                        trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
                        WandOfBlastWave.throwChar(ch, trajectory, Random.IntRange(min, max)); // 넉백 효과

                        Buff.detach(Dungeon.hero,ChenShooterBuff.TACMove_tacshot.class);
                    }
                }
            }
            else {
                String defense = ch.defenseVerb();
                ch.sprite.showStatus( CharSprite.NEUTRAL, defense );

                //TODO enemy.defenseSound? currently miss plays for monks/crab even when they parry
                Sample.INSTANCE.play(Assets.Sounds.MISS);

            }

        } else {
            Dungeon.level.pressCell(bolt.collisionPos);
        }

        Buff buff = Dungeon.hero.buff(TimekeepersHourglass.timeFreeze.class);
        if (buff != null) buff.detach();
        buff = Dungeon.hero.buff(Swiftthistle.TimeBubble.class);
        if (buff != null) buff.detach();

        if (Dungeon.hero.buff(Bonk.BonkBuff.class) != null) Buff.detach(Dungeon.hero, Bonk.BonkBuff.class);

        Invisibility.dispel();

        if (gunAccessories != null) {
            if (gunAccessories.GetSavingChance() < Random.Int(100)) {
                bullet -=1;
            }
        }
        else if (closerRange != null && closerRange.state() && Dungeon.hero.hasTalent(Talent.FRUGALITY)) {
            if (Random.Int(100) > Dungeon.hero.pointsInTalent(Talent.FRUGALITY) * 15) bullet-=1;
        }
        else bullet -=1;
        updateQuickslot();

        ACC = oldacc;

        if (pala) { curUser.spendAndNext(fireDelayFactor(FIRE_DELAY_MULT / 4)); }
        else curUser.spendAndNext(fireDelayFactor(FIRE_DELAY_MULT));

        if (ch != null && !ch.isAlive() && Dungeon.hero.hasTalent(Talent.BF_RULL) && Random.Int(5) < Dungeon.hero.pointsInTalent(Talent.BF_RULL)) {
            Buff.affect(Dungeon.hero, Swiftthistle.TimeBubble.class).bufftime(1f);
        }
    }

    @Override
    public String status() { return bullet+""; }

    @Override
    public String desc() {
        return Messages.get(this, "desc", bulletTier);
    }

    @Override
    public String info() {
        String info = super.info();
        if (gunAccessories != null) {
            info += "\n\n" + Messages.get(gunAccessories, "desc");
        }
        return info;
    }

    private final WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect( final Item item ) {
            if (item != null) {
                if (item instanceof Thunderbolt) {
                    bulletMax +=3;
                    gamza = true;}
                if (item instanceof UpMagazine) {reload(((MissileWeapon)item).tier, true); }
                else reload(((MissileWeapon)item).tier, false);
                item.detach(Dungeon.hero.belongings.backpack);
            }
        }
    };

    @Override
    public String name() {
        if (gamza) return Messages.get(this, "gamza_name");
        return super.name();
    }

    public String statsInfo() {
        if (specialFire) return Messages.get(this, "stats_desc_sp", fireMin(), fireMax());
        return Messages.get(this, "stats_desc", fireMin(), fireMax());
    }

    private static final String BULLET = "bullet";
    private static final String BULLET_CAP = "bulletCap";
    private static final String GAMZA = "gamza";
    private static final String TIER = "bullettier";
    private static final String SP = "spshot";
    private static final String ACCESSORIES = "GunAccessories";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(BULLET, bullet);
        bundle.put(BULLET_CAP, bulletMax);
        bundle.put(GAMZA, gamza);
        bundle.put(TIER, bulletTier);
        bundle.put(SP, specialFire);
        bundle.put( ACCESSORIES, gunAccessories);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        bulletMax = bundle.getInt(BULLET_CAP);
        if (bulletMax > 0) bullet = Math.min(bulletMax, bundle.getInt(BULLET));
        else bullet = bundle.getInt(BULLET);

        bulletTier = bundle.getInt(TIER);
        specialFire = bundle.getBoolean(SP);
        gamza = bundle.getBoolean(GAMZA);
        gunAccessories = (Accessories) bundle.get(ACCESSORIES);
    }
}
