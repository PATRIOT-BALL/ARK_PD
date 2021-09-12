package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BreakerSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CroninSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class SiestaBoss extends Mob {
    private static final String[] LINE_KEYS = {"skill1", "skill2"};
    {
        spriteClass = CroninSprite.class;

        HP = HT = 1200;
        defenseSkill = 0;

        EXP = 40;

        state = HUNTING;
        baseSpeed = 0;

        properties.add(Property.BOSS);
        properties.add(Property.IMMOVABLE);
        immunities.add(Amok.class);
        immunities.add(Terror.class);
        immunities.add(Silence.class);
        immunities.add(TalismanOfForesight.CharAwareness.class);
    }

    private int phase = 0;
    private int Life = 5;
    private int TelType = 0;

    private SiestaBoss.BossAgent Agent1;
    private SiestaBoss.BossAgent Agent2;
    private Schwarz mySchwarz;

    @Override
    protected Char chooseEnemy() {
        return null;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 10);
    }

    @Override
    protected boolean act() {
        if (phase == 0) {
            if (Dungeon.hero.viewDistance >= Dungeon.level.distance(pos, Dungeon.hero.pos)) {
                Dungeon.observe();
            }
            if (Dungeon.level.heroFOV[pos]) {
                notice();
            }
        }

        if (phase == 0){
            damage(1,this);
            spend(TICK);
            phase++;
            return true;}

        return super.act();
    }

    @Override
    public void damage(int dmg, Object src) {
        int hpBracket = 200;

        int beforeHitHP = HP;
        super.damage(dmg, src);
        dmg = beforeHitHP - HP;

        //tengu cannot be hit through multiple brackets at a time
        if ((beforeHitHP / hpBracket - HP / hpBracket) >= 2) {
            HP = hpBracket * ((beforeHitHP / hpBracket) - 1) + 1;
        }

        if (phase == 1 && HP <= HT/2) {
            yell(Messages.get(this, "phase2"));
            phase++;
        }

        if (isAlive()) {
            if (beforeHitHP / hpBracket != HP / hpBracket) {
                Skill();
                Life--;
            }
        }
    }

    @Override
    public boolean isAlive() {
        return HP > 0 || Life > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void notice() {
        if (phase == 0) yell(Messages.get(this, "notice"));
        if (!BossHealthBar.isAssigned()) {
            BossHealthBar.assignBoss(this);
        }
    }

    @Override
    public void die(Object cause) {
        for (Mob mob : (Iterable<Mob>)Dungeon.level.mobs.clone()) {
            if (mob instanceof BossAgent || mob instanceof Schwarz) {
                mob.die( cause );
            }

            Badges.validatesiesta1();
        }

        yell(Messages.get(this, "defeated"));

        GameScene.bossSlain();
        Dungeon.level.unseal();
        super.die( cause );
    }

    public static int[] TelPos = new int[]{
            35, 49, 71, 81,
            93,124,130,174,
            182, 226, 232, 263,
            275, 285, 307, 321};

    private void Skill() {
        int A1pos;
        int A2pos;
        int SwPos;
        int ThisPos;

        switch (TelType) {
            case 0: default:
                ThisPos = TelPos[Random.IntRange(0,3)];
                SwPos = TelPos[Random.IntRange(4,7)];
                A2pos = TelPos[Random.IntRange(8,11)];
                A1pos = TelPos[Random.IntRange(12,15)];
                break;
            case 1:
                A2pos = TelPos[Random.IntRange(0,3)];
                A1pos = TelPos[Random.IntRange(4,7)];
                ThisPos = TelPos[Random.IntRange(8,11)];
                SwPos = TelPos[Random.IntRange(12,15)];
                break;
            case 2:
                A1pos = TelPos[Random.IntRange(0,3)];
                A2pos = TelPos[Random.IntRange(4,7)];
                SwPos = TelPos[Random.IntRange(8,11)];
                ThisPos = TelPos[Random.IntRange(12,15)];
                break;
            case 3:
                SwPos = TelPos[Random.IntRange(0,3)];
                ThisPos = TelPos[Random.IntRange(4,7)];
                A1pos = TelPos[Random.IntRange(8,11)];
                A2pos = TelPos[Random.IntRange(12,15)];
                break;
        }

        // 소환 몬스터 생존 확인
        if (Agent1 != null &&
                (!Agent1.isAlive()
                        || !Dungeon.level.mobs.contains(Agent1)
                        || Agent1.alignment != alignment)){
            Agent1 = null;
        }
        if (Agent2 != null &&
                (!Agent2.isAlive()
                        || !Dungeon.level.mobs.contains(Agent2)
                        || Agent2.alignment != alignment)){
            Agent2 = null;
        }
        if (mySchwarz != null &&
                (!mySchwarz.isAlive()
                        || !Dungeon.level.mobs.contains(mySchwarz)
                        || mySchwarz.alignment != alignment)){
            mySchwarz = null;
        }


        // 텔레포트 발동
        if (Agent1 == null) {
            Agent1 = new SiestaBoss.BossAgent();
            Agent1.pos = A1pos;
            GameScene.add( Agent1 );
        }
        else {
            Agent1.pos = A1pos;
            Agent1.sprite.move(pos,A1pos);
        }

        if (Agent2 == null) {
            Agent2 = new SiestaBoss.BossAgent();
            Agent2.pos = A2pos;
            GameScene.add( Agent2 );
        }
        else {
            Agent2.pos = A2pos;
            Agent2.sprite.move(pos,A2pos);
        }

        if (mySchwarz == null) {
            mySchwarz = new Schwarz();
            mySchwarz.pos = SwPos;
            GameScene.add( mySchwarz );
        }
        else {
            mySchwarz.pos = SwPos;
            mySchwarz.sprite.move(pos,SwPos);
        }


        // Phase2의 강화 판정
        if (phase == 2) {
            Buff.affect(Agent1, Stamina.class, 30f);
            Buff.affect(Agent2, Stamina.class, 30f);
            if (mySchwarz != null && mySchwarz.Phase != 2) mySchwarz.Phase = 2;
        }


        // 플레이어 디버프 판정
        // 흑요성을 사용할 때마다 능력이 1개씩 무효화됨
        if (Dungeon.siesta1_bosspower > 3) {
            Buff.affect(Dungeon.hero, Paralysis.class, 2f);
        }
        if (Dungeon.siesta1_bosspower > 2) {
            Buff.affect(Dungeon.hero, Slow.class, 4f);
        }
        if (Dungeon.siesta1_bosspower > 1) {
            Buff.affect(Dungeon.hero, Blindness.class, 5f);
        }
        if (Dungeon.siesta1_bosspower > 0) {
            Buff.affect(Dungeon.hero, Burning.class).reignite(Dungeon.hero);
        }

        this.pos = ThisPos;
        sprite.place(pos);

        yell(Messages.get(this, Random.element( LINE_KEYS )));

        GameScene.flash( 0x80FFFFFF );

        Dungeon.observe();
        GameScene.updateFog();

        if (TelType < 3) TelType++;
        else TelType = Random.IntRange(0,4);
    }

    private static final String PHASE   = "phase";
    private static final String LIFE   = "Life";
    private static final String TTYPE   = "TelType";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( PHASE, phase );
        bundle.put( LIFE, Life );
        bundle.put( TTYPE, TelType );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        phase = bundle.getInt(PHASE);
        Life = bundle.getInt(LIFE);
        TelType = bundle.getInt(TTYPE);

    }

    public static class BossAgent extends Agent {

        {
            HT=HP=70;
            state = HUNTING;

            spriteClass = BreakerSprite.class;
            immunities.add(Drowsy.class);
            immunities.add(MagicalSleep.class);
            immunities.add(Corruption.class);

            //no loot or exp
            maxLvl = -5;
        }
    }
}
