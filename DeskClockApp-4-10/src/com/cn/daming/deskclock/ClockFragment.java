package com.cn.daming.deskclock;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ClockFragment extends Fragment implements OnItemClickListener {

	static final String PREFERENCES = "AlarmClock";

	/**
	 * This must be false for production. If true, turns on logging, test code,
	 * etc.
	 */
	static final boolean DEBUG = false;

	private SharedPreferences mPrefs;
	public LayoutInflater mFactory;
	public ListView mAlarmsList;
	private Cursor mCursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.alarm_clock, null);
		// 取自定义布局的LayoutInflaterg
		mFactory = inflater;
		// 取getSharedPreferences中key==“AlarmClock”的值
		mPrefs = getActivity().getSharedPreferences(PREFERENCES, 0);
		// 获取闹钟的cursor
		mCursor = Alarms.getAlarmsCursor(getActivity().getContentResolver());
		// 更新布局界面
		updateLayout(view);

		return view;// super.onCreateView(inflater, container,
					// savedInstanceState);
	}

	// 加载更新界面布局
	private void updateLayout(View view) {

		mAlarmsList = (ListView) view.findViewById(R.id.alarms_list);
		AlarmTimeAdapter adapter = new AlarmTimeAdapter(getActivity(), mCursor);
		mAlarmsList.setAdapter(adapter);
		mAlarmsList.setVerticalScrollBarEnabled(true);
		mAlarmsList.setOnItemClickListener(this);
		mAlarmsList.setOnCreateContextMenuListener(getActivity());

		View addAlarm = view.findViewById(R.id.add_alarm);
		addAlarm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				addNewAlarm();
			}
		});
		// Make the entire view selected when focused.
		addAlarm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				v.setSelected(hasFocus);
			}
		});

		// ImageButton deskClock = (ImageButton)
		// view.findViewById(R.id.desk_clock_button);
		// deskClock.setOnClickListener(new View.OnClickListener() {
		// public void onClick(View v) {
		//
		// }
		// });
	}

	private void addNewAlarm() {
		startActivity(new Intent(getActivity(), SetAlarm.class));
	}

	/**
	 * listview的适配器继承CursorAdapter
	 * 
	 * @author wangxianming 也可以使用BaseAdapter
	 */
	private class AlarmTimeAdapter extends CursorAdapter {
		public AlarmTimeAdapter(Context context, Cursor cursor) {
			super(context, cursor);
		}

		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View ret = mFactory.inflate(R.layout.alarm_time, parent, false);

			DigitalClock digitalClock = (DigitalClock) ret.findViewById(R.id.digitalClock);
			digitalClock.setLive(false);
			return ret;
		}

		// 把view绑定cursor的每一项
		public void bindView(View view, Context context, Cursor cursor) {
			final Alarm alarm = new Alarm(cursor);

			View indicator = view.findViewById(R.id.indicator);

			// Set the initial resource for the bar image.
			final ImageView barOnOff = (ImageView) indicator.findViewById(R.id.bar_onoff);
			barOnOff.setImageResource(alarm.enabled ? R.drawable.ic_indicator_on : R.drawable.ic_indicator_off);

			// Set the initial state of the clock "checkbox"
			final CheckBox clockOnOff = (CheckBox) indicator.findViewById(R.id.clock_onoff);
			clockOnOff.setChecked(alarm.enabled);

			// Clicking outside the "checkbox" should also change the state.
			// 对checkbox设置监听，使里外一致
			indicator.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					clockOnOff.toggle();
					updateIndicatorAndAlarm(clockOnOff.isChecked(), barOnOff, alarm);
				}
			});

			DigitalClock digitalClock = (DigitalClock) view.findViewById(R.id.digitalClock);

			// set the alarm text
			final Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, alarm.hour);
			c.set(Calendar.MINUTE, alarm.minutes);
			digitalClock.updateTime(c);
			digitalClock.setTypeface(Typeface.DEFAULT);

			// Set the repeat text or leave it blank if it does not repeat.
			TextView daysOfWeekView = (TextView) digitalClock.findViewById(R.id.daysOfWeek);
			final String daysOfWeekStr = alarm.daysOfWeek.toString(getActivity(), false);
			if (daysOfWeekStr != null && daysOfWeekStr.length() != 0) {
				daysOfWeekView.setText(daysOfWeekStr);
				daysOfWeekView.setVisibility(View.VISIBLE);
			} else {
				daysOfWeekView.setVisibility(View.GONE);
			}

			// Display the label
			TextView labelView = (TextView) view.findViewById(R.id.label);
			if (alarm.label != null && alarm.label.length() != 0) {
				labelView.setText(alarm.label);
				labelView.setVisibility(View.VISIBLE);
			} else {
				labelView.setVisibility(View.GONE);
			}
		}
	};

	// 更新checkbox
	private void updateIndicatorAndAlarm(boolean enabled, ImageView bar, Alarm alarm) {
		bar.setImageResource(enabled ? R.drawable.ic_indicator_on : R.drawable.ic_indicator_off);
		Alarms.enableAlarm(getActivity(), alarm.id, enabled);
		if (enabled) {
			SetAlarm.popAlarmSetToast(getActivity(), alarm.hour, alarm.minutes, alarm.daysOfWeek);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 * 创建上下文菜单
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 * 设置菜单的点击事件的处理
	 */
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.menu_item_settings:
	// startActivity(new Intent(getActivity(), SettingsActivity.class));
	// return true;
	// case R.id.menu_item_desk_clock:
	// // modify by wangxianming in 2012-4-14
	// // startActivity(new Intent(getActivity(), DeskClock.class));
	// return true;
	// case R.id.menu_item_add_alarm:
	// addNewAlarm();
	// return true;
	// default:
	// break;
	// }
	// return super.onOptionsItemSelected(item);
	// }

	// /*
	// * (non-Javadoc)
	// *
	// * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu) 创建菜单
	// */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.alarm_list_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long) 创建菜单的点击事件响应
	 */
	public void onItemClick(AdapterView<?> adapterView, View v, int pos, long id) {
		Intent intent = new Intent(getActivity(), SetAlarm.class);
		intent.putExtra(Alarms.ALARM_ID, (int) id);
		startActivity(intent);

	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		ToastMaster.cancelToast();
		if (mCursor != null)
			mCursor.close();
	}

}
