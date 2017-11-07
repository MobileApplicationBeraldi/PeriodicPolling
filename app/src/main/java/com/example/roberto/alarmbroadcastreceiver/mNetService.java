package com.example.roberto.alarmbroadcastreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class mNetService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("INFO2","POLLING SERVICE");

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://newsapi.org/v1/articles?source=al-jazeera-english&sortBy=latest&apiKey=4f893bda35b041b48c2dd45f3e7f32b4";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            //Log.i("INFO2:",status);
                            JSONObject news = (jsonObject.getJSONArray("articles")).getJSONObject(0);
                            Log.i("INFO2:",""+news.toString());

                            SharedPreferences sharedPref = getSharedPreferences("LastNews",getApplicationContext().MODE_PRIVATE);
                            String latestNews =  sharedPref.getString("latest", "");
                            String publishedAt = news.getString("publishedAt");

                            Toast.makeText(getApplicationContext(),latestNews+" "+publishedAt,Toast.LENGTH_LONG).show();
                            if (latestNews.equals(publishedAt))
                                return;
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("latest", publishedAt);
                            editor.commit();

                            String title = news.getString("title");
                            String description = news.getString("description");
                            String url = news.getString("url");
                                    Intent i=new Intent(getApplicationContext(),Details.class);
                                    i.putExtra("url",url);
                            PendingIntent pendingIntent =
                                    PendingIntent.getActivity(getApplicationContext(),0,i, PendingIntent.FLAG_ONE_SHOT);
                            NotificationCompat.Builder mBulider = new NotificationCompat.Builder(getApplicationContext());
                            //Set the main paramters....
                            mBulider
                                    .setContentTitle(title)
                                    .setContentText(description.substring(Math.min(20,description.length())))
                                    .setSmallIcon(R.drawable.mail)
                                    .setAutoCancel(true)
                                    .setContentIntent(pendingIntent);

                            //Build the notification
                            Notification notification = mBulider.build();
                            NotificationManager notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
                            notificationManager.notify(0,notification);

                        }
                        catch (JSONException e) {
                            Log.i("INFO2",""+e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("INFO2","That didn't work!"+error.toString());
            }
        });
       // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return super.onStartCommand(intent, flags, startId);
    }
}
