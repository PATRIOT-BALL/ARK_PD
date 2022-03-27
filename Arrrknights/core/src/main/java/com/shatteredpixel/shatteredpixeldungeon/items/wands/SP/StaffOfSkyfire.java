package com.shatteredpixel.shatteredpixeldungeon.items.wands.SP;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.effects.Effects;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAmplified;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Blazing;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Elastic;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Door;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.TenguDartTrap;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class StaffOfSkyfire extends DamageWand {
    private static ItemSprite.Glowing COL = new ItemSprite.Glowing(0xFF1493);

    {
        image = ItemSpriteSheet.WAND_FIREBOLT;

        collisionProperties = Ballistica.PROJECTILE;
    }

    //1x/2x/3x damage
    public int min(int lvl) {
        return (3 + lvl);
    }

    //1x/2x/3x damage
    public int max(int lvl) {
        return (8 + 4 * lvl+ RingOfAmplified.DamageBonus(Dungeon.hero) * 4);
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return COL;
    }

    @Override
    protected void onZap(Ballistica bolt) {
        Sample.INSTANCE.play(Assets.Sounds.BLAST);
        StaffOfSkyfire.BlastWave.blast(bolt.collisionPos);


        //throws other chars around the center.
        for (int i : PathFinder.NEIGHBOURS9) {
            if (!Dungeon.level.solid[bolt.collisionPos+i]) {
            GameScene.add(Blob.seed(bolt.collisionPos+i, 1+this.level(), Fire.class)); }
            Char ch = Actor.findChar(bolt.collisionPos + i);

            if (ch != null) {
                processSoulMark(ch, this.level());
                if (ch.alignment != Char.Alignment.ALLY) ch.damage(damageRoll(), this);

                if (ch.isAlive() && ch.pos == bolt.collisionPos + i) {
                    Buff.affect(ch, Burning.class);
                    if (this.level() > Random.Int(0, 22)) {
                        Buff.affect(ch, Paralysis.class);
                    }
                } else if (ch == Dungeon.hero) {
                    Dungeon.fail(getClass());
                    GLog.n(Messages.get(this, "ondeath"));
                }
            }
        }

        //throws the char at the center of the blast
        Char ch = Actor.findChar(bolt.collisionPos);
        if (ch != null) {
            processSoulMark(ch, this.level());
            ch.damage(damageRoll(), this);

            if (ch.isAlive() && bolt.path.size() > bolt.dist + 1 && ch.pos == bolt.collisionPos) {
                Buff.affect(ch, Burning.class);
            }
        }

    }

    public static void throwChar(final Char ch, final Ballistica trajectory, int power) {
        throwChar(ch, trajectory, power, true);
    }

    public static void throwChar(final Char ch, final Ballistica trajectory, int power,
                                 boolean closeDoors) {
        throwChar(ch, trajectory, power, closeDoors, true);
    }

    public static void throwChar(final Char ch, final Ballistica trajectory, int power,
                                 boolean closeDoors, boolean collideDmg) {
        if (ch.properties().contains(Char.Property.BOSS)) {
            power /= 2;
        }

        int dist = Math.min(trajectory.dist, power);

        boolean collided = dist == trajectory.dist;

        if (dist == 0 || ch.properties().contains(Char.Property.IMMOVABLE) || ch.properties().contains(Char.Property.NO_KNOCKBACK)) return;

        //large characters cannot be moved into non-open space
        if (Char.hasProp(ch, Char.Property.LARGE)) {
            for (int i = 1; i <= dist; i++) {
                if (!Dungeon.level.openSpace[trajectory.path.get(i)]) {
                    dist = i - 1;
                    collided = true;
                    break;
                }
            }
        }

        if (Actor.findChar(trajectory.path.get(dist)) != null) {
            dist--;
            collided = true;
        }

        if (dist < 0) return;

        final int newPos = trajectory.path.get(dist);

        if (newPos == ch.pos) return;

        final int finalDist = dist;
        final boolean finalCollided = collided && collideDmg;
        final int initialpos = ch.pos;

        Actor.addDelayed(new Pushing(ch, ch.pos, newPos, new Callback() {
            public void call() {
                if (initialpos != ch.pos) {
                    //something caused movement before pushing resolved, cancel to be safe.
                    ch.sprite.place(ch.pos);
                    return;
                }
                int oldPos = ch.pos;
                ch.pos = newPos;
                if (finalCollided && ch.isAlive()) {
                    ch.damage(Random.NormalIntRange(finalDist, 2 * finalDist), this);
                    Paralysis.prolong(ch, Paralysis.class, 1 + finalDist / 2f);
                }
                if (closeDoors && Dungeon.level.map[oldPos] == Terrain.OPEN_DOOR) {
                    GameScene.add(Blob.seed(oldPos, 1, Fire.class));
                }
                Dungeon.level.occupyCell(ch);
                if (ch == Dungeon.hero) {
                    //FIXME currently no logic here if the throw effect kills the hero
                    Dungeon.observe();
                }
            }
        }), -1);
    }

    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        //acts like blazing enchantment
        new Blazing().proc(staff, attacker, defender, damage);
    }

    @Override
    protected void fx(Ballistica bolt, Callback callback) {
        MagicMissile.boltFromChar(curUser.sprite.parent,
                MagicMissile.FORCE,
                curUser.sprite,
                bolt.collisionPos,
                callback);
        Sample.INSTANCE.play(Assets.Sounds.ZAP);
    }

    public static class BlastWave extends Image {

        private static final float TIME_TO_FADE = 0.2f;

        private float time;

        public BlastWave() {
            super(Effects.get(Effects.Type.RIPPLE));
            origin.set(width / 2, height / 2);
        }

        public void reset(int pos) {
            revive();

            x = (pos % Dungeon.level.width()) * DungeonTilemap.SIZE + (DungeonTilemap.SIZE - width) / 2;
            y = (pos / Dungeon.level.width()) * DungeonTilemap.SIZE + (DungeonTilemap.SIZE - height) / 2;

            time = TIME_TO_FADE;
        }

        @Override
        public void update() {
            super.update();

            if ((time -= Game.elapsed) <= 0) {
                kill();
            } else {
                float p = time / TIME_TO_FADE;
                alpha(p);
                scale.y = scale.x = (1 - p) * 3;
            }
        }

        public static void blast(int pos) {
            Group parent = Dungeon.hero.sprite.parent;
            StaffOfSkyfire.BlastWave b = (StaffOfSkyfire.BlastWave) parent.recycle(StaffOfSkyfire.BlastWave.class);
            parent.bringToFront(b);
            b.reset(pos);
        }

    }

}
