package rmit.s3539519.madassignment1.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.view.NavigationItemSelectedListener;

public class TrackerActivity extends AppCompatActivity {

    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_trackers);
        navigation.setOnNavigationItemSelectedListener(new NavigationItemSelectedListener(this, navigation));

    }

}
