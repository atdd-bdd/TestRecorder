package kenpugh.TestRecorder.DomainTerms;

import kenpugh.TestRecorder.Log.Log;
import kenpugh.TestRecorder.Services.CurrentDateTimeService;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

public class MyDateTime {
    private Date theDate;
    public static final String NEVER_STRING = "Never";
    public static final String EARLIEST_STRING = "Earliest";

    public static final String STARTED_STRING = "Started";

    public static final String NOW_STRING = "Now";
    public static final MyDateTime NEVER_DATETIME;
    public static final MyDateTime EARLIEST_DATETIME;
    public static final MyDateTime DEFAULT_DATETIME;
    public static final MyDateTime STARTED_DATETIME;
    static {
        NEVER_DATETIME = new MyDateTime("Jan 1, 1970, 0:0:0 AM", true);
        EARLIEST_DATETIME = new MyDateTime("Jan 1, 1970, 0:0:1 AM", true);
        DEFAULT_DATETIME = new MyDateTime("Jan 1, 2021, 0:0:2 AM", true);
        STARTED_DATETIME = CurrentDateTimeService.getCurrentDateTime();
    }

    public MyDateTime() {
        theDate = NEVER_DATETIME.theDate;
    }
    @SuppressWarnings("SameParameterValue")
    private MyDateTime(String dateTime, boolean exit){
        // Use for static variables
        try {
            theDate = DateFormat.getDateTimeInstance().parse(dateTime);
        } catch (ParseException e) {
            Log.write(Log.Level.Severe, "Bad value in parse ", dateTime);
            if (exit) throw new RuntimeException(e);
        }
    }

    public MyDateTime(Date date) {
        theDate = date;
    }


    public MyDateTime(String value) {
        try {
            theDate = DateFormat.getDateTimeInstance().parse(value);
        } catch (ParseException e) {
            Log.write(Log.Level.Info, "MyDateTime bad format in constructor ", value);
            theDate = NEVER_DATETIME.theDate;
        }
    }

    public static String toDisplayString(String dateLastRun) {
        if (dateLastRun.equals(NEVER_DATETIME.toString()))
            return NEVER_STRING;
        return dateLastRun;
    }

    public int compareTo(MyDateTime other){
        return (this.theDate.compareTo(other.theDate));
    }
    static public MyDateTime parse(@NotNull String value) {
        if (value.equals(NEVER_STRING))
                return NEVER_DATETIME;
        if (value.equals(NOW_STRING))
                return CurrentDateTimeService.getCurrentDateTime();
        if (value.equals(EARLIEST_STRING))
                return EARLIEST_DATETIME;
        if (value.equals(STARTED_STRING))
                return STARTED_DATETIME;
        if (value.isEmpty() || value.isBlank())
            return DEFAULT_DATETIME;
        MyDateTime mdt;
        mdt = new MyDateTime();
        try {
            mdt.theDate = DateFormat.getDateTimeInstance().parse(value);
        } catch (ParseException e) {
            Log.write(Log.Level.Info, "MyDateTime bad format in parse ", value);
            mdt.theDate = NEVER_DATETIME.theDate;
        }
        return mdt;
        }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyDateTime that = (MyDateTime) o;
        return theDate.equals(that.theDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theDate);
    }

    @Override
    public String toString() {
        return DateFormat.getDateTimeInstance().format(theDate);
    }

    public String toStringWithSymbols() {
        if (theDate.equals(NEVER_DATETIME.theDate)) {
            return NEVER_STRING;
        }
        if (theDate.equals(STARTED_DATETIME.theDate)){
            return STARTED_STRING;
        }
        if (theDate.equals(EARLIEST_DATETIME.theDate))
            return EARLIEST_STRING;
        return DateFormat.getDateTimeInstance().format(theDate);

    }
}
