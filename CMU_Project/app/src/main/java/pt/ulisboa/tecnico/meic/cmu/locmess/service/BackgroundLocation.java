package pt.ulisboa.tecnico.meic.cmu.locmess.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.simple.JSONObject;

import pt.ulisboa.tecnico.meic.cmu.locmess.connection.Action;
import pt.ulisboa.tecnico.meic.cmu.locmess.connection.Connection;
import pt.ulisboa.tecnico.meic.cmu.locmess.connection.MessageType;

/**
 * Created by Sheng on 25/04/2017.
 */

public class BackgroundLocation extends Service implements LocationListener {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Context mContext;

    public BackgroundLocation() {

    }

    public BackgroundLocation(Context context) {
        this.mContext = context;
        getLocation();
    }

    // flag for GPS Status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS Tracking is enabled
    boolean isGPSTrackingEnabled = false;

    private Location location=null;

    // How many Geocoder should return our GPSTracker
    int geocoderMaxResults = 1;

    // The minimum distance to change updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    // Store LocationManager.GPS_PROVIDER or LocationManager.NETWORK_PROVIDER information
    private String provider_info;

    /**
     * Try to get my current location by GPS or Network Provider
     */
    public void getLocation() {

            try {
                locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

                //getting GPS status
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                //getting network status
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                Log.d("Test1", "before if");
                // Try to get location if you GPS Service is enabled
                if (isGPSEnabled) {
                    this.isGPSTrackingEnabled = true;

                    Log.d("gps error", "Application use GPS Service");

                /*
                 * This provider determines location using
                 * satellites. Depending on conditions, this provider may take a while to return
                 * a location fix.
                 */

                    provider_info = LocationManager.GPS_PROVIDER;
                    Log.d("gps error", "Application use GPS Service" + provider_info.toString());

                }
                if (isNetworkEnabled) { // Try to get location if you Network Service is enabled
                    this.isGPSTrackingEnabled = true;

                    Log.d("network error", "Application use Network State to get GPS coordinates");

                /*
                 * This provider determines location based on
                 * availability of cell tower and WiFi access points. Results are retrieved
                 * by means of a network lookup.
                 */
                    provider_info = LocationManager.NETWORK_PROVIDER;

                }
                // Application can use GPS or Network Provider
                if (!provider_info.isEmpty()) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    locationManager.requestLocationUpdates(
                            provider_info,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES,
                            this
                    );

                    if (locationManager != null) {
                        Log.d("test1", "getLocation: "+provider_info);
                        Toast.makeText(mContext, "locationManager", Toast.LENGTH_SHORT).show();
                        location = locationManager.getLastKnownLocation(provider_info);
                        Log.d("test1", "location: "+location.toString());
                    }
                }


            } catch (Exception e) {
                Toast.makeText(mContext, "Exception", Toast.LENGTH_SHORT).show();
                //e.printStackTrace();
                Log.e("locationManager", "Impossible to connect to LocationManager", e);
            }

    }

    public Location userLocation(){
        return location;
    }

    /**
     * BackgroundLocation isGPSTrackingEnabled getter.
     * Check GPS/wifi is enabled
     */
    public boolean getIsGPSTrackingEnabled() {

        return this.isGPSTrackingEnabled;
    }

    /**
     * Stop using GPS listener
     * Calling this method will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(BackgroundLocation.this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(mContext, "location changed", Toast.LENGTH_SHORT).show();
        this.location = location;
        SharedPreferences sharedPref = getSharedPreferences("file", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", null);
        String sessionid=sharedPref.getString("sessionid", null);
        JSONObject json=new JSONObject();
        json.put("username", username);
        json.put("sessionid", sessionid);
        if(this.userLocation()!=null) {
            json.put("latitude", String.valueOf(location.getLatitude()));
            json.put("longitude", String.valueOf(location.getLongitude()));
        }
        Action action =new Action(MessageType.userlocation, json);
        json=new Connection().execute(action);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
