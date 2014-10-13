package com.rfstar.kevin;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Scroller;

public class HeatRateView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private long currentTime;
    private Scroller mScroller;
    private ArrayList<MyPoint> points;
    private Paint paint_brokenLine;
    private float withGap = 10;
    private float baseTem = 20.00f;

    public HeatRateView(Context context) {
	super(context);
	init(context);
    }

    public HeatRateView(Context context, AttributeSet attrs) {

	super(context, attrs);
	init(context);

    }

    public HeatRateView(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	init(context);
    }

    private void init(Context ctx) {
	mScroller = new Scroller(ctx);
	points = new ArrayList<HeatRateView.MyPoint>();
	this.mHolder = this.getHolder();
	this.mHolder.addCallback(this);
	paint_brokenLine = new Paint();
	paint_brokenLine.setStrokeWidth(1f);
	paint_brokenLine.setColor(Color.WHITE);
	paint_brokenLine.setAntiAlias(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2,
	    int arg3) {
	// TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
	// TODO Auto-generated method stub
	currentTime = System.currentTimeMillis();
	MyPoint myPoint = new MyPoint(currentTime, 0);
	points.add(myPoint);
	onDraws();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
	// TODO Auto-generated method stub

    }

    public void onDraws() {
	Canvas canvas = mHolder.lockCanvas(new Rect(0, 0, 10000, getHeight()));
	canvas.drawColor(Color.WHITE, Mode.CLEAR);
	drawStraiLine(canvas);
	drawDot(canvas);
	mHolder.unlockCanvasAndPost(canvas);
	
    }

    public void drawStraiLine(Canvas canve) {
	int height = getHeight();
	int with = getWidth();

	float hgap = height / 20f;
	Paint paint = new Paint();
	paint.setAlpha(100);
	paint.setColor(Color.GRAY);
	for (int i = 0; i < 21; i += 1) {
	    canve.drawLine(0, hgap * i, points.size(), hgap * i, paint);
	}
	for (float i = 0; i < with; i += 4) {
	    canve.drawLine(i * withGap, 0f, i * withGap, height, paint);
	}
    }

    public void drawDot(Canvas canve) {

	float hgap = getHeight() / 20f;
	if (points.size() >= 1) {
	    for (int i = 0; i < points.size() - 1; i++) {
		MyPoint firsPoint = points.get(i);
		MyPoint secondPoint = points.get(i + 1);
		float startX = firsPoint.getxP() * withGap;
		float stopX = secondPoint.getxP() * withGap;
		float startY = this.getHeight()
			- (firsPoint.getyP() / 100 - baseTem) * hgap;
		float stopY = this.getHeight()
			- (secondPoint.getyP() / 100 - baseTem) * hgap;
		canve.drawLine(startX, startY, stopX, stopY,
			this.paint_brokenLine);
		canve.drawCircle(startX + 5, startY + 5, 10, paint_brokenLine);
		canve.drawCircle(stopX + 5, stopY + 5, 10, paint_brokenLine);
	    }
	} else {
	    float cx = points.get(0).getxP() + 1;
	    float cy = this.getHeight() - points.get(0).getyP() + 1;
	    canve.drawCircle(cx, cy, 2, paint_brokenLine);
	}

    }

    public void addPoints(long xP, float yP) {
	System.out.println(xP - currentTime + ">>>>>>>");
	float xp = (float) (xP - currentTime) / 1000f;
	MyPoint myPoint = new MyPoint(xp, yP);
	points.add(myPoint);
    }

    class MyPoint {
	private float xP;
	private float yP;
	private Paint paint;

	public MyPoint(float xP, float yP) {

	    this.xP = xP;
	    this.yP = yP;
	}

	public MyPoint(float xP, float yP, Paint paint) {

	    this.xP = xP;
	    this.yP = yP;
	    this.paint = paint;
	}

	public float getxP() {
	    return xP;
	}

	public float getyP() {
	    return yP;
	}

	public Paint getPaint() {
	    return paint;
	}

    }

    // 调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
	int dx = fx - mScroller.getFinalX();
	int dy = fy - mScroller.getFinalY();
	smoothScrollBy(dx, dy);
    }

    // 调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {

	// 设置mScroller的滚动偏移量
	mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx,
		dy);
	invalidate();// 这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {

	// 先判断mScroller滚动是否完成
	if (mScroller.computeScrollOffset()) {

	    // 这里调用View的scrollTo()完成实际的滚动
	    scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

	    // 必须调用该方法，否则不一定能看到滚动效果
	    System.out.println("ddddddddd"+mScroller.getCurrX());
	    postInvalidate();
	}
	super.computeScroll();
    }
@Override
public boolean onTouchEvent(MotionEvent event) {
    smoothScrollBy((int) (points.get(points.size() - 1).getxP() * withGap),
		0);
    return true;
    //return super.onTouchEvent(event);
}
}
