package rmit.s3539519.madassignment1.view;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.AbstractTrackable;
import rmit.s3539519.madassignment1.model.services.Observer;
import rmit.s3539519.madassignment1.model.TrackingInfo;
import rmit.s3539519.madassignment1.model.services.TrackingService;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String EXTRA_SCHEDULE_INFORMATION = "s3539519_ScheduleInformation";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng routeMarker = new LatLng(-34, 151);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        Intent intent = getIntent();
        int id = intent.getIntExtra(EXTRA_SCHEDULE_INFORMATION, -1);

        AbstractTrackable trackable = Observer.getSingletonInstance(this).getTrackableById(id);
        List<TrackingInfo> trackingInfos  = new ArrayList<TrackingInfo>(TrackingService.getSingletonInstance(this).getTrackingInfo());

        Iterator<TrackingInfo> iter = trackingInfos.iterator();
        while(iter.hasNext()) {
            TrackingInfo next = iter.next();
            if(next.trackableId != id) {
                iter.remove();
            }
        }
        if (trackingInfos.size() != 0) {
            for (TrackingInfo t : trackingInfos) {
                routeMarker = new LatLng(t.latitude, t.longitude);
                mMap.addMarker(new MarkerOptions().position(routeMarker).title("Route Marker for "  + trackable.getName()));
                builder.include(routeMarker);


            }
        }



        LatLngBounds bounds = builder.build();
        int padding = 200; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);

    }
}
