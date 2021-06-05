package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.levels.RhodesLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GreenCatSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Guard_operSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LensSprite;

import javax.swing.DefaultBoundedRangeModel;

public class SkinModel extends NPC {
    {
    }

    public SkinModel()
    {
        super();
       switch (Skin) {
           default:
           case 0:
           spriteClass = LensSprite.class;
           break;
           case 1: spriteClass = Guard_operSprite.class;

       }
    }

    private static int Skin = 0;

    @Override
    public int defenseSkill( Char enemy ) {
        return INFINITE_EVASION;
    }

    @Override
    public void damage( int dmg, Object src ) {
    }

    @Override
    public boolean interact(Char c) {
        sprite.attack(0);
        return true;
    }

    public static void spawn(RhodesLevel level, int poss) {
        SkinModel Modle = new SkinModel();
        do {
            Modle.pos = poss;
        } while (Modle.pos == -1);
        level.mobs.add(Modle);
    }

    public void SkinChange()
    {
        Skin++;
        if (Skin > 1) Skin = 0;

        int ppos = this.pos;
        this.destroy();
        this.sprite.killAndErase();
        Dungeon.level.mobs.remove(this);

        SkinModel Model = new SkinModel();
        Model.pos = ppos;
        GameScene.add(Model, 0f);
    }
}
