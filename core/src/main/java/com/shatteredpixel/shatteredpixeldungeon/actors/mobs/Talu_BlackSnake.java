package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem.Certificate;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.SurfaceScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.FistSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.YogSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Talu_BlackSnake extends Mob {
    private static final String[] LINE_KEYS = {"invincibility1", "invincibility2", "invincibility3"};
    {
        spriteClass = FistSprite.Burning.class;

        HP = HT = 2500;

        EXP = 100;

        //so that allies can attack it. States are never actually used.
        state = HUNTING;

        viewDistance = 12;

        properties.add(Property.BOSS);
        immunities.add(Amok.class);
        immunities.add(Terror.class);
        immunities.add(Paralysis.class);
        immunities.add(Vertigo.class);
        immunities.add(Silence.class);
        immunities.add(Blindness.class);
        properties.add(Property.FIERY);
    }

    private int phase = 0; // 1~5까지
    private int IgniteCooldown = 0; // 점화 패턴 쿨타임
    private int BurstCooldown = 0; // 화염 폭발 패턴 쿨타임
    private int OverwhelmCooldown = 0; // 압도 패턴 쿨타임
    private int InvincibilityCooldown = 0; // 무적 패턴 쿨타임
    private int InvincibilityTime = 0; // 무적 패턴 지속시간.
    private int BurstPos = -1; // 화염 폭발 패턴의 발동 지점
    private int BurstTime = 0; // 화염 폭발 발동 시간. 2가 되면 발동함
    private int drup = 0; // 방어 상승 상태 지속시간. 있을 경우 받는 피해 50%감소
    private boolean fx = false;



    @Override
    public int damageRoll() {
        if (InvincibilityTime > 0) return Random.NormalIntRange(55, 75);
        return Random.NormalIntRange(40, 50); }

    @Override
    public int attackSkill(Char target) { return 50; }

    @Override
    public int defenseSkill(Char enemy) {
        if (Dungeon.level.map[this.pos] == Terrain.WATER) return 16;
        return 32; }

    @Override
    public int drRoll() { return Random.NormalIntRange(0, 20); }

    @Override
    public void damage(int dmg, Object src) {
        if (InvincibilityTime > 0) {
            sprite.showStatus( CharSprite.NEUTRAL, Messages.get(this, "invincibility") );
            Sample.INSTANCE.play( Assets.Sounds.HIT_PARRY, 1, Random.Float(0.96f, 1.05f));
            return;
        }
        if (drup > 0) {
            dmg /= 2;
        }

        super.damage(dmg, src);

        if (phase==1 && HP < 2200) {
            HP = 2200;
            phase = 2;
            drup += 3;
            GameScene.flash(0x80FF0000);
            yell(Messages.get(this, "phase2"));
        }
        else if (phase==2 && HP < 1700) {
            HP = 1700;
            phase = 3;
            drup += 6;
            GameScene.flash(0x80FF0000);
            yell(Messages.get(this, "phase3"));
        }
        else if (phase==3 && HP < 1300) {
            HP = 1300;
            phase = 4;

            GameScene.flash(0x80FF0000);
            yell(Messages.get(this, "phase4"));
        }
        else if (phase==4 && HP < 500) {
            HP = 500;
            phase = 5;
            drup += 3;
            GameScene.flash(0x80FF0000);
            yell(Messages.get(this, "phase5"));
        }
    }

    @Override
    protected boolean act() {
        if (phase == 5 && HP < 1) {
            Badges.validateVictory();
            Badges.validateChampion(Challenges.activeChallenges());
            Badges.validateChampion_char(Challenges.activeChallenges());
            Badges.saveGlobal();

            Dungeon.level.drop(new Certificate(25), pos).sprite.drop(pos);
            Certificate.specialEndingBouns();

            Badges.silentValidateHappyEnd();
            Badges.validatewill();
            Dungeon.win(Amulet.class);
            Dungeon.deleteGame(GamesInProgress.curSlot, true);
            Game.switchScene(SurfaceScene.class);
        }

        if (phase > 3 && fx == false) {
            this.sprite.add(CharSprite.State.TALU_BOSS);
            fx = true;
        }

        if (phase == 0) {
            if (Dungeon.hero.viewDistance >= Dungeon.level.distance(pos, Dungeon.hero.pos)) {
                Dungeon.observe();
            }
            if (Dungeon.level.heroFOV[pos]) {
                notice();
            }
        }

        if (phase == 0) {
            spend(TICK);
            return true;
        }
        else {
            UseAbility();
        }

        if (Dungeon.level.map[this.pos] == Terrain.WATER) {
            damage(8, this);
        }

        if (InvincibilityTime > 0) {
            int evaporatedTiles;
            evaporatedTiles = Random.chances(new float[]{0, 2, 1, 1});
            for (int i = 0; i < evaporatedTiles; i++) {
                int cell = pos + PathFinder.NEIGHBOURS8[Random.Int(8)];
                if (Dungeon.level.map[cell] == Terrain.WATER) {
                    Level.set(cell, Terrain.EMPTY);
                    GameScene.updateMap(cell);
                    CellEmitter.get(cell).burst(Speck.factory(Speck.STEAM), 10);
                }
            }

            for (int i : PathFinder.NEIGHBOURS9) {
                int vol = Fire.volumeAt(pos+i, Fire.class);
                if (vol < 4 && !Dungeon.level.water[pos + i] && !Dungeon.level.solid[pos + i]){
                    GameScene.add( Blob.seed( pos + i, 4 - vol, Fire.class ) );
                }
            }

        }

        if (IgniteCooldown > 0) IgniteCooldown--;
        if (BurstCooldown > 0) BurstCooldown--;
        if (OverwhelmCooldown > 0) OverwhelmCooldown--;
        if (InvincibilityCooldown > 0) InvincibilityCooldown--;
        if (InvincibilityTime > 0) InvincibilityTime--;
        if (drup > 0) drup--;

        return super.act();
    }


    private boolean UseAbility() {
        // 화염 폭발 > 우르수스의 의지 > 압도 > 점화 순서의 우선도를 가집니다.

        //화염폭발
        if (BurstCooldown <= 0) {
            if (BurstPos == -1) {
                // 위치 미지정시, 이번 턴에는 폭발을 일으킬 지점을 정합니다.
                BurstPos = Dungeon.hero.pos;
                sprite.parent.addToBack(new TargetedCell(BurstPos, 0xFF0000));

                for (int i : PathFinder.NEIGHBOURS9) {
                    int vol = Fire.volumeAt(BurstPos+i, Fire.class);
                    if (vol < 4){
                        sprite.parent.addToBack(new TargetedCell(BurstPos + i, 0xFF0000));
                    }
                }

                Sample.INSTANCE.play( Assets.Sounds.BURNING );
                BurstTime++;

                return true;
            }
            else if (BurstTime == 1) {
                for (int i : PathFinder.NEIGHBOURS9) {
                    int vol = Fire.volumeAt(BurstPos+i, Fire.class);
                    if (vol < 4){
                        GameScene.add( Blob.seed( BurstPos + i, 4 - vol, Fire.class ) );
                    }
                }

                Sample.INSTANCE.play( Assets.Sounds.BURNING );

                BurstTime++;
                return true;}
            else if (BurstTime == 2) {
                PathFinder.buildDistanceMap(BurstPos, BArray.not(Dungeon.level.solid, null), 1);
                for (int cell = 0; cell < PathFinder.distance.length; cell++) {
                    if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                        if (Dungeon.level.map[cell] == Terrain.WATER) {
                            Level.set(cell, Terrain.EMPTY);
                            GameScene.updateMap(cell);
                            CellEmitter.get(cell).burst(Speck.factory(Speck.STEAM), 10);}

                        Char ch = Actor.findChar(cell);
                        if (ch != null&& !(ch instanceof Talu_BlackSnake)) {
                            ch.damage(Random.NormalIntRange(48, 72), this);
                        if (ch instanceof Hero) GameScene.flash(0x80FF0000);
                        }}}
                Camera.main.shake(2, 0.5f);
                BurstPos = -1;
                BurstTime = 0;
                if (phase == 5) BurstCooldown = Random.NormalIntRange(4,5);
                else BurstCooldown = Random.NormalIntRange(8,10);

                Sample.INSTANCE.play( Assets.Sounds.BLAST, 2.5f, 1.21f );

                return true;
            }
        }
        // 우르수스의 의지
            else if (InvincibilityCooldown <= 0 && phase >= 4) {
            if (phase == 5) InvincibilityTime = 8;
            else InvincibilityTime = 5;
            InvincibilityCooldown = 22;

            yell(Messages.get(this, Random.element( LINE_KEYS )));
            Sample.INSTANCE.play( Assets.Sounds.BLAST, 3f, 0.25f );
            Camera.main.shake(3, 0.5f);

            return true;
        }
        // 압도
        else if (OverwhelmCooldown <= 0 && phase >= 2) {
            blink( Dungeon.hero.pos );

            Ballistica trajectory = new Ballistica(this.pos, Dungeon.hero.pos, Ballistica.STOP_TARGET);
            trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);

            WandOfBlastWave.throwChar(Dungeon.hero, trajectory, 1); // 넉백 효과


            if (phase == 5) Buff.affect( this, Barrier.class).incShield(40);
            if (phase == 5) OverwhelmCooldown = Random.NormalIntRange(7,11);
            else OverwhelmCooldown = Random.NormalIntRange(12,16);

            return true;
        }
        // 점화
        else if (IgniteCooldown <= 0) {
            Dungeon.hero.sprite.emitter().burst( ElmoParticle.FACTORY, 5 );
            Dungeon.hero.damage(Random.NormalIntRange(6,12), this);
            Buff.affect( Dungeon.hero, Burning.class).reignite( Dungeon.hero);

            if (phase == 5) {
                Level.set(Dungeon.hero.pos, Terrain.EMPTY);
                GameScene.updateMap(Dungeon.hero.pos);
                CellEmitter.get(Dungeon.hero.pos).burst(Speck.factory(Speck.STEAM), 10);
                IgniteCooldown = 3;
            }
            else IgniteCooldown = Random.NormalIntRange(5, 6);

            Sample.INSTANCE.play( Assets.Sounds.BURNING );

            return true;
        }

        return true;
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
                return;
            }
        }

        ScrollOfTeleportation.appear( this, cell );
        Sample.INSTANCE.play( Assets.Sounds.CURSED );
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        enemy.damage(8, this);

        if (Dungeon.level.map[enemy.pos] == Terrain.WATER) {
            damage *= 0.7f;
        }
        return super.attackProc(enemy, damage);
    }

    @Override
    public void notice() {
        if (!BossHealthBar.isAssigned()) {
            BossHealthBar.assignBoss(this);
            yell(Messages.get(this, "notice"));
            for (Char ch : Actor.chars()) {
                if (ch instanceof DriedRose.GhostHero) {
                    ((DriedRose.GhostHero) ch).sayBoss();
                }
            }
            if (phase == 0) {
                phase = 1;
            }
        }
    }

    @Override
    public boolean isAlive() {
        return true;
    }


    private static final String PHASE   = "phase";
    private static final String SKILL1CD   = "IgniteCooldown";
    private static final String SKILL2CD   = "BurstCooldown";
    private static final String SKILL2POS   = "BurstPos";
    private static final String SKILL2TIME   = "BurstTime";
    private static final String SKILL3CD   = "OverwhelmCooldown";
    private static final String SKILL4CD   = "InvincibilityCooldown";
    private static final String SKILL4TIME   = "InvincibilityTime";
    private static final String DRUPTIME   = "drup";


    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( PHASE, phase );
        bundle.put( SKILL1CD, IgniteCooldown );
        bundle.put( SKILL2CD, BurstCooldown );
        bundle.put( SKILL2POS, BurstPos );
        bundle.put( SKILL2TIME, BurstTime );
        bundle.put( SKILL3CD, OverwhelmCooldown );
        bundle.put( SKILL4CD, InvincibilityCooldown );
        bundle.put( SKILL4TIME, InvincibilityTime );
        bundle.put( DRUPTIME, drup );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        phase = bundle.getInt(PHASE);
        IgniteCooldown = bundle.getInt(SKILL1CD);
        BurstCooldown = bundle.getInt(SKILL2CD);
        BurstPos = bundle.getInt(SKILL2POS);
        BurstTime = bundle.getInt(SKILL2TIME);
        OverwhelmCooldown = bundle.getInt(SKILL3CD);
        InvincibilityCooldown = bundle.getInt(SKILL4CD);
        InvincibilityTime = bundle.getInt(SKILL4TIME);
        drup = bundle.getInt(DRUPTIME);
        BossHealthBar.assignBoss(this);

    }
}
