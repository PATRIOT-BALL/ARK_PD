package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_DarioSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_PilotSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndPilot;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

public class NPC_Dario extends NPC {
    {
        spriteClass = NPC_DarioSprite.class;
        properties.add(Property.NPC);

        HP = HT = 1000;

        state = SLEEPING;
        alignment = Alignment.ALLY;
    }

    public static boolean QuestClear = false;
    
    @Override
    public int defenseSkill(Char enemy) {
        return 15;
    }

    @Override
    protected boolean act() {
        if (state == SLEEPING) {
            yell(Messages.get(this, "help")); // 해당 층에 들어오면 위험대사를 외칩니다
            Buff.append(Dungeon.hero, TalismanOfForesight.CharAwareness.class, 5f).charID = this.id(); // 이 NPC의 위치를 노출시킵니다
            state = PASSIVE;

            QuestClear = false;
        }

        else
        {
            Buff.append(Dungeon.hero, TalismanOfForesight.CharAwareness.class, 5f).charID = this.id(); // 이 NPC의 위치를 노출시킵니다

            for (Mob mob : Dungeon.level.mobs) {
                if (mob.paralysed <= 0
                        && Dungeon.level.distance(pos, mob.pos) <= 8
                        && mob.state != mob.HUNTING) {
                    mob.beckon(this.pos);
                }
                }
        }
        return super.act();
    }


    @Override
    public boolean interact(Char c) {
        sprite.turnTo(pos, c.pos);
        yell(Messages.get(this, "tnk")); // 접촉시 고마워 대사 출력

        QuestClear = true; // 퀘스트 클리어 처리
        die(this); // 삭제
        return true;
    }

    public static void spawn(Level level, int ppos) {
        NPC_Dario perro = new NPC_Dario();
        do {
            perro.pos = ppos;
        } while (perro.pos == -1);
        level.mobs.add(perro);
    }
}
