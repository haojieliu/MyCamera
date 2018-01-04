package com.cmrx.haojieliu.mycamera;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 2017/12/14.
 *
 * @Email liuhj@cm-rx.com
 */

public class MyPreView extends SurfaceView implements SurfaceHolder.Callback {
    private Camera mCamera;
    private Camera.Parameters mParameters;
    private SurfaceHolder mHolder;
    private int mWidth;

    public MyPreView(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mWidth = DisplayUtil.getScreenMetrics(context).x;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mParameters = mCamera.getParameters();
            mParameters.setPictureFormat(PixelFormat.JPEG);
            mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            PointBean pointBean = getPhonePicture(mParameters.getSupportedPictureSizes());
            mParameters.setPictureSize(pointBean.width, pointBean.height);
            mCamera.setDisplayOrientation(90);
            mCamera.setParameters(mParameters);
            mCamera.cancelAutoFocus();
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
    }

    private PointBean getPhonePicture(List<Camera.Size> supportedPictureSizes) {
        for (Camera.Size size:supportedPictureSizes) {
            if (mWidth!=0){
                if (size.height==mWidth){
                    return new PointBean(size.width,size.height);
                }
            }
        }
        return new PointBean(0,0);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e) {
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
