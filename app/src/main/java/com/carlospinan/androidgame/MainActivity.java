package com.carlospinan.androidgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.carlospinan.androidgame.game.FroggerView;


public class MainActivity extends Activity {

    private FroggerView mFroggerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        mFroggerView = new FroggerView(this);
        setContentView(mFroggerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFroggerView != null) {
            mFroggerView.getThread().resumeGame();
        }
    }

}
