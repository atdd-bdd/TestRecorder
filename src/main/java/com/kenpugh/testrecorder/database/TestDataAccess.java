package com.kenpugh.testrecorder.database;


import com.kenpugh.testrecorder.domainterms.IssueID;
import com.kenpugh.testrecorder.domainterms.SubIssueID;
import com.kenpugh.testrecorder.entities.TestDTO;
import com.kenpugh.testrecorder.log.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestDataAccess {

    public static boolean addTest(TestDTO aTest) {
        IssueID issueID = new IssueID(aTest.issueID);
        SubIssueID subIssueID = new SubIssueID(aTest.subIssueID);
        if (findByIssueID(issueID, subIssueID) != TestDTO.NOT_FOUND) {
            Log.write(Log.Level.Info, "Adding duplicate issue id", issueID.toString());
            return false;
        }
        try {
            DatabaseSetup.open();
            Statement statement = DatabaseSetup.connection.createStatement();
            String s = "INSERT INTO TESTS VALUES ('" +
                    aTest.issueID + "', '" +
                    aTest.name + "', '" +
                    aTest.runner + "', '" +
                    aTest.lastResult + "', '" +
                    aTest.dateLastRun + "', '" +
                    aTest.datePreviousResult + "', '" +
                    aTest.filePath + "', '" +
                    aTest.comments + "', '" +
                    aTest.subIssueID + "', '" +
                    aTest.testStatus +
                    "' );";
            statement.execute(s);
            return true;
        } catch (SQLException ex) {
            Log.write(Log.Level.Debug, "SQLException: ", ex.getMessage());
            return false;
        }
    }

    public static TestDTO findByIssueID(IssueID anIssueID, SubIssueID aSubIssueID) {
        String selectString = "select * from TESTS where IssueID = '" + anIssueID.toString() +
                "' and SubIssueID = '" + aSubIssueID.toString() +
                "';";
        Log.write(Log.Level.Debug, " string is ", selectString);
        try {
            DatabaseSetup.open();
            Statement s = DatabaseSetup.connection.createStatement();
            ResultSet rs = s.executeQuery(selectString);
            if (rs.next()) {
                return createTestDTO(rs);
            }
            s.close();
            return TestDTO.NOT_FOUND;

        } catch (SQLException ex) {
            Log.write(Log.Level.Severe, "SQLException: ", ex.getMessage());
        }

        return TestDTO.NOT_FOUND;
    }

    public static List<TestDTO> getAll() {
        List<TestDTO> list = new ArrayList<>();
        String selectString = "select * from TestS;";
        try {
            DatabaseSetup.open();
            Statement s = DatabaseSetup.connection.createStatement();
            ResultSet rs = s.executeQuery(selectString);
            while (rs.next()) {
                list.add(createTestDTO(rs));
            }
            s.close();
        } catch (SQLException ex) {
            Log.write(Log.Level.Severe, "SQLException: ", ex.getMessage());
        }
        return list;
    }

    private static TestDTO createTestDTO(ResultSet rs) throws SQLException {
        TestDTO testDTO = new TestDTO();
        testDTO.issueID = rs.getString(1).trim();
        testDTO.name = rs.getString(2).trim();
        testDTO.runner = rs.getString(3).trim();
        testDTO.lastResult = rs.getString(4).trim();
        testDTO.dateLastRun = rs.getString(5).trim();
        testDTO.datePreviousResult = rs.getString(6).trim();
        testDTO.filePath = rs.getString(7).trim();
        testDTO.comments = rs.getString(8).trim();
        testDTO.subIssueID = rs.getString(9).trim();
        testDTO.testStatus = rs.getString(10).trim();
        return testDTO;

    }

    public static boolean update(TestDTO testDTO) {
        try {
            DatabaseSetup.open();
            Statement statement = DatabaseSetup.connection.createStatement();
            String s = "UPDATE TESTS SET " +
                    "name='" + testDTO.name + "'," +
                    "runner='" + testDTO.runner + "'," +
                    "lastResult='" + testDTO.lastResult + "'," +
                    "dateLastRun='" + testDTO.dateLastRun + "'," +
                    "datePreviousResult='" + testDTO.datePreviousResult + "'," +
                    "filePath='" + testDTO.filePath + "'," +
                    "comments='" + testDTO.comments + "', " +
                    "subIssueID='" + testDTO.subIssueID + "', " +
                    "testStatus='" + testDTO.testStatus + "' " +
                    "WHERE issueID='" + testDTO.issueID + "'" +
                    "AND subIssueID='" + testDTO.subIssueID + "';";
            Log.write(Log.Level.Debug, " statement is ", s);
            statement.execute(s);
            return true;
        } catch (SQLException ex) {
            Log.write(Log.Level.Severe, "SQLException: ", ex.getMessage() + " Update");
            return false;
        }

    }

    public static void deleteAll() {
        try {
            DatabaseSetup.open();
            Statement statement = DatabaseSetup.connection.createStatement();
            statement.execute("DELETE FROM TESTS;");
        } catch (SQLException ex) {
            Log.write(Log.Level.Severe, "SQLException: ", ex.getMessage() + " RemoveTables");
        }

    }

}



