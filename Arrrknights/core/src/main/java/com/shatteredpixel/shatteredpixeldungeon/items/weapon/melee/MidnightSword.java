package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.RainbowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndUseItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MidnightSword extends MeleeWeapon {
    public static final String AC_ZAP = "ZAP";
    {
        image = ItemSpriteSheet.MIDSWORD;
        hitSound = Assets.Sounds.HIT;
        hitSoundPitch = 1f;

        tier = 2;

        usesTargeting = true;
        defaultAction = AC_ZAP;
    }

    protected int collisionProperties = Ballistica.MAGIC_BOLT;

    private int charge = 3;
    private int chargeCap = 3;

    @Override
    public int max(int lvl) {
        return  4*(tier) +  //9 + 2
                lvl*(tier)
                +1;
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        SPCharge(1);
        return super.proc(attacker, defender, damage);
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

        if (action.equals(AC_ZAP) && charge > 0) {
            if (this.cursed != true) {
                cursedKnown = true;
                GameScene.selectCell(zapper);
            }
            else {
                Buff.affect(Dungeon.hero, Burning.class).reignite(Dungeon.hero,4f);
                cursedKnown = true;
                charge -= 1;
            }
        }
    }

    public String statsInfo() {
            return Messages.get(this, "stats_desc", 1+buffedLvl(),6+buffedLvl()*2);
    }

    public void SPCharge(int n) {
        if (Random.Int(17) < 2) {
            charge += n;
            if (chargeCap < charge) charge = chargeCap;
            updateQuickslot();
        }
    }

    @Override
    public String status() {
        return charge + "/" + chargeCap;
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

    protected static CellSelector.Listener zapper = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer target) {

            if (target != null) {

                final MidnightSword ss;
                if (curItem instanceof MidnightSword) {
                    ss = (MidnightSword) MidnightSword.curItem;

                    Ballistica shot = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
                    int cell = shot.collisionPos;

                    if (target == curUser.pos || cell == curUser.pos) {
                        GLog.i(Messages.get(MidnightSword.class, "self_target"));
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
                MagicMissile.MAGIC_MISSILE,
                curUser.sprite,
                bolt.collisionPos,
                callback);
        Sample.INSTANCE.play( Assets.Sounds.ZAP );
    }
    public boolean tryToZap(Hero owner, int target) {

        if (owner.buff(MagicImmune.class) != null) {
            GLog.w(Messages.get(this, "no_magic"));
            return false;
        }

        if (charge >= 1) {
            return true;
        } else {
            GLog.w(Messages.get(this, "fizzles"));
            return false;
        }
    }


    protected void onZap( Ballistica bolt ) {
        Char ch = Actor.findChar( bolt.collisionPos );
        if (ch != null) {
            int dmg = Random.Int(1+buffedLvl(), 6+buffedLvl()*2);
            ch.damage(dmg, this);
            Sample.INSTANCE.play( Assets.Sounds.HIT_MAGIC, 1, Random.Float(0.87f, 1.15f) );

            ch.sprite.burst(0xFFFFFFFF, buffedLvl() / 2 + 2);

        } else {
            Dungeon.level.pressCell(bolt.collisionPos);
        }

        charge -=1;
        updateQuickslot();

        if (charge <= 0) curUser.spendAndNext(1f);
    }
}