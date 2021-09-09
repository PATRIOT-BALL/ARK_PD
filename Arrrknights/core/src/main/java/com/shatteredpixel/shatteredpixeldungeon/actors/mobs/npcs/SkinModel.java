package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.levels.RhodesLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GraniSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SkadiSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SussurroSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.talrufightSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.FnovaSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GreenCatSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Guard_operSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LensSprite;

import javax.swing.DefaultBoundedRangeModel;

public class SkinModel extends NPC {
    {
        properties.add(Property.IMMOVABLE);
        properties.add(Property.NPC);
    }

    public SkinModel()
    {
        super();
       switch (skin_ch) {
           default:
           case 0:
           spriteClass = talrufightSprite.class;
           break;
           case 1: spriteClass = FnovaSprite.class;
           break;
           case 2: spriteClass= GraniSprite.class;
           break;
           case 3: spriteClass= SkadiSprite.class;
           break;
           case 4: spriteClass= SussurroSprite.class;
           break;

       }
    }



    public int Skin = 0;
    public static int skin_ch = 0;

    @Override
    public int defenseSkill( Char enemy ) {
        return INFINITE_EVASION;
    }

    @Override
    public void damage( int dmg, Object src ) {
    }

    @Override
    public boolean interact(Char c) {
        SkinChange();
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
        skin_ch++;
        if (skin_ch > 4) skin_ch = 0;

        int ppos = this.pos;
        this.destroy();
        this.sprite.killAndErase();
        Dungeon.level.mobs.remove(this);

        SkinModel Model = new SkinModel();
        Model.pos = ppos;
        Model.Skin = skin_ch;
        GameScene.add(Model, 0f);
    }
}
