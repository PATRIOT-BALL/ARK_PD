package com.shatteredpixel.shatteredpixeldungeon.items.wands.SP;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.RainbowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAmplified;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class StaffOfShining extends DamageWand {
    private static ItemSprite.Glowing COL = new ItemSprite.Glowing( 0xFFFFFF );
    {
        image = ItemSpriteSheet.WAND_PRISMATIC_LIGHT;

        collisionProperties = Ballistica.MAGIC_BOLT;
    }

    public int min(int lvl){
        return 1+lvl;
    }

    public int max(int lvl){
        return 3 +2*lvl+ RingOfAmplified.DamageBonus(Dungeon.hero) * 2;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return COL;
    }

    @Override
    protected void onZap(Ballistica beam) {
        affectMap(beam);

        Char ch = Actor.findChar(beam.collisionPos);
        if (ch != null){
            processSoulMark(ch, chargesPerCast());
            affectTarget(ch);
        }
    }

    private void affectTarget(Char ch){
        int dmg = damageRoll();
        int Blinddmg;

        if (ch.buff(Blindness.class) == null) Blinddmg = 0;
        else Blinddmg = 4 + buffedLvl();

        //three in (5+lvl) chance of failing
        if (Random.Int(5+buffedLvl()) >= 3) {
            Buff.prolong(ch, Blindness.class, 2f + (buffedLvl() * 0.333f));
            ch.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 6 );
        }

        if (ch.properties().contains(Char.Property.SARKAZ)){
            ch.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10+buffedLvl() );
            Sample.INSTANCE.play(Assets.Sounds.SHINNING);
            ch.damage(Math.round(dmg * 1.333f), this);
        } else {
            ch.sprite.centerEmitter().burst( RainbowParticle.BURST, 10+buffedLvl() );

            ch.damage(dmg, this);
            Buff.affect(curUser, Barrier.class).incShield(Blinddmg);
        }

    }

    private void affectMap(Ballistica beam){
        boolean noticed = false;
        for (int c : beam.subPath(0, beam.dist)){
            if (!Dungeon.level.insideMap(c)){
                continue;
            }
            for (int n : PathFinder.NEIGHBOURS9){
                int cell = c+n;

                if (Dungeon.level.discoverable[cell])
                    Dungeon.level.mapped[cell] = true;

                int terr = Dungeon.level.map[cell];
                if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

                    Dungeon.level.discover( cell );

                    GameScene.discoverTile( cell, terr );
                    ScrollOfMagicMapping.discover(cell);

                    noticed = true;
                }
            }

            CellEmitter.center(c).burst( RainbowParticle.BURST, Random.IntRange( 1, 2 ) );
        }
        if (noticed)
            Sample.INSTANCE.play( Assets.Sounds.SECRET );

        GameScene.updateFog();
    }

    @Override
    protected void fx( Ballistica beam, Callback callback ) {
        curUser.sprite.parent.add(
                new Beam.LightRay(curUser.sprite.center(), DungeonTilemap.raisedTileCenterToWorld(beam.collisionPos)));
        callback.call();
    }

    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        //cripples enemy
        Buff.prolong( defender, Cripple.class, 1f+staff.buffedLvl());
    }


}
