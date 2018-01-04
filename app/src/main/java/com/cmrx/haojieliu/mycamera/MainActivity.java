package com.cmrx.haojieliu.mycamera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private FrameLayout mFrameLayout;
    private MaskView mMaskView;
    private Button mButton;
    private MyPreView mMyPreView;
    private Camera mCamera;

    private int Width_Pic,Heighe_Pic;
    private int mDpRectWidth = 150;
    private int mDpRectHeight = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mFrameLayout = ((FrameLayout) findViewById(R.id.camera_preview));
        mButton = ((Button) findViewById(R.id.button_camera));
        mCamera = Camera.open();
        mFrameLayout.addView(new MyPreView(this, mCamera));
        mMaskView = new MaskView(this, null);
        mFrameLayout.addView(mMaskView);
        if (mMaskView != null){
            Rect screenCenterRect = creatCenterRect(DisplayUtil.dip2px(this, mDpRectWidth)
                    ,DisplayUtil.dip2px(this, mDpRectHeight));
            mMaskView.setCenterRect(screenCenterRect);
        }
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Point point= creatPicRect(DisplayUtil.dip2px(MainActivity.this, mDpRectWidth),
                        DisplayUtil.dip2px(MainActivity.this, mDpRectHeight));
               doTakePicture(point.x,point.y);
            }
        });
    }
    /**
     * 计算拍照后图片中间矩形的宽度和高度
     *
     * @param width
     * @param height
     * @return
     */
    public Point creatPicRect(int width, int height) {
        int ScreenWid = DisplayUtil.getScreenMetrics(this).x;
        int ScreenHei = DisplayUtil.getScreenMetrics(this).y;
        int SaveWid = mCamera.getParameters().getPictureSize().height;
        int SaveHei = mCamera.getParameters().getPictureSize().width;
        float wRate = (float) SaveWid / (float) ScreenWid;
        float hRate = (float) SaveHei / (float) ScreenHei;
        float rate = (wRate <= hRate) ? wRate : hRate;
        int wRectPic = (int) (width * wRate);
        int hRectPic = (int) (height * hRate);
        return new Point(wRectPic, hRectPic);
    }
    /**
     * 生成中间透明矩形区域
     *
     * @param width
     * @param height
     * @return
     */
    public Rect creatCenterRect(int width, int height) {
        int left = DisplayUtil.getScreenMetrics(this).x / 2 - width / 2;
        int top = DisplayUtil.getScreenMetrics(this).y / 2 - height / 2;
        int right = left + width;
        int bottom = top + height;
        return new Rect(left, top, right, bottom);
    }

    public void doTakePicture(int width, int height){
        Width_Pic = width;
        Heighe_Pic = height;
                mCamera.takePicture(new Camera.ShutterCallback() {
                    @Override
                    public void onShutter() {

                    }
                }, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        Bitmap bitmap = null;
                        if (null != data) {
                            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            //测试代码
                            int width1 = bitmap.getWidth();
                            int height1 = bitmap.getHeight();


                            mCamera.stopPreview();
                        }
                        if (bitmap != null) {
                            Bitmap rotateBitmap = ImageUtil.getRotateBitmap(bitmap, 90.0f);
                            int x = rotateBitmap.getWidth() / 2 - Width_Pic / 2;
                            int y = rotateBitmap.getHeight() / 2 - Heighe_Pic / 2;
                            Bitmap rectBitmap = Bitmap.createBitmap(rotateBitmap, x, y, Width_Pic, Heighe_Pic);
                                FileUtil.saveBitmap(rectBitmap);
                            if (!rotateBitmap.isRecycled()) {
                                rotateBitmap.recycle();
                                rotateBitmap = null;
                            }
                            if (!rectBitmap.isRecycled()) {
                                rectBitmap.recycle();
                                rectBitmap = null;
                            }
                        }
                        if (!bitmap.isRecycled()){
                            bitmap.recycle();
                            bitmap=null;
                        }
                    }
                });

    }
}
