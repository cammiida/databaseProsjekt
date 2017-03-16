import java.sql.*;
import java.sql.Date;
import java.util.*;


class CreateTemplate {
    Statement stmt = null;
    ResultSet rs = null;

    private Integer workoutID;
    private ArrayList<Integer> workoutIDs = new ArrayList<Integer>();
    private String templateName;

    Date workoutDate;
    Time workoutTime;
    Time duration;
    String personalShape;
    String notes;
    String workoutDateString;
    String workoutTimeString;
    String durationString;
    java.sql.Date sqldate;
    java.sql.Time sqltime;


    CreateTemplate(Connection connection, Scanner scanner){

        try {
            stmt = connection.createStatement();

            //present all logged exercises (number + date + time)
            System.out.println("This is a list of all your previous workouts:");
            rs = stmt.executeQuery("SELECT ID, Dato, Tidspunkt FROM Treningsokt");

            while(rs.next()) {
                System.out.print("ID: " + rs.getString(1) + ", Date: " + rs.getString(2) + ", Time: " + rs.getString(3));
                workoutIDs.add(Integer.valueOf(rs.getString(1)));
                System.out.println();
            }

            //allow user to choose exercise
            System.out.print("\nPick an exercise to base your template off of: ");
            workoutID = Integer.valueOf(scanner.nextLine());
            while(!IsValidWorkoutID(workoutID)) {
                System.out.println("The ID is not valid. Try again.\n");
                System.out.print("Pick an exercise to base your template off of: ");
                workoutID = Integer.valueOf(scanner.nextLine());
            }

            //display exercise info (not implemented)


            //pick/don't pick the currently displayed exercise, and allow user to make a new selection (not implemented)


            //name the new template
            System.out.print("\n\nInsert a unique name for this template: ");
            templateName = scanner.nextLine();
            while(!IsValidTemplateName(templateName)) {
                System.out.println("The name is not valid. Try again.\n");
                System.out.print("Insert a unique name for this template: ");
                templateName = scanner.nextLine();
            }

            //execute SQL for creating template
            String insertTableSQL = "INSERT INTO Mal"
                    + "(Navn, OktID) VALUES"
                    + "(?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, templateName);
            preparedStatement.setInt(2, workoutID);
            preparedStatement.executeUpdate();

            System.out.println("\n\nLogged succesfully!");
            System.out.println("Template name: " + templateName);
            System.out.println("Workout ID: " + workoutID);
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

    private boolean IsValidWorkoutID(Integer workoutID) {
        for(Integer ID : workoutIDs) {
            if(workoutID.equals(ID)) {
                return true;
            }
        }
        return false;
    }

    private boolean IsValidTemplateName(String templateName) {
        return templateName.length() < 31;
    }
}