package com.dapgarage.lecture1.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class LocalBroadCastReceiver extends BroadcastReceiver {

    private static final String TAG = LocalBroadCastReceiver.class.getName();
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        Log.i(TAG, "onReceive: TITLE : " + title + " ; DESCRIPTION : " + description);
        Toast.makeText(context, "Broadcast received!", Toast.LENGTH_SHORT).show();
    }
}
