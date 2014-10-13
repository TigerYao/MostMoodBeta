package com.temp.tiger.app.tabC;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.research.fragmenttabstudy.R;

public class Fragment3Adatper extends BaseAdapter {
	LayoutInflater inflater;
	String[] disteses = null;

	public Fragment3Adatper(Context context, String disteses[]) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		this.disteses = disteses;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return disteses.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return disteses[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View contentView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		contentView = inflater.inflate(R.layout.gridviewitem, null);
		CheckBox rb = (CheckBox) contentView.findViewById(R.id.check_disesase);
		rb.setText(disteses[position]);
		rb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				onCheckedListener.onChecked(disteses[position], position,
						isChecked);
			}
		});
		return contentView;
	}

	onDistesesCheckedListener onCheckedListener;

	// public void setOnCheckedListener(onDistesesCheckedListener
	// onDistesesCheckedListener) {
	// this.onCheckedListener = onDistesesCheckedListener;
	// }

	public interface onDistesesCheckedListener {
		public void onChecked(String dist, int position, boolean isChecked);
	}

	public void setOnCheckedListener(
			onDistesesCheckedListener onDistesesCheckedListener) {
		// TODO Auto-generated method stub
		this.onCheckedListener = onDistesesCheckedListener;
	}
}
