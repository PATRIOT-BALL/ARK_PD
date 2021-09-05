package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Honeypot;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ErgateSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ThiefSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Ergate extends Mob {
    public Item item;

    {
        spriteClass = ErgateSprite.class;

        HP = HT = 75;
        defenseSkill = 40;

        EXP = 15;
        maxLvl = 29;

        state = WANDERING;

        loot = new MysteryMeat();
        lootChance = 0.2f;

    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 24, 35 );
    }

    @Override
    protected float attackDelay() {
        return super.attackDelay()*0.5f;
    }

    //generates an average of 1 dew, 0.25 seeds, and 0.25 stones
    @Override
    protected Item createLoot() {
        Item loot;
        switch(Random.Int(3)){
            case 0: case 1: default:
                loot = new Dewdrop();
                int ofs;
                do {
                    ofs = PathFinder.NEIGHBOURS8[Random.Int(8)];
                } while (Dungeon.level.solid[pos + ofs] && !Dungeon.level.passable[pos + ofs]);
                if (Dungeon.level.heaps.get(pos+ofs) == null) {
                    Dungeon.level.drop(new Dewdrop(), pos + ofs).sprite.drop(pos);
                } else {
                    Dungeon.level.drop(new Dewdrop(), pos + ofs).sprite.drop(pos + ofs);
                }
                break;
            case 2:
                loot = Generator.random(Generator.Category.SEED);
                break;
        }
        return loot;
    }

    @Override
    public int attackSkill( Char target ) {
        return 35;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 6);
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );

        if (this.buff(Silence.class) == null) {
            if (alignment == Alignment.ENEMY && item == null
                    && enemy instanceof Hero) {
                if (Random.Int(4) < 1) {
                    if (((Hero) enemy).belongings.weapon != null) {
                    ((Hero) enemy).belongings.weapon.doDrop((Hero) enemy);}
                    Buff.affect(this, Terror.class, 20f);
                }
            }
        }

        return damage;
    }
}
