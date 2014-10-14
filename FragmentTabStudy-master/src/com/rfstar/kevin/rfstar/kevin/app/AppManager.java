package com.rfstar.kevin.rfstar.kevin.app;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import com.rfstar.kevin.rfstar.kevin.params.CubicBLEDevice;

/*
 �������е� �����豸 
 * 			����:
 * 			   1)ɨ�����е������豸 
 * 			   2)�ж�����Ȩ���Ƿ��
 * @author Kevin.wu
 *
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class AppManager {
	private static int SCAN_TIME = 10000; // ɨ���ʱ��Ϊ10��
	private static final int REQUEST_CODE = 0x01;// ���ص�Ψһ��ʶ
	private Context context = null;
	public static BluetoothAdapter bleAdapter = null;

	private Handler handler = null;
	private boolean isScanning = false; // �Ƿ�����ɨ��

	private RFStarManageListener listener = null;

	private ArrayList<BluetoothDevice> scanBlueDeviceArray = new ArrayList<BluetoothDevice>(); // ɨ�赽�����

	public BluetoothDevice bluetoothDevice = null; // ѡ�е��豸
	public CubicBLEDevice cubicBLEDevice = null; // ѡ�е�cubicBLEDevice

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public AppManager(Context context) {
		// TODO Auto-generated constructor stub
		handler = new Handler();
		if (!context.getPackageManager().hasSystemFeature( // ���ϵͳ�Ƿ�������͹��ĵ�jar��
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(context, "��������4.0�ı�׼Jar��", Toast.LENGTH_SHORT).show();
			return;
		}
		this.context = context;
		BluetoothManager manager = (BluetoothManager) this.context.getSystemService(Context.BLUETOOTH_SERVICE);
		bleAdapter = manager.getAdapter();

		if (bleAdapter == null) { // ����ֻ�Ӳ���Ƿ�֧�������͹���
			Toast.makeText(context, "��֧��ble����4.0", Toast.LENGTH_SHORT).show();
			return;
		}
	}

	/**
	 * �ж��Ƿ�������Ȩ��
	 * 
	 * @return
	 */
	public boolean isEdnabled(Activity activity) {
		if (!bleAdapter.isEnabled()) {
			if (!bleAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				activity.startActivityForResult(enableBtIntent, REQUEST_CODE);
			}
			return true;
		}
		return false;
	}

	public ArrayList<BluetoothDevice> getScanBluetoothDevices() {
		return this.scanBlueDeviceArray;
	}

	/**
	 * ����Ȩ�޺󣬷���ʱ����
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	public void onRequestResult(int requestCode, int resultCode, Intent data) {
		// User chose not to enable Bluetooth.
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_CANCELED) {
			((Activity) this.context).finish();
			return;
		}
	}

	/**
	 * ɨ�������豸
	 */
	public void startScanBluetoothDevice() {
		if (scanBlueDeviceArray != null) {
			scanBlueDeviceArray = null;
		}
		scanBlueDeviceArray = new ArrayList<BluetoothDevice>();

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				stopScanBluetoothDevice();
			}
		}, SCAN_TIME); // 10���ֹͣɨ��
		isScanning = true;

		bleAdapter.startLeScan(bleScanCallback);
		listener.RFstarBLEManageStartScan();
	}

	/**
	 * ֹͣɨ�������豸
	 */
	public void stopScanBluetoothDevice() {
		if (isScanning) {
			isScanning = false;
			bleAdapter.stopLeScan(bleScanCallback);
			listener.RFstarBLEManageStopScan();
		}
	}

	// Device scan callback.
	private BluetoothAdapter.LeScanCallback bleScanCallback = new BluetoothAdapter.LeScanCallback() {
		@Override
		public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
			// TODO ���ɨ�赽��device����ˢ�����
			handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub

					if (!scanBlueDeviceArray.contains(device)) {
						scanBlueDeviceArray.add(device);

						listener.RFstarBLEManageListener(device, rssi, scanRecord);
					}
				}
			});
		}
	};

	/**
	 * ÿɨ�赽һ�������豸����һ��
	 * 
	 * @param listener
	 */
	public void setRFstarBLEManagerListener(RFStarManageListener listener) {
		this.listener = listener;
	}

	/**
	 * ���ڴ��?ˢ�µ��豸ʱ���½���
	 * 
	 * @author Kevin.wu
	 * 
	 */
	public interface RFStarManageListener {
		public void RFstarBLEManageListener(BluetoothDevice device, int rssi, byte[] scanRecord);

		public void RFstarBLEManageStartScan();

		public void RFstarBLEManageStopScan();
	}
}
