
import java.sql.*;


public class CreateWorkout {

    Statement stmt = null;
    ResultSet rs = null;

    public CreateWorkout(Connection connection){

        try {
            stmt = connection.createStatement();
            //rs = stmt.executeQuery("SELECT * from Gruppe");


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

    }



}