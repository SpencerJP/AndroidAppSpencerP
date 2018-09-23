package rmit.s3539519.madassignment1.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Pattern;

// class to read the file into memory
public class Importer implements Runnable {


    private Activity context;

    public Importer(Activity context) {
        this.context = context;
    }
    Map<Integer, AbstractTrackable> destinationMapForTrackables;
    Map<Integer, Tracking> destinationMapForTrackings;
    String txtFile = "";
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

    public void loadDatabase(final String backupTxtFile, final Map<Integer, AbstractTrackable> destinationMapForTrackables, Map<Integer, Tracking> destinationMapForTrackings) {
        this.destinationMapForTrackables = destinationMapForTrackables;
        this.destinationMapForTrackings = destinationMapForTrackings;
        this.txtFile = backupTxtFile;
        Thread t  = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        SQLiteConnection sqlObj = new SQLiteConnection(context);
        sqlObj.createTablesIfTheyDontExist();
        if (sqlObj.getTrackableCount() > 0) {
            sqlObj.importTrackableList(destinationMapForTrackables);
        }
        else {
            stringToFoodTruckMap(txtFile, destinationMapForTrackables);
            for(Map.Entry<Integer, AbstractTrackable> entry : destinationMapForTrackables.entrySet()) {
                sqlObj.createTrackable(entry.getValue());
            }
        }
        if (sqlObj.getTrackingCount() > 0) {
            sqlObj.importTrackingList(destinationMapForTrackings);
        }
        else {
            Observer.getSingletonInstance(context).seedTrackings();
            for(Map.Entry<Integer, Tracking> entry : Observer.getSingletonInstance(context).getTrackings().entrySet()) {
                sqlObj.createTracking(entry.getValue());
            }
        }
        context.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                Observer.getSingletonInstance(context).updateViews();

            }
        });
    }
}
