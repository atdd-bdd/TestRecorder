package kenpugh.TestRecorder.DomainTerms;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

public class MyDateTime {
    private Date theDate;
    public static final String NEVER = "Never";

    public MyDateTime() {
        theDate = getDefaultDate();
    }

    public MyDateTime(Date date) {
        theDate = date;
    }

    static private Date getDefaultDate() {
        Date def;
        try {
            def = DateFormat.getDateTimeInstance().parse("Jan 1, 1970, 0:0:0 AM");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return def;
    }

    public MyDateTime(String value) {
        try {
            theDate = DateFormat.getDateTimeInstance().parse(value);
        } catch (ParseException e) {
            System.err.println("MyDateTime bad format in constructor " + value);
            theDate = getDefaultDate();
        }
    }

    static public MyDateTime parse(String value) {
        if (value.equals(NEVER) || value.isEmpty() || value.isBlank())
            return new MyDateTime();
        MyDateTime mdt;
        mdt = new MyDateTime();
        try {
            mdt.theDate = DateFormat.getDateTimeInstance().parse(value);
        } catch (ParseException e) {
            System.err.println("MyDateTime bad format in parse " + value);
            mdt.theDate = getDefaultDate();
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

    public String toStringWithNever() {
        if (theDate.equals(getDefaultDate())) {
            return NEVER;
        }
        return DateFormat.getDateTimeInstance().format(theDate);

    }
}
