package com.example.user.moo_ptt4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = MyReceiver.class.getSimpleName(); // 抓java classname

    @Override
    public void onReceive(Context context, Intent intent) {
        //这个监听wifi的打开与关闭，与wifi的连接无关
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            Log.i("suvini", "wifiState" + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    Log.i("suvini", "WIFI_STATE_DISABLED");
                    Toast closewifi_toast = Toast.makeText(context.getApplicationContext(), " wifi 已關閉", Toast.LENGTH_SHORT);
                    closewifi_toast.show();
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    Log.i("suvini", "WIFI_STATE_DISABLING");
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    Log.i("suvini", "WIFI_STATE_ENABLING");
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    Log.i("suvini", "WIFI_STATE_ENABLED");
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    Log.i("suvini", "WIFI_STATE_UNKNOWN");
                    Toast openwifi_toast = Toast.makeText(context.getApplicationContext(), "wifi 已打開", Toast.LENGTH_SHORT);
                    openwifi_toast.show();
                    break;
            }
        }
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            isConnected(context); // isConnected = isNetworkConnected + isWifiConnected
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                Log.i("CONNECTIVITY_ACTION", "info.getTypeName()" + info.getTypeName());
                Log.i("CONNECTIVITY_ACTION", "getSubtypeName()" + info.getSubtypeName());
                Log.i("CONNECTIVITY_ACTION", "getState()" + info.getState());
                Log.i("CONNECTIVITY_ACTION", "getDetailedState()" + info.getDetailedState().name());
                Log.i("CONNECTIVITY_ACTION", "getDetailedState()" + info.getExtraInfo());
                if (info.getState().toString() == "CONNECTED") {
                    Toast wifiname_toast = Toast.makeText(context.getApplicationContext(), "連接 wifi : " + info.getExtraInfo(), Toast.LENGTH_SHORT);
                    wifiname_toast.show();
                }

                Log.i("CONNECTIVITY_ACTION", "getType()" + info.getType());
            }
        }
    }

    public void isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null
                && networkInfo.isConnected()
                && (networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                | networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)) {
            Toast connected_toast = Toast.makeText(context.getApplicationContext(), "已連接網路 " + networkInfo.isConnected(), Toast.LENGTH_SHORT);
            connected_toast.show();
        } else {
            Toast disconnected_toast = Toast.makeText(context.getApplicationContext(), "未連接網路", Toast.LENGTH_SHORT);
            disconnected_toast.show();
        }
    }
}



