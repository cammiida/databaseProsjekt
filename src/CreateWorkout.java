
import java.sql.*;
import java.util.Scanner;


public class CreateWorkout {

    Statement stmt = null;
    ResultSet rs = null;
    Integer workoutId;
    String workoutDate;

    public CreateWorkout(Connection connection, Scanner scanner){

        try {
            stmt = connection.createStatement();
            //rs = stmt.executeQuery("SELECT * from Gruppe");

            System.out.println("Write the desired unique number for your workout. Maximum 11 numbers long.\n");
            workoutId = Integer.parseInt(scanner.nextLine());

            //get all primarykeys from earlier workouts and check if there is a match.
            //If there is a match, ask for a new number from the user.
            while(!check_primary_key(workoutId)){
                System.out.println("The id number is not valid. Try again.\n");
                workoutId = Integer.parseInt(scanner.nextLine());
            }


            System.out.println("Specify the date for the workout in the format: YYYY-MM-DD\n");
            workoutDate = scanner.nextLine();
            while(!ckeck_workout_date){
                System.out.println("The date is not valid. Try again.\n");
                workoutDate = scanner.nextLine();
            }



            if (stmt.execute("SELECT * FROM Treningsokt")){
                rs = stmt.getResultSet();
            }
            rs.next();




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

    public boolean check_primary_key(Integer id){

    }

    public boolean check_workout_date(Date)


}