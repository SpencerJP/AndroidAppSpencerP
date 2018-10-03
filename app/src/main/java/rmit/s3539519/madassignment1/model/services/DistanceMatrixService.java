package rmit.s3539519.madassignment1.model.services;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import rmit.s3539519.madassignment1.model.DistanceMatrixModel;

public class DistanceMatrixService {

    // empty constructor
    private Context context;
    private DistanceMatrixService() {
    }

    private static class DistanceMatrixSingleton
    {
        static final DistanceMatrixService INSTANCE = new DistanceMatrixService();
    }


    public static DistanceMatrixService getSingletonInstance(Context context)
    {
        DistanceMatrixService.DistanceMatrixSingleton.INSTANCE.context = context;
        return DistanceMatrixService.DistanceMatrixSingleton.INSTANCE;
    }

    public DistanceMatrixModel makeAPIRequest(double sourceLat, double sourceLong, double destLat, double destLong) {
        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&key=" + "AIzaSyDl4OEv3cjEhEu87910XI4eCdYDRS3afZI" + "&" + String.format("origins=%.6f,%.6f&destinations=%.6f,%.6f", sourceLat, sourceLong, destLat, destLong));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.connect();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String output = "";
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                output = output + inputLine;
            }
            in.close();
            DistanceMatrixModel obj = new DistanceMatrixModel(output);
            con.disconnect();
            return obj;
        } catch (ProtocolException e) {
            Log.i("distancematrixmodel", e.getMessage());
            return null;
        } catch (MalformedURLException e) {
            Log.i("distancematrixmodel", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.i("distancematrixmodel", e.getMessage());
            return null;
        } catch (JSONException e) {
            Log.i("distancematrixmodel", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public String encodeParams(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }
        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

}
