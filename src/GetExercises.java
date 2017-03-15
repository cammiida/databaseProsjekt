import java.sql.*;


public class GetExercises {

    Statement stmt = null;
    ResultSet rs = null;

    public GetExercises(Connection connection){

        String selectTableSQL = "SELECT Navn, Beskrivelse FROM Ovelse";

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectTableSQL);
            System.out.println("Exercises:");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("Navn") +
                        "   Description: " + rs.getString("Beskrivelse"));

            }

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