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
    static CreateTemplate createTemplate;

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

        String commands = "If you want to save a new workout, type: \"new workout\".\n" +
                "If you want to create a new template workout from a saved workout, type: \"new template\".\n" +
                "If you want to read your workout diary, type: \"read diary\".\n" +
                "If you want to show all exercises, type: \"show exercises\". \n" +
                "If you want to create a new exercise, type: \"create exercise\". \n" +
                "To see commands, type: \"commands\".\n" +
                "To exit, type: \"exit\".\n";



        Scanner scanner = new Scanner(System.in);
        String input = "";
        while(!input.equals("exit")){
            System.out.println(commands);
            System.out.print("Insert command: ");
            input = scanner.nextLine();

            if (input.equals("new workout")){
                System.out.println();
                workoutCreater = new CreateWorkout(conn, scanner);
            } else if (input.equals("new template")){
                System.out.println();
                createTemplate = new CreateTemplate(conn, scanner);
            } else if (input.equals("read diary")) {
                System.out.println();
                exerciseDiary = new ExerciseDiary(conn);
            } else if (input.equals("show exercises")) {
                System.out.println();
                getExercises = new GetExercises(conn);
            } else if (input.equals("create exercise")) {
                System.out.println();
                createExercise = new CreateExercise(conn, scanner);
            } else if (!input.equals("commands")) {
                System.out.println("\nERROR: Could not understand the command. Try again.");
            }
            System.out.println("\n");
        }


    }


}