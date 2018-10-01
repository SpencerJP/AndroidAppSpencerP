package rmit.s3539519.madassignment1.model;

import android.util.Log;

import org.json.*;
public class DistanceMatrixModel {

    private String sourceAddress;
    private String destinationAddress;
    private long distanceInMetres;
    private long timeDifference;
    public DistanceMatrixModel(String output) throws JSONException {
            JSONObject obj = new JSONObject(output);
            this.destinationAddress = obj.getJSONArray("destination_addresses").getString(0);
            this.destinationAddress = obj.getJSONArray("origin_addresses").getString(0);
            this.distanceInMetres = obj.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("distance").getInt("value");
            this.timeDifference = obj.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getInt("value");

    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public long getDistanceInMetres() {
        return distanceInMetres;
    }

    public long getTimeDifference() {
        return timeDifference;
    }
}
