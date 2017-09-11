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
//    @Override public boolean onTouchEvent(MotionEvent e) {
//        float x = e.getX();
//        float y = e.getY();
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                float dx = x - mPreviousX;
//                float dy = y - mPreviousY;
//                mRenderer.mAngleX += dx * TOUCH_SCALE_FACTOR;
//                mRenderer.mAngleY += dy * TOUCH_SCALE_FACTOR;
//                requestRender();
//        }
//        mPreviousX = x;
//        mPreviousY = y;
//        return true;
//    }
    /**
     * Render a cube.
     * 3次元画面にレンダリング処理の内容を定義する。
     */
    private class CubeRenderer implements GLSurfaceView.Renderer {
        public CubeRenderer() {
            mCube = new Cube();
        }
        /*
         * (non-Javadoc)
         * @see android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.microedition.khronos.opengles.GL10)
         * システムでレンダリング処理が行うとき呼び出すメソッド
         */
        public void onDrawFrame(GL10 gl) {
            /*
             * Usually, the first thing one might want to do is to clear
             * the screen. The most efficient way of doing this is to use
             * glClear().
             */
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            /*
             * Now we're ready to draw some 3D objects
             *回転しながらキューブをレンダリングする。
             */
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glTranslatef(0, 0, -3.0f);
            gl.glRotatef(30f, 1, 1, 0);
//            gl.glRotatef(mAngleY, 1, 0, 0);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            mCube.draw(gl);
        }
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);
             /*
              * Set our projection matrix. This doesn't have to be done
              * each time we draw, but usually a new projection needs to
              * be set when the viewport is resized.
              */
            float ratio = (float) width / height;
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        }
        /*
         * (non-Javadoc)
         * @see android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
         * 3次元空間生成し初期化処理
         */
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            /*
             * By default, OpenGL enables features that improve quality
             * but reduce performance. One might want to tweak that
             * especially on software renderer.
             */
            gl.glDisable(GL10.GL_DITHER);
            /*
             * Some one-time OpenGL initialization can be made here
             * probably based on features of this particular context
             */
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                    GL10.GL_FASTEST);
            gl.glClearColor(1,1,1,1);
            gl.glEnable(GL10.GL_CULL_FACE);
            gl.glShadeModel(GL10.GL_SMOOTH);
            gl.glEnable(GL10.GL_DEPTH_TEST);
        }
        private Cube mCube;
        public float mAngleX;
        public float mAngleY;
    }
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private CubeRenderer mRenderer;
    private float mPreviousX;
    private float mPreviousY;
}