package rmit.s3539519.madassignment1.view;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;

public class CategorySpinnerListener implements AdapterView.OnItemSelectedListener {

    CategorySpinnerAdapter categorySpinnerAdapter;
    TrackableAdapter trackableAdapter;
    Context context;
    public CategorySpinnerListener(Context context, CategorySpinnerAdapter categorySpinnerAdapter, TrackableAdapter trackableAdapter) {
        this.context = context;
        this.categorySpinnerAdapter = categorySpinnerAdapter;
        this.trackableAdapter = trackableAdapter;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String category = categorySpinnerAdapter.getItem(position);

        trackableAdapter.getFilter().filter(category,new Filter.FilterListener() {
            @Override
            public void onFilterComplete(int count) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
