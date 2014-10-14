package com.temp.tiger.app.tabB;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.cn.daming.deskclock.Alarm;
import com.cn.daming.deskclock.Alarms;
import com.cn.daming.deskclock.ClockFragment;
import com.cn.daming.deskclock.R;
import com.cn.daming.deskclock.SetAlarm;
import com.yhz.tem.yh.ch.temp.view.TemperatureView;

public class TabBFragment<BaseFragment> extends ClockFragment {
	private ImageView edituserStatiu;
	private TextView edituserName;
	private TemperatureView tempre;

	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// View view = new ClockFragment().getView();//
	// inflater.inflate(R.layout.fragment_2,
	// // null);
	// edituserStatiu = (ImageView) view.findViewById(R.id.edituserstatiu);
	// edituserName = (TextView) view.findViewById(R.id.editusername);
	// tempre = (TemperatureView) view.findViewById(R.id.tempera);
	// return view;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu,
	 * android.view.View, android.view.ContextMenu.ContextMenuInfo) 创建菜单
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		// Inflate the menu from xml.
		getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);

		// Use the current item to create a custom view for the header.
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		final Cursor c = (Cursor) mAlarmsList.getAdapter().getItem((int) info.position);
		final Alarm alarm = new Alarm(c);

		// Construct the Calendar to compute the time.
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, alarm.hour);
		cal.set(Calendar.MINUTE, alarm.minutes);
		final String time = Alarms.formatTime(getActivity(), cal);

		// Inflate the custom view and set each TextView's text.
		final View v = mFactory.inflate(R.layout.context_menu_header, null);
		TextView textView = (TextView) v.findViewById(R.id.header_time);
		textView.setText(time);
		textView = (TextView) v.findViewById(R.id.header_label);
		textView.setText(alarm.label);

		// Set the custom view on the menu.
		menu.setHeaderView(v);
		// Change the text based on the state of the alarm.
		if (alarm.enabled) {
			menu.findItem(R.id.enable_alarm).setTitle(R.string.disable_alarm);
		}
	}

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		final int id = (int) info.id;
		// Error check just in case.
		if (id == -1) {
			return super.onContextItemSelected(item);
		}
		switch (item.getItemId()) {
		case R.id.delete_alarm:
			// Confirm that the alarm will be deleted.
			new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.delete_alarm)).setMessage(getString(R.string.delete_alarm_confirm))
					.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface d, int w) {
							Alarms.deleteAlarm(getActivity(), id);
						}
					}).setNegativeButton(android.R.string.cancel, null).show();
			return true;

		case R.id.enable_alarm:
			final Cursor c = (Cursor) mAlarmsList.getAdapter().getItem(info.position);
			final Alarm alarm = new Alarm(c);
			Alarms.enableAlarm(getActivity(), alarm.id, !alarm.enabled);
			if (!alarm.enabled) {
				SetAlarm.popAlarmSetToast(getActivity(), alarm.hour, alarm.minutes, alarm.daysOfWeek);
			}
			return true;

		case R.id.edit_alarm:
			Intent intent = new Intent(getActivity(), SetAlarm.class);
			intent.putExtra(Alarms.ALARM_ID, id);
			startActivity(intent);
			return true;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

}
