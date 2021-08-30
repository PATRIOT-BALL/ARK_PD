package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Necromancer;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.ACE_BATTLE;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShaftParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndUseItem;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class WalkieTalkie extends Item {
    public static final String AC_USE = "USE";

    {
        image = ItemSpriteSheet.TORCH;

        stackable = true;

        defaultAction = AC_USE;
    }

    private ACE_BATTLE ACE = null;
    private int ACEID = 0;
    private boolean isused = false; // true면 사용 불가

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if (Dungeon.depth == 25 && Dungeon.bossLevel() && Dungeon.level.locked) actions.add(AC_USE);

        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (ACEID != -1){
            Actor ch = Actor.findById(ACEID);
            if (ch instanceof ACE_BATTLE){
                ACE = (ACE_BATTLE) ch;
            }
        }
        if (ACE != null &&
                (!ACE.isAlive()
                        || !Dungeon.level.mobs.contains(ACE))){
            ACE = null;
            ACEID = -1;
        }

        if (Dungeon.depth == 25 && Dungeon.bossLevel() && Dungeon.level.locked) {
        if (!isused || ACEID != -1) {
        if (action.equals(AC_USE) && ACE == null) {

            ArrayList<Integer> spawnPoints = new ArrayList<>();
            for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                int p = hero.pos + PathFinder.NEIGHBOURS8[i];
                if (Actor.findChar(p) == null && (Dungeon.level.passable[p] || Dungeon.level.avoid[p])) {
                    spawnPoints.add(p);
                }
            }

            if (spawnPoints.size() > 0) {
                ACE = new ACE_BATTLE();
                ACEID = ACE.id();
                ACE.pos = Random.element(spawnPoints);

                GameScene.add(ACE, 1f);
                Dungeon.level.occupyCell(ACE);

                CellEmitter.get(ACE.pos).start(Speck.factory(Speck.ROCK), 0.3f, 4);
                CellEmitter.get(ACE.pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
                Camera.main.shake(3, 1.5f);
                GameScene.flash( 0x80FFF0F0 );
                Sample.INSTANCE.play(Assets.Sounds.ROCKS);

                ACE.yell(Messages.get(ACE, "summon"));
                isused = true;

                hero.spend(1f);
                hero.busy();
                hero.sprite.operate(hero.pos);

                Talent.onArtifactUsed(hero);
            }

        } else if (ACE != null) GameScene.selectCell(ACEDirector);
        }}
    }

    public CellSelector.Listener ACEDirector = new CellSelector.Listener(){

        @Override
        public void onSelect(Integer cell) {
            if (cell == null) return;

            Sample.INSTANCE.play( Assets.Sounds.GHOST );

            if (cell == null) return;

            Sample.INSTANCE.play( Assets.Sounds.GHOST );

            if (!Dungeon.level.heroFOV[cell]
                    || Actor.findChar(cell) == null
                    || (Actor.findChar(cell) != Dungeon.hero && Actor.findChar(cell).alignment != Char.Alignment.ENEMY)){
                return;
            }

            if (ACE.fieldOfView == null || ACE.fieldOfView.length != Dungeon.level.length()){
                ACE.fieldOfView = new boolean[Dungeon.level.length()];
            }
            Dungeon.level.updateFieldOfView( ACE, ACE.fieldOfView );

            if (Actor.findChar(cell) == Dungeon.hero){
                ArrayList<Integer> spawnPoints = new ArrayList<>();
                for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                    int p = Dungeon.hero.pos + PathFinder.NEIGHBOURS8[i];
                    if (Actor.findChar(p) == null && (Dungeon.level.passable[p] || Dungeon.level.avoid[p])) {
                        spawnPoints.add(p);
                    }
                }

                if (spawnPoints.size() > 0) {
                    int movepos = Random.element(spawnPoints);
                    ACE.sprite.move(ACE.pos, movepos);
                    ACE.pos = movepos;
                    ACE.state = ACE.WANDERING;
                    ACE.yell(Messages.get(ACE, "move"));
                    CellEmitter.get(ACE.pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
                }

            } else if (Actor.findChar(cell).alignment == Char.Alignment.ENEMY){
                ACE.yell(Messages.get(ACE, "target"));
                ACE.aggro(Actor.findChar(cell));
                ACE.setTarget(cell);
                ACE.state= ACE.HUNTING;
            }
        }

        @Override
        public String prompt() {
            return  "...";
        }
    };

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public String desc() {
        if (ACEID != -1){
            Actor ch = Actor.findById(ACEID);
            if (ch instanceof ACE_BATTLE){
                ACE = (ACE_BATTLE) ch;
            }
        }
        if (ACE != null &&
                (!ACE.isAlive()
                        || !Dungeon.level.mobs.contains(ACE))){
            ACE = null;
            ACEID = -1;
        }

        if (isused && ACEID == -1) return Messages.get(this, "desc_ace_die");
        if (ACE != null) return Messages.get(this, "desc_ace_alive");
        if (Dungeon.depth == 25 && Dungeon.bossLevel() && Dungeon.level.locked) return Messages.get(this, "desc_active_ok");
        return Messages.get(this, "desc_no");
    }

    private static final String MYACE = "ACE";
    private static final String FIRSTSUMMON =   "firstsummon";
    private static final String ID =       "ACEID";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);

        bundle.put( FIRSTSUMMON, isused );
        bundle.put( ID, ACEID );

    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);

        isused = bundle.getBoolean( FIRSTSUMMON );
        ACEID = bundle.getInt( ID );
    }

}
