package rmit.s3539519.madassignment1.controller;

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
import rmit.s3539519.madassignment1.model.Observer;
import rmit.s3539519.madassignment1.model.TrackableImporter;
import rmit.s3539519.madassignment1.model.TestTrackingService;
import rmit.s3539519.madassignment1.view.CategorySpinnerAdapter;
import rmit.s3539519.madassignment1.view.listeners.CategorySpinnerListener;
import rmit.s3539519.madassignment1.view.listeners.NavigationItemSelectedListener;
import rmit.s3539519.madassignment1.view.TrackableAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private TrackableImporter trackableImporter;
    private CategorySpinnerAdapter categorySpinnerAdapter;
    private Observer observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackable_list);
        TestTrackingService.test(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new NavigationItemSelectedListener(this, navigation));


        // load trackable list from txt file
        HashMap<Integer, AbstractTrackable> trackables = new HashMap<Integer, AbstractTrackable>();
        observer = Observer.getSingletonInstance(this);
        observer.importTrackables();
        trackables = new HashMap<Integer, AbstractTrackable>(observer.getTrackables());

        Spinner categorySpinner = findViewById(R.id.categorySpinner);

        RecyclerView  trackableRecyclerView = findViewById(R.id.trackableRecyclerView);

        categorySpinnerAdapter = new CategorySpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, observer.extractCategoryList(trackables) );

        trackableRecyclerView.setHasFixedSize(true);

        LinearLayoutManager trackableLayoutManager = new LinearLayoutManager(this);
        trackableRecyclerView.setLayoutManager(trackableLayoutManager);

        trackableRecyclerView.setItemAnimator(new DefaultItemAnimator());

        TrackableAdapter trackableAdapter = new TrackableAdapter(this, trackables);

        CategorySpinnerListener categorySpinnerListener = new CategorySpinnerListener(this, categorySpinnerAdapter, trackableAdapter);

        categorySpinner.setAdapter(categorySpinnerAdapter);
        categorySpinner.setOnItemSelectedListener(categorySpinnerListener);

        trackableRecyclerView.setAdapter(trackableAdapter);

    }


}
