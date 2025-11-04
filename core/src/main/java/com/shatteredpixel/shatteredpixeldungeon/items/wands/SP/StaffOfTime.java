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
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SkillBook;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAmplified;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.DamageWand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Blazing;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class StaffOfTime extends DamageWand {
    {
        image = ItemSpriteSheet.WAND_MOSTIMA;

        collisionProperties = Ballistica.STOP_SOLID | Ballistica.IGNORE_SOFT_SOLID;
    }

    //1x/2x/3x damage
    public int min(int lvl){
        return 1+lvl;
    }

    //1x/2x/3x damage
    public int max(int lvl){
        return 7+3*lvl + RingOfAmplified.DamageBonus(Dungeon.hero) * 3;
    }

    ConeAOE cone;

    @Override
    protected void onZap( Ballistica bolt ) {

        ArrayList<Char> affectedChars = new ArrayList<>();
        ArrayList<Integer> adjacentCells = new ArrayList<>();
        for( int cell : cone.cells ){

            //ignore caster cell
            if (cell == bolt.sourcePos){
                continue;
            }

            Char ch = Actor.findChar( cell );
            if (ch != null) {
                affectedChars.add(ch);
            }

        }

        for ( Char ch : affectedChars ){
            processSoulMark(ch, chargesPerCast());
            ch.damage(damageRoll(), this);
            if (ch.isAlive()) {
                Ballistica trajectory = new Ballistica(curUser.pos, ch.pos, Ballistica.STOP_TARGET);
                trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
                WandOfBlastWave.throwChar(ch, trajectory, 2); // 넉백 효과
            }
        }
    }

    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        if (Dungeon.hero.belongings.getItem(SkillBook.class) != null) {
            SkillBook Item = Dungeon.hero.belongings.getItem(SkillBook.class);
            Item.SetCharge(1);
            SpellSprite.show(attacker, SpellSprite.CHARGE);
        }
    }

    @Override
    protected void fx( Ballistica bolt, Callback callback ) {
        //need to perform flame spread logic here so we can determine what cells to put flames in.

        // 5/7/9 distance
        int maxDist = 5;
        int dist = Math.min(bolt.dist, maxDist);

        cone = new ConeAOE( bolt,
                maxDist,
                50,
                collisionProperties | Ballistica.STOP_TARGET);

        //cast to cells at the tip, rather than all cells, better performance.
        for (Ballistica ray : cone.rays){
            ((MagicMissile)curUser.sprite.parent.recycle( MagicMissile.class )).reset(
                    MagicMissile.TIME_CONE,
                    curUser.sprite,
                    ray.path.get(ray.dist),
                    null
            );
        }

        //final zap at half distance, for timing of the actual wand effect
        MagicMissile.boltFromChar( curUser.sprite.parent,
                MagicMissile.TIME_CONE,
                curUser.sprite,
                bolt.path.get(dist/2),
                callback );
        Sample.INSTANCE.play( Assets.Sounds.HIT_SPLASH );
        Sample.INSTANCE.play( Assets.Sounds.HIT_SPLASH );
    }

}
