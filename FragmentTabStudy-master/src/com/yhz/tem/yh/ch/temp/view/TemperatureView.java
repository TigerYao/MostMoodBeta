
package com.yhz.tem.yh.ch.temp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.research.fragmenttabstudy.R;

public class TemperatureView extends View {
    public TemperatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        xEnds = dm.widthPixels;
        realHe = dm.heightPixels;

    }

    int tem = 35;
    private float currentTem = 35;
    private int xEnds = 800;
    private int yends = 0;
    private int realHe;

    public void setCurrentTem(float currentTem) {
        this.currentTem = currentTem;
        invalidate();
    }

    String time = "00:00";

    public void setTime(String time) {
        this.time = time;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        tem = 35;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);//
        paint.setColor(Color.BLUE);//
        Paint paint1 = new Paint();
        paint1.setAntiAlias(true);//
        paint1.setColor(Color.rgb(135, 206, 250));
        paint1.setStyle(Style.FILL);
        paint.setTextSize(18); //
        int ydegree = drawGraduation(canvas, paint, paint1);
        // drawCenterTime(canvas, paint1, ydegree);
        super.onDraw(canvas);//
    }

    /**
     * @param canvas
     * @param paint
     * @param paint1
     * @return
     */
    private int drawGraduation(Canvas canvas, Paint paint, Paint paint1) {
        int ydegree = yends - 30;
        int index = 0;
        float top = (float) (ydegree - (currentTem - 35) * (yends / 10));
        Paint pa = paint1;
        pa.setColor(Color.rgb(0, 191, 255));
        canvas.drawRect(0, top, xEnds, realHe, pa);
        canvas.drawLine(0, ydegree, 17, ydegree, paint);
        canvas.drawLine(xEnds, ydegree, xEnds - 18, ydegree, paint);
        canvas.drawText(tem + "", 18, ydegree + 4, paint);
        canvas.drawText(tem + "", xEnds - 37, ydegree + 4, paint);
        tem++;
        while (tem < 44) {
            if (index == 10) {
                index = 0;
                canvas.drawLine(0, ydegree, 17, ydegree, paint);
                canvas.drawLine(xEnds, ydegree, xEnds - 18, ydegree, paint);
                canvas.drawText(tem + "", 18, ydegree + 4, paint);
                canvas.drawText(tem + "", xEnds - 37, ydegree + 4, paint);
                tem++;
            } else {
                canvas.drawLine(0, ydegree, 10, ydegree, paint);
                canvas.drawLine(xEnds, ydegree, xEnds - 10, ydegree, paint);
            }
            index = index + 1;
            ydegree = ydegree - (yends / 10) / 10;
        }
        paint.setTextSize(100);
        paint.setColor(Color.BLUE);
        paint.setStrokeCap(Cap.ROUND);

        return ydegree;
    }

    // /**
    // * @param canvas
    // * @param paint1
    // * @param ydegree
    // */
    // private void drawCenterTime(Canvas canvas, Paint paint1, int ydegree) {
    // Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
    // R.drawable.temp_last_bk);
    // bitmap = GraphicsBitmapUtils.zoomBitmap(bitmap, bitmap.getWidth(),
    // bitmap.getHeight());
    // // canvas.drawBitmap(bitmap, null, recf, paint1);
    // int leftb = (xEnds - bitmap.getWidth()) / 2;
    // int topb = bitmap.getHeight() / 2 + 25 + 30;
    // Paint last_test = new Paint();
    // last_test.setTextSize(20);
    // last_test.setColor(Color.BLACK);
    //
    // canvas.drawBitmap(bitmap, leftb, ydegree + (yends / 10) * 2 + 20 + 30,
    // paint1);
    // Bitmap bitmapc = BitmapFactory.decodeResource(getResources(),
    // R.drawable.last_clock);
    // canvas.drawBitmap(bitmapc, bitmap.getWidth() +30+leftb
    // -(bitmap.getWidth()/2), ydegree
    // + (yends / 10) * 2 + 20 + 30, paint1);
    // canvas.drawText(time,
    // bitmap.getWidth() +30+leftb -(bitmap.getWidth()/2) + bitmapc.getWidth(),
    // ydegree
    // + (yends / 10) * 2 + topb, last_test);
    // canvas.drawText("最近一次测量时间", leftb + 30, ydegree + (yends / 10) * 2
    // + topb, last_test);
    // }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        // TODO Auto-generated method stub
        yends = ((int) (bottom / 100)) * 100;
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        yends = ((int) (h / 100)) * 100;
        xEnds = w;
        super.onSizeChanged(w, h, oldw, oldh);

    }
}
