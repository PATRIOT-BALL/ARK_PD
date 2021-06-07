package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.levels.RhodesLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ClosureSprite;
import com.watabou.noosa.Game;

public class Closure extends NPC {
    {
        spriteClass = ClosureSprite.class;
        HP=HT=60;
        properties.add(Property.IMMOVABLE);
    }

    @Override
    public int defenseSkill(Char enemy) {
        return INFINITE_EVASION;
    }

    @Override
    public void damage(int dmg, Object src) {
    }

    @Override
    public boolean interact(Char c) {
        sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "NO"));

        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]) {
                if (mob instanceof SkinModel) ((SkinModel) mob).SkinChange();
            }
        }
        return true;
    }

    @Override
    public void die(Object cause) {
        sprite.showStatus( CharSprite.NEUTRAL, Messages.get(Closure.class, "die"));
        super.die(cause);
    }

    public static void spawn(RhodesLevel level, int poss) {
        Closure WhatYourName = new Closure();
        do {
            WhatYourName.pos = poss;
        } while (WhatYourName.pos == -1);
        level.mobs.add(WhatYourName);
    }
}
