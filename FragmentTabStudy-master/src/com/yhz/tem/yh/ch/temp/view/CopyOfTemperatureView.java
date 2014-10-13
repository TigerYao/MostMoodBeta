package com.yhz.tem.yh.ch.temp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class CopyOfTemperatureView extends View {
	public CopyOfTemperatureView(Context context, AttributeSet attrs) {
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
	private int yends = 600;
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
		paint1.setColor(Color.BLUE);
		paint1.setAlpha(200);
		paint1.setStyle(Style.FILL);
		paint.setTextSize(18); //
		int ydegree = yends - 50;
		int index = 0;
		// canvas.drawLine(0, ydegree, 17, ydegree, paint);
		// canvas.drawLine(xEnds, ydegree, xEnds - 18, ydegree, paint);
		canvas.drawText(tem + "", 18, ydegree + 4, paint);
		canvas.drawText(tem + "", xEnds - 37, ydegree + 4, paint);
		// float top = (float) (yends - (currentTem - 35) * (yends / 10)) - 50;
		tem++;
		while (tem < 44f) {
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
		super.onDraw(canvas);//
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		yends = ((int) (h / 100)) * 100;
		xEnds = w;
		setMeasuredDimension(xEnds, yends);
		super.onSizeChanged(w, h, oldw, oldh);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
