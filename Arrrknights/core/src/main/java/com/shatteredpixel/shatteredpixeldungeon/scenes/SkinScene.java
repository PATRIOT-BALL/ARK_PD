package com.shatteredpixel.shatteredpixeldungeon.scenes;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.TomorrowRogueNight;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.Archs;
import com.shatteredpixel.shatteredpixeldungeon.ui.BadgesGrid;
import com.shatteredpixel.shatteredpixeldungeon.ui.ExitButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.IconButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Music;
import com.watabou.utils.Bundle;

public class SkinScene extends PixelScene {

    @Override
public void create() {
    super.create();

    if (Statistics.ver0_3_2firstrun == false) {
        Badges.allskindestroy();
        Statistics.ver0_3_2firstrun = true;
    }

    Music.INSTANCE.play( Assets.Music.THEME, true );

    uiCamera.visible = false;

    int w = Camera.main.width;
    int h = Camera.main.height;

    Archs archs = new Archs();
    archs.setSize( w, h );
    add( archs );

    float margin = 5;
    float top = 20;

    RenderedTextBlock title = PixelScene.renderTextBlock( Messages.get(this, "title"), 9 );
    title.hardlight(Window.TITLE_COLOR);
    title.setPos(
            (w - title.width()) / 2f,
            (top - title.height()) / 2f
    );
    align(title);
    add(title);

    Badges.loadGlobal();
    BadgesGrid grid = new BadgesGrid(true, true);
    grid.setRect(margin, top, w-(2*margin), h-top-margin);
    add(grid);

    SkinExitButton btnExit = new SkinExitButton();
    btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
    add( btnExit );

    fadeIn();
}

    @Override
    public void destroy() {

        Badges.saveGlobal();

        super.destroy();
    }

    public class SkinExitButton extends IconButton {

        public SkinExitButton() {
            super(Icons.EXIT.get());

            width = 20;
            height = 20;
        }

        @Override
        protected void onClick() {
            Dungeon.quickslot.change=false;
            Game.switchScene(GameScene.class);
            Item.updateQuickslot();
        }
    }

    @Override
    protected void onBackPressed() {
        Dungeon.quickslot.change=false;
        Game.switchScene(GameScene.class);
        Item.updateQuickslot();
    }
}
