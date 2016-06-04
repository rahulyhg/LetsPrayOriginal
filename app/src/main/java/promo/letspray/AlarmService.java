package promo.letspray;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;

import promo.letspray.fragment.HomeFragment;

/**
 * Created by wali on 6/2/2016.
 */
public class AlarmService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Service", "Service Created");
        //notifiy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Service", "Service Started");
        if(isAlarmTime()) {
            notifiy();
            //setNextAlarm();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Service","Service Destroyed");
    }

    private boolean isAlarmTime(){
        Calendar calendar = Calendar.getInstance();
        SharedPreferences preferences = getSharedPreferences(StaticData.KEY_PREFERENCE,MODE_PRIVATE);
        long alarmTime = preferences.getLong(StaticData.NEXT_PRAYER_TIME,0);
        long currTime=calendar.getTimeInMillis();
        Log.e("ALARM TIME",alarmTime +"");
        Log.e("CURRENT TIME",currTime +"");
        if(alarmTime==currTime){
                return true;
            }else{
                return false;
            }
    }

    public void notifiy() {
        Notification.Builder builder;
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder = new Notification.Builder(getApplicationContext())
                .setContentTitle("NOTIFICATION")
                .setContentText("Click for details")
                .setSmallIcon(R.drawable.ic_arrow)
                .setSound(soundUri)
                .setLights(0xFF0000FF, 300, 1000);

        NotificationManager notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        long[] v = {500, 1000};
        builder.setVibrate(v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationManager.notify(1, builder.build());
        }

    }

//    public void setNextAlarm(){
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        Calendar calendar_main = Calendar.getInstance();
//        calendar_main.setTimeInMillis(System.currentTimeMillis());
//        Long alarmTime = calendar_main.getTimeInMillis();
//        Long time1 = 1464741120000L;
//
////        calendar_main.set(Calendar.HOUR_OF_DAY, 2);
////        calendar_main.set(Calendar.MINUTE, 11);
////        calendar_main.set(Calendar.SECOND, 0);
////        calendar_main.set(Calendar.AM_PM, Calendar.PM);
//        if(alarmTime==time1) {
//            Intent myIntent = new Intent(MyAlarmService.this, MyReceiver.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(MyAlarmService.this, 0, myIntent, 0);
//            alarmManager.set(AlarmManager.RTC, time1, pendingIntent);
//        }
//    }
}
