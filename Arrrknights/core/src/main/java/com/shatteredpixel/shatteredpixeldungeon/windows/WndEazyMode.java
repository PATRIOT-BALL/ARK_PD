package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Blackperro;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Imp;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.LeatherArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.DwarfToken;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

public class WndEazyMode extends Window {
    private static final int WIDTH      = 120;
    private static final int BTN_HEIGHT = 20;
    private static final int GAP        = 2;
    public WndEazyMode(final Blackperro perro ) {

        super();

        RenderedTextBlock message = PixelScene.renderTextBlock( Messages.get(this, "desc"), 6 );
        message.maxWidth(WIDTH);
        message.setPos(0, GAP);
        add( message );

        RedButton btnReward = new RedButton( Messages.get(this, "bt") ) {
            @Override
            protected void onClick() {
                GLog.i(Messages.get(WndEazyMode.class, "act"));
                Dungeon.eazymode = 1;
                new ScrollOfUpgrade().identify();
                new PotionOfStrength().identify();
                new ElixirOfAquaticRejuvenation().quantity(5).collect();

                Generator.Category c = Generator.wepTiers[1];
                MeleeWeapon weapon = (MeleeWeapon) Reflection.newInstance(c.classes[Random.chances(c.probs)]);
                weapon.identify();
                weapon.level(2);
                weapon.collect();

                LeatherArmor armor = new LeatherArmor();
                armor.identify();
                armor.level(2);
                armor.collect();
                hide();
            }
        };
        btnReward.setRect( 0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT );
        add( btnReward );

        resize( WIDTH, (int)btnReward.bottom() );
    }
}
