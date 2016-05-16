package promo.letspray;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import promo.letspray.utility.ApplicationUtils;
import promo.letspray.utility.PrayTime;

public class SplashActivity extends AppCompatActivity implements LocationListener {
    // Location Variables

    private LocationManager locationManager;
    private final static int DISTANCE_UPDATES = 1;
    private final static int TIME_UPDATES = 5;
    private static final int PERMISSION_REQUEST_CODE = 1;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initLocation();
    }

    private void initLocation(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ApplicationUtils.checkPermission(this)) {
            Location location = locationManager.getLastKnownLocation
                    (LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_UPDATES, DISTANCE_UPDATES, this);
            onLocationChanged(location);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

        } else {
            ApplicationUtils.requestPermission(this);
        }
    }
    /**
     * Monitor for location changes
     *
     * @param location holds the new location
     */
    @Override
    public void onLocationChanged(Location location) {

        setPrayerTImes(location.getLatitude(), location.getLongitude());

    }
    /**
     * GPS turned off, stop watching for updates.
     *
     * @param provider contains data on which provider was disabled
     */
    @Override
    public void onProviderDisabled(String provider) {
        if (ApplicationUtils.checkPermission(this)) {
            locationManager.removeUpdates(this);
        } else {
            ApplicationUtils.requestPermission(this);
        }
    }
    /**
     * GPS turned back on, re-enable monitoring
     *
     * @param provider contains data on which provider was enabled
     */
    @Override
    public void onProviderEnabled(String provider) {
        if (ApplicationUtils.checkPermission(this)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_UPDATES, DISTANCE_UPDATES, this);
        } else {
            ApplicationUtils.requestPermission(this);
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
    /**
     * Monitor for permission changes.
     *
     * @param requestCode  passed via PERMISSION_REQUEST_CODE
     * @param permissions  list of permissions requested
     * @param grantResults the result of the permissions requested
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /**
                     * We are good, turn on monitoring
                     */
                    if (ApplicationUtils.checkPermission(this)) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_UPDATES, DISTANCE_UPDATES, this);
                    } else {
                        ApplicationUtils.requestPermission(this);
                    }
                } else {
                    /**
                     * No permissions, block out all activities that require a location to function
                     */
                    Toast.makeText(this, "Permission Not Granted.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    private void setPrayerTImes(double latitude, double longitude) {
        Log.e("Latitude", latitude + "");
        Log.e("Longitude", longitude + "");
        double timezone = (Calendar.getInstance().getTimeZone()
                .getOffset(Calendar.getInstance().getTimeInMillis()))
                / (1000 * 60 * 60);
        PrayTime prayers = new PrayTime();

        prayers.setTimeFormat(prayers.Time12);
        prayers.setCalcMethod(prayers.Makkah);
        prayers.setAsrJuristic(prayers.Shafii);
        prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        ArrayList prayerTimes = prayers.getPrayerTimes(cal, latitude,
                longitude, timezone);
        ArrayList prayerNames = prayers.getTimeNames();

        for (int i = 0; i < prayerTimes.size(); i++) {
            //Add Prayer time in Database or SHared Preference Here
        }
    }

}
