import java.sql.*;


public class ExerciseDiary {

    Statement stmt = null;
    ResultSet rs = null;

    public ExerciseDiary(Connection connection) {

        String selectTableSQL = "SELECT Notat, Dato FROM Treningsokt";

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectTableSQL);
            System.out.println("Notatlog:");
            while (rs.next()) {
                System.out.println("Dato: " + rs.getString("Dato"));
                System.out.println("Notat: " + rs.getString("Notat"));

            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
        }

    }

}
