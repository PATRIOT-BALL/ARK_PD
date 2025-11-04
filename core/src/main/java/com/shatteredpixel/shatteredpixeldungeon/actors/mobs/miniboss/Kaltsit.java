package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss;

import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PurpleParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.NewGameItem.Certificate;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.SurfaceScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GreenCatSprite;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Kaltsit extends Mob {
    {
        spriteClass = GreenCatSprite.class;

        HP = HT = 1100;
        defenseSkill = 20;

        EXP = 0;
        maxLvl = 45;

        state = HUNTING;

        properties.add(Property.BOSS);
        properties.add(Property.LARGE);
        immunities.add(Charm.class);
        immunities.add(Silence.class);
        immunities.add(Amok.class);
        immunities.add(Terror.class);
    }
    private boolean firstSummon = true;
    private int SummontPos = 0;
    private int SummonTurn = 0; // 1이 되면 소환
    private int Burstcooldown = 0; // 1이 되면 은신 파괴

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 16, 24 );
    }

    @Override
    public int attackSkill(Char target) {
        return 30;
    }

    @Override
    protected boolean canAttack(Char enemy) {
        if (this.fieldOfView[enemy.pos]){ return true; }
        return false;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    protected boolean act() {
        if (HP < 1) {
            Certificate.specialEndingBouns();
            Dungeon.killcat = true;
            Badges.silentValidateFragging();
            Dungeon.win(Amulet.class);
            Dungeon.deleteGame(GamesInProgress.curSlot, true);
            Game.switchScene(SurfaceScene.class);
        }

        if (firstSummon == true) {
            if (SummonTurn == 0) {
                this.yell(Messages.get(this, "summon"));
                SummontPos = Dungeon.hero.pos;
                CellEmitter.get( SummontPos ).burst( ShadowParticle.UP, 10 );
                CellEmitter.get( SummontPos ).burst( ShadowParticle.UP, 7 );
                CellEmitter.get( SummontPos ).burst( ShadowParticle.CURSE, 4 );
                sprite.parent.addToBack(new TargetedCell(SummontPos, 0xFF0000));
                SummonTurn++;}
            else if (SummonTurn > 0) {
                Sample.INSTANCE.play(Assets.Sounds.SKILL_MON2);
                CellEmitter.get(SummontPos).burst(ShadowParticle.CURSE, 10);

                if (SummontPos == Dungeon.hero.pos) {
                    Dungeon.hero.damage(Dungeon.hero.HP/2, this);
                    Dungeon.hero.sprite.burst(CharSprite.NEGATIVE, 10);

                    Camera.main.shake(3, 0.5f);
                }
                Mon3tr ter = new Mon3tr();
                ter.pos = 537;
                GameScene.add(ter);

                GameScene.flash(0x80FF0000);
                ScrollOfTeleportation.appear(Dungeon.hero, 587);
                Sample.INSTANCE.play(Assets.Sounds.SKILL_MON1);

                ter.beckon( Dungeon.hero.pos );

                firstSummon = false;
            }


        }

        if (Dungeon.hero.buff(Invisibility.class) != null) {
            if (Burstcooldown == 0) Burstcooldown++;
            else {
                Burstcooldown = 0;
                this.yell(Messages.get(this, "burst"));
                Buff.detach(Dungeon.hero, Invisibility.class);
            }
        }
        else Burstcooldown = 0;
        return super.act();
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if (enemy instanceof Hero) {
            int bounsdmg = 23 - Dungeon.hero.lvl;
            bounsdmg = Math.max(bounsdmg, 0);

            if (bounsdmg > 0) {
                CellEmitter.get(enemy.pos).burst(PurpleParticle.BURST, bounsdmg);
                enemy.sprite.showStatus(CharSprite.NEGATIVE, Integer.toString(bounsdmg));
                enemy.HP -= bounsdmg;
            }
            if (enemy.HP <= 0) {
                enemy.die(this);
            }
        }
        return super.attackProc(enemy, damage);
    }

    private static final String FIRST_SUMMON = "first_summon";
    private static final String TURN = "SummonTurn";
    private static final String POS = "SummontPos";
    private static final String BURST = "Burstcooldown";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put( FIRST_SUMMON, firstSummon );
        bundle.put( TURN, SummonTurn );
        bundle.put( POS, SummontPos);
        bundle.put( BURST, Burstcooldown);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        if (bundle.contains(FIRST_SUMMON)) firstSummon = bundle.getBoolean(FIRST_SUMMON);
        SummonTurn = bundle.getInt(TURN);
        SummontPos = bundle.getInt(POS);
        Burstcooldown = bundle.getInt(BURST);

    }
    }
