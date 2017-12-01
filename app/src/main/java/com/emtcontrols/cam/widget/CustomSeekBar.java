package com.emtcontrols.cam.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.SeekBar;

class CustomSeekBar extends SeekBar {

    public CustomSeekBar(Context context) {
        super(context);
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub

        Paint linePaint = new Paint();
        /*linePaint.setColor(Color.GRAY); //Set the colour
        linePaint.setStyle(Paint.Style.STROKE); //set the style*/
        linePaint.setColor(0x333333);
        linePaint.setStrokeWidth(2); //Stoke width

        int seekBarHeight = getHeight();
        int seekBarLength = getWidth() - seekBarHeight;

        int sickBarStepSize = 1;
        float oneScaleSize = (float) seekBarLength/sickBarStepSize;
        float barStart = (float)seekBarHeight/2;

        for (int i=0; i <= sickBarStepSize; i++) {

            float x = oneScaleSize * i + barStart;
            canvas.drawLine(x, 0, x, seekBarHeight, linePaint);
        }
        canvas.drawRect(barStart, 0, seekBarLength + barStart, seekBarHeight, linePaint);

        super.onDraw(canvas);
    }
}