package com.shatteredpixel.shatteredpixeldungeon.actors.blobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChampionEnemy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.BlobEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.glyphs.AntiMagic;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class HealingGas extends Blob {
    //FIXME should have strength per-cell
    private int strength = 0;

    @Override
    protected void evolve() {
        super.evolve();

        if (volume == 0) {
            strength = 0;
        } else {
            Char ch;
            int cell;

            int bb = Random.Int(0, 8);
            for (int i = area.left; i < area.right; i++) {
                for (int j = area.top; j < area.bottom; j++) {
                    cell = i + j * Dungeon.level.width();
                    if (cur[cell] > 0 && (ch = Actor.findChar(cell)) != null) {
                            if (ch.HT > ch.HP) ch.HP+=1;
                            Buff.affect(ch, MagicImmune.class, 1f);
                            switch (bb) {
                                case 1:
                                    Buff.detach(ch, Blindness.class); // 실명 제거
                                    break;
                                case 2:
                                    Buff.detach(ch, Burning.class); // 연소 제거
                                    break;
                                case 3:
                                    Buff.detach(ch, Cripple.class); // 불구 제거
                                    break;
                                case 4:
                                    Buff.detach(ch, Poison.class); // 중독 제거
                                    break;
                                case 5:
                                    Buff.detach(ch, Bleeding.class); // 출혈 제거
                                    break;
                                default:
                                    break; } } } } } }

    public HealingGas setStrength(int str) {
        if (str > strength) {
            strength = str;
        }
        return this;
    }

    private static final String STRENGTH = "strength";

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        strength = bundle.getInt(STRENGTH);
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(STRENGTH, strength);
    }

    @Override
    public void use(BlobEmitter emitter) {
        super.use(emitter);

        emitter.pour(Speck.factory(Speck.HEALING), 0.4f);
    }

    @Override
    public String tileDesc() {
        return Messages.get(this, "desc");
    }
}
