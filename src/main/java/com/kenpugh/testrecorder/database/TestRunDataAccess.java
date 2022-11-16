package com.kenpugh.testrecorder.database;


import com.kenpugh.testrecorder.domainterms.IssueID;
import com.kenpugh.testrecorder.domainterms.SubIssueID;
import com.kenpugh.testrecorder.entities.TestRunDTO;
import com.kenpugh.testrecorder.log.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestRunDataAccess {

    public static boolean addTestRun(TestRunDTO aTestRun) {
        try {
            DatabaseSetup.open();
            Statement statement = DatabaseSetup.connection.createStatement();
            String s = "INSERT INTO TEST_RUNS VALUES ('" +
                    aTestRun.issueID + "', '" +
                    aTestRun.runner + "', '" +
                    aTestRun.result + "', '" +
                    aTestRun.dateTime + "', '" +
                    aTestRun.comments + "', '" +
                    aTestRun.subIssueID + "' );";
            statement.execute(s);
            return true;
        } catch (SQLException ex) {
            Log.write(Log.Level.Severe, "SQLException: ", ex.getMessage());
            return false;
        }
    }

    public static List<TestRunDTO> findByIssueID(IssueID anIssueID, SubIssueID aSubIssueID) {
        List<TestRunDTO> list = new ArrayList<>();
        String selectString = "select * from TEST_RUNS where IssueID = '"
                + anIssueID.toString() + "' " +
                " AND SubIssueID = '" + aSubIssueID.toString() + "';";
            Log.write(Log.Level.Debug, " statement is ", selectString);
        try {
            DatabaseSetup.open();
            Statement s = DatabaseSetup.connection.createStatement();
            ResultSet rs = s.executeQuery(selectString);
            while (rs.next()) {
                list.add(createTestRunDTO(rs));
            }
            s.close();

        } catch (SQLException ex) {
            Log.write(Log.Level.Severe, "SQLException: ", ex.getMessage());
        }

        return list;
    }

    public static List<TestRunDTO> getAll() {
        List<TestRunDTO> list = new ArrayList<>();
        String selectString = "select * from TEST_RUNS;";
        try {
            DatabaseSetup.open();
            Statement s = DatabaseSetup.connection.createStatement();
            ResultSet rs = s.executeQuery(selectString);
            while (rs.next()) {
                list.add(createTestRunDTO(rs));
            }
            s.close();
        } catch (SQLException ex) {
            Log.write(Log.Level.Severe, "SQLException: ", ex.getMessage());
        }
        return list;
    }

    private static TestRunDTO createTestRunDTO(ResultSet rs) throws SQLException {
        TestRunDTO TestRunDTO = new TestRunDTO();
        TestRunDTO.issueID = rs.getString(1).trim();
        TestRunDTO.runner = rs.getString(2).trim();
        TestRunDTO.result = rs.getString(3).trim();
        TestRunDTO.dateTime = rs.getString(4).trim();
        TestRunDTO.comments = rs.getString(5).trim();
        TestRunDTO.subIssueID = rs.getString(6).trim();
        return TestRunDTO;
    }

    public static void deleteAll() {
        try {
            DatabaseSetup.open();
            Statement statement = DatabaseSetup.connection.createStatement();
            statement.execute("DELETE FROM TEST_RUNS;");
        } catch (SQLException ex) {
            Log.write(Log.Level.Severe, "SQLException: ", ex.getMessage() + " RemoveTables");
        }

    }

}



