package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ExplodSulgSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class ExplodeSlug_A extends Mob {
    {
        spriteClass = ExplodSulgSprite.Elite.class;

        HP = HT = 165;
        defenseSkill = 28;

        EXP = 21;
        maxLvl = 36;

        loot = Generator.Category.WEAPON;
        lootChance = 0.2f;

       immunities.add(Burning.class);
        properties.add(Property.INFECTED);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 38,46 );
    }

    @Override
    public void die( Object cause ) {
        boolean Silenced = false;
        if (this.buff(Silence.class) != null) Silenced = true;
        super.die( cause );

        if (Silenced) return;
        if (cause == Chasm.class) return;

            boolean heroKilled = false;
            for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                Char ch = findChar(pos + PathFinder.NEIGHBOURS8[i]);
                if (ch != null && ch.isAlive()) {
                    int damage = Random.NormalIntRange(70, 100);
                    damage = Math.max(0, damage - (ch.drRoll() + ch.drRoll()));
                    ch.damage(damage, this);
                    if (ch.isAlive()) Buff.affect(ch, Burning.class).reignite(ch);
                    if (ch == Dungeon.hero && !ch.isAlive()) {
                        heroKilled = true;
                    }
                }
            }

            if (heroKilled) {
                Dungeon.fail(getClass());
                GLog.n(Messages.get(this, "explo_kill"));
            }
    }

    @Override
    public int attackSkill( Char target ) {
        return 45;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 24);
    }
}
