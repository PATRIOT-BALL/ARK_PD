package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.ACE_BATTLE;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.KNIGHT_NPC;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class KNIGHT_NPC_SUMMON extends Item {
    public static final String AC_USE = "USE";

    {
        image = ItemSpriteSheet.WALKIE;

        stackable = true;

        defaultAction = AC_USE;
    }
    
    // 추가해야할 기능 : 이 아이템을 사용하면 <기붕이 퀘스트>가 활성화되며, 40층에 보스가 추가된다

    private KNIGHT_NPC KNIGHT = null;
    private int KNIGHTID = 0;
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

        if (KNIGHTID != -1){
            Actor ch = Actor.findById(KNIGHTID);
            if (ch instanceof ACE_BATTLE){
                KNIGHT = (KNIGHT_NPC) ch;
            }
        }
        if (KNIGHT != null &&
                (!KNIGHT.isAlive()
                        || !Dungeon.level.mobs.contains(KNIGHT))){
            KNIGHT = null;
            KNIGHTID = -1;
        }

        if (Dungeon.depth == 25 && Dungeon.bossLevel() && Dungeon.level.locked) {
        if (!isused || KNIGHTID != -1) {
        if (action.equals(AC_USE) && KNIGHT == null) {

            ArrayList<Integer> spawnPoints = new ArrayList<>();
            for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                int p = hero.pos + PathFinder.NEIGHBOURS8[i];
                if (Actor.findChar(p) == null && (Dungeon.level.passable[p] || Dungeon.level.avoid[p])) {
                    spawnPoints.add(p);
                }
            }

            if (spawnPoints.size() > 0) {
                KNIGHT = new KNIGHT_NPC();
                KNIGHTID = KNIGHT.id();
                KNIGHT.pos = Random.element(spawnPoints);

                GameScene.add(KNIGHT, 1f);
                Dungeon.level.occupyCell(KNIGHT);

                CellEmitter.get(KNIGHT.pos).start(Speck.factory(Speck.ROCK), 0.3f, 4);
                CellEmitter.get(KNIGHT.pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
                Camera.main.shake(3, 1.5f);
                GameScene.flash( 0x80FFF0F0 );
                Sample.INSTANCE.play(Assets.Sounds.ROCKS);

                KNIGHT.yell(Messages.get(KNIGHT, "summon"));
                isused = true;

                hero.spend(1f);
                hero.busy();
                hero.sprite.operate(hero.pos);

                Talent.onArtifactUsed(hero);
            }

        } else if (KNIGHT != null) GameScene.selectCell(ACEDirector);
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

            if (KNIGHT.fieldOfView == null || KNIGHT.fieldOfView.length != Dungeon.level.length()){
                KNIGHT.fieldOfView = new boolean[Dungeon.level.length()];
            }
            Dungeon.level.updateFieldOfView(KNIGHT, KNIGHT.fieldOfView );

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
                    KNIGHT.sprite.move(KNIGHT.pos, movepos);
                    KNIGHT.pos = movepos;
                    KNIGHT.state = KNIGHT.WANDERING;
                    KNIGHT.yell(Messages.get(KNIGHT, "move"));
                    CellEmitter.get(KNIGHT.pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
                }

            } else if (Actor.findChar(cell).alignment == Char.Alignment.ENEMY){
                KNIGHT.yell(Messages.get(KNIGHT, "target"));
                KNIGHT.aggro(Actor.findChar(cell));
                KNIGHT.setTarget(cell);
                KNIGHT.state= KNIGHT.HUNTING;
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
        if (KNIGHTID != -1){
            Actor ch = Actor.findById(KNIGHTID);
            if (ch instanceof KNIGHT_NPC){
                KNIGHT = (KNIGHT_NPC) ch;
            }
        }
        if (KNIGHT != null &&
                (!KNIGHT.isAlive()
                        || !Dungeon.level.mobs.contains(KNIGHT))){
            KNIGHT = null;
            KNIGHTID = -1;
        }

        if (isused && KNIGHTID == -1) return Messages.get(this, "desc_ace_die");
        if (KNIGHT != null) return Messages.get(this, "desc_ace_alive");
        if (Dungeon.depth == 25 && Dungeon.bossLevel() && Dungeon.level.locked) return Messages.get(this, "desc_active_ok");
        return Messages.get(this, "desc_no");
    }

    private static final String MYACE = "KNIGHT";
    private static final String FIRSTSUMMON =   "firstsummon";
    private static final String ID =       "KNIGHTID";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);

        bundle.put( FIRSTSUMMON, isused );
        bundle.put( ID, KNIGHTID);

    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);

        isused = bundle.getBoolean( FIRSTSUMMON );
        KNIGHTID = bundle.getInt( ID );
    }

}
