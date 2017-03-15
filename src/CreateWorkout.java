
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
            while(!check_workout_date(workoutTimeString)){
                System.out.println("The time is not valid. Try again.\n");
                workoutTimeString = scanner.nextLine();
            }

            //ask for and check the duration
            System.out.println("How long should the workout last (in minutes)?");
            durationInt = Integer.parseInt(scanner.nextLine());




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


    // date here is a string of format yyyy-MM-dd
    java.util.Date date_1 = df.parse(date) ;
    java.sql.Date sqldate = new java.sql.Date(date_1.getTime());
    sql = "select * from fgs_stock_report where Report_date = ? ";

    PreparedStatement two = con.prepareStatement(sql);
two.setDate(1,sqldate);ResultSet rs ;
    rs = two.executeQuery(sql) ;


}