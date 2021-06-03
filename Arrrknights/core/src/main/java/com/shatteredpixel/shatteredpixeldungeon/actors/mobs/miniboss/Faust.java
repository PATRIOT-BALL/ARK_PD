package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.shatteredpixeldungeon.levels.CavesLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.CityLevel;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LancerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SarkazSniperSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Faust extends Mob {
    {
        spriteClass = SarkazSniperSprite.class;

        properties.add(Property.MINIBOSS);
        immunities.add(ScrollOfRage.class);

        HP = HT = 120;
        defenseSkill = 20;
        baseSpeed = 1f;

        EXP = 17;
        maxLvl = -1;

        loot = Generator.Category.SEED;
        lootChance = 1.0f;
    }

    private int charge = 0; // 3이 될경우 강화 사격

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(20, 30);
    }

    @Override
    public int attackSkill( Char target ) {
        return 35;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 12);
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
        return !Dungeon.level.adjacent( pos, enemy.pos ) && attack.collisionPos == enemy.pos;
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        if (charge >= 3) {
            damage = super.attackProc(enemy, damage) * 8;
            charge = 0;
        }
        else {
            damage = super.attackProc(enemy, damage);
            charge++;
        }

        return damage;
    }

    @Override
    public void move(int step) {
        charge = 0;
        super.move(step);
    }

    @Override
    protected boolean getCloser( int target ) {
        if (state == HUNTING) {
            return enemySeen && getFurther( target );
        } else {
            return super.getCloser( target );
        }
    }

    public static void spawn(CityLevel level) {
        if (Dungeon.depth >= 19 && !Dungeon.bossLevel()) {

            Faust fau = new Faust();
            do {
                fau.pos = level.randomRespawnCell(fau);
            } while (fau.pos == -1);
            level.mobs.add(fau);
        }
    }

    @Override
    public void die(Object cause) {
        GLog.w(Messages.get(Faust.class, "die"));
        Dungeon.mboss19 = 0;
        super.die(cause);
    }

    private static final String SKILLCD   = "charge";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( SKILLCD, charge );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        charge = bundle.getInt(SKILLCD);

    }
}
