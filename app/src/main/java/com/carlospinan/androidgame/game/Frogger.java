package com.carlospinan.androidgame.game;

import android.graphics.Bitmap;

/**
 * Created by mac on 13/12/14.
 */
public class Frogger extends Sprite {

    private float speed;

    public Frogger(Bitmap bitmap) {
        super(bitmap);
        speed = FroggerView.TILE_SIZE * 0.8f;
    }

    public float getSpeed() {
        return speed;
    }


}
