package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Bonk;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CustomeSet;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfSkyfire;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Suffering extends MeleeWeapon {
    public static final String AC_ZAP = "ZAP";
    {
        image = ItemSpriteSheet.FLAMMETTA;
        hitSound = Assets.Sounds.HIT_SHOTGUN;
        hitSoundPitch = 1f;

        tier =5;
        defaultAction = AC_ZAP;
    }

    protected int collisionProperties = Ballistica.MAGIC_BOLT;

    @Override
    public int max(int lvl) {
        return  4*(tier-1) - 2 +    //14 + 4 (one target = x2)
                lvl*(tier-1);
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if(isEquipped(hero)) actions.add(AC_ZAP);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_ZAP) && charge >= 100) {
            if (this.cursed != true) {
                cursedKnown = true;
                GameScene.selectCell(zapper);
            }
            else {
                Buff.affect(Dungeon.hero, Burning.class).reignite(Dungeon.hero,4f);
                cursedKnown = true;
                charge = 0;
            }
        }
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        int target = 1;
        if (attacker instanceof Hero) {
            for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
                if (Dungeon.level.adjacent(mob.pos, defender.pos) && mob.alignment != Char.Alignment.ALLY) {
                    mob.damage(Dungeon.hero.damageRoll() - Math.max(defender.drRoll(), defender.drRoll()), this);
                    target++;
                }
            }
        }

        if (target == 1) damage *= 2;

        return super.proc(attacker, defender, damage);
    }

    protected static CellSelector.Listener zapper = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer target) {

            if (target != null) {

                final Suffering ss;
                if (curItem instanceof Suffering) {
                    ss = (Suffering) Suffering.curItem;

                    Ballistica shot = new Ballistica(curUser.pos, target, Ballistica.PROJECTILE);
                    int cell = shot.collisionPos;

                    if (target == curUser.pos || cell == curUser.pos) {
                        GLog.i(Messages.get(Suffering.class, "self_target"));
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
            return Messages.get(Suffering.class, "prompt");
        }
    };

    protected void fx( Ballistica bolt, Callback callback ) {
        MagicMissile.boltFromChar( curUser.sprite.parent,
                MagicMissile.MAGIC_MISSILE,
                curUser.sprite,
                bolt.collisionPos,
                callback);
        Sample.INSTANCE.play( Assets.Sounds.ZAP_GUN );
    }
    public boolean tryToZap(Hero owner, int target) {
        if (charge >= 100) {
            return true;
        } else {
            GLog.w(Messages.get(this, "fizzles"));
            return false;
        }
    }


    protected void onZap( Ballistica bolt ) {
        Sample.INSTANCE.play(Assets.Sounds.BURNING);
        StaffOfSkyfire.BlastWave.blast(bolt.collisionPos);
        int dmg = damageRoll(Dungeon.hero) * 2;

        PathFinder.buildDistanceMap( bolt.collisionPos, BArray.not( Dungeon.level.solid, null ), 1 );
        for (int i = 0; i < PathFinder.distance.length; i++) {
            if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                if (Dungeon.level.pit[i])
                    GameScene.add(Blob.seed(i, 1, Fire.class));
                else
                    GameScene.add(Blob.seed(i, 4, Fire.class));
                CellEmitter.get(i).burst(FlameParticle.FACTORY, 3);
            }
        }

        //throws other chars around the center.
        for (int i : PathFinder.NEIGHBOURS9) {
            if (!Dungeon.level.solid[bolt.collisionPos+i]) {
                GameScene.add(Blob.seed(bolt.collisionPos+i, 1+this.level(), Fire.class)); }
            Char ch = Actor.findChar(bolt.collisionPos + i);

            if (ch != null) {
                if (ch.alignment != Char.Alignment.ALLY) ch.damage(dmg, this);

                if (ch.isAlive() && ch.pos == bolt.collisionPos + i) {
                    Buff.affect(ch, Burning.class);
                } else if (ch == Dungeon.hero) {
                    Dungeon.fail(getClass());
                    GLog.n(Messages.get(this, "ondeath"));
                }
            }
        }

        //throws the char at the center of the blast
        Char ch = Actor.findChar(bolt.collisionPos);
        if (ch != null) {
            ch.damage(dmg, this);

            if (ch.isAlive() && bolt.path.size() > bolt.dist + 1 && ch.pos == bolt.collisionPos) {
                Buff.affect(ch, Burning.class);
            }
        }

        charge = 0;
        updateQuickslot();

        Buff buff = Dungeon.hero.buff(TimekeepersHourglass.timeFreeze.class);
        if (buff != null) buff.detach();
        buff = Dungeon.hero.buff(Swiftthistle.TimeBubble.class);
        if (buff != null) buff.detach();

        if (Dungeon.hero.buff(Bonk.BonkBuff.class) != null) Buff.detach(Dungeon.hero, Bonk.BonkBuff.class);

        Invisibility.dispel();

        curUser.spendAndNext(1f);
    }

    @Override
    public void SPCharge(int value) {
        if(setbouns()) value *= 2;
        super.SPCharge(value);
    }

    @Override
    public String desc() {
        String info = Messages.get(this, "desc");
        if (setbouns()) info += "\n\n" + Messages.get( Suffering.class, "setbouns");

        return info;
    }

    @Override
    public String status() {
        if (chargeCap == 100)
            return Messages.format("%d%%", charge);
        return null;
    }

    private boolean setbouns() {
        if (Dungeon.hero.belongings.getItem(TimekeepersHourglass.class) != null) {
            if (Dungeon.hero.belongings.getItem(TimekeepersHourglass.class).isEquipped(Dungeon.hero))
                return true;
        }
        return false;
    }
}
