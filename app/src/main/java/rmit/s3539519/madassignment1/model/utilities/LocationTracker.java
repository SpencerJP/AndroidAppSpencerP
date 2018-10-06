package rmit.s3539519.madassignment1.model.utilities;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;

public class LocationTracker implements LocationListener {
    private Context context;
    private double latitude;
    private double longitude;
    private LocationManager locationManager;
    private Location location;

    public LocationTracker(Context context, LocationManager locationManager) {
        this.context = context;
        this.locationManager = locationManager;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    public Location getLocation() throws SecurityException {
        try {
            Log.i("SuggestTracking", "TEST1");
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            Log.i("SuggestTracking", "TEST2");
            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            Log.i("SuggestTracking", "TEST3");
            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Log.i("SuggestTracking", "TEST4");
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                // First get location from Network Provider

                Log.i("SuggestTracking", "TEST5");
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            0,
                            0, this);
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services

                Log.i("SuggestTracking", "TEST6");
                if (isGPSEnabled) {

                    Log.i("SuggestTracking", "TEST7");
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                0,
                                0, this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }
    
    public double getLatitude() {
        if(getLocation() != null) {
            return getLocation().getLatitude();
        }
        return 0;
    }

    public double getLongitude() {
        if(getLocation() != null) {
            return getLocation().getLongitude();
        }
        return 0;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}
}
