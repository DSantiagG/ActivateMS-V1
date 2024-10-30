package com.activate.ActivateMSV1.infra.util;

import javax.swing.*;

public class GUIVerifier {

    /**
     * Method to print a message in a JOptionPane.
     * @param message Message to print.
     */
    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Method to validate if a JTextField is empty.
     * @param textField JTextField to validate.
     * @param message Message to print if the JTextField is empty.
     * @return True if the JTextField is empty, False otherwise.
     */
    public static boolean isTextFieldEmpty(JTextField textField, String message) {
        boolean result = false;
        if (textField.getText().isEmpty()) {
            showMessage(message);
            textField.requestFocus();
            result = true;
        }
        return result;
    }

    /**
     * Method to validate if a JTextField is numeric.
     * @param textField JTextField to validate.
     * @param message Message to print if the JTextField is not numeric.
     * @return True if the JTextField is not numeric, False otherwise.
     */
    public static boolean isTextFieldNotNumeric(JTextField textField, String message) {
        boolean result = false;
        try {
            Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            showMessage(message);
            textField.requestFocus();
            result = true;
        }
        return result;
    }

    /**
     * Method to validate if a JTextField is a positive number.
     * @param textField JTextField to validate.
     * @param message Message to print if the JTextField is not a positive number.
     * @return True if the JTextField is not a positive number, False otherwise.
     */
    public static boolean isTextFieldNotPositiveNumeric(JTextField textField, String message) {
        boolean result = false;
        try {
            int number = Integer.parseInt(textField.getText());
            if (number < 0) {
                showMessage(message);
                textField.requestFocus();
                result = true;
            }
        } catch (NumberFormatException e) {
            showMessage(message);
            textField.requestFocus();
            result = true;
        }
        return result;
    }

    /**
     * Method to validate if a JComboBox is selected.
     * @param comboBox JComboBox to validate.
     * @param message Message to print if the JComboBox is not selected.
     * @return True if the JComboBox is not selected, False otherwise.
     */
    public static boolean isComboBoxNotSelected(@SuppressWarnings("rawtypes") JComboBox comboBox, String message) {
        boolean result = false;
        if (comboBox.getItemCount() == 0 || comboBox.getSelectedIndex() == 0) {
            showMessage(message);
            comboBox.requestFocus();
            result = true;
        }
        return result;
    }

    /**
     * Method to get the current date.
     * @param format Date format.
     * @return Current date.
     */
    public static String getCurrentDate(String format) {
        return new java.text.SimpleDateFormat(format).format(new java.util.Date());
    }


    /**
     * Method to show an unexpected error message.
     */
    public static void showUnexpectedErrorMessage() {
        JOptionPane.showMessageDialog(null, "An unexpected error occurred. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}