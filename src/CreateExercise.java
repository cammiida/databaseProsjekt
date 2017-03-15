import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.*;
import java.util.Scanner;


public class CreateExercise {

    Statement stmt = null;
    ResultSet rs = null;
    String exerciseName;
    String exerciseDescription;
    Boolean nameCheckValid = true;

    public CreateExercise(Connection connection, Scanner scanner){

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT Navn FROM Ovelse");


            System.out.println("Write the exercise name:");
            exerciseName = scanner.nextLine();

            while (rs.next() && nameCheckValid) {
                if (rs.getString("Navn").equals(exerciseName)) {
                    System.out.println("That exercise already exist. Write in anorther name:");
                    exerciseName = scanner.nextLine();
                } else {
                    nameCheckValid = false;
                }
            }
            System.out.println("Write a description of the exercise(can be empty):");
            exerciseDescription = scanner.nextLine();

            String insertTableSQL = "INSERT INTO Ovelse (Navn, Beskrivelse) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1,exerciseName);
            preparedStatement.setString(2,exerciseDescription);

            preparedStatement.executeUpdate();
            System.out.println("You just created a exercise whit name " + exerciseName +
                    " and description " + exerciseDescription);

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