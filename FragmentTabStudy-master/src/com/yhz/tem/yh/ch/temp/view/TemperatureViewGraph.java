package com.yhz.tem.yh.ch.temp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

public class TemperatureViewGraph extends View {
	private int realLengh = 0;

	public TemperatureViewGraph(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		xEnds = dm.widthPixels;
		// realHe = dm.heightPixels;
		nowX = xEnds;
	}

	public void setType(int type) {
		realLengh = (type) * 100;

		setMeasuredDimension(realLengh, xEnds);
		scrollTo(0, 0);

	}

	float points[][] = null;
	int tem = 35;

	public void setCurrentTemps(float[][] points) {
		this.points = points;
		invalidate();
	}

	private int xEnds = 800;
	private int heightw = 600;
	String time = ":00";
	String preTime = "";
	private VelocityTracker mVelocityTracker;

	public void setTime(String time, String preTime) {
		this.time = time;
		this.preTime = preTime;
		// invalidate();
	}

	int realHeight;
	int nowX;

	@Override
	protected void onDraw(Canvas canvas) {

		tem = 35;
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);// ȥ���
		paint.setColor(Color.BLUE);// ��ɫ
		Paint paint1 = new Paint();
		paint1.setAntiAlias(true);// ȥ���
		paint1.setColor(Color.BLUE);
		paint1.setAlpha(200);
		paint1.setStyle(Style.FILL);
		paint.setTextSize(18); // ���������ִ�С
		int ydegree = heightw - 50;
		int index = 0;
		if (points != null && points.length > 0) {

			if (points.length == 1) {
				canvas.drawCircle(canvasXY(points[0])[0],
						canvasXY(points[0])[1], 10, paint1);
				return;
			} else
				for (int i = 0; i < points.length - 1; i++) {

					float startP[] = canvasXY(points[i]);
					float endP[] = canvasXY(points[i + 1]);
					if (endP[0] - startP[0] < 200) {
						canvas.drawLine(startP[0], startP[1], endP[0], endP[1],
								paint1);
					}
					canvas.drawCircle(startP[0], startP[1], 10, paint1);

					if (i == points.length - 2) {
						canvas.drawCircle(endP[0], endP[1], 10, paint1);
					}

					Path path = new Path();
					// Paint pain=new Paint();
					// pain.setColor(Color.);
					if (points[i + 1][0] >= 38f && points[i + 1][0] < 39) {
						paint1.setColor(Color.YELLOW);
						paint1.setAlpha(200);
					} else if (points[i + 1][0] >= 39) {
						paint1.setColor(Color.rgb(205, 133, 63));
						paint1.setAlpha(200);
					} else {
						paint1.setColor(Color.rgb(0, 191, 255));
						paint1.setAlpha(200);
					}

					path.moveTo(startP[0], realHeight);
					path.lineTo(startP[0], startP[1]);
					path.lineTo(endP[0], endP[1]);
					path.lineTo(endP[0], realHeight);
					path.close();
					canvas.drawPath(path, paint1);
					paint1.setColor(Color.BLUE);
					paint1.setAlpha(200);
				}
			canvas.drawLine(0, ydegree, realLengh, ydegree, paint);
			tem++;
			while (tem < 44f) {
				if (index == 10) {
					index = 0;
					canvas.drawLine(0, ydegree, realLengh, ydegree, paint);
					tem++;
				}

				index = index + 1;
				ydegree = ydegree - (heightw / 10) / 10;
			}
			canvas.drawLine(0, realHeight, realLengh, realHeight, paint);
			for (int i = 0; i <= realLengh / 100; i++) {
				int yWith = i * 100 + 10;
				canvas.drawLine(yWith, realHeight - 30, yWith, realHeight,
						paint);
				canvas.drawText(preTime + (i + 1) + time, yWith - 10,
						realHeight - 30, paint);
				if (time.equals(":00"))
					for (int indexs = 1; indexs < 6; indexs++) {
						if (indexs == 3) {
							canvas.drawLine(yWith + 100 / 6 * indexs,
									realHeight - 20, yWith + 100 / 6 * indexs,
									realHeight, paint);
						} else
							canvas.drawLine(yWith + 100 / 6 * indexs,
									realHeight - 10, yWith + 100 / 6 * indexs,
									realHeight, paint);
						if (i == realLengh / 100) {
							canvas.drawLine(yWith + 100, realHeight - 30,
									yWith + 100, realHeight, paint);
							canvas.drawText(preTime + (i + 1) + time,
									yWith + 90, realHeight - 30, paint);
						}
					}
			}

		}
		super.onDraw(canvas);// ��дonDraw����
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();

		if (mVelocityTracker == null) {
			// ���VelocityTracker���һ��ʵ�����
			mVelocityTracker = VelocityTracker.obtain();
		}
		// ��Ӹ���
		// ����ǰ���ƶ��¼����ݸ�VelocityTracker����
		mVelocityTracker.addMovement(event);
		int startx = 0;
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			startx = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int addnum = (int) event.getX() - startx;
			final VelocityTracker velocityTracker = mVelocityTracker;
			velocityTracker.computeCurrentVelocity(1000);
			System.out.println(mVelocityTracker.getXVelocity());
			if (Math.abs(mVelocityTracker.getXVelocity()) > 1000) {
				addnum = 100;
			} else {
				addnum = 10;
			}
			if (mVelocityTracker.getXVelocity() < 0)
				nowX = (int) (nowX + addnum);
			else
				nowX = (int) (nowX - addnum);
			if (nowX < realLengh - xEnds && nowX > 0)
				scrollTo(nowX, 0);
			else if (nowX > 0) {
				scrollTo(realLengh - xEnds, 0);
			} else {
				scrollTo(0, 0);
			}

			break;

		case MotionEvent.ACTION_CANCEL:
			System.out.println(mVelocityTracker.getXVelocity() + "???????????");
			// ������Ի�ȡ����������
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}

			break;
		}

		return true;
	}

	private float[] canvasXY(float[] p) {
		float top = (float) (heightw - (p[0] - 35) * (heightw / 10)) - 50;

		float y = (p[1] - 1) * 100 + 10;
		return new float[] { y, top };
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		heightw = ((int) (h / 100) * 100);
		xEnds = w;
		realHeight = h;
		setMeasuredDimension(xEnds, heightw);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		setMeasuredDimension(realLengh, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
