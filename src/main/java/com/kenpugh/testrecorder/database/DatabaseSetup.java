package com.kenpugh.testrecorder.database;


import com.kenpugh.testrecorder.entities.MyConfiguration;
import com.kenpugh.testrecorder.log.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseSetup {

    public static Connection connection = null;
    public static boolean setupComplete = false;

    static public void setup() {

        String userid = MyConfiguration.databaseUserID;
        String password = MyConfiguration.databasePassword;
        String url = MyConfiguration.databaseURL;
        String jdbcDriver = MyConfiguration.databaseJDBCDriver;
        try {
            Class.forName(jdbcDriver);

        } catch (java.lang.ClassNotFoundException e) {
            Log.write(Log.Level.Severe, "Did not find driver class", e.getMessage());
            System.exit(1);
        }

        try {
            connection = DriverManager.getConnection(url, userid, password);
        } catch (SQLException ex) {
            Log.write(Log.Level.Severe, "SQLException: ", ex.getMessage() + " Setup");
        }
        if (connection == null) {
            Log.write(Log.Level.Severe, "Cannot make connection to database", url);
            System.exit(0);
        }
    }

    public static void setupTables() {
        try {

            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE TESTS (issueID CHAR(5), name CHAR(50), runner CHAR(20), lastResult CHAR(10), dateLastRun CHAR(30), " +
                    "datePreviousResult CHAR(30), filePath CHAR(200), comments VARCHAR(1000), subIssueID CHAR(5), testStatus CHAR(10) );");
            statement.execute("CREATE TABLE TEST_RUNS (issueID CHAR(5), runner CHAR(20), result CHAR(10), dateTime CHAR(30), " +
                    " comments VARCHAR(1000), subIssueID CHAR(5));");
            setupComplete = true;
        } catch (SQLException ex) {
            Log.write(Log.Level.Severe, "SQLException: ", ex.getMessage() + " Create Tables");
        }

    }

    public static void removeTables() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE TESTS;");
            statement.execute("DROP TABLE TEST_RUNS ;");
            setupComplete = true;
        } catch (SQLException ex) {
            Log.write(Log.Level.Severe, "SQLException: ", ex.getMessage() + " RemoveTables");
        }

    }

    static public void open() {
        if (connection == null || !setupComplete)
            setup();
    }
}




