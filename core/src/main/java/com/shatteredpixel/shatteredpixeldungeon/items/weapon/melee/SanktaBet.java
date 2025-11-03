package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HandclapSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class SanktaBet extends MeleeWeapon {
    public static final String AC_RE = "RE";
    {
        image = ItemSpriteSheet.REVOLVER;
        hitSound = Assets.Sounds.HIT_REVOLVER;
        hitSoundPitch = 1.12f;

        charge = 0;
        chargeCap = 5;
        ACC = 1.33f;
        DLY = 0.8f;

        tier = 4;

        defaultAction = AC_RE;
    }

    @Override
    public int max(int lvl) {
        return  4*(tier+1) +   //20 + 5
                lvl*(tier+1); }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_RE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_RE)) {
            Sample.INSTANCE.play(Assets.Sounds.RELOAD);
            charge = 0;
            hero.sprite.operate(hero.pos);
            hero.spendAndNext(1f);
            updateQuickslot();
        }
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        if (charge < 5) charge++;
        else {


            Camera.main.shake(4, 0.12f);
            Sample.INSTANCE.play(Assets.Sounds.HIT_GUNLANCE, 1.33f, 1.76f);
            CellEmitter.get( attacker.pos ).burst( Speck.factory( Speck.WOOL ), 10 );

            PathFinder.buildDistanceMap(defender.pos, BArray.not(Dungeon.level.solid, null), 2);
            for (int cell = 0; cell < PathFinder.distance.length; cell++) {
                if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                    CellEmitter.center(cell).burst(BlastParticle.FACTORY, 10);
                    }}

            if (attacker instanceof Hero) damage *= 2f;
            else damage *= 0.6f;
            for (int i = 0; i < 5; i++) defender.damage(damage, this);

            attacker.die(this);
            if (attacker.isAlive()) {
            attacker.damage(1+attacker.HP / 2, this);
            Buff.affect(attacker, Burning.class).reignite(attacker);

            }

            charge = 0;
        }
        updateQuickslot();

        return super.proc(attacker, defender, damage);
    }

    @Override
    public String status() {
        if (this.isIdentified()) return charge + "/" + 5;
        else return null;}
}
