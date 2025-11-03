package com.shatteredpixel.shatteredpixeldungeon.actors.blobs;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.BlobEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookExecutionMode;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookHikari;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookPowerfulStrike;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookTacticalChanting;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookThoughts;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK1.BookWhispers;
import com.shatteredpixel.shatteredpixeldungeon.items.Skill.SK2.BookGenesis;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.CorpseDust;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Gamza;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Nmould;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAdvanceguard;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.SuperAdvanceguard;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.SP.StaffOfTime;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CatGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrabGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Firmament;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ImageoverForm;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PatriotSpear;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SanktaBet;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Shortsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SwordofArtorius;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WintersScar;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.PurgatoryKnife;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Thunderbolt;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class WaterOfAdvanceguard extends WellWater {

    @Override
    protected Item affectItem(Item item, int pos) {
        int Price = 0;

        if (item instanceof Runestone) { // 아이템이 돌일 경우
            if (Random.Int(2) == 0) item = new SuperAdvanceguard();
            else item = new StoneOfAdvanceguard();
        } else if (item instanceof BookPowerfulStrike || item instanceof BookTacticalChanting || item instanceof BookExecutionMode || item instanceof BookThoughts
    || item instanceof BookHikari) {
            if (Random.IntRange(0,21) < 12) item = new BookWhispers();
            else item = new BookGenesis();
        } else if (item instanceof SwordofArtorius || item instanceof WintersScar) {
            item = new PatriotSpear();
            item.identify();
        }
        else if (item instanceof Wand) {
            if (Random.Int(2) == 0) {
                item = new StaffOfTime();
                item.identify();
            }
        }else if (item instanceof Shortsword) {
            if (Random.Int(4) < 3){ item = new Firmament();
            item.identify();}
        }else if (item instanceof PurgatoryKnife) {
          item = new ImageoverForm();
          item.identify();
        } else if (item instanceof Gamza) {
            if (Random.IntRange(0,100) < 31) { item = new Nmould();}
            else if (Random.IntRange(0,11) < 3) {
                Sample.INSTANCE.play(Assets.Sounds.HIT_PARRY);
                item = new Gamza();
            }
            else item = new Thunderbolt();
        } else if (item instanceof CrabGun) {
            if (Random.Int(4) < 3) {
                item = new CatGun();
                item.identify();
            }
        } else if (item.value() != 0 && item.unique != true) // 아이템 가격이 0이상(판매 가능)이며, 유니크 아이템이 아닐 경우
        {
            Price = item.value() * 3;
            item = new Gold(Price);
            GLog.p(String.format(Messages.get(this, "procced"), Price));
        }
        else {
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
