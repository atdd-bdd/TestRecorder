package com.kenpugh.testrecorder.ui;

import com.kenpugh.testrecorder.entities.TestFilter;

import javax.swing.*;
import java.awt.event.*;

public class TestFilterDialog extends JDialog {
    public TestFilter testFilter;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox includeActiveCheckBox;
    private JCheckBox includeInactiveCheckBox;
    private JCheckBox includeRetiredCheckBox;

    public TestFilterDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void main(String[] args) {
        TestFilterDialog dialog = new TestFilterDialog();
        dialog.testFilter = new TestFilter();
        dialog.initialize();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onOK() {
        // add your code here
        testFilter.includeActive = includeActiveCheckBox.isSelected();
        testFilter.includeInactive = includeInactiveCheckBox.isSelected();
        testFilter.includeRetired = includeRetiredCheckBox.isSelected();

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void initialize() {
        includeActiveCheckBox.setSelected(testFilter.includeActive);
        includeInactiveCheckBox.setSelected(testFilter.includeInactive);
        includeRetiredCheckBox.setSelected(testFilter.includeRetired);
    }
}
