package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK3;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrabGun;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class EveryoneTogether extends Skill {
    public void doSkill() {
        ArrayList<Integer> respawnPoints = new ArrayList<>();

        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
            int p = Dungeon.hero.pos + PathFinder.NEIGHBOURS8[i];
            if (Actor.findChar(p) == null && Dungeon.level.passable[p]) {
                respawnPoints.add(p);
            }
        }
        int spawnd = 0;
        while (respawnPoints.size() > 0 && spawnd < 4) {
            int index = Random.index(respawnPoints);

            CrabGun cc = new CrabGun();
            int lvl = 0;
            if (Dungeon.hero.belongings.weapon != null) lvl = (Dungeon.hero.belongings.weapon.level()/2) + 1;

            cc.SpawnCrab(lvl, respawnPoints.get(index));

            respawnPoints.remove(index);
            spawnd++;
        }

        CellEmitter.get(curUser.pos).burst(Speck.factory(Speck.WOOL), 10);
        Sample.INSTANCE.play(Assets.Sounds.SKILL_BASIC);
        curUser.spendAndNext(1);
    }
}