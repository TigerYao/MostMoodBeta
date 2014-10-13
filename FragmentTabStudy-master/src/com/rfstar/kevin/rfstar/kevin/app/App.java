package com.rfstar.kevin.rfstar.kevin.app;

import android.app.Application;

public class App extends Application {
    public static final int BLUETOOTH_DATA_CHANNEL = 0; // ��������ͨ�� ffe5
    public static final int SERIAL_DATA_CHANNEL = 1; // ��������ͨ�� ffe0
    public static final int ADC_INPUT = 2; // adc���루2·��ffd0
    public static final int RSSI_REPORT = 3; // RSSI���� ffa0
    public static final int PWM_OUTPUT = 4; // pwm�����4·��ffb0
    public static final int BATTERY_REPORT = 5; // ��ص������� 180f

    public static final int TURNIMING_OUTPUT = 6; // ��ʱ��ת��� fff0
    public static final int LEVEL_COUNTING_PULSE = 7; // ��ƽ������� fff0
    public static final int PORT_TIMING_EVENTS_CONFIG = 8;// �˿ڶ�ʱ�¼����� fe00
    public static final int PROGRAMM_ABLEIO = 9; // �ɱ��io(8·) fff0
    public static final int DEVICE_INFORMATION = 10; // �豸��Ϣ 180a
    public static final int MODULE_PARAMETER = 11; // ģ��������� ff90
    public static final int ANTI_HIJACKINGKEY = 12;// ���ٳ���Կ ffc0

    public static final String RFSTAR_DEVICE = "rfstar_device";

    public AppManager manager = null;

    public static final String TAG = "_TAG";

    @Override
    public void onCreate() {
	// TODO Auto-generated method stub
	super.onCreate();

	manager = new AppManager(getApplicationContext());

    }

}
