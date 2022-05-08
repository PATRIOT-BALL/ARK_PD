package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SkillBook;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.SealOfLight;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Firmament;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.GunWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShadowFirmament;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndChenCombo;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndCombo;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndKnightSkill;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class ChenCombo extends Buff implements ActionIndicator.Action {
    private int count = 0;
    private float comboTime = 0f;
    private float initialComboTime = 5f;

    @Override
    public int icon() {
        return BuffIndicator.COMBO;
    }

    @Override
    public void tintIcon(Image icon) {
        Combo.ComboMove move = getHighestMove();
        if (move != null){
            icon.hardlight(move.tintColor & 0x00FFFFFF);
        } else {
            icon.resetColor();
        }
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (initialComboTime - comboTime)/ initialComboTime);
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    public void hit( Char enemy ) {

        count++;
        if (Dungeon.hero.hasTalent(Talent.DEADLY_REACH)) {
            comboTime = 5f + Dungeon.hero.pointsInTalent(Talent.DEADLY_REACH);
        }
        else {
            comboTime = 5f;
        }

        if (!enemy.isAlive() || (enemy.buff(Corruption.class) != null && enemy.HP == enemy.HT)){
            comboTime = Math.max(comboTime, 15*((Hero)target).pointsInTalent(Talent.CLEAVE));
        }

        initialComboTime = comboTime;

        if ((getHighestMove() != null)) {

            ActionIndicator.setAction( this );
            Badges.validateMasteryCombo( count );

            GLog.p( Messages.get(this, "combo", count) );

        }

        BuffIndicator.refreshHero(); //refresh the buff visually on-hit
    }

    public void bounshit( Char enemy ) {

        count++;
        comboTime = 5f;

        if (!enemy.isAlive() || (enemy.buff(Corruption.class) != null && enemy.HP == enemy.HT)){
            comboTime = Math.max(comboTime, 15*((Hero)target).pointsInTalent(Talent.CLEAVE));
        }

        initialComboTime = comboTime;

        if ((getHighestMove() != null)) {

            ActionIndicator.setAction( this );
            Badges.validateMasteryCombo( count );

        }

        BuffIndicator.refreshHero(); //refresh the buff visually on-hit
    }

    @Override
    public void detach() {
        super.detach();
        ActionIndicator.clearAction(this);
    }

    @Override
    public boolean act() {
        comboTime-=TICK;
        spend(TICK);
        if (comboTime <= 0) {
            detach();
        }
        return true;
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc");
    }

    private static final String COUNT = "count";
    private static final String TIME  = "combotime";
    private static final String INITIAL_TIME  = "initialComboTime";


    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(COUNT, count);
        bundle.put(TIME, comboTime);
        bundle.put(INITIAL_TIME, initialComboTime);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        count = bundle.getInt( COUNT );
        comboTime = bundle.getFloat( TIME );

        //pre-0.9.2
        if (bundle.contains(INITIAL_TIME))  initialComboTime = bundle.getFloat( INITIAL_TIME );
        else                                initialComboTime = 5;

        if (getHighestMove() != null) ActionIndicator.setAction(this);
    }

    @Override
    public Image getIcon() {
        Image icon;
        if (((Hero)target).belongings.weapon != null){
            icon = new ItemSprite(((Hero)target).belongings.weapon.image, null);
        } else {
            icon = new ItemSprite(new Item(){ {image = ItemSpriteSheet.WEAPON_HOLDER; }});
        }

        icon.tint(getHighestMove().tintColor);
        return icon;
    }

    @Override
    public void doAction() {
        GameScene.show(new WndChenCombo(this));
    }

    public enum ComboMove {
        CLOBBER(2, 0xFF00FF00),
        SLAM   (4, 0xFFCCFF00),
        PARRY  (6, 0xFFFFFF00),
        CRUSH  (8, 0xFFFFCC00),
        FURY   (10, 0xFFFF0000);

        public int comboReq, tintColor;

        ComboMove(int comboReq, int tintColor){
            this.comboReq = comboReq;
            this.tintColor = tintColor;
        }

        public String desc(){
            if (Dungeon.hero.belongings.weapon instanceof GunWeapon) {
                if (name() == "FURY" || name() == "CRUSH" || name() == "SLAM") return Messages.get(this, name()+"_desc2");
            }
            return Messages.get(this, name()+"_desc");
        }

    }

    public Combo.ComboMove getHighestMove(){
        Combo.ComboMove best = null;
        for (Combo.ComboMove move : Combo.ComboMove.values()){
            if (count >= move.comboReq){
                best = move;
            }
        }
        return best;
    }

    public int getComboCount(){
        return count;
    }

    public boolean canUseMove(Combo.ComboMove move){
        return move.comboReq <= count;
    }

    public void useMove(Combo.ComboMove move){
        if (move == Combo.ComboMove.PARRY){
            comboTime = 5f;
            Buff.affect(target, Combo.ParryTracker.class, Actor.TICK);
            ((Hero)target).spendAndNext(Actor.TICK);
            Dungeon.hero.busy();
        } else {
            moveBeingUsed = move;
            GameScene.selectCell(listener);
        }
    }

    public static class ParryTracker extends FlavourBuff{
        { actPriority = HERO_PRIO+1;}

        public boolean parried;

        @Override
        public void detach() {
            if (!parried && target.buff(ChenCombo.class) != null) target.buff(ChenCombo.class).detach();
            super.detach();
        }
    }

    private static Combo.ComboMove moveBeingUsed;

    private void doAttack(final Char enemy){

        AttackIndicator.target(enemy);

        boolean wasAlly = enemy.alignment == target.alignment;
        Hero hero = (Hero)target;

        if (enemy.defenseSkill(target) >= Char.INFINITE_EVASION){
            enemy.sprite.showStatus( CharSprite.NEUTRAL, enemy.defenseVerb() );
            Sample.INSTANCE.play(Assets.Sounds.MISS);

        } else if (enemy.isInvulnerable(target.getClass())){
            enemy.sprite.showStatus( CharSprite.POSITIVE, Messages.get(Char.class, "invulnerable") );
            Sample.INSTANCE.play(Assets.Sounds.MISS);

        } else {

            int dmg = target.damageRoll();

            //variance in damage dealt
            switch (moveBeingUsed) {
                case CLOBBER:
                    dmg = 0;
                    break;
                case SLAM:
                    if (Dungeon.hero.belongings.weapon instanceof GunWeapon) {
                        dmg = Random.IntRange(((GunWeapon) Dungeon.hero.belongings.weapon).shotmin(), ((GunWeapon) Dungeon.hero.belongings.weapon).shotmax());
                        dmg = Math.round(dmg * 0.2f * count);
                    }
                    else dmg += Math.round(target.drRoll() * count / 5f);
                    break;
                case CRUSH:
                    if (Dungeon.hero.belongings.weapon instanceof GunWeapon) {
                        dmg *= 1.25f;
                    }
                    else dmg = Math.round(dmg * 0.25f * count);
                    break;
                case FURY:
                    if (Dungeon.hero.belongings.weapon instanceof GunWeapon) {
                        dmg = Random.IntRange(((GunWeapon) Dungeon.hero.belongings.weapon).shotmin(), ((GunWeapon) Dungeon.hero.belongings.weapon).shotmax());
                        dmg = Math.round(dmg * 0.4f * count);
                    } else dmg = Math.round(dmg * 0.6f);
                    break;
            }

            dmg = enemy.defenseProc(target, dmg);
            dmg -= enemy.drRoll();

            if (enemy.buff(Vulnerable.class) != null) {
                dmg *= 1.33f;
            }

            dmg = target.attackProc(enemy, dmg);
            enemy.damage(dmg, target);

            //special effects
            switch (moveBeingUsed) {
                case CLOBBER:
                    hit(enemy);
                    if (enemy.isAlive()) {
                        //trace a ballistica to our target (which will also extend past them
                        Ballistica trajectory = new Ballistica(target.pos, enemy.pos, Ballistica.STOP_TARGET);
                        //trim it to just be the part that goes past them
                        trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
                        //knock them back along that ballistica, ensuring they don't fall into a pit
                        int dist = 2;
                        if (count >= 6 && hero.pointsInTalent(Talent.ENHANCED_COMBO) >= 1) {
                            dist++;
                            Buff.prolong(enemy, Vertigo.class, 3);
                        } else if (!enemy.flying) {
                            while (dist > trajectory.dist ||
                                    (dist > 0 && Dungeon.level.pit[trajectory.path.get(dist)])) {
                                dist--;
                            }
                        }
                        WandOfBlastWave.throwChar(enemy, trajectory, dist, true, false);
                    }
                    break;
                case SLAM:
                    if (Dungeon.hero.belongings.weapon instanceof GunWeapon) {
                        hit(enemy);
                    }
                    break;
                case PARRY:
                    hit(enemy);
                    break;
                case CRUSH:
                    if (Dungeon.hero.belongings.weapon instanceof GunWeapon) {
                        if (enemy.isAlive()) {
                            GameScene.flash( 0x80FFFFFF );
                            Sample.INSTANCE.play(Assets.Sounds.BLAST, 1.1f, 1.26f);
                            Buff.affect(enemy, Paralysis.class, 1f);
                            Buff.affect(enemy, Blindness.class, count*0.3f);
                            hit(enemy);
                        }
                    }
                    else {
                        WandOfBlastWave.BlastWave.blast(enemy.pos);
                        PathFinder.buildDistanceMap(target.pos, Dungeon.level.passable, 3);
                        for (Char ch : Actor.chars()) {
                            if (ch != enemy && ch.alignment == Char.Alignment.ENEMY
                                    && PathFinder.distance[ch.pos] < Integer.MAX_VALUE) {
                                int aoeHit = Math.round(target.damageRoll() * 0.25f * count);
                                aoeHit /= 2;
                                aoeHit -= ch.drRoll();
                                if (ch.buff(Vulnerable.class) != null) aoeHit *= 1.33f;
                                ch.damage(aoeHit, target);
                                ch.sprite.bloodBurstA(target.sprite.center(), dmg);
                                ch.sprite.flash();

                                if (!ch.isAlive()) {
                                    if (hero.hasTalent(Talent.FINALBLOW)) {
                                        if (hero.buff(BrokenSeal.WarriorShield.class) != null) {
                                            BrokenSeal.WarriorShield shield = hero.buff(BrokenSeal.WarriorShield.class);
                                            shield.supercharge(Math.round(shield.maxShield() * hero.pointsInTalent(Talent.FINALBLOW) / 3f));}

                                        if (hero.belongings.getItem(SkillBook.class) != null) {
                                            SkillBook book = Dungeon.hero.belongings.getItem(SkillBook.class);
                                            book.SetCharge(hero.pointsInTalent(Talent.FINALBLOW) * 2);
                                            SpellSprite.show(hero, SpellSprite.CHARGE);
                                            Item.updateQuickslot();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case FURY:
                    if (Dungeon.hero.belongings.weapon instanceof GunWeapon){
                        Camera.main.shake(2, 0.5f);
                        Sample.INSTANCE.play(Assets.Sounds.HIT_SHOTGUN, 1.87f, 1.26f);
                    }
                    break;
                default:
                    //nothing
                    break;
            }

            if (target.buff(FireImbue.class) != null)   target.buff(FireImbue.class).proc(enemy);
            if (target.buff(FrostImbue.class) != null)  target.buff(FrostImbue.class).proc(enemy);

            target.hitSound(Random.Float(0.87f, 1.15f));
            if (moveBeingUsed != Combo.ComboMove.FURY) Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
            enemy.sprite.bloodBurstA(target.sprite.center(), dmg);
            enemy.sprite.flash();

            if (!enemy.isAlive()) {
                GLog.i(Messages.capitalize(Messages.get(Char.class, "defeat", enemy.name())));
            }

        }

        Invisibility.dispel();

        //Post-attack behaviour
        switch(moveBeingUsed){
            case CLOBBER:
                if (getHighestMove() == null) ActionIndicator.clearAction(this);
                hero.spendAndNext(hero.attackDelay());
                break;

            case SLAM:
                if (Dungeon.hero.belongings.weapon instanceof GunWeapon) {
                    if (getHighestMove() == null) ActionIndicator.clearAction(this);
                    hero.spendAndNext(hero.attackDelay());
                } else {
                    detach();
                    Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
                    ActionIndicator.clearAction(this);
                    hero.spendAndNext(hero.attackDelay());
                }
                break;

            case PARRY:
                //do nothing
                break;

            case CRUSH:
                if (Dungeon.hero.belongings.weapon instanceof GunWeapon) {
                    ActionIndicator.clearAction(this);
                    hero.spendAndNext(hero.attackDelay());
                } else {
                    detach();
                    Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
                    ActionIndicator.clearAction(this);
                    hero.spendAndNext(hero.attackDelay());
                }
                break;

            case FURY:
                if (Dungeon.hero.belongings.weapon instanceof GunWeapon)
                {
                    detach();
                    ActionIndicator.clearAction(this);
                    hero.spendAndNext(hero.attackDelay());
                    break;
                }
                else {
                    if (Dungeon.hero.belongings.weapon instanceof Firmament || Dungeon.hero.belongings.weapon instanceof ShadowFirmament)
                        count--;
                    count--;
                    //fury attacks as many times as you have combo count
                    if (count > 0 && enemy.isAlive() && hero.canAttack(enemy) &&
                            (wasAlly || enemy.alignment != target.alignment)) {
                        target.sprite.attack(enemy.pos, new Callback() {
                            @Override
                            public void call() {
                                doAttack(enemy);
                            }
                        });
                    } else {
                        detach();
                        Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
                        ActionIndicator.clearAction(this);
                        hero.spendAndNext(hero.attackDelay());
                    }
                }
                break;

            default:
                detach();
                ActionIndicator.clearAction(this);
                hero.spendAndNext(hero.attackDelay());
                break;
        }

        if (!enemy.isAlive() || (!wasAlly && enemy.alignment == target.alignment)) {
            if (hero.hasTalent(Talent.FINALBLOW)) {
                if (hero.buff(BrokenSeal.WarriorShield.class) != null) {
                    BrokenSeal.WarriorShield shield = hero.buff(BrokenSeal.WarriorShield.class);
                    shield.supercharge(Math.round(shield.maxShield() * hero.pointsInTalent(Talent.FINALBLOW) / 3f));}

                if (hero.belongings.getItem(SkillBook.class) != null) {
                    SkillBook book = Dungeon.hero.belongings.getItem(SkillBook.class);
                    book.SetCharge(hero.pointsInTalent(Talent.FINALBLOW) * 2);
                    SpellSprite.show(hero, SpellSprite.CHARGE);
                    Item.updateQuickslot();
                }
            }
        }

    }

    private CellSelector.Listener listener = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer cell) {
            if (cell == null) return;
            final Char enemy = Actor.findChar( cell );
            if (enemy == null
                    || enemy == target
                    || !Dungeon.level.heroFOV[cell]
                    || target.isCharmedBy( enemy )) {
                GLog.w(Messages.get(Combo.class, "bad_target"));

            } else if (!((Hero)target).canAttack(enemy)){
                if (((Hero) target).pointsInTalent(Talent.ENHANCED_COMBO) < 3
                        || Dungeon.level.distance(target.pos, enemy.pos) > 1 + target.buff(ChenCombo.class).count/3){
                    GLog.w(Messages.get(Combo.class, "bad_target"));
                } else {
                    Ballistica c = new Ballistica(target.pos, enemy.pos, Ballistica.PROJECTILE);
                    if (c.collisionPos == enemy.pos){
                        final int leapPos = c.path.get(c.dist-1);
                        if (!Dungeon.level.passable[leapPos]){
                            GLog.w(Messages.get(Combo.class, "bad_target"));
                        } else {
                            Dungeon.hero.busy();
                            target.sprite.jump(target.pos, leapPos, new Callback() {
                                @Override
                                public void call() {
                                    target.move(leapPos);
                                    Dungeon.level.occupyCell(target);
                                    Dungeon.observe();
                                    GameScene.updateFog();
                                    target.sprite.attack(cell, new Callback() {
                                        @Override
                                        public void call() {
                                            doAttack(enemy);
                                        }
                                    });
                                }
                            });
                        }
                    } else {
                        GLog.w(Messages.get(Combo.class, "bad_target"));
                    }
                }

            } else {
                Dungeon.hero.busy();
                target.sprite.attack(cell, new Callback() {
                    @Override
                    public void call() {
                        doAttack(enemy);
                    }
                });
            }
        }

        @Override
        public String prompt() {
            return Messages.get(Combo.class, "prompt");
        }
    };
    }