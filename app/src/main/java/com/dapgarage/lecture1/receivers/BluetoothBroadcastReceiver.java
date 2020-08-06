package com.dapgarage.lecture1.receivers;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = BluetoothBroadcastReceiver.class.getName();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            Toast.makeText(context, "Bluetooth state changes!", Toast.LENGTH_LONG).show();

            switch (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1) ){
                case BluetoothAdapter.STATE_OFF:
                    Log.i(TAG, "onReceive: STATE_OFF");
                    break;
                case BluetoothAdapter.STATE_ON:
                    Log.i(TAG, "onReceive: STATE_ON");
                    break;
                case BluetoothAdapter.STATE_DISCONNECTED:
                    Log.i(TAG, "onReceive: STATE_DISCONNECTED");
                    break;
                case BluetoothAdapter.STATE_CONNECTED:
                    Log.i(TAG, "onReceive: STATE_CONNECTED");
                    break;
                case BluetoothAdapter.STATE_DISCONNECTING:
                    Log.i(TAG, "onReceive: STATE_DISCONNECTING");
                    break;
                case BluetoothAdapter.STATE_CONNECTING:
                    Log.i(TAG, "onReceive: STATE_CONNECTING");
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Log.i(TAG, "onReceive: STATE_TURNING_OFF");
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    Log.i(TAG, "onReceive: STATE_TURNING_ON");
                    break;
                default:
                    Log.i(TAG, "onReceive: STATE_NONE");
            }
        }
    }
}
