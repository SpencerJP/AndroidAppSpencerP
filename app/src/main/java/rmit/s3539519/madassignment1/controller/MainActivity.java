package rmit.s3539519.madassignment1.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.FoodTruck;
import rmit.s3539519.madassignment1.model.TestTrackingService;
import rmit.s3539519.madassignment1.view.TrackableAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestTrackingService.test(this);

        Map<Integer, FoodTruck> trackables = new HashMap<Integer, FoodTruck>();

        try {

            InputStream foodtruckdata = this.getResources().openRawResource(R.raw.food_truck_data);
            BufferedReader br = new BufferedReader(new InputStreamReader(foodtruckdata));

            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("|"));
                FoodTruck ft  = new FoodTruck(parts[0], parts[1], parts[2], parts[3], parts[4], "");
                trackables.put((Integer) i, ft);
                i++;
            }
        }
        catch(Exception e) {

        }
        for(Map.Entry<Integer, FoodTruck> entry : trackables.entrySet()) {
            Log.i(LOG_TAG, entry.getValue().toString());
        }
        //LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        TrackableAdapter tAdapter = new TrackableAdapter(trackables);

        RecyclerView  mRecyclerView = findViewById(R.id.mainRecyclerView);

        //mRecyclerView.setLayoutManager(mLayoutManager);
        // Plug the adapter into the RecyclerView:
        mRecyclerView.setAdapter(tAdapter);
    }
}
