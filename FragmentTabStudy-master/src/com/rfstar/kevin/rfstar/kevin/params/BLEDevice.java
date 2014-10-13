package com.rfstar.kevin.rfstar.kevin.params;import java.util.Calendar;import java.util.List;import android.app.Service;import android.bluetooth.BluetoothDevice;import android.bluetooth.BluetoothGattCharacteristic;import android.bluetooth.BluetoothGattService;import android.content.BroadcastReceiver;import android.content.ComponentName;import android.content.Context;import android.content.Intent;import android.content.IntentFilter;import android.content.ServiceConnection;import android.os.IBinder;import android.util.Log;import com.rfstar.kevin.rfstar.kevin.app.App;import com.rfstar.kevin.rfstar.kevin.service.RFStarBLEService;import com.rfstar.kevin.rfstar.kevin.tools.ToastUntil;import com.rfstar.kevin.rfstar.kevin.tools.Tools;/* * 蓝牙设备的基类 *         功能： *           1）保存设备属性 *           2）获取设备属性 *           3）结束服务，断开连接 *           4）获取服务 *           5）监视广播的属性 *            * @author Kevin.wu *  */public abstract class BLEDevice {	Intent serviceIntent;	protected Context context = null;	public String deviceName = null, deviceMac = null;	protected RFStarBLEService bleService = null;	public BluetoothDevice device = null;	public RFStarBLEBroadcastReceiver delegate = null;	public BLEDevice(Context context, BluetoothDevice device) {		this.device = device;		this.deviceName = this.device.getName();		this.deviceMac = this.device.getAddress();		this.context = context;		this.registerReceiver();		if (serviceIntent == null) {			serviceIntent = new Intent(this.context, RFStarBLEService.class);			this.context.bindService(serviceIntent, serviceConnection,					Service.BIND_AUTO_CREATE);			Log.d(App.TAG, "55 66666666666666666666666");		}	}	/**	 * 设置连接，绑定服务	 */	public void setBLEBroadcastDelegate(RFStarBLEBroadcastReceiver delegate) {		this.delegate = delegate;	}	/**	 * 连接服务	 */	private ServiceConnection serviceConnection = new ServiceConnection() {		@Override		public void onServiceDisconnected(ComponentName name) {			// TODO Auto-generated method stub			bleService = null;			Log.w(App.TAG, "bbbbbbbbbbb gatt is onServiceDisconnected");		}		@Override		public void onServiceConnected(ComponentName name, IBinder service) {			// TODO Auto-generated method stub			// d Log.d(BLEApp.KTag, "55 serviceConnected :   服务启动 ");			bleService = ((RFStarBLEService.LocalBinder) service).getService();			bleService.initBluetoothDevice(device);			Log.w(App.TAG, "bbbbbbbbbbb gatt is init");		}	};	/**	 * 获取特征值	 * 	 * @param characteristic	 */	public void readValue(BluetoothGattCharacteristic characteristic) {		if (characteristic == null) {			Log.w(App.TAG, "55555555555 readValue characteristic is null");		} else {			bleService.readValue(this.device, characteristic);		}	}	/**	 * 根据特征值写入数据	 * 	 * @param characteristic	 */	public void writeValue(BluetoothGattCharacteristic characteristic) {		if (characteristic == null) {			Log.w(App.TAG, "55555555555 writeValue characteristic is null");		} else {			Log.d(App.TAG, "charaterUUID write is success  : "					+ characteristic.getUuid().toString());			bleService.writeValue(this.device, characteristic);		}	}	/**	 * 消息使能	 * 	 * @param characteristic	 * @param enable	 */	public void setCharacteristicNotification(			BluetoothGattCharacteristic characteristic, boolean enable) {		if (characteristic == null) {			Log.w(App.TAG, "55555555555 Notification characteristic is null");		} else {			bleService.setCharacteristicNotification(this.device,					characteristic, enable);		}	}	/**	 * 断开连接	 */	public void disconnectedDevice() {		bleService.disconnect(device);		context.unregisterReceiver(gattUpdateRecevice);		context.unbindService(serviceConnection);		bleService = null;	}	public void closeDevice() {		this.ungisterReceiver();		this.context.unbindService(serviceConnection);	}	/**	 * 获取服务	 * 	 * @return	 */	public List<BluetoothGattService> getBLEGattServices() {		return this.bleService.getSupportedGattServices(this.device);	}	/**	 * 监视广播的属性	 * 	 * @return	 */	protected IntentFilter bleIntentFilter() {		final IntentFilter intentFilter = new IntentFilter();		intentFilter.addAction(RFStarBLEService.ACTION_GATT_CONNECTED);		intentFilter.addAction(RFStarBLEService.ACTION_GATT_DISCONNECTED);		intentFilter				.addAction(RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED);		intentFilter.addAction(RFStarBLEService.ACTION_DATA_AVAILABLE);		intentFilter.addAction(RFStarBLEService.ACTION_GAT_RSSI);		intentFilter.addAction(RFStarBLEService.ACTION_GATT_CONNECTING);		return intentFilter;	}	public interface RFStarBLEBroadcastReceiver {		/**		 * 监视蓝牙状态的广播 macData蓝牙地址的唯一识别码		 */		public void onReceive(Context context, Intent intent, String macData,				String uuid);	}	/**	 * 注册监视蓝牙设备（返回数据的）广播	 * 	 * @param context	 * @param delegate	 * @param filter	 */	public void registerReceiver() {		this.context.registerReceiver(gattUpdateRecevice,				this.bleIntentFilter());	}	/**	 * 注销监视蓝牙返回的广播	 */	public void ungisterReceiver() {		this.context.unregisterReceiver(gattUpdateRecevice);	}	/**	 * 初始化服务中的特征	 */	protected abstract void discoverCharacteristicsFromService();	private int countDisconted = 0; // 计算重连的次数	/**	 * 接收蓝牙广播	 */	private BroadcastReceiver gattUpdateRecevice = new BroadcastReceiver() {		@Override		public void onReceive(final Context context, Intent intent) {			// TODO Auto-generated method stub			String characteristicUUID = intent					.getStringExtra(RFStarBLEService.RFSTAR_CHARACTERISTIC_ID);			if (RFStarBLEService.ACTION_GATT_CONNECTED.equals(intent					.getAction())) {				Log.d(App.TAG, " connect is succed");				ToastUntil.makeText(context, "蓝牙连接成功", 1);				countDisconted = 0;			} else if (RFStarBLEService.ACTION_GATT_DISCONNECTED.equals(intent					.getAction())) { // 断开				if (countDisconted < 5) // 断开后重连5次				{					// TODO Auto-generated method stub					bleService.connect(device);					countDisconted++;				} else {					ToastUntil.makeText(context, "蓝牙连接断开", 1);				}			} else if (RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED					.equals(intent.getAction())) {				discoverCharacteristicsFromService();			} else if (RFStarBLEService.ACTION_DATA_AVAILABLE.equals(intent					.getAction())) {				if (intent.getByteArrayExtra(RFStarBLEService.EXTRA_DATA) == null) {					ToastUntil.makeText(context, "ble设备无数据返回", 1);					return;				}			}			delegate.onReceive(context, intent, device.getAddress(),					characteristicUUID);		}	};	/**	 * 获取系统时间	 * 	 * @return	 */	protected byte[] getSystemTime() {		byte[] cal = new byte[7];		Calendar calendar = Calendar.getInstance();		cal[0] = (byte) (calendar.get(Calendar.YEAR) & 0xff);		cal[1] = (byte) (calendar.get(Calendar.YEAR) >> 8 & 0xff);		cal[2] = (byte) ((calendar.get(Calendar.MONTH) + 1) & 0xff);		cal[3] = (byte) (calendar.get(Calendar.DAY_OF_MONTH) & 0xff);		cal[4] = (byte) (calendar.get(Calendar.HOUR_OF_DAY) & 0xff);		cal[5] = (byte) (calendar.get(Calendar.MINUTE) & 0xff);		cal[6] = (byte) (calendar.get(Calendar.SECOND) & 0xff);		Log.d(App.TAG, "33333333   " + Tools.byte2Hex(cal));		return cal;	}}