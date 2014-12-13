package com.carlospinan.androidgame.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mac on 13/12/14.
 */
public class Helper {

    private static Helper instance = null;

    public static Helper getInstance() {

        if (instance == null)
            instance = new Helper();
        return instance;

    }

    public Bitmap loadBitmap(Context context, String path) {

        Bitmap bitmap = null;
        AssetManager mAssetManager = context.getAssets();

        try {
            InputStream is = mAssetManager.open(path);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;

    }

    public float randomRange(float min, float max) {
        float random = min + (float) (Math.random() * (max - min + 1));
        return random;
    }
}
