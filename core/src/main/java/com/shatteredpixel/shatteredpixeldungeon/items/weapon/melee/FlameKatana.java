package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

public class FlameKatana extends MeleeWeapon {
    {
        image = ItemSpriteSheet.FIRE_KATANA;
        hitSound = Assets.Sounds.HIT_KNIFE;
        hitSoundPitch = 1f;

        tier = 2;
    }

    private int killpoint = 0;

    @Override
    public int max(int lvl) {
        return  4*(tier+1) +    //12 base, down from 15
                lvl*(tier+1);   //scaling unchanged
    }

    public void GetKillPoint() {
        killpoint++;

        if (killpoint == 125) {
            int lvl = this.level();

            BladeDemon n = new BladeDemon();

            n.enchantment = enchantment;
            n.curseInfusionBonus = curseInfusionBonus;
            n.levelKnown = levelKnown;
            n.cursedKnown = cursedKnown;
            n.cursed = cursed;
            n.augment = augment;
            n.level(lvl);

            Dungeon.hero.belongings.weapon = n;

            Dungeon.hero.sprite.emitter().burst(Speck.factory(Speck.RED_LIGHT),12);
        }
    }

    @Override
    public String status() {
        if (this.isIdentified()) return "" + killpoint;
        else return null;}


    private static final String KILL = "killpoint";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(KILL, killpoint);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        killpoint = bundle.getInt(KILL);
    }
}
