package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class ChargingPS extends Skill {
    public void doSkill() {
        curUser.sprite.zap(0);
            PathFinder.buildDistanceMap(curUser.pos, BArray.not(Dungeon.level.solid, null), 2);
            for (int cell = 0; cell < PathFinder.distance.length; cell++) {
                    if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                        Char ch = Actor.findChar(cell);
                        if (ch != null&& !(ch instanceof Hero)) {
                            dohit(ch); }}}
        CellEmitter.get( curUser.pos ).burst( Speck.factory( Speck.TOXIC ), 10 );
        Buff.affect(curUser, Stamina.class, 7f);
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
        curUser.spendAndNext( 1 );
    }

    public void dohit(final Char enemy) {
        int dmg = Random.NormalIntRange(35, 35 + curUser.STR * 3);
        CellEmitter.get(enemy.pos).burst(Speck.factory(Speck.WOOL), 10);
        Buff.affect(enemy, Blindness.class, 15f);
        Buff.affect(enemy, Paralysis.class, 6f);
        enemy.damage(dmg, enemy);
    }
}
