package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.IsekaiItem;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class R4C extends GunWeapon {
    {
        image = ItemSpriteSheet.R4C;
        hitSound = Assets.Sounds.HIT_AR;
        hitSoundPitch = 0.9f;

        FIRE_ACC_MULT = 2f;
        FIRE_DELAY_MULT = 0.5f;
        FIRE_DAMAGE_MULT = 0.4f;
        bulletMax = 31;

        usesTargeting = true;

        defaultAction = AC_ZAP;

        tier = 5;
    }
    @Override
    protected void specialFire(Char ch) {
        Ballistica trajectory = new Ballistica(curUser.pos, ch.pos, Ballistica.STOP_TARGET);
        trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
        WandOfBlastWave.throwChar(ch, trajectory, 2); // 넉백 효과
    }

    @Override
    public String desc() {
       String info = Messages.get(this, "desc", bulletTier);
            if (Dungeon.hero.belongings.getItem(IsekaiItem.class) != null) {
                if (Dungeon.hero.belongings.getItem(IsekaiItem.class).isEquipped(Dungeon.hero))
                    info += "\n\n" + Messages.get( R4C.class, "setbouns");}


        return info;
    }
}
