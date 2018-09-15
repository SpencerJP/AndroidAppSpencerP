package rmit.s3539519.madassignment1.model;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    public void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:geotracker.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            Log.i("SQLite","Connection to SQLite has been established.");

        } catch (SQLException e) {
            Log.i("SQLite",e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Log.i("SQLite",ex.getMessage());
            }
        }
    }


    public boolean createTracking(Tracking tracking) {
        return false;
    }

    public Tracking readTracking(int trackingId) {
        return null;
    }

    public  boolean updateTracking(Tracking tracking) {
        return false;
    }

    public boolean deleteTracking(int trackingId) {
        return false;
    }

    public boolean createTrackable(Trackable trackable) {
        return false;
    }

    public AbstractTrackable readTrackable(int trackableId) {
        return null;
    }

    public  boolean updateTrackable(Trackable trackable) {
        return false;
    }

    public boolean deleteTrackable(int trackableId) {
        return false;
    }
}

