package com.rfstar.kevin.rfstar.kevin.params;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.rfstar.kevin.rfstar.kevin.app.App;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class CubicBLEDevice extends BLEDevice {

    public CubicBLEDevice(Context context, BluetoothDevice bluetoothDevice) {
	// TODO Auto-generated constructor stub
	super(context, bluetoothDevice);
    }

    @Override
    protected void discoverCharacteristicsFromService() {
	// TODO Auto-generated method stub
	Log.d(App.TAG, "load all the services ");

	for (BluetoothGattService bluetoothGattService : bleService
		.getSupportedGattServices(device)) {
	    String serviceUUID = Long.toHexString(
		    bluetoothGattService.getUuid().getMostSignificantBits())
		    .substring(0, 4);
	    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService
		    .getCharacteristics()) {
		String characterUUID = Long.toHexString(
			bluetoothGattCharacteristic.getUuid()
				.getMostSignificantBits()).substring(0, 4);

	    }
	}
    }

    /**
     * 
     * @param serviceUUID
     * @param characteristicUUID
     * @param data
     */
    public void writeValue(String serviceUUID, String characteristicUUID,
	    byte[] value) {
	// TODO Auto-generated method stub
	for (BluetoothGattService bluetoothGattService : bleService
		.getSupportedGattServices(device)) {
	    String gattServiceUUID = Long.toHexString(
		    bluetoothGattService.getUuid().getMostSignificantBits())
		    .substring(0, 4);
	    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService
		    .getCharacteristics()) {
		String characterUUID = Long.toHexString(
			bluetoothGattCharacteristic.getUuid()
				.getMostSignificantBits()).substring(0, 4);
		if (gattServiceUUID.equals(serviceUUID)
			&& characteristicUUID.equals(characterUUID)) {
		    bluetoothGattCharacteristic.setValue(value);
		    this.writeValue(bluetoothGattCharacteristic);
		}
	    }
	}
    }

    /**
     * 
     * @param serviceUUID
     * @param characteristicUUID
     */
    public void readValue(String serviceUUID, String characteristicUUID) {
	for (BluetoothGattService bluetoothGattService : bleService
		.getSupportedGattServices(device)) {
	    String gattServiceUUID = Long.toHexString(
		    bluetoothGattService.getUuid().getMostSignificantBits())
		    .substring(0, 4);
	    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService
		    .getCharacteristics()) {
		String characterUUID = Long.toHexString(
			bluetoothGattCharacteristic.getUuid()
				.getMostSignificantBits()).substring(0, 4);
		if (gattServiceUUID.equals(serviceUUID)
			&& characteristicUUID.equals(characterUUID)) {
		    Log.d(App.TAG, "charaterUUID read is success  : "
			    + characterUUID);
		    this.readValue(bluetoothGattCharacteristic);
		}
	    }
	}
    }

    /**
     * 
     * @param serviceUUID
     * @param characteristicUUID
     */
    public void setNotification(String serviceUUID, String characteristicUUID,
	    boolean enable) {
	for (BluetoothGattService bluetoothGattService : bleService
		.getSupportedGattServices(device)) {
	    String gattServiceUUID = Long.toHexString(
		    bluetoothGattService.getUuid().getMostSignificantBits())
		    .substring(0, 4);
	    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService
		    .getCharacteristics()) {
		String characterUUID = Long.toHexString(
			bluetoothGattCharacteristic.getUuid()
				.getMostSignificantBits()).substring(0, 4);
		if (gattServiceUUID.equals(serviceUUID)
			&& characteristicUUID.equals(characterUUID)) {
		    this.setCharacteristicNotification(
			    bluetoothGattCharacteristic, enable);
		}
	    }
	}
    }

}
