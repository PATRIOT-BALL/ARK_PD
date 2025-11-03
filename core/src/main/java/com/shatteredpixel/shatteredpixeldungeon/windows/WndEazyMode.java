package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Blackperro;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Imp;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.LeatherArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.DwarfToken;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfDivination;
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
                new ScrollOfRemoveCurse().identify();
                new ScrollOfTransmutation().identify();
                new PotionOfStrength().identify();
                new PotionOfExperience().identify();
                new PotionOfHealing().identify();
                new ElixirOfAquaticRejuvenation().collect();
                new ScrollOfDivination().quantity(2).collect();

                hide();
            }
        };
        btnReward.setRect( 0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT );
        add( btnReward );

        resize( WIDTH, (int)btnReward.bottom() );
    }
}
