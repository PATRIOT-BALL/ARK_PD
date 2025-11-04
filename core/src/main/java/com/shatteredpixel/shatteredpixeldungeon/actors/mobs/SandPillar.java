package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.CorrosiveGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gluttony;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RotHeartSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SandPillarSprite;

public class SandPillar extends Mob {
    {
        spriteClass = SandPillarSprite.class;

        HP = HT = 80;
        defenseSkill = 0;

        EXP = 4;

        state = PASSIVE;

        properties.add(Property.IMMOVABLE);
        properties.add(Property.MINIBOSS);
        immunities.add(CorrosiveGas.class);
        immunities.add( Paralysis.class );
        immunities.add( Amok.class );
        immunities.add( Sleep.class );
        immunities.add( Terror.class );
        immunities.add( Vertigo.class );

        loot = new Gluttony();
        lootChance = 1f;
    }

    @Override
    public void beckon(int cell) {
        //do nothing
    }

    @Override
    public int defenseProc(Char enemy, int damage) {
        GameScene.add(Blob.seed(pos, 30, CorrosiveGas.class));

        return super.defenseProc(enemy, damage);
    }
}
