package com.carlospinan.androidgame.game;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mac on 13/12/14.
 */
public class Resources {

    private static Resources instance = null;
    private static HashMap<String, Bitmap> cache = null;

    public static final String CAR_01 = "car_01.png";
    public static final String CAR_02 = "car_02.png";
    public static final String CAR_03 = "car_03.png";
    public static final String CAR_04 = "car_04.png";
    public static final String CAR_05 = "car_05.png";
    public static final String CAR_06 = "car_06.png";
    public static final String CAR_07 = "car_07.png";

    public static final String FROGGER = "frogger.png";
    public static final String TILE_GRASS = "grass.png";
    public static final String TILE_TRACK = "track.png";

    public static Resources getInstance() {
        if (instance == null) {
            instance = new Resources();
            cache = new HashMap<>();
        }
        return instance;
    }

    public void loadResources(Context context) {

        Helper helper = Helper.getInstance();

        cache.put(CAR_01, helper.loadBitmap(context, CAR_01));
        cache.put(CAR_02, helper.loadBitmap(context, CAR_02));
        cache.put(CAR_03, helper.loadBitmap(context, CAR_03));
        cache.put(CAR_04, helper.loadBitmap(context, CAR_04));
        cache.put(CAR_05, helper.loadBitmap(context, CAR_05));
        cache.put(CAR_06, helper.loadBitmap(context, CAR_06));
        cache.put(CAR_07, helper.loadBitmap(context, CAR_07));
        cache.put(FROGGER, helper.loadBitmap(context, FROGGER));
        cache.put(TILE_GRASS, helper.loadBitmap(context, TILE_GRASS));
        cache.put(TILE_TRACK, helper.loadBitmap(context, TILE_TRACK));

    }

    public Bitmap getBitmapFor(String key) {
        return cache.get(key);
    }

    public List<String> getCars() {
        List<String> list = new ArrayList<>();
        list.add(CAR_01);
        list.add(CAR_02);
        list.add(CAR_03);
        list.add(CAR_04);
        list.add(CAR_05);
        list.add(CAR_06);
        list.add(CAR_07);
        return list;
    }

}
