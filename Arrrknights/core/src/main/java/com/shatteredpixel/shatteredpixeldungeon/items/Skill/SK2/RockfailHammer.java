package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class RockfailHammer extends Skill {
    public void doSkill() {

        curUser.sprite.zap(0);
        for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
            if (Dungeon.level.adjacent(mob.pos, curUser.pos) && mob.alignment != Char.Alignment.ALLY) {
                Ballistica trajectory = new Ballistica(curUser.pos, mob.pos, Ballistica.STOP_TARGET);
                trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
                WandOfBlastWave.throwChar(mob, trajectory, 2); // 넉백 효과
                Buff.prolong(mob, Paralysis.class, 2);
                Buff.prolong(mob, Vertigo.class, 7);
                dohit(mob);
            }
        }

        Camera.main.shake(2, 0.5f);
        CellEmitter.get( curUser.pos ).burst( Speck.factory( Speck.DUST ), 10 );
        Buff.affect(curUser, Healing.class).setHeal(curUser.HT/5,0.5f,0);
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
        curUser.spendAndNext( 1 );
    }

    public void dohit(final Char enemy) {
        int dmg = Random.NormalIntRange(15 + (curUser.STR * 2), 20 + (curUser.STR * 3));
        CellEmitter.get( enemy.pos ).burst( Speck.factory( Speck.ROCK ), 10 );
        enemy.damage(dmg, enemy);
    }
}
