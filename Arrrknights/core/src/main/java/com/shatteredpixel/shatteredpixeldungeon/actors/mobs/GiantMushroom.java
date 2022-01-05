package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Mushroomslices;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GiantMushRoomSprtie;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class GiantMushroom extends Mob {
    {
        spriteClass = GiantMushRoomSprtie.class;

        HP = HT = 250;
        defenseSkill = 0;

        EXP = 20;
        maxLvl = 40;

        loot = new Mushroomslices();
        lootChance = 1f; //initially, see rollToDropLoot

        properties.add(Property.IMMOVABLE);
        properties.add(Property.LARGE);
        immunities.add(Silence.class);

        state = PASSIVE;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 24);
    }

    @Override
    protected boolean act() {
        PathFinder.buildDistanceMap(this.pos, BArray.not(Dungeon.level.solid, null), 2);
        for (int cell = 0; cell < PathFinder.distance.length; cell++) {
            if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                Char ch = Actor.findChar(cell);
                if (ch != null && ch != this) {
                    if (Random.Int(3) == 0) {
                        Buff.affect(ch, Weakness.class, 2f); }
                    else if (Random.Int(3) == 0) {
                        Buff.affect(ch, Vulnerable.class, 2f); }
                    else Buff.affect(ch, Hex.class, 2f);
                }}}
        return super.act();
    }

    @Override
    public void beckon(int cell) {
        //do nothing
    }

    {
        immunities.add( Paralysis.class );
        immunities.add( Amok.class );
        immunities.add( Sleep.class );
        immunities.add( Terror.class );
        immunities.add( Vertigo.class );
    }
}
