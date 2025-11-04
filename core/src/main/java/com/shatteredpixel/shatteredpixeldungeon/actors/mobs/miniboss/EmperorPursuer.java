package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Dominion;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Talu_BlackSnake;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem.Certificate;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPassage;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Firesteel;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.FistSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.PursuerSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class EmperorPursuer extends Mob {
    {
        spriteClass = PursuerSprite.class;

        HP = HT = 500;
        defenseSkill = 16;

        EXP = 50;
        maxLvl = 45;

        state = PASSIVE;

        properties.add(Property.BOSS);
        properties.add(Property.LARGE);
        immunities.add(Charm.class);
        immunities.add(Silence.class);
        immunities.add(Amok.class);
        immunities.add(Terror.class);
    }

    private int GasCoolDown = 0;
    private int BurstCoolDown = 0;
    private int BurstTime = 0;
    private int Burstpos = -1;

    @Override
    public void beckon( int cell ) {
        // Do nothing
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 38, 45 );
    }

    @Override
    public int attackSkill(Char target) {
        return 48;
    }

    @Override
    public int drRoll() { return Random.NormalIntRange( 0, 16 ); }

    @Override
    public void damage(int dmg, Object src) {
        // 90을 초과하는 피해는 25%로 감소시킵니다.
        if (dmg > 90) { dmg = 90 + ((dmg-90) / 4); }
        if (state == PASSIVE) state = HUNTING;
        super.damage(dmg, src);
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
    }

    @Override
    protected boolean doAttack( Char enemy ) {
        if (Dungeon.level.adjacent( pos, enemy.pos )) {

            return super.doAttack( enemy );

        } else {

            if (sprite != null && (sprite.visible || enemy.sprite.visible)) {
                sprite.zap( enemy.pos );
                return false;
            } else {
                zap();
                return true;
            }
        }
    }

    public static class DarkBolt{}

    protected void zap() {
        if (enemy == null) return;
            spend( 1f );
            if (hit(this, enemy, true)) {

                if (enemy.buff(Weakness.class) != null && enemy.buff(Vulnerable.class) != null && enemy.buff(Hex.class) != null) {
                    enemy.damage(Random.NormalIntRange(16, 32), new EmperorPursuer.DarkBolt());
                }
                else enemy.damage(Random.NormalIntRange(4, 10), new EmperorPursuer.DarkBolt());
                if (!enemy.isAlive() && enemy == Dungeon.hero) {
                    Dungeon.fail(getClass());
                    GLog.n(Messages.get(Char.class, "kill", name()));
                }

            } else {
                enemy.sprite.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
            }
    }


    @Override
    protected boolean act() {
        if (state == PASSIVE) return super.act();
        if (!UseAbility()) {
            return true; }

        if (BurstCoolDown > 0) BurstCoolDown--;
        if (GasCoolDown > 0) GasCoolDown--;
        return super.act();
    }

    private boolean UseAbility() {
        // 폭발 > 국가 순

        if (enemy == null) return true;

        //폭발
        if (BurstCoolDown <= 0) {
            if (Burstpos == -1) {
                // 위치 미지정시, 이번 턴에는 폭발을 일으킬 지점을 정합니다.
                Burstpos = Dungeon.hero.pos;
                sprite.parent.addToBack(new TargetedCell(Burstpos, 0xFF0000));

                for (int i : PathFinder.NEIGHBOURS9) {
                    int vol = Fire.volumeAt(Burstpos+i, Fire.class);
                    if (vol < 4){
                        sprite.parent.addToBack(new TargetedCell(Burstpos + i, 0xFF0000));
                    }
                }

                sprite.zap(Burstpos);

                Sample.INSTANCE.play( Assets.Sounds.BURNING );
                BurstTime++;

                return false;
            }
            else if (BurstTime == 1) {
                Sample.INSTANCE.play( Assets.Sounds.CURSED );

                BurstTime++;
                return true;}
            else if (BurstTime == 2) {
                PathFinder.buildDistanceMap(Burstpos, BArray.not(Dungeon.level.solid, null), 1);
                for (int cell = 0; cell < PathFinder.distance.length; cell++) {
                    if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                       CellEmitter.get(cell).burst(ShadowParticle.UP, 10);
                        Char ch = Actor.findChar(cell);
                        if (ch != null&& !(ch instanceof EmperorPursuer)) {
                            ch.damage(Random.NormalIntRange(45, 70), this);
                        }}}
                Camera.main.shake(2, 0.5f);
                Burstpos = -1;
                BurstTime = 0;
                BurstCoolDown = Random.NormalIntRange(5,8);

                Sample.INSTANCE.play( Assets.Sounds.CURSED, 1.5f, 1.21f );

                return true;
            }
        }


        if (GasCoolDown <= 0) {
            ThorwGas(enemy);
            return true;
        }

        return true;
    }

    public void ThorwGas(Char target) {
        Dungeon.hero.interrupt();
        GameScene.add(Blob.seed(target.pos, 250, Dominion.class));
        GasCoolDown = 10;

    }
    public void onZapComplete(){
        zap();
        next();
    }

    @Override
    public void die(Object cause) {
        super.die(cause);

        Dungeon.level.drop(new Gold(1000), pos).sprite.drop();
        Dungeon.level.drop(new Certificate(1), pos).sprite.drop(pos);

            int ofs1;
            do {
                ofs1 = PathFinder.NEIGHBOURS8[Random.Int(8)];
            } while (!Dungeon.level.passable[pos + ofs1]);
            Dungeon.level.drop(new ScrollOfPassage(), pos + ofs1).sprite.drop(pos);

        int ofs2;
        do {
            ofs2 = PathFinder.NEIGHBOURS8[Random.Int(8)];
        } while (!Dungeon.level.passable[pos + ofs2]);
        Dungeon.level.drop(new StoneOfEnchantment(), pos + ofs2).sprite.drop(pos);

        int ofs3;
        do {
            ofs3 = PathFinder.NEIGHBOURS8[Random.Int(8)];
        } while (!Dungeon.level.passable[pos + ofs3]);
        Dungeon.level.drop(Generator.random(Generator.Category.SCROLL), pos+ofs3 ).sprite.drop( pos );

        int ofs4;
        do {
            ofs4 = PathFinder.NEIGHBOURS8[Random.Int(8)];
        } while (!Dungeon.level.passable[pos + ofs4]);
        Dungeon.level.drop(Generator.random(Generator.Category.POTION), pos+ofs4 ).sprite.drop( pos );

        Badges.validatepursuerkill();
    }

    private static final String SKILL2TIME   = "BurstTime";
    private static final String SKILL2TPOS   = "Burstpos";
    private static final String SKILL2TCD   = "BurstCoolDown";
    private static final String SKILL3TCD   = "GasCoolDown";


    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( SKILL2TIME, BurstTime );
        bundle.put( SKILL2TCD, BurstCoolDown );
        bundle.put( SKILL2TPOS, Burstpos );
        bundle.put( SKILL3TCD, GasCoolDown );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        BurstTime = bundle.getInt(SKILL2TIME);
        BurstCoolDown = bundle.getInt(SKILL2TCD);
        Burstpos = bundle.getInt(SKILL2TPOS);
        GasCoolDown = bundle.getInt(SKILL3TCD);

    }
}
