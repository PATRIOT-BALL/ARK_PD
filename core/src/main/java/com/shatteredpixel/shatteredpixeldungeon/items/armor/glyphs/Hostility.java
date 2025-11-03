package com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;

public class Hostility extends Armor.Glyph {
    private static ItemSprite.Glowing BLUE = new ItemSprite.Glowing( 0x0000FF );

    @Override
    public int proc(Armor armor, Char attacker, Char defender, int damage) {
        //no proc effect, see armor.speedfactor for effect.
        if (attacker.properties().contains(Char.Property.SARKAZ) == true)
        {
            return damage - (defender.drRoll() / 2);
        }

        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return BLUE;
    }
}
