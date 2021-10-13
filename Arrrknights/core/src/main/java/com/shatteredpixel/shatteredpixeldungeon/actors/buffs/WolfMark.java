package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Hunt;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.Wound;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Door;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class WolfMark extends FlavourBuff implements ActionIndicator.Action  {
    public int object = 0;

    private static final String OBJECT    = "object";

    public static final float DURATION = 8f;

    {
        type = buffType.POSITIVE;
    }

    public void set(int object){
        this.object = object;
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
        bundle.put( OBJECT, object );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        object = bundle.getInt( OBJECT );
    }
    @Override
    public Image getIcon() {
        return new ItemSprite(ItemSpriteSheet.THROWING_KNIFE, null);
    }

    @Override
    public void doAction() {

        Hero hero = Dungeon.hero;
        if (hero == null) return;

        Char ch = (Char) Actor.findById(object);
        if (ch == null) return;

        int dmg = ch.HT / 2;
        if (hero.hasTalent(Talent.ASSASSINS_REACH)) {
            if (Random.Int(4 - hero.pointsInTalent(Talent.ASSASSINS_REACH)) == 0) {
                dmg *= 2;
            }
        }
        if (ch.properties().contains(Char.Property.BOSS)) {
            dmg = 0;
        }
        Dungeon.hero.sprite.zap(ch.pos);

        ch.damage( dmg, this);
        ch.sprite.burst(CharSprite.NEGATIVE, 15);

        CellEmitter.get( hero.pos ).burst( Speck.factory( Speck.WOOL ), 12 );
        Sample.INSTANCE.play(Assets.Sounds.HIT_SLASH, 1.32f);

        Ballistica trajectory = new Ballistica(hero.pos, ch.pos, Ballistica.STOP_TARGET);
        trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);

        int movepower = 1;
        while (movepower > trajectory.dist ||
                (movepower > 0 && Dungeon.level.pit[trajectory.path.get(movepower)])) {
            movepower--;
        }
        moveChar(hero, trajectory, movepower, ch.pos, false, false); // 자신이 이동효과
        Dungeon.level.occupyCell(hero );
        Dungeon.observe();

        Buff.affect(hero, ThrowingKnife.huntcooldown.class, 450f - hero.pointsInTalent(Talent.ASSASSINS_REACH) * 75);

        if (!ch.isAlive()) {
            Wound.hit(ch);
            Hunt.hit(ch);
        }

        if (hero.hasTalent(Talent.TRACKER)){
            Buff.affect(hero, Haste.class, hero.pointsInTalent(Talent.TRACKER));
            Buff.detach(hero, Cripple.class);
            Buff.detach(hero, Blindness.class);
            Buff.detach(hero, Roots.class);
        }

        hero.spendAndNext(1f);

        detach();

    }

    private void moveChar(final Char ch, final Ballistica trajectory, int power, int enemypos,
                          boolean closeDoors, boolean collideDmg){
        if (ch.properties().contains(Char.Property.BOSS)) {
            power /= 2;
        }

        int dist = Math.min(trajectory.dist, power);

        boolean collided = dist == trajectory.dist;

        if (dist == 0 || ch.properties().contains(Char.Property.IMMOVABLE)) return;

        //large characters cannot be moved into non-open space
        if (Char.hasProp(ch, Char.Property.LARGE)) {
            for (int i = 1; i <= dist; i++) {
                if (!Dungeon.level.openSpace[trajectory.path.get(i)]){
                    dist = i-1;
                    collided = true;
                    break;
                }
            }
        }

        if (Actor.findChar(trajectory.path.get(dist)) != null){
            dist--;
            collided = true;
        }

        if (dist < 0) return;

        final int newPos = trajectory.path.get(dist);

        if (newPos == enemypos) return;

        final int finalDist = dist;
        final boolean finalCollided = collided && collideDmg;
        final int initialpos = ch.pos;

        Actor.addDelayed(new Pushing(ch, ch.pos, newPos, new Callback() {
            public void call() {
                if (initialpos != ch.pos) {
                    //something caused movement before pushing resolved, cancel to be safe.
                    ch.sprite.place(ch.pos);
                    return;
                }
                int oldPos = ch.pos;
                ch.pos = newPos;
                if (finalCollided && ch.isAlive()) {
                    ch.damage(Random.NormalIntRange(finalDist, 2*finalDist), this);
                    Paralysis.prolong(ch, Paralysis.class, 1 + finalDist/2f);
                }
                if (closeDoors && Dungeon.level.map[oldPos] == Terrain.OPEN_DOOR){
                    Door.leave(oldPos);
                }
                Dungeon.level.occupyCell(ch);
                if (ch == Dungeon.hero){
                    //FIXME currently no logic here if the throw effect kills the hero
                    Dungeon.observe();
                }
            }
        }), -1);
    }
}
