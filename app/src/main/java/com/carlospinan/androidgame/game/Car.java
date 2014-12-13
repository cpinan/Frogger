package com.carlospinan.androidgame.game;

import android.graphics.Bitmap;

/**
 * Created by mac on 13/12/14.
 */
public class Car extends Sprite {

    private float speedX;
    private int direction;

    public Car(Bitmap bitmap) {
        super(bitmap);
        this.speedX = 0;
        this.direction = -1;
    }

    public void update(float dt) {

        float new_x = getX() + speedX * direction;

        if (direction == -1 && new_x <= -250 ) {
            new_x = FroggerView.GAME_WIDTH + 500;
        }

        if (direction == 1 && new_x >= FroggerView.GAME_WIDTH + 250 ) {
            new_x = -500;
        }

        setPosition(new_x, getY());
    }

    public void setDirectionToLeft() {
        direction = -1;
        setScaleX(-1);
    }

    public void setDirectionToRight() {
        direction = 1;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }
}
