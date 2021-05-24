package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MindVision;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Degradation;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class TrueSilverSlash extends Skill {
    @Override
    public void doSkill() {
        if (curUser.buff(MindVision.class) != null) {
            Buff.detach(curUser, MindVision.class);
        }

        curUser.sprite.zap(0);

        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (mob.alignment != Char.Alignment.ALLY && Dungeon.level.heroFOV[mob.pos]) {
                dohit(mob);
            }
        }
        curUser.sprite.centerEmitter().start( Speck.factory( Speck.BONE ), 0.3f, 3 );
        Sample.INSTANCE.play( Assets.Sounds.SKILL_SILVERSLASH );
        GameScene.flash( 0x80FFFFFF );
        Camera.main.shake(2, 0.5f);

        Buff.affect(curUser, Vulnerable.class,50);
        Buff.affect(curUser, Degrade.class,50);

        Invisibility.dispel();
        identify();
        curUser.spendAndNext(1f);
    }

    public void dohit(final Char enemy) {
        int dmg = Random.NormalIntRange(curUser.STR() + curUser.lvl + Dungeon.depth, (curUser.STR() + curUser.lvl + Dungeon.depth) + 15);
        enemy.sprite.burst(CharSprite.NEGATIVE, 10);
        dmg -= enemy.drRoll();

        if (!enemy.properties().contains(Char.Property.BOSS) && !enemy.properties().contains(Char.Property.MINIBOSS)) dmg = 9999;
        enemy.damage(dmg, enemy);
    }
}
