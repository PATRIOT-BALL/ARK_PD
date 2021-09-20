package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BombtailSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SpinnerSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class ExplodeSlug extends Mob {
    {
        spriteClass = SpinnerSprite.class;

        HP = HT = 165;
        defenseSkill = 28;

        EXP = 21;
        maxLvl = 36;

        loot = Generator.Category.WEAPON;
        lootChance = 0.2f;

       immunities.add(Burning.class);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 38,46 );
    }

    @Override
    public void die( Object cause ) {

        super.die( cause );

        if (buff(Silence.class) != null) return;
        if (cause == Chasm.class) return;

        boolean heroKilled = false;
        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
            Char ch = findChar( pos + PathFinder.NEIGHBOURS8[i] );
            if (ch != null && ch.isAlive()) {
                int damage = Random.NormalIntRange(45,70);
                damage = Math.max( 0,  damage - (ch.drRoll() +  ch.drRoll()) );
                ch.damage( damage, this );
                if (ch.isAlive()) Buff.affect(ch,Burning.class).reignite(ch);
                if (ch == Dungeon.hero && !ch.isAlive()) {
                    heroKilled = true;
                }
            }
        }

        if (Dungeon.level.heroFOV[pos]) {
            Sample.INSTANCE.play( Assets.Sounds.BLAST );
        }

        if (heroKilled) {
            Dungeon.fail( getClass() );
            GLog.n( Messages.get(this, "explo_kill") );
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
