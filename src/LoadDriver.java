import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import java.sql.*;
import java.util.Scanner;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class LoadDriver {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no/eivinklo_db-gr2";

    //  Database credentials
    static final String USER = "camillmd";
    static final String PASS = "password";
    static CreateWorkout workoutCreater;
    static ExerciseDiary exerciseDiary;
    static GetExercises getExercises;
    static CreateExercise createExercise;

    public static void main(String[] args) {


        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName(JDBC_DRIVER).newInstance();
        } catch (Exception ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }

        Connection conn = null;

        try {
            conn =
                    DriverManager.getConnection(DB_URL,
                            USER, PASS);

            //create objects for the different user cases

            // Do something with the Connection
            //Statement stmt = null;
            //ResultSet rs = null;



        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        String commands = "If you want to create a workout, write: new workout.\n" +
                "If you want to create a workout based on a saved workout, write: create from old workout.\n" +
                "If you want to read your excercise diary, write: read diary.\n" +
                "If you want to show all exercises, write: show exercises. \n" +
                "If you want to create an exercise, write: create exercises. \n" +
                "To see commands, write: commands.\n" +
                "To exit, write: exit.\n";

        System.out.println(commands);


        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while(!input.equals("exit")){
            if (input.equals("new workout")){
                workoutCreater = new CreateWorkout(conn, scanner);
                System.out.println(commands);
                input = scanner.nextLine();
            } else if (input.equals("create from old workout")){
                //create object for crating workout from mal
                System.out.println(commands);
                input = scanner.nextLine();
            } else if (input.equals("read diary")) {
                exerciseDiary = new ExerciseDiary(conn);
                System.out.println(commands);
                input = scanner.nextLine();
            } else if (input.equals("show exercises")) {
                getExercises = new GetExercises(conn);
                System.out.println(commands);
                input = scanner.nextLine();
            } else if (input.equals("create exercise")) {
                createExercise = new CreateExercise(conn, scanner);
                System.out.println(commands);
                input = scanner.nextLine();
            } else {
                System.out.println("ERROR: Could not understand the command. Try again.\n");
                System.out.println(commands);
                input = scanner.nextLine();
            }
        }


    }


}