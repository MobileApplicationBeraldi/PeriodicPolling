package com.example.roberto.alarmbroadcastreceiver;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Intent intent=null;
    private  PendingIntent pollingPendingIntent=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //finish();
        setContentView(R.layout.activity_main);
        intent = new Intent(this, PollingBroadcastReceiver.class);

        //Setting a pending intent for a specific broadcast Receiver
        //This is a little odd, as broadcast should be sent to all

        pollingPendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Button btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.POLLING");
                sendBroadcast(intent);
            }
        });


        btn=(Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager am = (AlarmManager)(getApplicationContext()).getSystemService(Context.ALARM_SERVICE);
                am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        3*1000,
                        3*1000, pollingPendingIntent);
                Toast.makeText(getApplicationContext(),"Polling started...."+Integer.toString(android.os.Process.myTid()),Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        btn=(Button)findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager am = (AlarmManager)(getApplicationContext()).getSystemService(Context.ALARM_SERVICE);
                am.cancel(pollingPendingIntent);
                Toast.makeText(getApplicationContext(),"Polling stopped...."+Integer.toString(android.os.Process.myTid()),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
