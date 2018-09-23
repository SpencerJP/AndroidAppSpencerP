package rmit.s3539519.madassignment1.model;
import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;

public class SQLiteConnection {

    private Context context;

    public SQLiteConnection(Context context) {
        this.context = context;
        try {
            DriverManager.registerDriver((Driver) Class.forName("org.sqldroid.SQLDroidDriver").newInstance());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqldroid:" + context.getExternalFilesDir(null).getAbsolutePath() + "/geotracker.db";
            Log.i("jdbc url", url);
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            Log.i("SQLite","Connection to SQLite has been established.");
            return conn;

        } catch (SQLException e) {

            Log.i("SQLite", e.getMessage());
            return null;
        }
    }

    public boolean createTablesIfTheyDontExist() {
        try {
            Connection conn = connect();
            if (conn == null) {
                return false;
            }
            Statement stmt = null;
            stmt = conn.createStatement();

            String sql = "CREATE TABLE" +
                    " IF NOT EXISTS" +
                    " TRACKABLES " +
                    "(id INTEGER not NULL, " +
                    " name text, " +
                    " description text, " +
                    " url text, " +
                    " category text," +
                    " photo text," +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);

            stmt = conn.createStatement();

            sql = "CREATE TABLE" +
                    " IF NOT EXISTS" +
                    " TRACKINGS " +
                    "(id INTEGER not NULL, " +
                    " title text, " +
                    " startTime text, " +
                    " endTime text, " +
                    " meetTime text," +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);

            conn.close();
            return true;
        }
        catch(SQLException e) {
            Log.i("SQLite createTablesIfTheyDontExist",e.getMessage());
            return false;
        }
    }


    public boolean createTracking(Tracking tracking) {
        try {
            Connection conn = connect();
            if (conn == null) {
                return false;
            }
            Statement stmt = null;
            stmt = conn.createStatement();

            String sql = String.format("INSERT INTO TRACKINGS " +
            "VALUES (%s," +
                    " '%s'," +
                    " '%d'," +
                    " '%d'," +
                    " '%d')", tracking.getTrackingId(), tracking.getTitle(), tracking.getStartTime().getTime(), tracking.getEndTime().getTime(), tracking.getMeetTime().getTime());

            stmt.executeUpdate(sql);
            conn.close();
            return true;
        }
        catch(SQLException e) {
            Log.i("SQLite createTracking",e.getMessage());
            return false;
        }
    }

    public Tracking readTracking(int trackingId) {
        try {
            Connection conn = connect();
            if (conn == null) {
                return null;
            }
            Statement stmt = null;
            stmt = conn.createStatement();

            String sql = String.format("SELECT FROM TRACKINGS" +
                    " WHERE id = %d", trackingId);
            ResultSet rs    = stmt.executeQuery(sql);
            while(rs.next()) {
                Tracking t = new Tracking(rs.getInt("id"), rs.getString("title"), new Date(rs.getLong("startTime")), new Date(rs.getLong("endTime")), new Date(rs.getLong("meetTime")) );
                conn.close();
                return t;
            }
            conn.close();
            return null;
        }
        catch(SQLException e) {
            Log.i("SQLite readTracking",e.getMessage());
            return null;
        }
    }

    public  boolean updateTracking(Tracking tracking) {
        boolean success = false;
        success = deleteTracking(tracking.getTrackableId());
        if (success) {
            success = createTracking(tracking);
        }
        return success;
    }

    public boolean deleteTracking(int trackingId) {
        try {
            Connection conn = connect();
            if (conn == null) {
                return false;
            }
            Statement stmt = null;
            stmt = conn.createStatement();

            String sql = String.format("DELETE FROM TRACKINGS" +
                " WHERE id = %d", trackingId);
            stmt.executeUpdate(sql);
            conn.close();
            return true;
        }
        catch(SQLException e) {
            Log.i("SQLite deleteTracking",e.getMessage());
            return false;
        }
    }

    public int getTrackingCount() {
        try {
            int i = 0;
            Connection conn = connect();
            if (conn == null) {
                return 0;
            }
            Statement stmt = null;
            stmt = conn.createStatement();

            String sql = "SELECT COUNT(*) AS total FROM TRACKINGS";
            ResultSet rs    = stmt.executeQuery(sql);
            rs.next();
            i = rs.getInt("total");
            conn.close();
            return i;
        }
        catch(SQLException e) {
            Log.i("SQLite getTrackingCount",e.getMessage());
            return 0;
        }
    }

    public boolean importTrackingList(Map<Integer, Tracking> destinationMapForTrackings) {
        try {
            Connection conn = connect();
            if (conn == null) {
                return false;
            }
            Statement stmt = null;
            stmt = conn.createStatement();

            String sql = "SELECT * FROM TRACKINGS";
            ResultSet rs    = stmt.executeQuery(sql);
            while(rs.next()) {
                Tracking t = new Tracking(rs.getInt("id"), rs.getString("title"), new Date(rs.getLong("startTime")), new Date(rs.getLong("endTime")), new Date(rs.getLong("meetTime")) );
                Log.i("importing objects from SQLite", "Tracking: " + t.getTitle());
                destinationMapForTrackings.put(t.getTrackableId(), t);
            }
            conn.close();
            return true;
        }
        catch(SQLException e) {
            Log.i("SQLite importTrackingList",e.getMessage());
            return false;
        }
    }

    public boolean createTrackable(Trackable trackable) {
        try {
            Connection conn = connect();
            if (conn == null) {
                return false;
            }
            Statement stmt = null;
            stmt = conn.createStatement();

            String sql = String.format("INSERT INTO TRACKABLES " +
                    "VALUES (%s," +
                    " '%s'," +
                    " '%s'," +
                    " '%s'," +
                    " '%s'," +
                    " '%s')", trackable.getId(), trackable.getName(), trackable.getDescription(), trackable.getUrl(), trackable.getCategory(), trackable.getPhoto());

            stmt.executeUpdate(sql);
            conn.close();
            return true;
        }
        catch(SQLException e) {
            Log.i("SQLite createTrackable",e.getMessage());
            return false;
        }
    }

    public AbstractTrackable readTrackable(int trackableId) {
        try {
            Connection conn = connect();
            if (conn == null) {
                return null;
            }
            Statement stmt = null;
            stmt = conn.createStatement();

            String sql = String.format("SELECT FROM TRACKABLES" +
                    " WHERE id = %d", trackableId);
            ResultSet rs    = stmt.executeQuery(sql);
            while(rs.next()) {
                FoodTruck t = new FoodTruck(Integer.toString(rs.getInt("id")), rs.getString("name"), rs.getString("description"), rs.getString("url"), rs.getString("category"), rs.getString("photo") );
                conn.close();
                return t;
            }
            conn.close();
            return null;
        }
        catch(SQLException e) {
            Log.i("SQLite readTrackable",e.getMessage());
            return null;
        }
    }

    public  boolean updateTrackable(Trackable trackable) {
        boolean success = false;
        success = deleteTrackable(trackable.getId());
        if (success) {
            success = createTrackable(trackable);
        }
        return success;
    }

    public boolean deleteTrackable(String trackableId) {
        try {
            Connection conn = connect();
            if (conn == null) {
                return false;
            }
            Statement stmt = null;
            stmt = conn.createStatement();

            String sql = String.format("DELETE FROM TRACKABLES" +
                    " WHERE id = %s", trackableId);
            stmt.executeUpdate(sql);
            conn.close();
            return true;
        }
        catch(SQLException e) {
            Log.i("SQLite deleteTrackable",e.getMessage());
            return false;
        }
    }

    public int getTrackableCount() {
        try {
            int i = 0;
            Connection conn = connect();
            if (conn == null) {
                return 0;
            }
            Statement stmt = null;
            stmt = conn.createStatement();

            String sql = "SELECT COUNT(*) AS total FROM TRACKABLES";
            ResultSet rs    = stmt.executeQuery(sql);
            rs.next();
            i = rs.getInt("total");

            conn.close();
            return i;
        }
        catch(SQLException e) {
            Log.i("SQLite getTrackableCount",e.getMessage());
            return 0;
        }
    }

    public boolean importTrackableList(Map<Integer, AbstractTrackable> destinationMapForTrackables) {
        try {
            Connection conn = connect();
            if (conn == null) {
                return false;
            }
            Statement stmt = null;
            stmt = conn.createStatement();

            String sql = "SELECT * FROM TRACKABLES";
            ResultSet rs    = stmt.executeQuery(sql);
            while(rs.next()) {
                FoodTruck t = new FoodTruck(Integer.toString(rs.getInt("id")), rs.getString("name"), rs.getString("description"), rs.getString("url"), rs.getString("category"), rs.getString("photo") );
                Log.i("importing objects from SQLite", "FoodTruck: " + t.getName());
                destinationMapForTrackables.put(Integer.parseInt(t.getId()), t);
            }
            conn.close();
            return true;
        }
        catch(SQLException e) {
            Log.i("SQLite importTrackableList",e.getMessage());
            return false;
        }
    }
}

