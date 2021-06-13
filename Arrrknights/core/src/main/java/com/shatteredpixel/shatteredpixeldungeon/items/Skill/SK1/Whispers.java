package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class Whispers extends Skill {
    public void doSkill() {
        curUser.sprite.zap(0);
        PathFinder.buildDistanceMap(curUser.pos, BArray.not(Dungeon.level.solid, null), 4);
        for (int cell = 0; cell < PathFinder.distance.length; cell++) {
            if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                Char ch = Actor.findChar(cell);
                if (ch != null&& !(ch instanceof Hero)) {
                    Buff.affect(ch,Vertigo.class,3f);
                    CellEmitter.get( ch.pos ).burst( Speck.factory( Speck.WOOL ), 10 );
                }}}
        CellEmitter.get( curUser.pos ).burst( Speck.factory( Speck.WOOL ), 10 );
        Buff.affect(curUser, Stamina.class, 7f);
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );
        curUser.spendAndNext( 1 );
    }
}
