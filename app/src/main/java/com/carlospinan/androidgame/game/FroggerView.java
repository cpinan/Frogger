package com.carlospinan.androidgame.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 13/12/14.
 */
public class FroggerView extends SurfaceView implements SurfaceHolder.Callback {

    private Vibrator vibrator;

    public static final boolean FROGGER_DEBUG_COLLISION = false;

    public static final float GAME_WIDTH = 1024.0f;
    public static final float GAME_HEIGHT = 768.0f;
    public static final float TILE_SIZE = 64.0f;

    private float world_x = 0;
    private float world_y = 0;
    private float sX = -1, sY = -1;

    private FroggerThread mFroggerThread;

    // Game Elements
    private int currentMap[][];
    private Frogger frogger;
    private List<Car> cars;
    private boolean gameOver;
    private Paint paintWin;


    public FroggerView(Context context) {
        super(context);

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mFroggerThread = new FroggerThread(getHolder(), this);
        getHolder().addCallback(this);

    }

    private void prepareGameElements() {

        paintWin = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintWin.setTextSize(100.0f);
        paintWin.setARGB(255, 0, 0, 255);
        paintWin.setTextAlign(Paint.Align.CENTER);
        paintWin.setFakeBoldText(true);

        gameOver = false;
        int i;
        Resources resources = Resources.getInstance();

        currentMap = Maps.map_01;

        frogger = new Frogger(resources.getBitmapFor(Resources.FROGGER));
        frogger.setPosition(GAME_WIDTH * 0.5f, GAME_HEIGHT * 0.5f);


        Car car;
        cars = new ArrayList<>();
        List<String> cars_keys = resources.getCars();
        int index;
        float width = 420;
        float margin = 150;

        // Row 1 cars
        for (i = 0; i < 3; i++) {

            car = new Car(resources.getBitmapFor(cars_keys.get(i)));
            car.setDirectionToRight();
            car.setSpeedX(5.0f);
            car.setPosition(i * width + margin, GAME_HEIGHT - 120);
            cars.add(car);

        }

        // Row 2 cars
        for (i = 0; i < 3; i++) {

            index = (i + 3) % cars_keys.size();
            car = new Car(resources.getBitmapFor(cars_keys.get(index)));
            car.setDirectionToLeft();
            car.setSpeedX(9.0f);
            car.setPosition(i * width + margin, GAME_HEIGHT - 240);
            cars.add(car);

        }

        // Row 3 cars
        for (i = 0; i < 4; i++) {

            index = (int) (Math.random() * cars_keys.size());
            car = new Car(resources.getBitmapFor(cars_keys.get(index)));
            car.setDirectionToRight();
            car.setSpeedX(15.0f);
            car.setPosition(i * width + margin, GAME_HEIGHT - 390);
            cars.add(car);

        }

        // Row 4 cars
        for (i = 0; i < 4; i++) {

            index = (int) (Math.random() * cars_keys.size());
            car = new Car(resources.getBitmapFor(cars_keys.get(index)));
            car.setDirectionToRight();
            car.setSpeedX(25.0f);
            car.setPosition(i * width + margin, GAME_HEIGHT - 520);
            cars.add(car);

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Resources.getInstance().loadResources(getContext());

        prepareGameElements();

        reset();

        if (mFroggerThread.getState() == Thread.State.TERMINATED)
            mFroggerThread = new FroggerThread(getHolder(), this);

        mFroggerThread.resumeGame();
        mFroggerThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        mFroggerThread.pauseGame();
        mFroggerThread.setRunning(false);
        while (retry) {
            try {
                mFroggerThread.join();
                retry = false;
            } catch (InterruptedException ie) {

            }
        }

    }

    public void reset() {

        float x, y;

        x = GAME_WIDTH * 0.5f;
        y = currentMap.length * TILE_SIZE - frogger.getHeight() * 0.5f;
        frogger.setPosition(x, y);

    }

    public void draw(Canvas canvas) {

        int i, j;
        float x, y;
        Resources resources = Resources.getInstance();

        if (sX == -1 && sY == -1) {

            float canvas_width = canvas.getWidth();
            float canvas_height = canvas.getHeight();

            sX = canvas_width / GAME_WIDTH;
            sY = canvas_height / GAME_HEIGHT;

        }

        canvas.save();
        canvas.drawColor(0xFFFFFFFF);

        canvas.scale(sX, sY);

        canvas.translate(world_x, world_y);

        // Draw tile map
        for (i = 0; i < currentMap.length; i++) {
            for (j = 0; j < currentMap[0].length; j++) {
                Bitmap bitmap = null;
                int index = currentMap[i][j];

                if (index == Maps.GRASS) {
                    bitmap = resources.getBitmapFor(Resources.TILE_GRASS);
                } else if (index == Maps.TRACK) {
                    bitmap = resources.getBitmapFor(Resources.TILE_TRACK);
                }

                if (bitmap != null) {

                    x = TILE_SIZE * j;
                    y = TILE_SIZE * i;
                    canvas.drawBitmap(bitmap, x, y, null);

                }

            }
        }

        // Draw frogger
        frogger.onDraw(canvas);

        // Draw cars
        if (!cars.isEmpty()) {

            for (i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                car.onDraw(canvas);
            }

        }

        if (gameOver) {

            canvas.drawText("Ganaste", GAME_WIDTH * 0.5f, GAME_HEIGHT * 0.5f, paintWin);

        }

        canvas.restore();

    }

    public void update(float dt) {

        int i;

        if (!gameOver && frogger.getY() <= frogger.getHeight() * 1.5f) {
            gameOver = true;
            vibrator.vibrate(1000);
        }

        // Update cars
        if (!gameOver && !cars.isEmpty()) {

            for (i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                car.update(dt);

                if (car.isCollide(frogger)) {
                    vibrator.vibrate(500);
                    reset();
                }

            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (sX == -1 && sY == -1)
            return false;

        float x = event.getX() / sX;
        float y = event.getY() / sY;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            float diffY = y - frogger.getY();
            float diffX = x - frogger.getX();

            float new_x = frogger.getX();
            float new_y = frogger.getY();
            float rotation = 0;

            if (Math.abs(diffY) > Math.abs(diffX)) {

                if (diffY < 0) {
                    new_y -= frogger.getSpeed();
                } else {
                    rotation = 180;
                    new_y += frogger.getSpeed();
                }

            } else {
                if (diffX > 0) {
                    rotation = 90;
                    new_x += frogger.getSpeed();
                } else if (diffX < 0) {
                    rotation = -90;
                    new_x -= frogger.getSpeed();
                }
            }

            if (new_x < frogger.getWidth() * 0.5f) {
                new_x = frogger.getWidth() * 0.5f;
            } else if (new_x >= GAME_WIDTH) {
                new_x = GAME_WIDTH;
            }

            if (new_y < 0)
                new_y = 0;
            else if (new_y >= GAME_HEIGHT)
                new_y = GAME_HEIGHT;

            frogger.setPosition(new_x, new_y);
            frogger.setRotation(rotation);

        }

        return true;
    }

    public FroggerThread getThread() {
        return mFroggerThread;
    }
}
