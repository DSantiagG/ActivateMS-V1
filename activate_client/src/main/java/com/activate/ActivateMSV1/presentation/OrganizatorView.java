package com.activate.ActivateMSV1.presentation;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OrganizatorView {


    private JPanel OrganizatorPanel;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JTextArea textArea1;
    private JButton button6;
    private JButton button7;

    private JFrame loginFrame;
    private JFrame frame;
    public OrganizatorView(JFrame loginFrame) {

        this.loginFrame = loginFrame;

        frame = new JFrame("Organizator");
        frame.setContentPane(OrganizatorPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public void show() {
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loginFrame.setVisible(true);
            }
        });
    }
}
