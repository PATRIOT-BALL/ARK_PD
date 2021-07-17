package com.shatteredpixel.shatteredpixeldungeon.actors.blobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.BlobEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.AnnihilationGear;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookExecutionMode;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookPowerfulStrike;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookTacticalChanting;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookThoughts;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookWhispers;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookGenesis;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Gamza;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Nmould;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAdvanceguard;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Thunderbolt;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class WaterOfAdvanceguard extends WellWater {

    @Override
    protected Item affectItem(Item item, int pos) {
        int Price = 0;

        if (item instanceof Runestone) { // 아이템이 돌일 경우
            item = new StoneOfAdvanceguard();
        } else if (item instanceof BookPowerfulStrike || item instanceof BookTacticalChanting || item instanceof BookExecutionMode || item instanceof BookThoughts) {
            if (Random.IntRange(0,21) < 12) item = new BookWhispers();
            else item = new BookGenesis();
        } else if (item instanceof Gamza) {
            if (Random.IntRange(0,100) < 31) { item = new Nmould();}
            else if  (Random.IntRange(0,11) < 7) item = new Gamza();
            else item = new Thunderbolt();
        } else if (item.value() != 0 && item.unique != true) // 아이템 가격이 0이상(판매 가능)이며, 유니크 아이템이 아닐 경우
        {
            Price = item.value() * 3;
            item = new Gold(Price);
            GLog.p(String.format(Messages.get(this, "procced"), Price));
        } else {
            item = null;
        }

        return item;
    }

    @Override
    protected boolean affectHero(Hero hero) {
        return false;
    }

    @Override
    protected Notes.Landmark record() {
        return Notes.Landmark.WELL_OF_ADVANCEGUARD;
    }

    @Override
    public void use(BlobEmitter emitter) {
        super.use(emitter);
        emitter.start(Speck.factory(Speck.COIN), 0.5f, 0);
    }

    @Override
    public String tileDesc() {
        return Messages.get(this, "desc");
    }
}
