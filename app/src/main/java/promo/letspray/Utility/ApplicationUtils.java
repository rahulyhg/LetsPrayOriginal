package promo.letspray.Utility;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import promo.letspray.SplashActivity;

/**
 * Created by ppobd_six on 5/15/2016.
 */
public class ApplicationUtils {


    public static final int PERMISSION_REQUEST_CODE = 1;
    public static final int MORNING = 5;
    public static final int NOON = 14;
    public static final int EVENING = 17;
    public static final int NIGHT = 19;
    public static final int GPS_REQUEST_CODE = 2;


    /**
     * See if we have permissionf or locations
     *
     * @return boolean, true for good permissions, false means no permission
     */

    public static boolean checkPermission(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Request permissions from the user
     */
    public static void requestPermission(Activity activity) {

        /**
         * Previous denials will warrant a rationale for the user to help convince them.

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(activity, "This app relies on location data for it's main functionality. Please enable GPS data to access all features.", Toast.LENGTH_LONG).show();
        } else {*/
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
//        }
    }

    public static int getDayState() {

        Calendar calendar = Calendar.getInstance();
        int time = calendar.get(Calendar.HOUR_OF_DAY);
        if (time >= MORNING && time < NOON) {
            return MORNING;
        } else if (time >= NOON && time < EVENING) {
            return NOON;
        } else if (time >= EVENING && time < NIGHT) {
            return EVENING;
        } else {
            return NIGHT;
        }
    }

    public static Date formatDate(String inputDate, SimpleDateFormat sdFormat) {
        Date dt = null;
        try {
            dt = sdFormat.parse(inputDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dt;
    }

    public static long getPrayerTimeInMs(String time) {
        time = modifyTime(time);
        Calendar calendar = Calendar.getInstance();
        String dateString = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + time.substring(0, 5);
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date postDate = formatDate(dateString, dtFormat);
        calendar.setTime(postDate);
        return calendar.getTimeInMillis();
    }

    private static String modifyTime(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        String ampm = time.substring(6, 8);
        if (ampm.equalsIgnoreCase("am")) {
            return time;
        } else {
            hour = hour + 12;
            return hour + ":" + time.substring(3);
        }
    }


    public static Typeface setTimeTypeface(Context context) {
        Typeface timeTypeface =
                Typeface.createFromAsset(context.getAssets(), "fonts/time.ttf");
        return timeTypeface;
    }

    public static Typeface setTimeDateTypeface(Context context) {
        Typeface timeTypeface =
                Typeface.createFromAsset(context.getAssets(), "fonts/time_date.ttf");
        return timeTypeface;
    }

    public static Typeface setIfterSehriTypeface(Context context) {
        Typeface timeTypeface =
                Typeface.createFromAsset(context.getAssets(), "fonts/ifterAndSehri.ttf");
        return timeTypeface;
    }

    public static Typeface setHadithDetailTypeface(Context context) {
        Typeface timeTypeface =
                Typeface.createFromAsset(context.getAssets(), "fonts/hadith_details.ttf");
        return timeTypeface;
    }

    public static void showSettingsAlert(final Activity activity){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivityForResult(intent,GPS_REQUEST_CODE);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
