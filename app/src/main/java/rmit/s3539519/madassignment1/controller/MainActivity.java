package rmit.s3539519.madassignment1.controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.AbstractTrackable;
import rmit.s3539519.madassignment1.model.Observer;
import rmit.s3539519.madassignment1.model.TrackableImporter;
import rmit.s3539519.madassignment1.model.FoodTruck;
import rmit.s3539519.madassignment1.model.TestTrackingService;
import rmit.s3539519.madassignment1.view.CategorySpinnerAdapter;
import rmit.s3539519.madassignment1.view.CategorySpinnerListener;
import rmit.s3539519.madassignment1.view.NavigationItemSelectedListener;
import rmit.s3539519.madassignment1.view.TrackableAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private TrackableImporter trackableImporter;
    private CategorySpinnerAdapter categorySpinnerAdapter;
    private Observer observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackable_list);
        TestTrackingService.test(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new NavigationItemSelectedListener(this, navigation));



        HashMap<Integer, AbstractTrackable> trackables = new HashMap<Integer, AbstractTrackable>();
        observer = Observer.getSingletonInstance(this);
        observer.importTrackables();
        trackables = new HashMap<Integer, AbstractTrackable>(observer.getTrackables());

        Spinner mSpinner = findViewById(R.id.categorySpinner);
        categorySpinnerAdapter = new CategorySpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.extractCategoryList(trackables) );



        RecyclerView  mRecyclerView = findViewById(R.id.the_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        TrackableAdapter tAdapter = new TrackableAdapter(this, trackables);
        CategorySpinnerListener categorySpinnerListener = new CategorySpinnerListener(this, categorySpinnerAdapter, tAdapter);
        mSpinner.setAdapter(categorySpinnerAdapter);
        mSpinner.setOnItemSelectedListener(categorySpinnerListener);
        mRecyclerView.setAdapter(tAdapter);

    }

    private List<String> extractCategoryList(Map<Integer, AbstractTrackable> trackableList) {
        // using a set to ensure there are no duplicate categories
        Set<String> categorySet = new HashSet<String>();
        List<String> categories = new ArrayList<String>();
        categorySet.add("All");// add "All" to our spinner, make sure it cannot be duplicated
        for(Map.Entry<Integer, AbstractTrackable> entry : trackableList.entrySet()) {
            categorySet.add(entry.getValue().getCategory());
        }
        for(String s : categorySet) {
            categories.add(s);
        }
        return categories;
    }
}
