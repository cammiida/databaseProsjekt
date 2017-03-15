
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class CreateWorkout {

    Statement stmt = null;
    ResultSet rs = null;
    Integer workoutId;
    Date workoutDate;
    Time workoutTime;
    Time duration;
    String personalShape;
    String notes;
    String workoutDateString;
    String workoutTimeString;
    String durationString;

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


            //ask for and check the date
            System.out.println("Specify the date for the workout in the format: YYYY-MM-DD\n");
            workoutDateString = scanner.nextLine();
            while(!check_workout_date(workoutDateString)){
                System.out.println("The date is not valid. Try again.\n");
                workoutDateString = scanner.nextLine();
            }

            //ask for an check the time
            System.out.println("Specify the time for the workout in the format: hh:mm");
            workoutTimeString = scanner.nextLine();
            while(!check_workout_time(workoutTimeString)){
                System.out.println("The time is not valid. Try again.\n");
                workoutTimeString = scanner.nextLine();
            }

            //ask for and check the duration
            System.out.println("How long should the workout last (in minutes)?");
            durationString = scanner.nextLine();
            while(!check_duration(durationString)){
                System.out.println("The time is not valid. Try again.\n");
                durationString = scanner.nextLine();
            }

            Integer durationInt = Integer.parseInt(durationString);

            //ask for and check the duration
            System.out.println("Write your personal shape or press enter:");
            personalShape = scanner.nextLine();

            //ask for and check the duration
            System.out.println("Write general notes or press enter:");
            notes = scanner.nextLine();

            //actually create a new workout in the database
            String insertTableSQL = "INSERT INTO Treningsokt"
                    + "(Id, Dato, Tidspunkt, Varighet, Personlig_form, Notat) VALUES"
                    + "(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
            preparedStatement.setInt(1, workoutId);
            preparedStatement.setDate(2, workoutDate);
            preparedStatement.setTime(3, workoutTime);
            preparedStatement.setInt(4, durationInt);
            preparedStatement.setString(5, personalShape);
            preparedStatement.setString(6, notes);

            preparedStatement.executeUpdate();

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

    public boolean check_workout_date(String dateString){
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
        // date here is a string of format yyyy-MM-dd

        try {
            java.util.Date date1 = newFormat.parse(dateString);
            java.sql.Date sqldate = new java.sql.Date(date1.getTime());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean check_workout_time(String timeString){
        SimpleDateFormat newFormat = new SimpleDateFormat("hh:mm");

        try {
            java.util.Date time1 = newFormat.parse(timeString);
            java.sql.Date sqltime = new java.sql.Date(time1.getTime());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean check_duration(String durationString){
        try {
            Integer.parseInt(durationString);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}