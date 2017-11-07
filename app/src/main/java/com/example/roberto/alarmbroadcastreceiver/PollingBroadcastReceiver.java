package com.example.roberto.alarmbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;



/**
 * Created by roberto on 02.11.2015.
 */
public class PollingBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //Toast.makeText(context, "Intent received..!" + Integer.toString(android.os.Process.myTid()), Toast.LENGTH_SHORT).show();
        Log.i("INFO2","POLLING RECEIVER");

        Intent i = new Intent(context,mNetService.class);
        context.startService(i);

        //The code is executed on the main thread
        // Operation cannot take more than 10 sec (see documentation)


    }

}
