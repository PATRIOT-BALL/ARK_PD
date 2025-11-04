package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMistress;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class KollamSword extends MeleeWeapon {
    public static final String AC_ZAP = "ZAP";
    {
        image = ItemSpriteSheet.DONKEY_SWORD;
        hitSound = Assets.Sounds.HIT_SWORD2;
        hitSoundPitch = 1.03f;

        tier = 5;
        defaultAction = AC_ZAP;
    }

    @Override
    public int max(int lvl) {
        return  5*(tier+1) + // 30+6
                lvl*(tier+1);
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        SPCharge(2);
        return super.proc(attacker, defender, damage);
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_ZAP);
        return actions;
    }

    @Override
    public String desc() {
        String info = Messages.get(this, "desc");
        if (setbouns()) info += "\n\n" + Messages.get( KollamSword.class, "setbouns");

        return info;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_ZAP) && isEquipped(hero)) {
            if (charge >= chargeCap) {
                MindBrack(hero);
                Sample.INSTANCE.play(Assets.Sounds.CURSED);
                charge = 0;

                hero.sprite.Sattack(0);
                updateQuickslot();
                curUser.spendAndNext(1f);
            }
        }
    }

    private void MindBrack(Hero hero) {
        PathFinder.buildDistanceMap(hero.pos, BArray.not(Dungeon.level.solid, null), 2);
        for (int cell = 0; cell < PathFinder.distance.length; cell++) {
            if (PathFinder.distance[cell] < Integer.MAX_VALUE) {
                CellEmitter.get( cell ).burst( ShadowParticle.CURSE, 10 );
                Char ch = Actor.findChar(cell);
                if (ch != null&& !(ch instanceof Hero) && ch instanceof Mob && ch.alignment != Char.Alignment.ALLY) {
                    if (!ch.isImmune(Corruption.class)) {
                        boolean chance = true;
                        if (!setbouns() && Random.Int(2) != 0) chance = true;
                        else if (setbouns()) chance = true;

                        if (chance)Buff.affect(ch, Corruption.class);

                        boolean droppingLoot = ch.alignment != Char.Alignment.ALLY;

                        if (ch.buff(Corruption.class) != null){
                            if (droppingLoot) ((Mob)ch).rollToDropLoot();
                            Statistics.enemiesSlain++;
                            Badges.validateMonstersSlain();
                            Statistics.qualifiedForNoKilling = false;
                            if (((Mob)ch).EXP > 0 && curUser.lvl <= ((Mob)ch).maxLvl) {
                                curUser.sprite.showStatus(CharSprite.POSITIVE, Messages.get(((Mob)ch), "exp", ((Mob)ch).EXP));
                                curUser.earnExp(((Mob)ch).EXP, ((Mob)ch).getClass());
                            } else {
                                curUser.earnExp(0, ((Mob)ch).getClass());
                            }
                        }
                    }

                }}}
    }

    private boolean setbouns() {
        if (Dungeon.hero.belongings.getItem(RingOfMistress.class) != null && Dungeon.hero.belongings.getItem(ChaliceOfBlood.class) != null) {
            if (Dungeon.hero.belongings.getItem(RingOfMistress.class).isEquipped(Dungeon.hero) && Dungeon.hero.belongings.getItem(ChaliceOfBlood.class).isEquipped(Dungeon.hero))
                return true;
        }
        return false;
    }

    @Override
    public String status() {
        if (chargeCap == 100)
            return Messages.format("%d%%", charge);
        return null;
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{ShadowFirmament.class, Firmament.class, SwordofArtorius.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 0;

            output = KollamSword.class;
            outQuantity = 1;
        }

    }
}
