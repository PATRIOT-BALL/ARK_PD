package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.AirborneSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.InfantrySprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class AirborneSoldier extends Mob{
    private int blinkCooldown = 0;
    {
    spriteClass = AirborneSprite .class;

    HP = HT = 20;
    defenseSkill = 5;

    EXP = 4;
    maxLvl = 12;

    loot = Gold .class;
    lootChance = 0.3f;
    immunities.add(Silence.class);
}

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 2, 9 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 12;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 4);
    }


	@Override
	protected boolean getCloser( int target ) {
		if (fieldOfView[target] && Dungeon.level.distance( pos, target ) > 2 && blinkCooldown <= 0) {

			blink( target );
			spend( -1 / speed() );
			return true;

		} else {

			blinkCooldown--;
			return super.getCloser( target );

		}
	}


	private void blink( int target ) {

		Ballistica route = new Ballistica( pos, target, Ballistica.PROJECTILE);
		int cell = route.collisionPos;

		//can't occupy the same cell as another char, so move back one.
		if (Actor.findChar( cell ) != null && cell != this.pos)
			cell = route.path.get(route.dist-1);

		if (Dungeon.level.avoid[ cell ]){
			ArrayList<Integer> candidates = new ArrayList<>();
			for (int n : PathFinder.NEIGHBOURS8) {
				cell = route.collisionPos + n;
				if (Dungeon.level.passable[cell] && Actor.findChar( cell ) == null) {
					candidates.add( cell );
				}
			}
			if (candidates.size() > 0)
				cell = Random.element(candidates);
			else {
				blinkCooldown = Random.IntRange(16, 20);
				return;
			}
		}

		ScrollOfTeleportation.appear( this, cell );
		if (Dungeon.isChallenged(Challenges.TACTICAL_UPGRADE)) Buff.affect(this, Barrier.class).setShield(4);

		blinkCooldown = Random.IntRange(16, 20);
	}


    private static final String BLINK = "blinkcooldown";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(BLINK, blinkCooldown);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        blinkCooldown = bundle.getInt(BLINK);
    }
}
