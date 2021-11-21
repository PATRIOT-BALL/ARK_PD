package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.skins.FrostLeafSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.skins.GraniSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.JessiSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.skins.LappySprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.skins.SkadiSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.skins.SussurroSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.skins.talrufightSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.skins.FnovaSprite;

public class SkinModel extends NPC {
    {
        properties.add(Property.IMMOVABLE);
        properties.add(Property.NPC);
    }

    public SkinModel()
    {
        super();
       switch (Dungeon.skin_ch) {
           default:
           case 0:
           spriteClass = talrufightSprite.class;
           break;
           case 1: spriteClass = FnovaSprite.class;
           break;
           case 2: spriteClass= SkadiSprite.class;
           break;
           case 3: spriteClass= SussurroSprite.class;
           break;
           case 4: spriteClass= GraniSprite.class;
           break;
           case 5: spriteClass= JessiSprite.class;
               break;
           case 6: spriteClass= LappySprite.class;
           break;
           case 7: spriteClass= FrostLeafSprite.class;
           break;
       }
    }



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

    public static void spawn(Level level, int poss) {
        SkinModel Modle = new SkinModel();
        do {
            Modle.pos = poss;
        } while (Modle.pos == -1);
        level.mobs.add(Modle);
    }

    public void SkinChange()
    {
        Dungeon.skin_ch++;
        if (Dungeon.skin_ch > 7) Dungeon.skin_ch = 0;

        int ppos = this.pos;
        this.destroy();
        this.sprite.killAndErase();
        Dungeon.level.mobs.remove(this);

        SkinModel Model = new SkinModel();
        Model.pos = ppos;
        GameScene.add(Model, 0f);
    }
}
