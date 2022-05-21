package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;

public class CloserangeShot extends Buff {
    private boolean actived = false;
    private static final String ACTIVE    = "actived";

    @Override
    public int icon() {
        return BuffIndicator.MARK;
    }

    @Override
    public void tintIcon(Image icon) {
        if (actived){
            icon.hardlight(1,1,1);
        } else {
            icon.hardlight(0.5f,0.5f,0);
        }
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc()
    {
        if (actived)
        return Messages.get(this, "active");
        else { return Messages.get(this, "passive"); }
    }

    public void isActived() {
        int range = 2;
        boolean isactive = false;

        PathFinder.buildDistanceMap(target.pos, BArray.not(Dungeon.level.solid, null), range);
        for (int cell = 0; cell < PathFinder.distance.length; cell++) {
            if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                Char ch = Actor.findChar(cell);
                if (ch != null && ch.alignment == Char.Alignment.ENEMY ) {
                    isactive = true;
                }}}

        if (isactive) actived = true;
        else actived = false;
    }

    public boolean state() {
        return actived;
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( ACTIVE, actived );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        actived = bundle.getBoolean( ACTIVE );
    }
}
