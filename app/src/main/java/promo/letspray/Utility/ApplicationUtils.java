package promo.letspray.Utility;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by ppobd_six on 5/15/2016.
 */
public class ApplicationUtils {




    public static final int PERMISSION_REQUEST_CODE = 1;
    public static final int MORNING = 5;
    public static final int NOON = 9;
    public static final int EVENING = 16;
    public static final int NIGHT = 18;



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
         */
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(activity, "This app relies on location data for it's main functionality. Please enable GPS data to access all features.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    public static int getDayState() {





        Calendar calendar = Calendar.getInstance();
        int time = calendar.get(Calendar.HOUR_OF_DAY);
        if (MORNING <= time && time < NOON) {

            return MORNING;
        } else if (NOON <= time && time < EVENING) {
            return NOON;
        } else if (EVENING <= time && time < NIGHT) {

            return EVENING;

        } else {
            return NIGHT;
        }



    }
}
