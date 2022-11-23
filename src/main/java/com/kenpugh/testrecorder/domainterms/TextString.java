package com.kenpugh.testrecorder.domainterms;

import com.kenpugh.testrecorder.log.Log;

import java.util.Objects;
import java.util.regex.Pattern;

public class TextString {
        private final String value;

        public TextString() {
            value = "";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TextString myString = (TextString) o;
            return value.equals(myString.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return value;
        }

        static final Pattern patternAlmostAnyCharsOnly = Pattern.compile("[\\\\$]",
                Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

        public TextString(String value) {
            String newValue = patternAlmostAnyCharsOnly.matcher(value).replaceAll("");
            if (!newValue.equals(value))
                Log.write(Log.Level.Severe, " Replaced ", value + " with " + newValue);
            this.value = newValue;
        }
    }

