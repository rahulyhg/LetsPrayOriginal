package promo.letspray.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import promo.letspray.MainActivity;
import promo.letspray.R;
import promo.letspray.data.StaticData;
import promo.letspray.reciever.AlarmReceiver;

public class NewAlarmService extends IntentService {
    public AlarmManager alarmManager;
    Intent alarmIntent;
    //   PendingIntent pendingIntent;
    private static final int NOTIFICATION_ID = 1;
    private static final String TAG = "Let's Pray";
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent, pendingIntent2;
    private static final String NOTIFICATION_MSG = " waqt coundown started. Get ready for your Salah.";

    public NewAlarmService() {
        super("NewAlarmService");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // don't notify if they've played in last 24 hr
        Log.i(TAG, "Alarm Service has started.");
        Context context = this.getApplicationContext();

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent mIntent = new Intent(this, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Resources res = this.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setTicker(res.getString(R.string.notification_title))
                .setAutoCancel(true)
                .setContentTitle(res.getString(R.string.notification_title))
                .setContentText(getNotificationText())
                .setSound(soundUri);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
        Log.e(TAG, "Notification generated");
        setNextPrayerAlarm();

    }

    private void setNextPrayerAlarm() {
        SharedPreferences preferences = getSharedPreferences(StaticData.KEY_PREFERENCE, MODE_PRIVATE);
        long currAlarm = preferences.getLong(StaticData.ALARM_TIME, 0);
        long nextAlarm = getNextAlarm(currAlarm);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmIntent = new Intent(NewAlarmService.this, AlarmReceiver.class);
        pendingIntent2 = PendingIntent.getBroadcast(NewAlarmService.this, 0, alarmIntent, 0);

        Log.d(TAG, "alarmserViCE ::" + " new time " + nextAlarm);
        alarmManager.setRepeating(AlarmManager.RTC, nextAlarm, 0, pendingIntent2);
    }

    private long getNextAlarm(long currAlarm) {
        SharedPreferences preferences = getSharedPreferences(StaticData.KEY_PREFERENCE, MODE_PRIVATE);
        long fazr = preferences.getLong(StaticData.PRAYER_TIME_FAJR, 0);
        long duhr = preferences.getLong(StaticData.PRAYER_TIME_DUHR, 0);
        long asr = preferences.getLong(StaticData.PRAYER_TIME_ASR, 0);
        long maghrib = preferences.getLong(StaticData.PRAYER_TIME_MAGRIB, 0);
        long isha = preferences.getLong(StaticData.PRAYER_TIME_ISHA, 0);
        if (currAlarm == fazr) {
            return duhr;
        } else if (currAlarm == duhr) {
            return asr;
        } else if (currAlarm == asr) {
            return maghrib;
        } else if (currAlarm == maghrib) {
            return isha;
        } else if (currAlarm == isha) {
            return fazr;
        } else {
            return fazr;
        }
    }

    private String getNotificationText(){
        SharedPreferences preferences = getSharedPreferences(StaticData.KEY_PREFERENCE, MODE_PRIVATE);
        long currAlarm = preferences.getLong(StaticData.ALARM_TIME, 0);
        long fazr = preferences.getLong(StaticData.PRAYER_TIME_FAJR, 0);
        long duhr = preferences.getLong(StaticData.PRAYER_TIME_DUHR, 0);
        long asr = preferences.getLong(StaticData.PRAYER_TIME_ASR, 0);
        long maghrib = preferences.getLong(StaticData.PRAYER_TIME_MAGRIB, 0);
        long isha = preferences.getLong(StaticData.PRAYER_TIME_ISHA, 0);
        if (currAlarm == fazr) {
            return "Fazr"+NOTIFICATION_MSG;
        } else if (currAlarm == duhr) {
            return "Duhr"+NOTIFICATION_MSG;
        } else if (currAlarm == asr) {
            return "Asr"+NOTIFICATION_MSG;
        } else if (currAlarm == maghrib) {
            return "Maghrib"+NOTIFICATION_MSG;
        } else if (currAlarm == isha) {
            return "Isha"+NOTIFICATION_MSG;
        } else {
            return "Fazr"+NOTIFICATION_MSG;
        }
    }

}