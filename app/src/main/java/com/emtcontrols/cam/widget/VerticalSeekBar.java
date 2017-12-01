package com.emtcontrols.cam.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class VerticalSeekBar extends SeekBar {

    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {

        Paint linePaint = new Paint();
   /*     linePaint.setColor(Color.GRAY); //Set the colour
        linePaint.setStyle(Paint.Style.STROKE); //set the style*/
        linePaint.setColor(0x333333);
        linePaint.setStrokeWidth(2); //Stoke width

        int seekBarWidth = getWidth();
        int seekBarHeight = getHeight()-seekBarWidth;

        int sickBarStepSize = 1;
        float oneScaleSize = (float) seekBarHeight/sickBarStepSize;
        float barStart = (float)seekBarWidth/2;

        for (int i=0; i <= sickBarStepSize; i++) {

            float y = oneScaleSize * i + barStart;
            c.drawLine(0, y, seekBarWidth, y, linePaint);
        }
        c.drawRect(0, barStart, seekBarWidth, seekBarHeight + barStart, linePaint);

        c.rotate(-90);
        c.translate(-getHeight(), 0);
        super.onDraw(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                int i=0;
                i=getMax() - (int) (getMax() * event.getY() / getHeight());
                setProgress(i);
                Log.i("Progress",getProgress()+"");
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;

            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

}