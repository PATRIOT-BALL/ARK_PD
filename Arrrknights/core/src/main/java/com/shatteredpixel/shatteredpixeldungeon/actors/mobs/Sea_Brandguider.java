package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Camouflage;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.OriginiutantSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Sea_BrandguiderSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Sea_LeefSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Sea_Brandguider extends Mob {
    {
        spriteClass = Sea_BrandguiderSprite.class;

        HP = HT = 110;
        EXP = 18;
        maxLvl = 38;

        defenseSkill = 20;

        loot = Generator.Category.SCROLL;
        lootChance = 0.33f;

        properties.add(Property.SEA);
    }

    @Override
    public int damageRoll() { return Random.NormalIntRange(30, 46); }

    @Override
    public int attackSkill( Char target ) {
        return 37;
    }

    @Override
    public int drRoll() {
        if (HT /2 >= HP) return Random.NormalIntRange(20, 40);
        return Random.NormalIntRange(0, 20);
    }

    @Override
    protected boolean act() {
        if (HT /2 >= HP && this.buff(Silence.class) == null) {
            if (Dungeon.level.map[this.pos] == Terrain.EMPTY || Dungeon.level.map[this.pos] == Terrain.WATER) {
                Dungeon.level.map[pos] = Terrain.EMPTY_SP;
                CellEmitter.get(pos).burst(Speck.factory(Speck.BUBBLE), 10);
                GameScene.updateMap( pos );
                Dungeon.observe();
            }
        }
        return super.act();
    }
}
