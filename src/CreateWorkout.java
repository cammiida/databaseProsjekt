
import java.sql.*;
import java.sql.Date;
import java.util.*;


public class CreateWorkout {

    Statement stmt = null;
    ResultSet rs = null;
    Integer workoutId;
    Date workoutDate;
    Time workoutTime;
    Integer duration;
    String personalShape;
    String notes;
    String workoutDateString;

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
            workoutDateString = scanner.nextLine();
            while(!check_workout_date(workoutDateString)){
                System.out.println("The date is not valid. Try again.\n");
                //workoutDate = scanner.nextLine();
            }


            //actually create a new workout in the database
            String insertTableSQL = "INSERT INTO Treningsokr"
                    + "(workoutId, workoutDate, workoutTime, duration, personalShape, notes) VALUES"
                    + "(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
            preparedStatement.setInt(1, workoutId);
            preparedStatement.setDate(2, workoutDate);
            preparedStatement.setTime(3, workoutTime);
            preparedStatement.setInt(4, duration);
            preparedStatement.setString(5, personalShape);
            preparedStatement.setString(6, notes);


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
        return true;
    }

    public boolean check_workout_date(String date){
        Date.parse(date);
        System.out.println(date.getClass());
        java.util.Date currentDate = new java.util.Date();
        System.out.println(currentDate.getClass());
        /*if (date > currentDate) {
            workoutDate = java.sql.Date.valueOf(date);
        }*/
        return true;
    }


}