package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.miniboss;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Silence;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Amulet;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.SurfaceScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GreenCatSprite;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Kaltsit extends Mob {
    {
        spriteClass = GreenCatSprite.class;

        HP = HT = 900;
        defenseSkill = 20;

        EXP = 0;
        maxLvl = 45;

        state = HUNTING;

        properties.add(Property.BOSS);
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
    public void die(Object cause) {
        Badges.silentValidateFragging();
        Dungeon.win(Amulet.class);
        Dungeon.deleteGame(GamesInProgress.curSlot, true);
        Game.switchScene(SurfaceScene.class);
        super.die(cause);
    }

    @Override
    protected boolean canAttack(Char enemy) {
        if (this.fieldOfView[enemy.pos]){ return true; }
        return false;
    }

    @Override
    protected boolean act() {
        if (firstSummon == true) {
            if (SummonTurn == 0) {
                this.yell(Messages.get(this, "summon"));
                SummontPos = Dungeon.hero.pos;
                CellEmitter.get( SummontPos ).burst( ShadowParticle.UP, 10 );
                CellEmitter.get( SummontPos ).burst( ShadowParticle.UP, 7 );
                CellEmitter.get( SummontPos ).burst( ShadowParticle.CURSE, 4 );
                SummonTurn++;}
            else if (SummonTurn > 0) {
                Sample.INSTANCE.play( Assets.Sounds.CURSED );
                CellEmitter.get( SummontPos ).burst( ShadowParticle.CURSE, 10 );

                if (SummontPos == Dungeon.hero.pos) {
                    Item item = Dungeon.hero.belongings.getItem(Ankh.class);
                    if (item != null) item.detachAll(Dungeon.hero.belongings.backpack);
                    while (Dungeon.hero.isAlive() == true) {
                        Dungeon.hero.damage(Random.NormalIntRange( 87, 154 ), new Kaltsit());
                        Dungeon.hero.sprite.burst(CharSprite.NEGATIVE, 10);
                    }
                    Camera.main.shake(3, 0.5f);
                }
                else {
                Mon3tr ter = new Mon3tr();
                ter.pos = SummontPos;
                GameScene.add(ter);}

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
