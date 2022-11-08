package kenpugh.TestRecorder.Database;


import java.sql.*;


public class DatabaseSetup {

    public static Connection connection = null;
    public static boolean setupComplete = false;
    static public void setup()
    {
/*

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver" );
        } catch (Exception e) {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

*/

        String userid="SA";
        String password = "";
        String url = "jdbc:hsqldb:hsql://localhost";
        String jdbcDriver = "org.hsqldb.jdbcDriver";
        try {
            Class.forName(jdbcDriver);

        } catch(java.lang.ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        try {
            connection = DriverManager.getConnection(url, userid, password);
        } catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage() + " Setup");
        }

    }
    public static void setupTables()
    {
        try {

            Statement statement = connection.createStatement();
            System.out.println(" Creating first one");
            statement.execute("CREATE TABLE TESTS (issueID CHAR(5), name CHAR(50), runner CHAR(20), lastResult CHAR(10), dateLastRun CHAR(30), " +
                    "datePreviousResult CHAR(30), filePath CHAR(200), comments VARCHAR(1000));");
 /*         System.out.println("Creating second one");
            statement.execute("CREATE TABLE TESTRUNS (issueID CHAR(5), dateTime CHAR(20), runner CHAR(20), testResult CHAR(10));") ;
 */
            setupComplete= true;
        }
        catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage() + " Create Tables");
        }

    }
    public static void removeTables()
    {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE TESTS;");
          //   statement.execute("DROP TABLE TESTRUNS ;");
            setupComplete= true;
        }
        catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage() + " RemoveTables");
        }

    }
    static public void open()
    {
        if (connection == null)
            setup();
    }
}




