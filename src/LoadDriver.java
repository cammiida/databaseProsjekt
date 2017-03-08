import java.sql.*;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class LoadDriver {


    public static void main(String[] args) {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
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

                /*or alternatively, if you don't know ahead of time that
                 the query will be a SELECT...*/

                if (stmt.execute("SELECT * FROM Gruppe")) {
                    rs = stmt.getResultSet();
                }
                System.out.println(rs.toString());

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


