package com.carlospinan.androidgame.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by mac on 13/12/14.
 */
public class FroggerThread extends Thread {

    public static final long FPS = 24L;

    private SurfaceHolder mSurfaceHolder;
    private FroggerView mFroggerView;

    private boolean running;
    private boolean pause;
    private long start_time;

    public FroggerThread(SurfaceHolder holder, FroggerView view) {

        this.mSurfaceHolder = holder;
        this.mFroggerView = view;
        this.running = true;
        this.pause = false;

    }

    @Override
    public void run() {

        Canvas canvas = null;
        start_time = System.currentTimeMillis();

        while (running) {

            if (!pause) {

                try {
                    canvas = mSurfaceHolder.lockCanvas();
                    synchronized (mSurfaceHolder) {
                        float dt = 1.0f / (System.currentTimeMillis() - (start_time - 1));
                        start_time = System.currentTimeMillis();
                        mFroggerView.draw(canvas);
                        mFroggerView.update(dt);
                    }

                } finally {
                    if (canvas != null)
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                }

                try {
                    Thread.sleep(1000 / FPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        }

    }

    public void pauseGame() {
        start_time = System.currentTimeMillis();
        pause = true;
    }

    public void resumeGame() {
        start_time = System.currentTimeMillis();
        pause = false;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

}
