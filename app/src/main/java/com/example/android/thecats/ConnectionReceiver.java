package com.example.android.thecats;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ConnectionReceiver extends BroadcastReceiver {
    public ConnectionReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Utils.isOnline(context)) {
            ((MainActivity) context).tryConnect();
        }
        Toast.makeText(context, "connection available " + Utils.isOnline(context), Toast.LENGTH_LONG).show();

    }


}
