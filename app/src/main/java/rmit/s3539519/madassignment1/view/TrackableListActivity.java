package rmit.s3539519.madassignment1.view;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Spinner;

import java.util.HashMap;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.AbstractTrackable;
import rmit.s3539519.madassignment1.model.DistanceMatrixAPIThread;
import rmit.s3539519.madassignment1.model.Importer;
import rmit.s3539519.madassignment1.model.Observer;
import rmit.s3539519.madassignment1.view.viewmodels.GeoTrackerSpinnerAdapter;
import rmit.s3539519.madassignment1.controller.CategorySpinnerListener;
import rmit.s3539519.madassignment1.controller.NavigationItemSelectedListener;
import rmit.s3539519.madassignment1.view.viewmodels.TrackableAdapter;

public class TrackableListActivity extends AppCompatActivity {

    private static final String LOG_TAG = TrackableListActivity.class.getName();
    private Importer importer;
    private GeoTrackerSpinnerAdapter categorySpinnerAdapter;
    private Observer observer;
    private HashMap<Integer, AbstractTrackable> trackables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load trackable list from txt file
        trackables = new HashMap<Integer, AbstractTrackable>();
        observer = Observer.getSingletonInstance(this);

        observer.createSQLTables();
        observer.importData();

        // my house to mcdonalds

        Thread t = new Thread(new DistanceMatrixAPIThread(this, -37.578101, 144.715287, -37.576557, 144.728455));
        t.start();
    }

    @Override
    public void onStart() {

        super.onStart();
        setContentView(R.layout.activity_trackable_list);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new NavigationItemSelectedListener(this, navigation));



        // hardcoded trackings
        //observer.seedTrackings();
        trackables = new HashMap<Integer, AbstractTrackable>(observer.getTrackables());
        // find spinner
        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        // find list
        RecyclerView  trackableRecyclerView = findViewById(R.id.trackableRecyclerView);

        categorySpinnerAdapter = new GeoTrackerSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, observer.extractCategoryList(trackables) );

        trackableRecyclerView.setHasFixedSize(true);

        LinearLayoutManager trackableLayoutManager = new LinearLayoutManager(this);
        trackableRecyclerView.setLayoutManager(trackableLayoutManager);
        // not sure if this is required but a lot of sample code online seemed to have it, plus I am animating the onclick
        trackableRecyclerView.setItemAnimator(new DefaultItemAnimator());

        TrackableAdapter trackableAdapter = new TrackableAdapter(this, trackables);

        CategorySpinnerListener categorySpinnerListener = new CategorySpinnerListener(this, categorySpinnerAdapter, trackableAdapter);

        categorySpinner.setAdapter(categorySpinnerAdapter);
        categorySpinner.setOnItemSelectedListener(categorySpinnerListener);

        trackableRecyclerView.setAdapter(trackableAdapter);
        observer.setTrackableAdapter(trackableAdapter);
    }

    @Override
    public void onStop() {

        super.onStop();
    }


}
