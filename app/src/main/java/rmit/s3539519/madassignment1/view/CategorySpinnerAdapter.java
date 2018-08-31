package rmit.s3539519.madassignment1.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> categoryList;

    public CategorySpinnerAdapter(Context context, int textViewResourceId, List<String> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.categoryList = values;
    }

    public int getCount(){
        return categoryList.size();
    }

    public String getItem(int position){
        return categoryList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(context);
        view.setTextColor(Color.BLACK);
        view.setGravity(Gravity.CENTER);
        view.setText(categoryList.get(position));

        return view;
    }

    //View of Spinner on dropdown Popping

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView view = new TextView(context);
        view.setTextColor(Color.BLACK);
        view.setText(categoryList.get(position));
        view.setHeight(60);

        return view;
    }
}