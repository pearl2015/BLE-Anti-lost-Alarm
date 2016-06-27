package com.pearl.subwayguider.locate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 17/06/2016.
 */

public class DrawView extends View {

    private float circleX = 0;
    private float circleY = 0;
    private float circleR = 10;

    public float getCircleX() {
        return circleX;
    }

    public void setCircleX(float circleX) {
        this.circleX = circleX;
    }

    public float getCircleY() {
        return circleY;
    }

    public void setCircleY(float circleY) {
        this.circleY = circleY;
    }

    public float getCircleR() {
        return circleR;
    }

    public void setCircleR(float circleR) {
        this.circleR = circleR;
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(circleX,circleY,circleR,paint);
    }
}
