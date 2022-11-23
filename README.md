# TestRecorder
Record manually run tests

This application is used to record the results of tests that are run manually.   It is also used to demonstrate BDD/ATDD, automation, and some coding practices.   A document that describes the details is in production.

In order to run the application, you will need access to a database.  If you do not have access, you can install hsqldb (http://hsqldb.org/).   The default configuration is set to use hsqlsb.   Start up the database prior to running the application.

There are two sets of tests - RunAutoTests and RunManualTests.   The manual tests bring up the UI.   The UI uses Swing.

Main scenarios for the application  are in TestRecorder.feature.   The configuration settings allow for test doubles for the date/time and for the test runner (i.e. the user).    
