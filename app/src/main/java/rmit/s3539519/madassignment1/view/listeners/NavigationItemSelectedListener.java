package rmit.s3539519.madassignment1.view.listeners;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.controller.TrackableListActivity;
import rmit.s3539519.madassignment1.controller.TrackingListActivity;

public class NavigationItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Context context;
    BottomNavigationView navigation;
    public NavigationItemSelectedListener(Context context, BottomNavigationView navigation ) {
        this.context = context;
        this.navigation = navigation;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.navigation_trackables:
                    if (this.context.getClass().toString().contains("TrackableListActivity")) {
                        return false;
                    }
                    Intent startTrackables = new Intent(context, TrackableListActivity.class);
                    context.startActivity(startTrackables);
                    return true;
                case R.id.navigation_trackers:
                    if (this.context.getClass().toString().contains("TrackingListActivity")) {
                        return false;
                    }
                    Intent startTracking = new Intent(context, TrackingListActivity.class);
                    context.startActivity(startTracking);

                    navigation.setSelectedItemId(R.id.navigation_trackables);
                    return true;
            }
            return false;
    }
}
