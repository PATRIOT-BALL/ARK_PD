/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.NewDM300;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.watabou.noosa.Camera;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Callback;

public class MudrockSprite extends MobSprite {

    private Animation charge;
    private Animation slam;

    private Emitter superchargeSparks;

    public MudrockSprite() {
        super();

        texture(Assets.Sprites.MUD);

        updateChargeState(false, false, false);
    }

    public void updateChargeState(boolean enraged, boolean FinalSkill, boolean FinalPhase) {
        if (superchargeSparks != null) superchargeSparks.on = enraged;

        int c = enraged ? 44 : 0;
        if (FinalPhase == true) c = 88;

        TextureFilm frames = new TextureFilm(texture, 42, 42);

        idle = new Animation(enraged ? 15 : 10, true);
        idle.frames(frames, c + 0);

        run = new Animation(enraged ? 15 : 10, true);
        run.frames(frames, c + 1, c + 2, c + 3, c + 4, c + 5, c + 6, c + 7, c + 8);

        attack = new Animation(15, false);
        attack.frames(frames, c + 9, c + 10, c + 11, c + 12, c + 13, c + 14, c + 15, c + 16, c + 17);

        Sattack = new Animation(1, true);
        Sattack.frames(frames, c + 34);

        //unaffected by enrage state

        if (charge == null) {
            charge = new Animation(4, true);
            charge.frames(frames, c +34);


            slam = attack.clone();

            zap = new Animation(15, false);
            zap.frames(frames, c+1, c+2, c+3, c+4, c+5, c+6, c+7, c+8);

            die = new Animation(7, false);
            die.frames(frames, c+17, c+18, c+19, c+20, c+21, c+22, c+23, c+24, c+25, c+26, c+27, c+28, c+29, c+30, c+31, c+32, c+33,c+ 43);
        }

        if (curAnim != charge) play(Sattack);
    }

    public void zap(int cell) {

        turnTo(ch.pos, cell);
        play(zap);

        MagicMissile.boltFromChar(parent,
                MagicMissile.TOXIC_VENT,
                this,
                cell,
                new Callback() {
                    @Override
                    public void call() {
                        ((NewDM300) ch).onZapComplete();
                    }
                });
        Sample.INSTANCE.play(Assets.Sounds.GAS);
    }

    public void charge() {
        play(charge);
    }

    public void slam(int cell) {
        turnTo(ch.pos, cell);
        play(slam);
        Sample.INSTANCE.play(Assets.Sounds.ROCKS);
        Camera.main.shake(3, 0.7f);
    }

    @Override
    public void onComplete(Animation anim) {

        if (anim == zap || anim == slam) {
            idle();
        }

        if (anim == slam) {
            ((NewDM300) ch).onSlamComplete();
        }

        super.onComplete(anim);

        if (anim == die) {
            Sample.INSTANCE.play(Assets.Sounds.BLAST);
            killAndErase();
        }
    }

    @Override
    public void place(int cell) {
        if (parent != null) parent.bringToFront(this);
        super.place(cell);
    }

    @Override
    public void link(Char ch) {
        super.link(ch);
        superchargeSparks = emitter();
        superchargeSparks.autoKill = false;
        superchargeSparks.pour(ShadowParticle.UP, 0.05f);
        superchargeSparks.on = false;

        if (ch instanceof NewDM300 && ((NewDM300) ch).isSupercharged()) {
            updateChargeState(true, false, false);
        }
    }



    @Override
    public void update() {
        super.update();

        if (superchargeSparks != null) {
            superchargeSparks.visible = visible;
        }
    }

    @Override
    public void die() {
        super.die();
        if (superchargeSparks != null) {
            superchargeSparks.on = false;
        }
    }

    @Override
    public void kill() {
        super.kill();
        if (superchargeSparks != null) {
            superchargeSparks.killAndErase();
        }
    }

    @Override
    public int blood() {
        return 0xFFFFFF88;
    }
}
