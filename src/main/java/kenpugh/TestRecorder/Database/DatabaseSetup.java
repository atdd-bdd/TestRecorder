package kenpugh.TestRecorder.Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseSetup {

    public static Connection connection = null;
    public static boolean setupComplete = false;

    static public void setup() {

        String userid = "SA";
        String password = "";
        String url = "jdbc:hsqldb:hsql://localhost";
        String jdbcDriver = "org.hsqldb.jdbcDriver";
        try {
            Class.forName(jdbcDriver);

        } catch (java.lang.ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.err.println("Did not find driver class");
            System.exit(1);
        }

        try {
            connection = DriverManager.getConnection(url, userid, password);
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage() + " Setup");
        }
        if (connection == null) {
            System.err.println("Cannot make connection to database");
            System.exit(0);
        }
    }

    public static void setupTables() {
        try {

            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE TESTS (issueID CHAR(5), name CHAR(50), runner CHAR(20), lastResult CHAR(10), dateLastRun CHAR(30), " +
                    "datePreviousResult CHAR(30), filePath CHAR(200), comments VARCHAR(1000));");
            setupComplete = true;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage() + " Create Tables");
        }

    }

    public static void removeTables() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE TESTS;");
            //   statement.execute("DROP TABLE TEST_RUNS ;");
            setupComplete = true;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage() + " RemoveTables");
        }

    }

    static public void open() {
        if (connection == null || !setupComplete)
            setup();
    }
}




