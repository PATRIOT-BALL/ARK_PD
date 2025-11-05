package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;


import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.NPC;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem.Certificate;
import com.shatteredpixel.shatteredpixeldungeon.scenes.SurfaceScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Mula_2Sprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Mula_3Sprite;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

//패턴 : 근처에 모든 적에게 고정데미지를 입히는 스킬을 사용한다
public class SeaBoss2_Phase2_Tail extends Mob {
    {
        spriteClass = Mula_3Sprite.class;

        HP = HT = 1000;

        defenseSkill = 25;

        properties.add(Property.SEA);
        properties.add(Property.BOSS);
        properties.add(Property.IMMOVABLE);

        state = HUNTING;
    }

    // 모든 믈라 파츠가 파괴되면 사망
    private boolean dieChacke = false;

    private int cooldown = 3;

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(25, 55);
    }

    @Override
    public int attackSkill( Char target ) {
        return 50;
    }

    @Override
    public int defenseSkill(Char enemy) {
        if (dieChacke) return INFINITE_EVASION;
        else return 20;
    }

    // 사거리 2
    @Override
    protected boolean canAttack(Char enemy) {
        return !dieChacke && this.fieldOfView[enemy.pos] && Dungeon.level.distance(this.pos, enemy.pos) <= 2;
    }

    @Override
    protected boolean act() {

        sprite.turnTo(pos, 999999);
        rooted = true;

        if (dieChacke) return super.act();

        if (cooldown > 0) cooldown--;
        else {
            Dungeon.hero.damage(10, this);
            for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
                if (mob.alignment == Alignment.ALLY)
                    mob.damage(10, this);
            }
            if (Dungeon.isChallenged(Challenges.DECISIVE_BATTLE)) cooldown = 3;
            else cooldown = 5;
        }

        return super.act();
    }

    @Override
    public void damage(int dmg, Object src) {

        if (dieChacke) return;

        super.damage(dmg, src);

        if (HP < 1) {
            dieChacke = true;
            Buff.affect(this, Doom.class);
            Dungeon.mulaCount++;

        }
    }

    @Override
    public void die(Object cause) { }


    private static final String DIECHACKE_TAIL   = "dieChackeTail";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( DIECHACKE_TAIL, dieChacke );
    }

    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);

        dieChacke = bundle.getBoolean(DIECHACKE_TAIL);
    }
    }





