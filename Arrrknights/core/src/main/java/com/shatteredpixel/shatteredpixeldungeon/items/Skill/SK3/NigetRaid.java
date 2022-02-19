package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.PixelParticle;

public class NigetRaid extends Skill {
    public void doSkill() {
        CellEmitter.get( curUser.pos ).start( ShadowParticle.UP, 0.05f, 10 );
        Sample.INSTANCE.play( Assets.Sounds.SKILL_BASIC );

        for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
            if (Dungeon.level.adjacent(mob.pos, curUser.pos) && mob.alignment != Char.Alignment.ALLY) {
                mob.sprite.emitter().burst(Speck.factory(Speck.BONE), 8);
                mob.sprite.emitter().burst( Speck.factory( Speck.LIGHT ), 4 );
                Buff.affect(mob, Blindness.class, 10);
            }
        }
        Buff.affect(curUser, Invisibility.class, Invisibility.DURATION/2f);
        Buff.affect(curUser, ArtifactRecharge.class).set(35).ignoreHornOfPlenty=false;
        Buff.affect(curUser, Stamina.class,35);
    }
}
