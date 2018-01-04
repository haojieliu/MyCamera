package com.cmrx.haojieliu.mycamera;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


/**
 * Created by admin on 2017/12/12.
 *
 * @Email liuhj@cm-rx.com
 */

public class MaskView extends android.support.v7.widget.AppCompatImageView {
    private Paint mLinePaint;
    private Paint mAreaPaint;
    private Paint mImagePaint;
    private Rect mCenterRect = null;
    private Context mContext;
    private int widthScreen, heightScreen;

    public MaskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        mContext = context;
        Point screenMetrics = DisplayUtil.getScreenMetrics(mContext);
        widthScreen = screenMetrics.x;
        heightScreen = screenMetrics.y;
    }

    private void initPaint() {
        //绘制中间透明区域以及边界
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(5f);
        mLinePaint.setAlpha(30);
        //绘制四周阴影部分
        mAreaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAreaPaint.setColor(Color.GRAY);
        mAreaPaint.setStyle(Paint.Style.FILL);
        mAreaPaint.setAlpha(180);

    }

    @Override
    public void draw(Canvas canvas) {
        if (mCenterRect==null){
            return;
        }
        //绘制四周的阴影
        canvas.drawRect(0,0,widthScreen,mCenterRect.top,mAreaPaint);
        canvas.drawRect(0,mCenterRect.bottom+1,widthScreen,heightScreen,mAreaPaint);
        canvas.drawRect(0,mCenterRect.top,mCenterRect.left-1,mCenterRect.bottom+1,mAreaPaint);
        canvas.drawRect(mCenterRect.right+1,mCenterRect.top,widthScreen,mCenterRect.bottom+1,mAreaPaint);
        //绘制目标透明区域
        canvas.drawRect(mCenterRect,mLinePaint);
        mImagePaint=new Paint();
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.head),mCenterRect.left,mCenterRect.top,mImagePaint);
        super.draw(canvas);
    }

    public void clearCenterRect() {
        this.mCenterRect = null;
    }

    public void setCenterRect(Rect centerRect) {
        mCenterRect = centerRect;
        invalidate();
    }
}
