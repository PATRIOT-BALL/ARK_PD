package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Acid_AlphaSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class AcidSlug_A extends Mob implements Callback {
    private static final float TIME_TO_ZAP	= 1f;

    {
        spriteClass = Acid_AlphaSprite.class;

        HP = HT = 150;
        defenseSkill = 30;

        EXP = 22;
        maxLvl = 37;

        loot = Generator.Category.POTION;
        lootChance = 0.25f;

        properties.add(Property.INFECTED);

    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 36, 48 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 40;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 20);
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        if(this.buff(Silence.class) != null) return false;
        return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
    }

    protected boolean doAttack( Char enemy ) {

        if (Dungeon.level.adjacent( pos, enemy.pos )) {

            return super.doAttack( enemy );

        } else {

            if (sprite != null && (sprite.visible || enemy.sprite.visible)) {
                sprite.zap( enemy.pos );
                return false;
            } else {
                zap();
                return true;
            }
        }
    }

    //used so resistances can differentiate between melee and magical attacks
    public static class AcidBolt{}

    private void zap() {
        spend( TIME_TO_ZAP );

        if (hit( this, enemy, true )) {

            int dmg = Random.NormalIntRange( 38, 44 );
            enemy.damage( dmg, new AcidSlug_A.AcidBolt() );
            if (enemy.isAlive()) {
                Buff.affect(enemy, Ooze.class).set(Ooze.DURATION);
                if (Dungeon.isChallenged(Challenges.TACTICAL_UPGRADE)) Buff.affect(enemy,Silence.class, 1f);
            }

            if (enemy == Dungeon.hero && !enemy.isAlive()) {
                Dungeon.fail( getClass() );
                GLog.n( Messages.get(this, "bolt_kill") );
            }
        } else {
            enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
        }
    }

    public void onZapComplete() {
        zap();
        next();
    }

    @Override
    public void call() {
        next();
    }
}
