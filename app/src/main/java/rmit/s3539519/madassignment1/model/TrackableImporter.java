package rmit.s3539519.madassignment1.model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import rmit.s3539519.madassignment1.R;

public class TrackableImporter {

    public String readFile(Context context, int resId) {
        String s = "";
        try {

            InputStream foodtruckdata = context.getResources().openRawResource(resId);
            BufferedReader br = new BufferedReader(new InputStreamReader(foodtruckdata));

            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                s = s + line + "\n";

            }
            return s;
        }
        catch(IOException ex) {
            Log.i("IOException", "An IOException occurred in the model.TrackableImporter class. " + ex.getMessage());
        }
        return null;
    }

    public void stringToFoodTruckMap(String toBeImported, Map<Integer, AbstractTrackable> destinationMap) {
        int i = 0;
        String[] lines = toBeImported.split("\n");
        for(String line : lines)  {
                String[] parts = line.split(Pattern.quote("|"));
                FoodTruck ft = new FoodTruck(parts[0], parts[1], parts[2], parts[3], parts[4], "");
                destinationMap.put((Integer) i, ft);
                i++;
        }
    }
}
