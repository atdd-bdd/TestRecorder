package com.kenpugh.testrecorder.ui;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.util.prefs.Preferences;

public class UIHelpers {
    static final String programName = "TestRecorder";

    public static void setColumnWidths(JTable jTable, int[] columnWidths) {
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        for (int i = 0; i < jTable.getColumnCount() - 1; i++) {
            TableColumn column = jTable.getColumnModel().getColumn(i);
            column.setWidth(columnWidths[i]);
            column.setPreferredWidth(columnWidths[i]);
        }
        jTable.invalidate();
        TestRecorder.frame.invalidate();
        TestRecorder.frame.validate();
        TestRecorder.frame.repaint();
    }

    public static int[] getColumnWidths(JTable jTable) {
        int columnCount = jTable.getColumnCount();
        int[] columnWidths = new int[columnCount];
        for (int i = 0; i < columnCount; i++) {
            int width = jTable.getColumnModel().getColumn(i).getWidth();
            columnWidths[i] = width;
        }
        return columnWidths;
    }


    public static void storeColumnWidthsIntoPreferences(int[] columnWidths, String identifier) {
        System.out.println(" Saving columns for " + identifier);
        Preferences preferences;
        preferences = Preferences.userRoot().node(programName);
        for (int i = 0; i < columnWidths.length; i++) {
            String variable = identifier + i;
            preferences.putInt(variable, columnWidths[i]);
        }
    }

    public static int[] loadColumnWidthsFromPreferences(JTable jTable, String identifier) {
        System.out.println(" Loading columns for " + identifier);
        int columnCount = jTable.getColumnCount();
        int[] columnWidths = new int[columnCount];
        Preferences preferences;
        preferences = Preferences.userRoot().node(programName);
        for (int i = 0; i < columnWidths.length; i++) {
            String variable = identifier + i;
            columnWidths[i] = preferences.getInt(variable, 75);
        }
        return columnWidths;
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

}
