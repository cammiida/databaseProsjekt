import java.sql.*;
import java.util.Scanner;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class LoadDriver {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no/eivinklo_db-gr2";

    //  Database credentials
    static final String USER = "camillmd";
    static final String PASS = "password";


    public static void main(String[] args) {


        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName(JDBC_DRIVER).newInstance();
        } catch (Exception ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }

        Connection conn = null;

        try {
            conn =
                    DriverManager.getConnection(DB_URL,
                            USER, PASS);

            //create objects for the different user cases
            CreateWorkout workoutCreater = new CreateWorkout(conn);

            // Do something with the Connection
            //Statement stmt = null;
            //ResultSet rs = null;



        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }


    }


}