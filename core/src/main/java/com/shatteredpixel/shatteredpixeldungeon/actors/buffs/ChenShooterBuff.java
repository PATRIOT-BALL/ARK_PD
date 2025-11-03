package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SnowHunter;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class ChenShooterBuff extends FlavourBuff implements ActionIndicator.Action {
    public int targetid = 0;

    private static final String TARGET = "targetid";

    {
        type = buffType.POSITIVE;
    }
    @Override
    public boolean attachTo(Char target) {
        ActionIndicator.setAction(this);
        return super.attachTo(target);
    }

    @Override
    public void detach() {

        super.detach();
        ActionIndicator.clearAction(this);
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put(TARGET, targetid);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        targetid = bundle.getInt(TARGET);
        bundle.put(TARGET, targetid);
    }
    @Override
    public Image getIcon() {
        Image im = new Image(Assets.Interfaces.BUFFS_LARGE, 144, 32, 16, 16);
        im.hardlight(0x99992E);
        return im;
    }

    public void set(int id) {
        targetid = id;
    }

    @Override
    public void doAction() {

        Hero hero = Dungeon.hero;
        if (hero == null) return;

        Char ch = (Char) Actor.findById(targetid);
        if (ch == null) return;

        final Ballistica chain = new Ballistica(hero.pos, ch.pos, Ballistica.STOP_TARGET);
        SnowHunter.chainEnemy(chain, hero);

        CellEmitter.get( hero.pos ).burst( Speck.factory( Speck.WOOL ), 6 );
        hero.spendAndNext(0f);

        if (hero.hasTalent(Talent.TAC_DEF)) Buff.affect(hero, Barrier.class).incShield(hero.pointsInTalent(Talent.TAC_DEF) * 2);
        if (hero.hasTalent(Talent.TAC_SHOT)) Buff.affect(hero, TACMove_tacshot.class);

        float CD = 20f;
        if (hero.hasTalent(Talent.GORGEOUS_VACATION)) CD -= hero.pointsInTalent(Talent.GORGEOUS_VACATION) * 4;
        if (hero.hasTalent(Talent.TECHNICAL) && Random.Int(4) < hero.pointsInTalent(Talent.TECHNICAL)) CD -= 5;

        Buff.affect(hero, TACMoveCooldown.class, CD);

        Dungeon.level.occupyCell(hero);
        Dungeon.observe();
        detach();
    }



    public static class TACMoveCooldown extends FlavourBuff {
        @Override
        public int icon() {
            return BuffIndicator.COOL_TIME;
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", dispTurns());
        }
    }

    public static class TACMove_tacshot extends Buff{

    }
}
