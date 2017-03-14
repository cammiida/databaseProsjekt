import java.sql.*;
import java.util.Scanner;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class LoadDriver {


    public static void main(String[] args) {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }

        Connection conn = null;

        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/eivinklo_db-gr2",
                            "camillmd", "password");

            // Do something with the Connection
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = conn.createStatement();
                //rs = stmt.executeQuery("SELECT * from Gruppe");

                Scanner nyScanner = new Scanner(System.in);


                //create objects for the different user cases
                CreateWorkout workoutCreater = new CreateWorkout(conn);




                if (stmt.execute("SELECT * FROM Gruppe")) {
                    rs = stmt.getResultSet();
                }
                rs.next();
                System.out.println(rs.getString("Navn"));
                System.out.println(rs.getString("Beskrivelse"));

                }

            catch (SQLException ex){
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            finally {
                // it is a good idea to release
                // resources in a finally{} block
                // in reverse-order of their creation
                // if they are no-longer needed

                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException sqlEx) { } // ignore

                    rs = null;
                }

                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) { } // ignore

                    stmt = null;
                }
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }


    }


}


