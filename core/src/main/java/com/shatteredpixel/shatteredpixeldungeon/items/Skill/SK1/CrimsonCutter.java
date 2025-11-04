package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class CrimsonCutter extends Skill {
    public void doSkill() {

        curUser.sprite.zap(0);
        for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
            if (Dungeon.level.adjacent(mob.pos, curUser.pos) && mob.alignment != Char.Alignment.ALLY) {
                dohit(mob);
            }
        }

        Camera.main.shake(1, 0.5f);
        CellEmitter.get(curUser.pos).burst(Speck.factory(Speck.WOOL), 10);
        Sample.INSTANCE.play(Assets.Sounds.SKILL_BASIC);
        Sample.INSTANCE.play(Assets.Sounds.HIT_SLASH);
        curUser.spendAndNext(1);
    }

    public void dohit(final Char enemy) {
        int dmg;
        enemy.sprite.burst(CharSprite.NEGATIVE, 10);
        if (enemy.properties().contains(Char.Property.DRONE)) {
            dmg = Random.NormalIntRange(10, 10+curUser.STR) * 2;
        }
        else if (enemy.buff(Bleeding.class) != null) {
            dmg = Random.NormalIntRange(5, 5+curUser.STR) * 2;
            Buff.affect(enemy, Bleeding.class).set(curUser.STR / 5);}
        else {
            dmg = Random.NormalIntRange(5, 5+curUser.STR);
            Buff.affect(enemy, Bleeding.class).set(curUser.STR / 5);}
        enemy.damage(dmg, enemy);
    }
}