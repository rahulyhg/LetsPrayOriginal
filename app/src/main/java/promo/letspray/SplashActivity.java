package promo.letspray;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import promo.letspray.Model.Prayer;
import promo.letspray.database.DatabaseHelper;
import promo.letspray.utility.ApplicationUtils;
import promo.letspray.utility.PrayTime;

public class SplashActivity extends AppCompatActivity implements LocationListener {
    // Location Variables

    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private ProgressDialog pd = null;
    private LocationManager locationManager;
    private final static int DISTANCE_UPDATES = 1;
    private final static int TIME_UPDATES = 24*60*60*1000;
    private static final int PERMISSION_REQUEST_CODE = 1;
    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    double latitude;
    double longitude;

    // Database helper
    DatabaseHelper helper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        initLocation();
    }

    private void initLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        progressBar.setVisibility(View.VISIBLE);
      //  isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
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

//    private void initLocation() {
//
//        progressBar.setVisibility(View.VISIBLE);
//
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        // getting GPS status
//        isGPSEnabled = locationManager
//                .isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//        // getting network status
//        isNetworkEnabled = locationManager
//                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//        if (!isGPSEnabled && !isNetworkEnabled) {
//            // no network provider is enabled
//            showSettingsAlert("GPS");
//            progressBar.setVisibility(View.GONE);
//
//        }else{
//            if(ApplicationUtils.checkPermission(this)){
//                if(isNetworkEnabled){
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, TIME_UPDATES, DISTANCE_UPDATES, this);
//                    Log.d("Network", "Network");
//                    if (locationManager != null) {
//                        Location location = locationManager.getLastKnownLocation
//                                (LocationManager.GPS_PROVIDER);;
//                        onLocationChanged(location);
//                        if (location != null) {
//                            latitude = location.getLatitude();
//                            longitude = location.getLongitude();
//                        }
//                    }
//
//                }//isNetworkEnabled
//
//                if(isGPSEnabled){
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_UPDATES, DISTANCE_UPDATES, this);
//                    Log.d("GPS", "GPS");
//                    if (locationManager != null) {
//                        Location location = locationManager.getLastKnownLocation
//                                (LocationManager.GPS_PROVIDER);;
//                        onLocationChanged(location);
//                        if (location != null) {
//                            latitude = location.getLatitude();
//                            longitude = location.getLongitude();
//                        }
//                    }
//
//                }//isGPSEnabled
//            }else{
//                ApplicationUtils.requestPermission(this);
//            }
//        }
//    }




    /**
     * Monitor for location changes
     *
     * @param location holds the new location
     */
    @Override
    public void onLocationChanged(Location location) {

        if(location != null){
            setPrayerTImes(location.getLatitude(), location.getLongitude());
            progressBar.setVisibility(View.GONE);
        }else{
//            Snackbar snackbar = Snackbar
//                    .make(coordinatorLayout, "No GPS connection!", Snackbar.LENGTH_LONG)
//                    .setAction("RETRY", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                         //   setPrayerTImes(location.getLatitude(), location.getLongitude());
//                        }
//                    });
//            snackbar.show();

        }
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

    //////////////////// GOING TO SETTING MENU //////////////////////////////////////////

    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                SplashActivity.this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        SplashActivity.this.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        System.exit(0);
                    }
                });

        alertDialog.show();
    }

    public void progressBar(){

        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Loading...");
        pd.setMessage("Finding your location... Please wait");
        pd.setCancelable(Boolean.FALSE);
        pd.setIndeterminate(Boolean.TRUE);
        pd.show();
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

        ArrayList<Prayer> prayerList = new ArrayList<>();
        for (int i = 0; i < prayerNames.size(); i++) {

            //Add Prayer time in Database
            if(i!=4) {
                Log.e("Prayer Time", prayerNames.get(i).toString() + " " + prayerTimes.get(i).toString());
                Prayer prayer = new Prayer();
                prayer.setPrayerName((String) prayerNames.get(i));
                prayer.setPrayerTime((String) prayerTimes.get(i));
                prayerList.add(prayer);
            }else{
                continue;
            }
        }
        helper.insertPrayer(prayerList);

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

}
