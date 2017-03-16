import com.mysql.jdbc.*;

import java.sql.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
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
            while (true) {
                stmt = connection.createStatement();

                //present all logged exercises (number + date + time)
                System.out.println("This is a list of all of your previous workouts:");
                rs = stmt.executeQuery("SELECT ID, Dato, Tidspunkt FROM Treningsokt");

                while (rs.next()) {
                    System.out.print("ID: " + rs.getString(1) + ", Date: " + rs.getString(2) + ", Time: " + rs.getString(3));
                    workoutIDs.add(Integer.valueOf(rs.getString(1)));
                    System.out.println();
                }

                //allow user to choose exercise
                System.out.print("\nPick an exercise to base your template off of: ");
                workoutID = Integer.valueOf(scanner.nextLine());
                while (!IsValidWorkoutID(workoutID)) {
                    System.out.println("The ID is not valid. Try again.\n");
                    System.out.print("Pick an exercise to base your template off of: ");
                    workoutID = Integer.valueOf(scanner.nextLine());
                }

                System.out.print("\n\n");

                //display exercise info
                String selectInfoSQL = "SELECT R.OvelseNavn, IA.OktID, UA.OktID, SO.Belastning, "
                        + "SO.Antall_repetisjoner, SO.Antall_sett, UO.Lengde, UO.Varighet "
                        + "FROM Treningsokt T "
                        + "LEFT OUTER JOIN Innendorsaktivitet IA ON T.ID = IA.OktID "
                        + "LEFT OUTER JOIN Utendorsaktivitet UA ON T.ID = UA.OktID "
                        //the following two joins may be changed to LEFT OUTER JOIN in order to circumvent
                        //problems with lack of information in database (due to non-implemented restrictions)
                        + "JOIN Resultat R ON T.ID = R.OktID "
                        + "JOIN Ovelse O ON R.OvelseNavn = O.Navn "
                        + "LEFT OUTER JOIN Styrkeovelse SO ON O.Navn = SO.OvelseNavn "
                        + "LEFT OUTER JOIN Utholdenhetsovelse UO ON O.Navn = UO.OvelseNavn "
                        + "WHERE T.ID = (?)";

                PreparedStatement selectWorkoutInfoStatement = connection.prepareStatement(selectInfoSQL);
                selectWorkoutInfoStatement.setInt(1, workoutID);

                rs = selectWorkoutInfoStatement.executeQuery();

                //do print if value not null (because of ISAs). If specific values (e.g. IA.ID) are not null, print also
                //table name
                ResultSetMetaData rsmd = rs.getMetaData();
                int numColumns = rsmd.getColumnCount();

                //if the resultset is not empty (that is, the rest of the program does what it should), then iterate
                //through the columns and print relevant info
                if (rs.next()) {
                    System.out.println("You have chosen the following exercise:\n");

                    for (int i = 1; i <= numColumns; i++) {
                        String columnValue = rs.getString(i);
                        String columnName = rsmd.getColumnName(i);
                        String tableName = rsmd.getTableName(i);

                        if(columnValue == null) {
                            continue;
                        }
                        else if (tableName.equals("Innendorsaktivitet") && columnName.equals("OktID")) {
                            System.out.println("Type aktivitet: Innendorsaktivitet");
                            continue;
                        }
                        else if (tableName.equals("Utendorsaktivitet") && columnName.equals("OktID")) {
                            System.out.println("Type aktivitet: Utendorsaktivitet");
                            continue;
                        }

                        System.out.println(columnName + ": " + columnValue);
                    }
                }
                //will trigger if the information required for join operations does not exist
                //(for example, there is no row in "Resultat" with the corresponding
                else {
                    System.out.println("Unfortunately, this info is unavailable.");
                }

                //pick/don't pick the currently displayed exercise, and allow user to make a new selection (not implemented)
                System.out.print("\nDo you wish do create a new template with these specifications (y/n)? ");
                String token = scanner.nextLine();
                while (!(token.equals("y") || token.equals("n"))) {
                    System.out.println("You must answer \"y\" or \"n\". Try again.\n");
                    System.out.print("Do you wish do create a new template with these specifications? ");
                    token = scanner.nextLine();
                }

                //if user answers "y", move on, else do two line breaks and start over
                if (token.equals("y")) {
                    break;
                }

                System.out.print("\n\n");
            }

            //name the new template
            System.out.print("\n\nInsert a unique name for this template: ");
            templateName = scanner.nextLine();
            while (!IsValidTemplateName(templateName)) {
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
        for (Integer ID : workoutIDs) {
            if (workoutID.equals(ID)) {
                return true;
            }
        }
        return false;
    }

    private boolean IsValidTemplateName(String templateName) {
        return templateName.length() < 31;
    }
}