package com.yhz.tem.yh.ch.temp.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;

public class MySeekBar extends SeekBar {

	public MySeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
		// TODO Auto-generated method stub
		super.setOnSeekBarChangeListener(l);
	}

	@Override
	public void setProgressDrawable(Drawable d) {
		// TODO Auto-generated method stub
		super.setProgressDrawable(d);
	}

}
