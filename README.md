# TestRecorder
Record manually run tests

This application is used to record the results of tests that are run manually.   It is also used to demonstrate BDD/ATDD, automation, and some coding practices.   A document that describes the details is in production.

In order to run the application, you will need access to a database.  If you do not have access, you can install hsqldb (http://hsqldb.org/).   The default configuration is set to use hsqlsb.   Start up the database prior to running the application.

There are two sets of tests - RunAutoTests and RunManualTests.   The manual tests bring up the UI.   The UI uses Swing.

Main scenarios for the application are in TestRecorder.feature.   The configuration settings allow for test doubles for the date/time and for the test runner (i.e. the user).    

If you are running this from an IDE:

1. Start up the database.   For hsqldb, use: 
       java -cp lib/hsqldb.jar org.hsqldb.Server
2. Run RunAutoTests 
    The first time you run it, there may be errors, as the sequence of test execution may cause the database create to take place after its first use.  Just run it again and the errors should go away. 
3. Run the RunManualTests
    For the first test, Run Test and enter the information shown in the console. This checks that the data is being updated correctly. 
    For the second test, try out any or all of the button options.   
    For the third test, you should see Run Test use your logon and the current date/time.    
    
 To run the program from the command line, alter this command line to use the location where the hsqldb jar is stored.   
    java -cp TestRecorder-0.9.9.jar;C:\Users\KenV1\.m2\repository\org\hsqldb\hsqldb\2.7.1\hsqldb-2.7.1.jar   
             com.kenpugh.testrecorder.ui.TestRecorder

  The application does not store the contents of a manual script test.  The testscript should be created and kept in the source code repository.  The path to that script is a combination of a filename and a path that is in configuration.txt.  You can change the configuration by changing rootFilePath where it appears in the TestRecorder.feature and UI.feature files.  
