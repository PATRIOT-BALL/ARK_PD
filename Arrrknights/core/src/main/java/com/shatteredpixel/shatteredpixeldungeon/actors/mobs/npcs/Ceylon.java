package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Agent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Ergate;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Infantry;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.ArmorUpKit;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Bottle;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.TeaRose;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.levels.SiestaLevel_part1;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CannotSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CeylonSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndCeylon;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndQuest;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Ceylon extends NPC {
    {
        spriteClass = CeylonSprite.class;

        properties.add(Property.IMMOVABLE);
    }

    private boolean seenBefore = false;

    @Override
    protected boolean act() {

        if (!Ceylon.Quest.given && Dungeon.level.heroFOV[pos]) {
            if (!seenBefore) {
                yell( Messages.get(this, "hey" ) );
            }
            Notes.add( Notes.Landmark.CEYLON );
            seenBefore = true;
        } else {
            seenBefore = false;
        }

        return super.act();
    }

    @Override
    public int defenseSkill( Char enemy ) {
        return INFINITE_EVASION;
    }

    @Override
    public void damage( int dmg, Object src ) {
    }

    @Override
    public void add( Buff buff ) {
    }

    @Override
    public boolean reset() {
        return true;
    }

    @Override
    public boolean interact(Char c) {

        sprite.turnTo( pos, Dungeon.hero.pos );

        if (c != Dungeon.hero){
            return true;
        }

        if (Ceylon.Quest.given) {

            TeaRose tokens = Dungeon.hero.belongings.getItem( TeaRose.class );
            Bottle bottle = Dungeon.hero.belongings.getItem( Bottle.class );
            if (tokens != null && tokens.quantity() >= 8 &&  bottle != null) {
                Game.runOnRenderThread(new Callback() {
                    @Override
                    public void call() {
                        GameScene.show( new WndCeylon( Ceylon.this, tokens, bottle ) );
                    }
                });
            } else {
                tell(Messages.get(this, "water", Dungeon.hero.heroClass.title()));
            }

        } else {
            tell( Messages.get(this, "quest"));
            Ceylon.Quest.given = true;
            Ceylon.Quest.completed = false;
            Notes.add( Notes.Landmark.CEYLON );
        }

        return true;
    }

    private void tell( String text ) {
        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {
                GameScene.show( new WndQuest( Ceylon.this, text ));
            }
        });
    }

    public void flee() {

        yell( Messages.get(this, "cya", Dungeon.hero.heroClass.title()) );

        destroy();
        sprite.die();
    }

    public static class Quest {

        private static boolean alternative;

        private static boolean spawned;
        private static boolean given;
        private static boolean completed;

        public static ArmorUpKit reward;

        public static void reset() {
            spawned = false;

            reward = null;
        }

        private static final String NODE		= "CEYLON";

        private static final String ALTERNATIVE	= "alternative";
        private static final String SPAWNED		= "spawned";
        private static final String GIVEN		= "given";
        private static final String COMPLETED	= "completed";
        private static final String REWARD		= "reward";

        public static void storeInBundle( Bundle bundle ) {

            Bundle node = new Bundle();

            node.put( SPAWNED, spawned );

            if (spawned) {
                node.put( ALTERNATIVE, alternative );

                node.put( GIVEN, given );
                node.put( COMPLETED, completed );
            }

            bundle.put( NODE, node );
        }

        public static void restoreFromBundle( Bundle bundle ) {

            Bundle node = bundle.getBundle( NODE );

            if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {
                alternative	= node.getBoolean( ALTERNATIVE );

                given = node.getBoolean( GIVEN );
                completed = node.getBoolean( COMPLETED );
                reward = new ArmorUpKit();
            }
        }

        public static void spawn( SiestaLevel_part1 level ) {
            if (!spawned && Dungeon.depth > 31 && Random.Int( 35 - Dungeon.depth ) == 0) {

                Ceylon npc = new Ceylon();
                do {
                    npc.pos = level.randomRespawnCell( npc );
                } while (
                        npc.pos == -1 ||
                                level.heaps.get( npc.pos ) != null ||
                                level.traps.get( npc.pos) != null ||
                                level.findMob( npc.pos ) != null ||
                                //The imp doesn't move, so he cannot obstruct a passageway
                                !(level.passable[npc.pos + PathFinder.CIRCLE4[0]] && level.passable[npc.pos + PathFinder.CIRCLE4[2]]) ||
                                !(level.passable[npc.pos + PathFinder.CIRCLE4[1]] && level.passable[npc.pos + PathFinder.CIRCLE4[3]]));
                level.mobs.add( npc );

                spawned = true;
                level.addItemToSpawn(new Bottle());

                switch (Dungeon.depth){
                    case 32: default:
                        alternative = true;
                        break;
                    case 33:
                        alternative = true;
                        break;
                    case 34:
                        alternative = true;
                        break;
                }

                given = false;

                reward = new ArmorUpKit();
            }
        }

        public static void process( Mob mob ) {
            if (spawned && given && !completed) {
                if ((alternative && mob instanceof Infantry) ||
                        (alternative && mob instanceof Ergate) ||
                        (alternative && mob instanceof Agent)){

                    Dungeon.level.drop( new TeaRose(), mob.pos ).sprite.drop();
                }
            }
        }

        public static void complete() {
            reward = null;
            completed = true;

            Notes.remove( Notes.Landmark.CEYLON );
        }

        public static boolean isCompleted() {
            return completed;
        }
    }
}
