package kenpugh.TestRecorder.Database;


import kenpugh.TestRecorder.DomainTerms.IssueID;
import kenpugh.TestRecorder.Entities.TestRunDTO;
import kenpugh.TestRecorder.Log.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static kenpugh.TestRecorder.Database.DatabaseSetup.connection;

public class TestRunDataAccess {

    public static boolean addTestRun(TestRunDTO aTestRun) {
        try {
            DatabaseSetup.open();
            Statement statement = connection.createStatement();
            String s = "INSERT INTO TEST_RUNS VALUES ('" +
                    aTestRun.issueID + "', '" +
                    aTestRun.runner + "', '" +
                    aTestRun.result + "', '" +
                    aTestRun.dateTime + "', '" +
                    aTestRun.comments + "' );";
            statement.execute(s);
            return true;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return false;
        }
    }

    public static List<TestRunDTO> findByIssueID(IssueID anIssueID) {
        List<TestRunDTO> list = new ArrayList<>();
        String selectString = "select * from TEST_RUNS where IssueID = '" + anIssueID.toString() + "';";
        try {
            DatabaseSetup.open();
            Statement s = connection.createStatement();
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
            Statement s = connection.createStatement();
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
        return TestRunDTO;
    }

    public static void deleteAll() {
        try {
            DatabaseSetup.open();
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM TEST_RUNS;");
        } catch (SQLException ex) {
            Log.write(Log.Level.Severe, "SQLException: ", ex.getMessage() + " RemoveTables");
        }

    }

}



