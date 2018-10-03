package rmit.s3539519.madassignment1.view;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.services.Observer;
import rmit.s3539519.madassignment1.model.Tracking;
import rmit.s3539519.madassignment1.view.viewmodels.TrackingAdapter;
import rmit.s3539519.madassignment1.controller.NavigationItemSelectedListener;

public class TrackingListActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Observer observer;
    private Map<Integer, Tracking> trackings;
    TrackingAdapter trackingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_list);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_trackers);
        navigation.setOnNavigationItemSelectedListener(new NavigationItemSelectedListener(this, navigation));


        RecyclerView trackingRecyclerView = findViewById(R.id.trackingRecyclerView);
        observer = Observer.getSingletonInstance(this);
        trackings = new HashMap<Integer, Tracking>(observer.getTrackings());

        trackingRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        trackingRecyclerView.setLayoutManager(mLayoutManager);
        trackingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (observer.getTrackingAdapter() != null) {
            trackingAdapter = observer.getTrackingAdapter();
        }
        else {
            trackingAdapter = new TrackingAdapter(this, trackings);
            observer.setTrackingAdapter(trackingAdapter);
        }
        trackingRecyclerView.setAdapter(trackingAdapter);

    }

}
