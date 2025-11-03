package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.LeatherArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.PlateArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ScaleArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.AlchemyKit;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CustomeSet;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HornOfPlenty;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.IsekaiItem;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.MasterThievesArmband;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.SandalsOfNature;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TalismanOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.UnstableSpellbook;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.WoundsofWar;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAccuracy;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAssassin;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfElements;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEvasion;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfForce;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfFuror;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMistress;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSunLight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfTenacity;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.AssassinsBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.BattleAxe;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Castlebreaker;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CatGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.CrabGun;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Decapitator;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DeepAbyss;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Destreza;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Dirk;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.DivineAvatar;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Echeveria;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Firmament;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.FolkSong;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KRISSVector;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.KollamSword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Laevateinn;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Naginata;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PatriotSpear;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.R4C;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RadiantSpear;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.RoundShield;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Scythe;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ShadowFirmament;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Suffering;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Sword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.SwordofArtorius;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.ThermiteBlade;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WarJournalist;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WintersScar;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Blood_ShamanSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NPC_AstesiaSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;

public class Npc_Astesia extends NPC {
    {
        spriteClass = NPC_AstesiaSprite.class;
        properties.add(Char.Property.IMMOVABLE);
        properties.add(Property.NPC);
    }

    Item setItem1;
    Item setItem2;
    Item setItem3;
    int SetValue = 0;

    @Override
    public int defenseSkill(Char enemy) {
        return INFINITE_EVASION;
    }

    @Override
    public void damage(int dmg, Object src) {
    }

    @Override
    public boolean interact(Char c) {
        sprite.turnTo(pos, c.pos);
        Bounsinfo();
        return true;
    }

    private void Bounsinfo() {
        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {
                if (Dungeon.hero.belongings.weapon == null) GameScene.show(new WndMessage(Messages.get(Npc_Astesia.class, "no_weapon", Dungeon.hero.heroClass.title())));
                else {
                    Item weapon = Dungeon.hero.belongings.weapon;
                    if (bounschack(weapon)) {
                        if (SetValue == 1) GameScene.show(new WndMessage(Messages.get(Npc_Astesia.class, "setbouns_1", Dungeon.hero.heroClass.title(), setItem1.trueName())));
                        else if (SetValue == 2) GameScene.show(new WndMessage(Messages.get(Npc_Astesia.class, "setbouns_2", Dungeon.hero.heroClass.title(), setItem1.trueName(), setItem2.trueName())));
                        else if (SetValue == 3)
                            GameScene.show(new WndMessage(Messages.get(Npc_Astesia.class, "setbouns_3", Dungeon.hero.heroClass.title(), setItem1.trueName(), setItem2.trueName(), setItem3.trueName())));
                    }
                    else {
                        GameScene.show(new WndMessage(Messages.get(Npc_Astesia.class, "no_setbouns", Dungeon.hero.heroClass.title())));
                    }
                }
            }
        });
    }

    private boolean bounschack(Item weapon) {
        if (weapon instanceof AssassinsBlade) {setItem1 = new LeatherArmor(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof BattleAxe) {setItem1 = new TalismanOfForesight(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof Castlebreaker) {setItem1 = new RingOfAccuracy(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof Decapitator) {setItem1 = new RingOfEvasion(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof Dirk) {setItem1 = new RingOfAssassin(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof DivineAvatar) {setItem1 = new RingOfSunLight(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof Firmament) {setItem1 = new ScaleArmor(); setItem2 = new RingOfForce(); setItem3 = null; SetValue = 2; return true;}
        else if (weapon instanceof FolkSong) {setItem1 = new RingOfTenacity(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof ShadowFirmament) {setItem1 = new ChaliceOfBlood(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof DeepAbyss || weapon instanceof Sword) {setItem1 = new SandalsOfNature(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof ThermiteBlade || weapon instanceof R4C) {setItem1 = new IsekaiItem(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof Naginata) {setItem1 = new HornOfPlenty(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof PatriotSpear) {setItem1 = new RingOfMight(); setItem2 = new RingOfTenacity(); setItem3 = new PlateArmor(); SetValue = 3; return true;}
        else if (weapon instanceof RadiantSpear) {setItem1 = new RingOfSunLight(); setItem2 = new RingOfHaste(); setItem3 = null; SetValue = 2; return true;}
        else if (weapon instanceof RoundShield) {setItem1 = new CustomeSet(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof Scythe) {setItem1 = new RingOfFuror(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof Laevateinn) {setItem1 = new RingOfElements(); setItem2 = new RingOfMight(); setItem3 = null; SetValue = 2; return true;}
        else if (weapon instanceof Destreza) {setItem1 = new AlchemyKit(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof CatGun) {setItem1 = new ChaliceOfBlood(); setItem2 = new UnstableSpellbook(); setItem3 = new RingOfMistress(); SetValue = 3; return true;}
        else if (weapon instanceof SwordofArtorius) {setItem1 = new RingOfMistress(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof CrabGun) {setItem1 = new RingOfWealth(); setItem2 = new CustomeSet(); setItem3 = null; SetValue = 2; return true;}
        else if (weapon instanceof KRISSVector) {setItem1 = new RingOfAccuracy(); setItem2 = new MasterThievesArmband(); setItem3 = null; SetValue = 2; return true;}
        else if (weapon instanceof WarJournalist) {setItem1 = new WoundsofWar(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof Echeveria) {setItem1 = new RingOfWealth(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof Suffering) {setItem1 = new TimekeepersHourglass(); setItem2 = null; setItem3 = null; SetValue = 1; return true;}
        else if (weapon instanceof KollamSword) {setItem1 = new RingOfMistress(); setItem2 = new ChaliceOfBlood(); setItem3 = null; SetValue = 2; return true;}
        else if (weapon instanceof WintersScar) {setItem1 = new RingOfAssassin(); setItem2 = new WoundsofWar(); setItem3 = null; SetValue = 2; return true;}


        return false;
    }

    public static void spawn(Level level, int ppos) {
        Npc_Astesia Astesia = new Npc_Astesia();
        do {
            Astesia.pos = ppos;
        } while (Astesia.pos == -1);
        level.mobs.add(Astesia);
    }
}
