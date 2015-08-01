package com.yumikoazu.appleprogressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.yumikoazu.appleprogressbar.R;

/**
 * Created by joker on 2015/8/1.
 */
public class AppleProgressBar extends View {

    private int mBarColor;
    private int mSpeed;
    private int mProgress;

    private int mCircleWidth;
    private int mRadius;

    private Paint mPaint;

    public AppleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AppleProgressBar);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.AppleProgressBar_barColor:
                    mBarColor = a.getColor(attr, Color.BLUE);
                    break;

                case R.styleable.AppleProgressBar_circleWidth:
                    mCircleWidth = a.getDimensionPixelSize(
                            attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_PX,
                                    20,
                                    getResources().getDisplayMetrics()
                            )
                    );
                    break;

                case R.styleable.AppleProgressBar_speed:
                    mSpeed = a.getInteger(attr, 20);
                    break;

                case R.styleable.AppleProgressBar_radius:
                    mRadius = (int) a.getDimension(
                            attr, TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_PX,
                                    80,
                                    getResources().getDisplayMetrics()
                            )
                    );
                    break;
            }
        }

        a.recycle();

        mPaint = new Paint();

        new Thread() {
            @Override
            public void run() {

                while (mProgress <= 360) {
                    mProgress++;

                    postInvalidate();

                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        Log.d("center", center + "");

        Log.d("mradius", mRadius + "");

        int radius = center-mCircleWidth/2;// 半径

        Log.d("radius", radius + "");
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.parseColor("#1976D2"));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(center, center, mRadius, mPaint);

        mPaint.setStyle(Paint.Style.FILL);

        canvas.drawRect(center - mRadius / 4, center - mRadius / 4, center + mRadius / 4, center + mRadius / 4, mPaint);


        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        RectF oval = new RectF(center-mRadius+3,center-mRadius+3,center+mRadius-3,center+mRadius-3);

        canvas.drawArc(
                oval,//弧线所使用的矩形区域大小
                -90,//开始角度
                mProgress,//扫过的角度
                false,//是否使用中心
                mPaint
        );

    }
}
