package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class StomeCharge extends FlavourBuff {
    {
        immunities.add(Roots.class);
    }

    public static final float DURATION = 10f;

    @Override
    public int icon() {
        return BuffIndicator.WEAPON;
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (DURATION - visualcooldown()) / DURATION);
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc");
    }

    public float speedMultiplier(){
        return 2;
    }

    @Override
    public void detach() {
        int damage = target.damageRoll();
        boolean SONG = false;
        if (Dungeon.hero.hasTalent(Talent.SONG_OF_STOME)) {
            damage *= (2.1f + Dungeon.hero.pointsInTalent(Talent.SONG_OF_STOME) * 0.2f);
            SONG = true;
        }
        else damage *= 2f;

        PathFinder.buildDistanceMap(target.pos, BArray.not(Dungeon.level.solid, null), 2);
        for (int cell = 0; cell < PathFinder.distance.length; cell++) {
            if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                CellEmitter.get( cell ).burst(Speck.factory(Speck.WOOL), 5);
                Char ch = Actor.findChar(cell);
                if (ch != null&& !(ch instanceof Hero)) {
                    ch.damage(damage, target);
                    if (SONG) {
                        Ballistica trajectory = new Ballistica(target.pos, ch.pos, Ballistica.STOP_TARGET);
                        trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
                        WandOfBlastWave.throwChar(ch, trajectory, Dungeon.hero.pointsInTalent(Talent.SONG_OF_STOME)); // 넉백 효과
                    }
                }}}

        Sample.INSTANCE.play( Assets.Sounds.MISS, 3f, 1.35f );
        Camera.main.shake(1, 0.17f);

        super.detach();
    }
}
