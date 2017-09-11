package jp.ac.dendai.c.jtp.a3dcube2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;



class TouchSurfaceView extends GLSurfaceView {
    public TouchSurfaceView(Context context) {
        super(context);
        mRenderer = new CubeRenderer();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    /*
    * (non-Javadoc)
    * @see android.view.View#onTouchEvent(android.view.MotionEvent)
    * タッチイベント発生し処理
    * タッチ加減による回転角度を計算しレンダリングを行う。
    */

    @Override public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;
                mRenderer.mAngleX += dx * TOUCH_SCALE_FACTOR;
                mRenderer.mAngleY += dy * TOUCH_SCALE_FACTOR;
                requestRender();
        }
        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
    /**
     * Render a cube.
     * 3次元画面にレンダリング処理の内容を定義する。
     */

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private CubeRenderer mRenderer;
    private float mPreviousX;
    private float mPreviousY;

}