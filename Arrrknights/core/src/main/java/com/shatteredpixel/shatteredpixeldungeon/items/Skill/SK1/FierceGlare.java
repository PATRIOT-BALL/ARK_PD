package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Freezing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class FierceGlare extends Skill {
    public void doSkill() {
        curUser.sprite.zap(0);
        PathFinder.buildDistanceMap(curUser.pos, BArray.not(Dungeon.level.solid, null), 2);
        for (int cell = 0; cell < PathFinder.distance.length; cell++) {
            if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                Char ch = Actor.findChar(cell);
                if (ch != null&& !(ch instanceof Hero)) {
                    dohit(ch); }}}
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
        curUser.spendAndNext( 1 );
    }

    public void dohit(final Char enemy) {
        int dmg = Random.NormalIntRange(12 + curUser.STR / 2, 18 + curUser.STR + curUser.lvl/2);
        CellEmitter.get(enemy.pos).burst(Speck.factory(Speck.BLIZZARD), 10);
        if (enemy.HP <= enemy.HT / 2) Buff.affect(enemy, Paralysis.class, 2f);
        enemy.damage(dmg, enemy);
    }
}
