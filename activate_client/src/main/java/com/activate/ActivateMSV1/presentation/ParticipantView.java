package com.activate.ActivateMSV1.presentation;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ParticipantView {
    private JPanel ParticipantPanel;
    private JTabbedPane tbpParticipant;
    private JPanel pnlEvents;
    private JPanel pnlParticipanEvents;
    private JPanel pnlParticipantInfo;
    private JTextArea textArea1;
    private JComboBox comboBox1;
    private JButton button2;
    private JTextArea textArea2;
    private JButton button3;
    private JComboBox comboBox2;
    private JButton button1;
    private JComboBox comboBox3;
    private JTextArea textArea3;
    private JButton button4;
    private JTextField textField1;
    private JButton button5;
    private JButton button6;
    private JComboBox comboBox4;
    private JTextArea textArea4;
    private JButton button7;
    private JButton button8;

    private JFrame loginFrame;
    private JFrame frame;

    public ParticipantView(JFrame loginFrame) {
        this.loginFrame = loginFrame;

        frame = new JFrame("Participant");
        frame.setContentPane(ParticipantPanel);
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
