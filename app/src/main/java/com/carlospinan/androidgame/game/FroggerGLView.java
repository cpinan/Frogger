package com.carlospinan.androidgame.game;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by mac on 13/12/14.
 */
public class FroggerGLView extends GLSurfaceView {

    private FroggerRenderer mFroggerRenderer;

    public FroggerGLView(Context context) {
        super(context);

        mFroggerRenderer = new FroggerRenderer(context);
        setRenderer(mFroggerRenderer);
        setEGLContextClientVersion(2);

    }
}
