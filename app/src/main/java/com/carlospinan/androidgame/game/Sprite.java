package com.carlospinan.androidgame.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by mac on 13/12/14.
 */
public class Sprite {

    private Bitmap bitmap;
    private float x, y;
    private float width, height;
    private boolean visible;
    private float anchor_x, anchor_y;
    private Matrix matrix;
    private float rotation;
    private float scale_x, scale_y;
    private Paint paintDebug;

    public Sprite(Bitmap bitmap) {

        this.bitmap = bitmap;
        this.x = 0;
        this.y = 0;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.visible = true;
        this.anchor_x = 0.5f;
        this.anchor_y = 0.5f;
        this.rotation = 0;
        this.matrix = new Matrix();
        this.scale_x = 1;
        this.scale_y = 1;

        paintDebug = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintDebug.setARGB(128, 255, 0, 0);

    }

    public void onDraw(Canvas canvas) {

        if (visible) {

            matrix.reset();
            matrix.postScale(scale_x, scale_y);
            matrix.postRotate(rotation, anchor_x * width, anchor_y * height);
            matrix.postTranslate(x - anchor_x * width, y - anchor_y * height);
            canvas.drawBitmap(bitmap, matrix, null);

            if (FroggerView.FROGGER_DEBUG_COLLISION) {
                RectF rectangle = getBoundingBox();
                canvas.drawRect(rectangle, paintDebug);
            }
        }

    }

    public RectF getBoundingBox() {

        float left = x - width * anchor_x;
        if( scale_x == -1 )
            left -= width;

        float right = left + width;
        float top = y - height * anchor_y;
        float bottom = top + height;

        return new RectF(left, top, right, bottom);
    }

    public boolean isCollide(Sprite sprite) {
        return visible &&
                sprite.isVisible() &&
                getBoundingBox().intersect(sprite.getBoundingBox());
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAnchorX() {
        return anchor_x;
    }

    public void setAnchorX(float anchor_x) {
        this.anchor_x = anchor_x;
    }

    public float getAnchorY() {
        return anchor_y;
    }

    public void setAnchorY(float anchor_y) {
        this.anchor_y = anchor_y;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public float getScaleX() {
        return scale_x;
    }

    public void setScaleX(float scale_x) {
        this.scale_x = scale_x;
    }

    public float getScaleY() {
        return scale_y;
    }

    public void setScaleY(float scale_y) {
        this.scale_y = scale_y;
    }
}
