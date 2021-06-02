package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ActiveOriginium;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CivilianSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LensSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Lens extends NPC {

    private static final String[] LINE_KEYS = {"T1", "T2", "T3", "T4"};

    {
        spriteClass = LensSprite.class;
        baseSpeed = 2f;
        state = WANDERING;
        flying = false;
    }

    public Lens() {
        super();
        HP = 45;
        defenseSkill = 5 + Dungeon.hero.lvl;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 1);
    }

    @Override
    protected Char chooseEnemy() {
        return null;
    }

    @Override
    protected boolean act() {

        if (this.buff(Invisibility.class) == null) {
            baseSpeed = 0.25f;
            damage(3, this);
        }
        else Buff.affect(Dungeon.hero, TalismanOfForesight.CharAwareness.class, 12).charID = this.id();
        return super.act();
    }

    @Override
    public boolean interact(Char c) {
        sprite.attack(0);
        sprite.showStatus( CharSprite.NEUTRAL, Messages.get(this, Random.element( LINE_KEYS )) );
        return true;
    }
}
