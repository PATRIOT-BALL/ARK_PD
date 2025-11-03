package com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.MirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.Skill;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAggression;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BenasProtracto extends Skill {
    public void doSkill() {
        ArrayList<Integer> respawnPoints = new ArrayList<>();
        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
            int p = Dungeon.hero.pos + PathFinder.NEIGHBOURS8[i];
            if (Actor.findChar(p) == null && Dungeon.level.passable[p]) {
                respawnPoints.add(p); } }

        int index = Random.index(respawnPoints);

        MirrorImage mob = new MirrorImage();
        mob.duplicate(Dungeon.hero);
        GameScene.add(mob);
        ScrollOfTeleportation.appear(mob, respawnPoints.get(index));
        respawnPoints.remove(index);
        Buff.affect(mob, Barrier.class).setShield(25+(Dungeon.hero.lvl*6));
        Buff.prolong(mob, StoneOfAggression.Aggression.class, StoneOfAggression.Aggression.DURATION);

        Buff.affect(curUser, Invisibility.class, Invisibility.DURATION / 2f);
        CellEmitter.get(curUser.pos).burst(Speck.factory(Speck.WOOL), 10);
        Sample.INSTANCE.play(Assets.Sounds.SKILL_BASIC);
        curUser.spendAndNext(1);
    }
}
