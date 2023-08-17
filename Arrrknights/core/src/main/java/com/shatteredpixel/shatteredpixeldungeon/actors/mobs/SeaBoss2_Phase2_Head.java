package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;


import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CounterBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem.Certificate;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.SurfaceScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.First_talkSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Mula_1Sprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

//패턴 : 미정
public class SeaBoss2_Phase2_Head extends Mob {
    {
        spriteClass = Mula_1Sprite.class;

        HP = HT = 1000;

        defenseSkill = 25;

        properties.add(Property.SEA);
        properties.add(Property.BOSS);
        properties.add(Property.IMMOVABLE);

        state = HUNTING;

    }

    // 모든 믈라 파츠가 파괴되면 사망
    private boolean dieChacke = false;

    @Override
    public int defenseSkill(Char enemy) {
        if (dieChacke) return INFINITE_EVASION;
        else return 20;
    }

    // 사거리 3
    @Override
    protected boolean canAttack(Char enemy) {
        return this.fieldOfView[enemy.pos] && Dungeon.level.distance(this.pos, enemy.pos) <= 3;
    }

    @Override
    protected boolean act() {
        sprite.turnTo(pos, 999999);
        rooted = true;
        if (dieChacke)
        {

            if (Dungeon.mulaCount == 3) {
                Badges.validateVictory();
                Badges.validateChampion(Challenges.activeChallenges());
                Badges.validateChampion_char(Challenges.activeChallenges());
                Badges.saveGlobal();

                Certificate.specialEndingBouns();

                Badges.silentValidateHappyEnd();
                Badges.validatewill();
                Dungeon.win(Amulet.class);
                Dungeon.deleteGame(GamesInProgress.curSlot, true);
                Game.switchScene(SurfaceScene.class);
            }
        }

        return super.act();
    }

    @Override
    public void damage(int dmg, Object src) {

        if (dieChacke) return;;

        super.damage(dmg, src);

        if (HP < 1) {
            dieChacke = true;
            Buff.affect(this, Doom.class);
            Dungeon.mulaCount++;
        }
    }


    @Override
    public void die(Object cause) { }


    private static final String DIECHACKE   = "dieChacke";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( DIECHACKE, dieChacke );
    }

    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        dieChacke = bundle.getBoolean(DIECHACKE);
    }
    }





