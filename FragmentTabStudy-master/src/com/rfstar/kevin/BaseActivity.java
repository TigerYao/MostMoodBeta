package com.rfstar.kevin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.research.fragmenttabstudy.R;
import com.rfstar.kevin.rfstar.kevin.app.App;
import com.rfstar.kevin.rfstar.kevin.service.RFStarBLEService;
import com.rfstar.kevin.rfstar.kevin.tools.ToastUntil;
import com.rfstar.kevin.rfstar.kevin.view.NavigateView;

public class BaseActivity extends FragmentActivity {
    protected NavigateView navigateView = null;
    public App app = null;

    private final static int SHOW_INT = 0x01;
    private final static int SHOW_STRING = 0x02;
    private final static int SHOW_TIME = 0x03;
    private final static String SHOW_MESSAGGE = "_showMessage";
    private final static String SHOW_INTERVAL = "_showInterval";
    public final static String ARRAYFLAG = "_array";

    protected final int result_Main = 0, result_Search = 1;
    /**
     * 显示toast
     */
    protected Handler showMessage = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    Bundle bundle = msg.getData();
	    switch (msg.what) {
	    case SHOW_INT:
		ToastUntil.makeText(getApplication(),
			bundle.getInt(SHOW_MESSAGGE), 0);
		break;
	    case SHOW_STRING:
		ToastUntil.makeText(getApplicationContext(),
			bundle.getString(SHOW_MESSAGGE), 0);
		break;
	    case SHOW_TIME:
		Log.d("grandmapal",
			"     showtime  " + bundle.getInt(SHOW_INTERVAL));
		ToastUntil.makeText(getApplicationContext(),
			bundle.getString(SHOW_MESSAGGE),
			bundle.getInt(SHOW_INTERVAL));
		break;
	    default:
		break;
	    }
	}
    };
    public SharedPreferences preference;

    /**
     * 
     * @param message
     */
    protected void showMessage(String message) {
	Bundle bundle = new Bundle();
	bundle.putString(SHOW_MESSAGGE, message);
	Message msg = new Message();
	msg.setData(bundle);
	msg.what = SHOW_STRING;
	showMessage.sendMessage(msg);
    }

    protected void showMessage(int message) {
	Bundle bundle = new Bundle();
	bundle.putInt(SHOW_MESSAGGE, message);
	Message msg = new Message();
	msg.setData(bundle);
	msg.what = SHOW_INT;
	showMessage.sendMessage(msg);
    }

    /**
     * 
     * @param message
     * @param time
     */
    protected void showMessage(String message, int time) {
	Bundle bundle = new Bundle();
	bundle.putString(SHOW_MESSAGGE, message);
	bundle.putInt(SHOW_INTERVAL, time);
	Message msg = new Message();
	msg.setData(bundle);
	msg.what = SHOW_TIME;
	showMessage.sendMessage(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	super.onCreate(savedInstanceState);
	app = (App) getApplication();
	preference = PreferenceManager.getDefaultSharedPreferences(this);
	Log.d(App.TAG, "broadcast    :  " + BaseActivity.class.getName());

    }

    public <T> void startNewActivity(Class<T> clz, boolean isNewTask) {
	Intent intent = new Intent();
	intent.setClass(this, clz);
	if (isNewTask) {
	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	}
	startActivity(intent);
    }

    @Override
    protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	if (navigateView != null) {
	    navigateView.refreshBG();
	}
    }

    @Override
    protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
    }

    @SuppressWarnings("static-access")
    protected void initNavigation(String title) {
	navigateView = (NavigateView) this.findViewById(R.id.navigateView);
	if (title != null)
	    navigateView.setTitle(title);
	navigateView.setRightHideBtn(false);
	navigateView.rightBtn.setText("搜索");
	navigateView.rightBtn.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(BaseActivity.this,
			SearchActivity.class);
		startActivity(intent);
	    }
	});
    }

    @SuppressWarnings("static-access")
    protected void initNavigation(int title) {
	navigateView = (NavigateView) this.findViewById(R.id.navigateView);
	navigateView.setTitle(title);
	navigateView.setRightHideBtn(false);
	navigateView.rightBtn.setText("搜索");
	navigateView.rightBtn.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(BaseActivity.this,
			SearchActivity.class);
		startActivity(intent);
	    }
	});
    }

    @SuppressWarnings("static-access")
    @Override
    public void finish() {
	// TODO Auto-generated method stub
	super.finish();

    }

    @SuppressWarnings("static-access")
    @Override
    public void startActivity(Intent intent) {
	// TODO Auto-generated method stub
	super.startActivity(intent);

    }

    // 判断是连接还是断开
    protected void connectedOrDis(String action) {
	if (RFStarBLEService.ACTION_GATT_CONNECTED.equals(action)) {
	    Log.d(App.TAG, "111111111 连接完成");
	    // navigateView.setEnable(true);

	} else if (RFStarBLEService.ACTION_GATT_DISCONNECTED.equals(action)) {
	    Log.d(App.TAG, "111111111 连接断开");
	    // navigateView.setEnable(false);
	}
    }

}
