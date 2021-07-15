package com.shatteredpixel.shatteredpixeldungeon.items.wands.SP;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BloodParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAmplified;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class StaffOfAngelina extends Wand {
    private static ItemSprite.Glowing COL = new ItemSprite.Glowing( 0xE6E6FA );
    {
        image = ItemSpriteSheet.WAND_TRANSFUSION;

        collisionProperties = Ballistica.PROJECTILE;
    }

    private boolean freeCharge = false;

    @Override
    public ItemSprite.Glowing glowing() {
        return COL;
    }

    @Override
    protected void onZap(Ballistica beam) {

        for (int c : beam.subPath(0, beam.dist))
            CellEmitter.center(c).burst( BloodParticle.BURST, 1 );

        int cell = beam.collisionPos;

        Char ch = Actor.findChar(cell);

        if (ch instanceof Mob){

            processSoulMark(ch, chargesPerCast());

            //this wand does different things depending on the target.

            //heals/shields an ally or a charmed enemy while damaging self
            if (ch.alignment == Char.Alignment.ALLY || ch.buff(Charm.class) != null){

                // 5% of max hp
                int selfDmg = Math.round(curUser.HT*0.03f);

                int healing = selfDmg + 3*buffedLvl();
                int shielding = (ch.HP + healing) - ch.HT;
                if (shielding > 0){
                    healing -= shielding;
                    Buff.affect(ch, Barrier.class).setShield(shielding);
                } else {
                    shielding = 0;
                }

                ch.HP += healing;

                ch.sprite.emitter().burst(Speck.factory(Speck.HEALING), 2 + buffedLvl() / 2);
                ch.sprite.showStatus(CharSprite.POSITIVE, "+%dHP", healing + shielding);

                if (!freeCharge) {
                    damageHero(selfDmg);
                } else {
                    freeCharge = false;
                }

                //for enemies...
            } else {

                //charms living enemies
                if (!ch.properties().contains(Char.Property.DRONE)) {
                    Buff.affect(ch, Roots.class,2 + buffedLvl() / 2);
                    ch.sprite.centerEmitter().start( Speck.factory( Speck.WOOL ), 0.2f, 3 );
                    ch.damage(Random.NormalIntRange(3 + buffedLvl()/2, 6+buffedLvl() * 2+ RingOfAmplified.DamageBonus(Dungeon.hero) * 2), this);

                } else {
                    Buff.affect(ch, Paralysis.class,1 + buffedLvl() / 2);
                    ch.sprite.emitter().start(ShadowParticle.UP, 0.05f, 10 + buffedLvl());
                    Sample.INSTANCE.play(Assets.Sounds.AJIMU);
                }

            }

        }

    }

    //this wand costs health too
    private void damageHero(int damage){

        curUser.damage(damage, this);

        if (!curUser.isAlive()){
            Dungeon.fail( getClass() );
            GLog.n( Messages.get(this, "ondeath") );
        }
    }

    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        if (defender.buff(Roots.class) != null){
            //grants a free use of the staff and shields self
            freeCharge = true;
            Buff.affect(curUser, Barrier.class).setShield(2*(2 + buffedLvl()));
            GLog.p( Messages.get(this, "charged") );
            attacker.sprite.emitter().burst(BloodParticle.BURST, 20);
        }
    }

    @Override
    protected void fx(Ballistica beam, Callback callback) {
        curUser.sprite.parent.add(
                new Beam.HealthRay(curUser.sprite.center(), DungeonTilemap.raisedTileCenterToWorld(beam.collisionPos)));
        callback.call();
    }

    @Override
    public String statsDesc() {
        int selfDMG = Math.round(Dungeon.hero.HT*0.03f);
        if (levelKnown)
            return Messages.get(this, "stats_desc", selfDMG, selfDMG + 3*buffedLvl(), 3+buffedLvl()/2, 6+ buffedLvl() * 2+ RingOfAmplified.DamageBonus(Dungeon.hero) * 2);
        else
            return Messages.get(this, "stats_desc", selfDMG, selfDMG, 3, 6);
    }

    private static final String FREECHARGE = "freecharge";

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        freeCharge = bundle.getBoolean( FREECHARGE );
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( FREECHARGE, freeCharge );
    }

}
